package com.revature.SocialNetworkP2.models;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/*
 * HELPER CLASS USED TO ENCRYPT AND DECRYPT USER PASSWORD
 * */

public class Encryptor {
    private static final String ENCRYPTION_ALGORITHM = "AES/CBC/PKCS5Padding";

    private static final byte[] KEY_DATA = {
            (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03,
            (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07,
            (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B,
            (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F,
    };

    private static final byte[] IV_DATA = {
            (byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03,
            (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07,
            (byte) 0x08, (byte) 0x09, (byte) 0x0A, (byte) 0x0B,
            (byte) 0x0C, (byte) 0x0D, (byte) 0x0E, (byte) 0x0F,
    };

    public static String encrypt(String password) {
        try {
            SecretKey key = new SecretKeySpec(KEY_DATA, "AES");
            IvParameterSpec iv = new IvParameterSpec(IV_DATA);

            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] cipherText = cipher.doFinal(password.getBytes());

            return Base64.getEncoder().encodeToString(cipherText);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // DECRYPT
    public static String decrypt(String cipherText) {

        try {
            SecretKey key = new SecretKeySpec(KEY_DATA, "AES");
            IvParameterSpec iv = new IvParameterSpec(IV_DATA);

            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));

            return new String(plainText);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}

