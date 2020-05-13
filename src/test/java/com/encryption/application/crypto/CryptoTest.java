package com.encryption.application.crypto;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.*;

import java.util.Random;

public class CryptoTest {
    @Test
    public void AESTest() throws DecoderException {
        String data = "Hello World!!!!!";

        byte[] ranKey = new byte[16];
        byte[] ranIV = new byte[16];
        new Random().nextBytes(ranKey);
        new Random().nextBytes(ranIV);
        String key = "1234567812345678";
        String IV = "1234567812345678";

        Crypto crypto = new AESCrypto();
//        String enc = crypto.encrypt(data.getBytes(), key.getBytes(), IV.getBytes(), "CBC");
        String enc = crypto.encrypt(data.getBytes(), ranKey, ranIV, "CBC", "PKCS5Padding");
//        String enc = crypto.encrypt(data.getBytes(), ranKey, ranIV, "CBC", "NoPADDING");

        String resKey = new String(Hex.encodeHex(ranKey));
        String resIV = new String(Hex.encodeHex(ranIV));

        System.out.println("Original: " + data);
        System.out.println("Key: " + resKey);
        System.out.println("IV: " + resIV);
        System.out.println("Encrypted: " + enc);

    }

}
