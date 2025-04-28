package com.github.cypekdev;

public interface StreamCallback {
    void onMessageChunk(String message);
    void onComplete();
    void onError(Throwable t);
}
