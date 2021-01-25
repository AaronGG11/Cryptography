package com.example.demospringfileupload;

import com.example.demospringfileupload.crypto.Keys;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static com.example.demospringfileupload.crypto.Keys.crearArchivoLLave;

public class GenerateKeys {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, NoSuchProviderException {
        Boolean generate_symmetric_key = Boolean.TRUE;
        Boolean generate_asymmetric_key = Boolean.TRUE;

        StringBuilder path_directory = new StringBuilder();
        path_directory.append("..");
        path_directory.append(File.separator);
        path_directory.append("Keys");
        path_directory.append(File.separator);


        if(generate_symmetric_key){ // Symmetric key AES
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128); // for example
            SecretKey secretKey = keyGen.generateKey();

            crearArchivoLLave("../Keys/symmetricKey.txt", secretKey.getEncoded());
            System.out.println("LLave simétrica generada correctamente");

        }

        if(generate_asymmetric_key){ // Generate keys
            KeyPairGenerator generator = KeyPairGenerator.getInstance ("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

            generator.initialize (1024, random);

            KeyPair pair = generator.generateKeyPair();
            RSAPrivateKey priv = (RSAPrivateKey)pair.getPrivate();
            RSAPublicKey pub = (RSAPublicKey)pair.getPublic();

            crearArchivoLLave("../Keys/privateKey.txt", priv.getEncoded());
            crearArchivoLLave("../Keys/publicKey.txt", pub.getEncoded());
            System.out.println("LLave privada generada correctamente");
            System.out.println("LLave pública generada correctamente");

        }




    }
}
