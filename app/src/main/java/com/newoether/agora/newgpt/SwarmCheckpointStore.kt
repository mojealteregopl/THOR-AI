package com.newoether.agora.newgpt

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.SecretKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

/**
 * Exactly one local GGUF swarm execution; never grants tool execution or replays side effects.
 * Checkpoints contain completed inference results only, not tools, permissions or raw prompts.
 */
interface SwarmCheckpointStore {
    suspend fun read(fingerprint: ByteArray): List<SwarmRecord>
    suspend fun write(fingerprint: ByteArray, records: List<SwarmRecord>)
    suspend fun delete()
}

/** Binds progress to task, agent configuration, parent links and model-slot allocation. */
object SwarmExecutionFingerprint {
    fun digest(task: String, steps: List<SwarmStep>): ByteArray {
        val bytes = ByteArrayOutputStream()
        DataOutputStream(bytes).use { stream ->
            stream.writeUTF("THOR_SWARM_EXECUTION_V1")
            stream.writeUTF(task)
            stream.writeInt(steps.size)
            for (step in steps) {
                stream.writeUTF(step.agent.id)
                stream.writeUTF(step.agent.name)
                stream.writeUTF(step.agent.systemPrompt)
                stream.writeUTF(step.parentId.orEmpty())
                stream.writeInt(step.modelSlot)
                stream.writeInt(step.agent.handoffs.size)
                step.agent.handoffs.forEach(stream::writeUTF)
            }
        }
        return MessageDigest.getInstance("SHA-256").digest(bytes.toByteArray())
    }
}

/**
 * AES-GCM authenticated, atomic, bounded checkpoint for Android application-private storage.
 * The caller supplies a durable non-exportable Android Keystore AES key.
 *
 * This is a single-device recovery journal; it is NOT distributed WAL, BFT or time travel.
 * Files are never accepted after authentication fails, and no permissions are persisted.
 */
class FileSwarmCheckpointStore(
    private val directory: File,
    private val secretKey: SecretKey,
) : SwarmCheckpointStore {
    companion object {
        const val CHECKPOINT_FILE_NAME = "thor-swarm-checkpoint.v1"
        private const val MAGIC = "THORCP01"
        private const val IV_BYTES = 12
        private const val TAG_BITS = 128
        private const val MAX_FILE_BYTES = 256 * 1024
        private const val MAX_RECORDS = 32
    }

    private val lock = Mutex()
    private val checkpoint: File get() = File(directory, CHECKPOINT_FILE_NAME)

    override suspend fun read(fingerprint: ByteArray): List<SwarmRecord> =
        withContext(Dispatchers.IO) {
            lock.withLock {
                require(fingerprint.size == 32) { "Invalid execution fingerprint" }
                val file = checkpoint
                if (!file.exists()) return@withLock emptyList()
                try {
                    require(file.isFile && file.length() in 36L..MAX_FILE_BYTES.toLong()) {
                        "Invalid checkpoint file size"
                    }
                    val content = file.readBytes()
                    val prefix = MAGIC.toByteArray(Charsets.US_ASCII)
                    require(content.copyOfRange(0, prefix.size).contentEquals(prefix)) {
                        "Unsupported checkpoint format"
                    }
                    val iv = content.copyOfRange(prefix.size, prefix.size + IV_BYTES)
                    val encrypted = content.copyOfRange(prefix.size + IV_BYTES, content.size)
                    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
                    cipher.init(
                        Cipher.DECRYPT_MODE,
                        secretKey,
                        javax.crypto.spec.GCMParameterSpec(TAG_BITS, iv),
                    )
                    cipher.updateAAD(prefix)
                    val plain = cipher.doFinal(encrypted)
                    require(plain.size <= MAX_FILE_BYTES) { "Checkpoint payload too large" }
                    val decoded = DataInputStream(ByteArrayInputStream(plain)).use { input ->
                        require(input.readInt() == 1) { "Unsupported checkpoint schema" }
                        val actualFingerprint = ByteArray(32)
                        input.readFully(actualFingerprint)
                        val count = input.readInt()
                        require(count in 0..MAX_RECORDS) { "Invalid checkpoint record count" }
                        val records = (0 until count).map {
                            SwarmRecord(
                                agentId = input.readUTF(),
                                agentName = input.readUTF(),
                                modelSlot = input.readInt(),
                                output = input.readUTF(),
                            )
                        }
                        require(input.available() == 0) { "Checkpoint has trailing data" }
                        require(records.all(::isValidRecord)) { "Malformed checkpoint record" }
                        actualFingerprint to records
                    }
                    if (MessageDigest.isEqual(decoded.first, fingerprint)) {
                        decoded.second
                    } else {
                        emptyList()
                    }
                } catch (error: Exception) {
                    if (error is kotlinx.coroutines.CancellationException) throw error
                    throw IOException("Cannot authenticate or restore THOR checkpoint", error)
                }
            }
        }

    override suspend fun write(fingerprint: ByteArray, records: List<SwarmRecord>) =
        withContext(Dispatchers.IO) {
            lock.withLock {
                require(fingerprint.size == 32) { "Invalid execution fingerprint" }
                require(records.size in 1..MAX_RECORDS) { "Invalid checkpoint size" }
                require(records.all(::isValidRecord)) { "Checkpoint contains invalid record" }
                val plain = ByteArrayOutputStream()
                DataOutputStream(plain).use { out ->
                    out.writeInt(1)
                    out.write(fingerprint)
                    out.writeInt(records.size)
                    for (record in records) {
                        out.writeUTF(record.agentId)
                        out.writeUTF(record.agentName)
                        out.writeInt(record.modelSlot)
                        out.writeUTF(record.output)
                    }
                }
                require(plain.size() <= MAX_FILE_BYTES / 2) { "Checkpoint too large" }
                val cipher = Cipher.getInstance("AES/GCM/NoPadding")
                cipher.init(Cipher.ENCRYPT_MODE, secretKey)
                val iv = cipher.iv
                require(iv.size == IV_BYTES) { "Unsupported encryption IV length" }
                val prefix = MAGIC.toByteArray(Charsets.US_ASCII)
                cipher.updateAAD(prefix)
                val encrypted = cipher.doFinal(plain.toByteArray())
                require(prefix.size + iv.size + encrypted.size <= MAX_FILE_BYTES) {
                    "Checkpoint exceeds size limit"
                }

                check(directory.isDirectory || directory.mkdirs()) {
                    "Cannot create private checkpoint directory"
                }
                val temporary = File.createTempFile("thor-cp-", ".tmp", directory)
                try {
                    java.io.FileOutputStream(temporary).use { stream ->
                        stream.write(prefix)
                        stream.write(iv)
                        stream.write(encrypted)
                        stream.fd.sync()
                    }
                    Files.move(
                        temporary.toPath(),
                        checkpoint.toPath(),
                        StandardCopyOption.ATOMIC_MOVE,
                        StandardCopyOption.REPLACE_EXISTING,
                    )
                } finally {
                    if (temporary.exists()) temporary.delete()
                }
                Unit
            }
        }

    override suspend fun delete() = withContext(Dispatchers.IO) {
        lock.withLock {
            if (checkpoint.exists() && !checkpoint.delete()) {
                throw IOException("Cannot erase THOR checkpoint")
            }
        }
    }

    private fun isValidRecord(record: SwarmRecord): Boolean =
        record.agentId.isNotBlank() && record.agentId.length <= 256 &&
            record.agentName.isNotBlank() && record.agentName.length <= 80 &&
            record.modelSlot in 0..1 &&
            record.output.isNotBlank() && record.output.length <= 12_000
}
