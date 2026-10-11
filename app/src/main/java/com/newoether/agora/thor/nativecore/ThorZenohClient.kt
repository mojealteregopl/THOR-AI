package com.newoether.agora.thor.nativecore

import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

/**
 * Scoped, worker-thread-only adapter to THOR's Rust Zenoh TLS client.
 * No permissions are inherited from MCP, agents or the Android Accessibility service.
 * Native binaries must be built and packaged explicitly; a missing library fails closed.
 */
class ThorZenohClient private constructor(private val handle: Long) {
    private val closed = AtomicBoolean(false)

    companion object {
        private const val MAX_PAYLOAD_BYTES = 1_048_576

        suspend fun connect(
            endpoint: String,
            trustedCaFile: String,
            clientCertificateFile: String,
            clientPrivateKeyFile: String,
        ): ThorZenohClient = withContext(Dispatchers.IO) {
            require(endpoint.startsWith("tls/")) { "THOR native transport requires TLS" }
            require(
                trustedCaFile.isNotBlank() &&
                    clientCertificateFile.isNotBlank() &&
                    clientPrivateKeyFile.isNotBlank()
            ) { "THOR TLS certificate and key paths are mandatory" }

            val id = ThorNativeCoreBridge.nativeOpen(
                endpoint,
                trustedCaFile,
                clientCertificateFile,
                clientPrivateKeyFile,
            )
            if (id <= 0L) throw IOException("THOR secure session was rejected")
            ThorZenohClient(id)
        }

        suspend fun metrics(): NativeTransportMetrics = withContext(Dispatchers.IO) {
            val payload = ThorNativeCoreBridge.nativeCounters()
                ?: throw IOException("THOR native transport metrics unavailable")
            val json = JSONObject(String(payload, Charsets.UTF_8))
            NativeTransportMetrics(
                sentMessages = json.getLong("sent"),
                receivedMessages = json.getLong("received"),
                droppedMessages = json.getLong("dropped"),
            )
        }
    }

    suspend fun publish(key: String, payload: ByteArray) = withContext(Dispatchers.IO) {
        require(key.startsWith("thor/") && '*' !in key) {
            "Publication requires an exact THOR namespace key"
        }
        require(payload.size in 1..MAX_PAYLOAD_BYTES) {
            "Payload exceeds THOR native transport limits"
        }
        check(!closed.get()) { "THOR session was closed" }
        if (!ThorNativeCoreBridge.nativePublish(handle, key, payload)) {
            throw IOException("THOR native publication was rejected")
        }
    }

    suspend fun subscribe(key: String): ThorZenohSubscription = withContext(Dispatchers.IO) {
        require(key.startsWith("thor/")) { "Subscription requires THOR namespace" }
        check(!closed.get()) { "THOR session was closed" }
        val subscription = ThorNativeCoreBridge.nativeSubscribe(handle, key)
        if (subscription <= 0L) throw IOException("THOR subscription was rejected")
        ThorZenohSubscription(subscription)
    }

    suspend fun disconnect() = withContext(Dispatchers.IO) {
        if (closed.compareAndSet(false, true)) {
            ThorNativeCoreBridge.nativeClose(handle)
        }
    }
}

class ThorZenohSubscription internal constructor(private val handle: Long) {
    private val closed = AtomicBoolean(false)

    suspend fun poll(timeoutMs: Int = 250): ByteArray? = withContext(Dispatchers.IO) {
        require(timeoutMs in 0..1_000) { "Poll timeout outside the allowed range" }
        check(!closed.get()) { "THOR subscription was closed" }
        ThorNativeCoreBridge.nativePoll(handle, timeoutMs)
    }

    suspend fun unsubscribe() = withContext(Dispatchers.IO) {
        if (closed.compareAndSet(false, true)) {
            ThorNativeCoreBridge.nativeUnsubscribe(handle)
        }
    }
}

data class NativeTransportMetrics(
    val sentMessages: Long,
    val receivedMessages: Long,
    val droppedMessages: Long,
)
