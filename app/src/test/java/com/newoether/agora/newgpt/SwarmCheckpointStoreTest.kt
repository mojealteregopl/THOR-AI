package com.newoether.agora.newgpt

import java.io.File
import java.nio.file.Files
import javax.crypto.spec.SecretKeySpec
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class SwarmCheckpointStoreTest {
    private fun key(value: Byte) = SecretKeySpec(ByteArray(32) { value }, "AES")
    private fun fingerprint() = ByteArray(32) { it.toByte() }
    private fun record(text: String) = SwarmRecord("lead", "Koordynator", 0, text)

    @Test fun encryptsAndRestoresAcrossInstances() = runBlocking {
        val directory = Files.createTempDirectory("thor-state").toFile()
        try {
            val store = FileSwarmCheckpointStore(directory, key(3))
            store.write(fingerprint(), listOf(record("sample-private-output")))
            val binary = File(directory, FileSwarmCheckpointStore.CHECKPOINT_FILE_NAME).readBytes()
            assertFalse(String(binary, Charsets.ISO_8859_1).contains("sample-private-output"))
            assertEquals(listOf(record("sample-private-output")),
                FileSwarmCheckpointStore(directory, key(3)).read(fingerprint()))
            assertTrue(store.read(fingerprint().also { it[0] = 99 }).isEmpty())
            store.delete()
            assertFalse(File(directory, FileSwarmCheckpointStore.CHECKPOINT_FILE_NAME).exists())
        } finally {
            directory.deleteRecursively()
        }
    }

    @Test fun rejectsModifiedOrUnrecognizedSnapshot() = runBlocking {
        val directory = Files.createTempDirectory("thor-state").toFile()
        try {
            val store = FileSwarmCheckpointStore(directory, key(3))
            store.write(fingerprint(), listOf(record("original")))
            try {
                FileSwarmCheckpointStore(directory, key(7)).read(fingerprint())
                fail("A different key must not read the snapshot")
            } catch (_: java.io.IOException) { }
            val file = File(directory, FileSwarmCheckpointStore.CHECKPOINT_FILE_NAME)
            val bytes = file.readBytes()
            bytes[bytes.lastIndex] = (bytes.last().toInt() xor 1).toByte()
            file.writeBytes(bytes)
            try {
                store.read(fingerprint())
                fail("Invalid snapshot must not be replayed")
            } catch (_: java.io.IOException) { }
        } finally {
            directory.deleteRecursively()
        }
    }

    @Test fun rejectsOversizedOutput() = runBlocking {
        val directory = Files.createTempDirectory("thor-state").toFile()
        try {
            val store = FileSwarmCheckpointStore(directory, key(3))
            try {
                store.write(fingerprint(), listOf(record("a".repeat(12_001))))
                fail("Expected output limit")
            } catch (_: IllegalArgumentException) { }
        } finally {
            directory.deleteRecursively()
        }
    }
}
