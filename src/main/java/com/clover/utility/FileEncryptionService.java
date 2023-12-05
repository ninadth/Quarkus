package com.clover.utility;


import java.security.Key;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class FileEncryptionService {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    // Replace this key with a secure key for your application
    private static final String SECRET_KEY = "h7Ui63Mzqj61G87j";

    public byte[] encrypt(byte[] data, String encryptionKey) throws Exception {
        Key key = generateKey(encryptionKey);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public byte[] decrypt(byte[] encryptedData, String decryptionKey) throws Exception {
        Key key = generateKey(decryptionKey);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encryptedData);
    }

    private Key generateKey(String key) {
        byte[] keyValue = key.getBytes();
        return new SecretKeySpec(keyValue, ALGORITHM);
    }

    public String encodeBase64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public byte[] decodeBase64(String encodedData) {
        return Base64.getDecoder().decode(encodedData);
    }
}
