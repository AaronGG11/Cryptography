package com.example.demospringfileupload.service;

import com.example.demospringfileupload.crypto.RSA;
import com.google.common.hash.Hashing;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * https://www.baeldung.com/sha-256-hashing-java
 *
 * */


public class DigitalSignature {
    public static String sign(MultipartFile text, MultipartFile key) throws Exception {
        // Get plain text
        String plain_text = new String(text.getBytes());
        // Hashing step SHA (Secure Hashing Algorithm)
        String sha256hex = Hashing.sha256().hashString(plain_text, StandardCharsets.UTF_8).toString();

        // RSA encryption step
            // Instantiate class
        RSA cifrador = new RSA();

            // Set private key
        PrivateKey privateKey = cifrador.getPrivate2(key.getBytes());

            // encrypt
        String digital_signature = cifrador.encryptText(sha256hex, privateKey);

        return digital_signature;
    }


    public static Boolean verify(MultipartFile text, MultipartFile key)throws Exception {
        Boolean result = null;

        // Get data

            // Get cipher text
        String complete_document = new String(text.getBytes());

        String sign_text = complete_document.substring(0,685);
        String plain_text = complete_document.substring(685);

        // Hashing step SHA (Secure Hashing Algorithm)
        String sha256hex = Hashing.sha256().hashString(plain_text, StandardCharsets.UTF_8).toString();

        // Section decryption RSA
        RSA cifrador = new RSA();
        PublicKey publicKey = cifrador.getPublic2(key.getBytes());

        String decipher_text = cifrador.decryptText(sign_text, publicKey);

        // Comparison
        if(sha256hex.equals(decipher_text)){
            result = Boolean.TRUE;
        }else{
            result = Boolean.FALSE;
        }

        System.out.println(result);

        return result;
    }
}
