package com.newoether.agora.newgpt

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

/**
 * Device-local non-exportable key. Missing or invalidated Android Keystore material fails
 * closed; a previous encrypted journal must not silently become plaintext.
 */
internal object AndroidSwarmCheckpointKeys {
    private const val STORE = "AndroidKeyStore"
    private const val ALIAS = "thor_ai_swarm_checkpoint_aes_v1"

    @Synchronized
    fun loadOrCreate(): SecretKey {
        val store = KeyStore.getInstance(STORE).apply { load(null) }
        val existing = store.getKey(ALIAS, null)
        if (existing != null) {
            return existing as? SecretKey
                ?: error("Invalid THOR checkpoint key type")
        }
        val generator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, STORE)
        val spec = KeyGenParameterSpec.Builder(
            ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .build()
        generator.init(spec)
        return generator.generateKey()
    }

    /** Explicit user reset after a completed, rejected, or obsolete run. */
    @Synchronized
    fun discard() {
        val store = KeyStore.getInstance(STORE).apply { load(null) }
        if (store.containsAlias(ALIAS)) store.deleteEntry(ALIAS)
    }
}
