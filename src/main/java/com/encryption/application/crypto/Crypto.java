package com.encryption.application.crypto;
/**
 * The interface of Crypto
 */
public interface Crypto {
    String encrypt(byte[] data, byte[] key, byte[] initVector, String BCA, String padding);

    String decrypt(String data);
}
