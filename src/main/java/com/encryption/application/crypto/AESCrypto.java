package com.encryption.application.crypto;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * A crypto that implement AES encryption
 */
public class AESCrypto implements Crypto {


    @Override
    public String encrypt(byte[] data, byte[] key, byte[] initVector, String BCA, String padding) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

            Cipher cipher;
            cipher = Cipher.getInstance("AES/" + BCA + "/" + padding);

            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(data);
            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public String decrypt(String data) {
        return data;
    }
}
