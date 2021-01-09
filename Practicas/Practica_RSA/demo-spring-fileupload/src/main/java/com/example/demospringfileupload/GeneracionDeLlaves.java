package com.example.demospringfileupload;

import com.example.demospringfileupload.crypto.RSA;


public class GeneracionDeLlaves {
    public static void main(String[] args) throws Exception {
        //Instanciamos la clase
        RSA rsa = new RSA();

        //Generamos un par de claves
        //Admite claves de 512, 1024, 2048 y 4096 bits
        rsa.genKeyPair(4096);


        String file_private = "./src/main/resources/rsa.pri.txt";
        String file_public = "./src/main/resources/rsa.pub.txt";

        //Las guardamos asi podemos usarlas despues
        //a lo largo del tiempo
        rsa.saveToDiskPrivateKey("./src/main/resources/rsa.pri.txt");
        rsa.saveToDiskPublicKey("./src/main/resources/rsa.pub.txt");
    }
}
