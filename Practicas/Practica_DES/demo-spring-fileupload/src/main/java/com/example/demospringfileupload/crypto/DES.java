package com.example.demospringfileupload.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class DES {
    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "DES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String strToEncrypt, String secret, String mode){
        setKey(secret);

        if (mode.equals("ECB")) {
            try
            {
                setKey(secret);
                Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            }
            catch (Exception e)
            {
                System.out.println("Error while encrypting: " + e.toString());
            }
            return null;
        }

        if (mode.equals("CBC")) {

        }

        if (mode.equals("CFB")) {

        }

        if (mode.equals("OFB")) {

        }

        return null;
    }

    public static String decrypt(String strToDecrypt, String secret, String mode){
        setKey(secret);

        if (mode.equals("ECB")) {
            try
            {
                setKey(secret);
                Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
            }
            catch (Exception e)
            {
                System.out.println("Error while decrypting: " + e.toString());
            }
            return null;
        }

        if (mode.equals("CBC")) {

        }

        if (mode.equals("CFB")) {

        }

        if (mode.equals("OFB")) {

        }

        return null;
    }
}
