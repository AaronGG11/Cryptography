package com.example.demospringfileupload.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
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
            key = Arrays.copyOf(key, 8);
            secretKey = new SecretKeySpec(key, "DES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static byte[] encrypt(byte[] strToEncrypt, String secret, String mode){
        if (mode.equals("ECB")) {
            try
            {
                setKey(secret);
                Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                return cipher.doFinal(strToEncrypt);
                //return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            }
            catch (Exception e)
            {
                System.out.println("Error while encrypting: " + e.toString());
            }
            return null;
        }

        if (mode.equals("CBC")) {
            try
            {
                setKey(secret);

                byte[] initializationVector = { 11, 22, 33, 44, 99, 88, 77, 66 };
                AlgorithmParameterSpec algorithmParameterSpec = new IvParameterSpec(initializationVector);

                Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, algorithmParameterSpec);
                return cipher.doFinal(strToEncrypt);
            }
            catch (Exception e)
            {
                System.out.println("Error while encrypting: " + e.toString());
            }
            return null;
        }

        if (mode.equals("CFB")) {
            try
            {
                setKey(secret);

                byte[] initializationVector = { 11, 22, 33, 44, 99, 88, 77, 66 };
                AlgorithmParameterSpec algorithmParameterSpec = new IvParameterSpec(initializationVector);

                Cipher cipher = Cipher.getInstance("DES/CFB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, algorithmParameterSpec);
                return cipher.doFinal(strToEncrypt);
            }
            catch (Exception e)
            {
                System.out.println("Error while encrypting: " + e.toString());
            }
            return null;
        }

        if (mode.equals("OFB")) {
            try
            {
                setKey(secret);

                byte[] initializationVector = { 11, 22, 33, 44, 99, 88, 77, 66 };
                AlgorithmParameterSpec algorithmParameterSpec = new IvParameterSpec(initializationVector);

                Cipher cipher = Cipher.getInstance("DES/OFB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, algorithmParameterSpec);
                return cipher.doFinal(strToEncrypt);
            }
            catch (Exception e)
            {
                System.out.println("Error while encrypting: " + e.toString());
            }
            return null;
        }

        return null;
    }

    public static byte[] decrypt(byte[] strToDecrypt, String secret, String mode){

        if (mode.equals("ECB")) {
            try
            {
                setKey(secret);
                Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                return cipher.doFinal(strToDecrypt);
            }
            catch (Exception e)
            {
                System.out.println("Error while decrypting: " + e.toString());
            }
            return null;
        }

        if (mode.equals("CBC")) {
            try
            {
                setKey(secret);
                byte[] initializationVector = { 11, 22, 33, 44, 99, 88, 77, 66 };
                AlgorithmParameterSpec algorithmParameterSpec = new IvParameterSpec(initializationVector);

                Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey, algorithmParameterSpec);

                return cipher.doFinal(strToDecrypt);
            }
            catch (Exception e)
            {
                System.out.println("Error while encrypting: " + e.toString());
            }
            return null;
        }

        if (mode.equals("CFB")) {
            try
            {
                setKey(secret);

                byte[] initializationVector = { 11, 22, 33, 44, 99, 88, 77, 66 };
                AlgorithmParameterSpec algorithmParameterSpec = new IvParameterSpec(initializationVector);

                Cipher cipher = Cipher.getInstance("DES/CFB/NoPadding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey, algorithmParameterSpec);

                return cipher.doFinal(strToDecrypt);
            }
            catch (Exception e)
            {
                System.out.println("Error while encrypting: " + e.toString());
            }
            return null;
        }

        if (mode.equals("OFB")) {
            try
            {
                setKey(secret);

                byte[] initializationVector = { 11, 22, 33, 44, 99, 88, 77, 66 };
                AlgorithmParameterSpec algorithmParameterSpec = new IvParameterSpec(initializationVector);

                Cipher cipher = Cipher.getInstance("DES/OFB/NoPadding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey, algorithmParameterSpec);

                return cipher.doFinal(strToDecrypt);
            }
            catch (Exception e)
            {
                System.out.println("Error while encrypting: " + e.toString());
            }
            return null;
        }
        return null;
    }
}
