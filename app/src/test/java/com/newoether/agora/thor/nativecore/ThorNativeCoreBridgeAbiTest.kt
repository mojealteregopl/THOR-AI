package com.newoether.agora.thor.nativecore

import java.lang.reflect.Modifier
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/** Checks JVM name/signatures without initializing or loading the native library. */
class ThorNativeCoreBridgeAbiTest {
    private val owner = ThorNativeCoreBridge::class.java

    @Test fun exactStaticNativeMethodShapesMatchRustExports() {
        val methods = listOf(
            Triple("nativeOpen", Long::class.javaPrimitiveType, listOf(
                String::class.java, String::class.java, String::class.java, String::class.java
            )),
            Triple("nativePublish", Boolean::class.javaPrimitiveType, listOf(
                Long::class.javaPrimitiveType, String::class.java, ByteArray::class.java
            )),
            Triple("nativeSubscribe", Long::class.javaPrimitiveType, listOf(
                Long::class.javaPrimitiveType, String::class.java
            )),
            Triple("nativePoll", ByteArray::class.java, listOf(
                Long::class.javaPrimitiveType, Int::class.javaPrimitiveType
            )),
            Triple("nativeUnsubscribe", Void.TYPE, listOf(Long::class.javaPrimitiveType)),
            Triple("nativeClose", Void.TYPE, listOf(Long::class.javaPrimitiveType)),
            Triple("nativeCounters", ByteArray::class.java, emptyList()),
        )
        for ((name, returnType, params) in methods) {
            val method = owner.getDeclaredMethod(name, *params.filterNotNull().toTypedArray())
            assertEquals(returnType, method.returnType)
            assertTrue("$name must be static", Modifier.isStatic(method.modifiers))
            assertTrue("$name must be native", Modifier.isNative(method.modifiers))
        }
    }

    @Test fun compiledJvmClassHasStableJniPackage() {
        assertEquals(
            "com.newoether.agora.thor.nativecore.ThorNativeCoreBridge",
            owner.name,
        )
    }
}
