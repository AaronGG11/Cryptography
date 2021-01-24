package com.example.demospringfileupload;

import com.example.demospringfileupload.crypto.GenerateKeys;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static com.example.demospringfileupload.crypto.GenerateKeys.crearArchivoLLave;

public class App {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        Boolean generate_symmetric_key = Boolean.TRUE;
        Boolean generate_private_key = Boolean.TRUE;
        Boolean generate_public_key = Boolean.TRUE;

        StringBuilder path_directory = new StringBuilder();
        path_directory.append("..");
        path_directory.append(File.separator);
        path_directory.append("Keys");
        path_directory.append(File.separator);

        GenerateKeys gk;

        if(generate_symmetric_key){ // Symmetric key AES
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128); // for example
            SecretKey secretKey = keyGen.generateKey();

            crearArchivoLLave("../Keys/symmetricKey.txt", secretKey.getEncoded());
            System.out.println("LLave simétrica generada correctamente");

        }

        if(generate_private_key || generate_public_key){ // Generate keys
            gk = new GenerateKeys(1024);
            gk.createKeys();

            if(generate_private_key){ // Private key RSA
                crearArchivoLLave("../Keys/privateKey.txt", gk.getLlavePrivada().getEncoded());
                System.out.println("LLave privada generada correctamente");
            }

            if(generate_public_key){ // Public key RSA
                System.out.println("LLave pública generada correctamente");
                crearArchivoLLave("../Keys/publicKey.txt", gk.getLlavePublica().getEncoded());
            }
        }



    }
}
