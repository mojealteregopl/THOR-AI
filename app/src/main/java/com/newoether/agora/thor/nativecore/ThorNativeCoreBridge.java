package com.newoether.agora.thor.nativecore;

/**
 * Native JNI ABI. Every call must originate from a worker thread.
 * System.loadLibrary fails closed if the verified Rust library is not packaged.
 */
public final class ThorNativeCoreBridge {
    static {
        System.loadLibrary("thor_nativecore");
    }

    private ThorNativeCoreBridge() {
        throw new AssertionError("No instances");
    }

    public static native long nativeOpen(
            String endpoint,
            String rootCaPath,
            String clientCertificatePath,
            String clientPrivateKeyPath
    );

    public static native boolean nativePublish(long handle, String key, byte[] payload);

    public static native long nativeSubscribe(long handle, String key);

    public static native byte[] nativePoll(long subscription, int timeoutMs);

    public static native void nativeUnsubscribe(long subscription);

    public static native void nativeClose(long handle);

    public static native byte[] nativeCounters();
}
