package com.example.demospringfileupload;

import com.example.demospringfileupload.crypto.DES;

public class App {
    public static void main(String[] args) {
        final String clave = "abcd1234";
        String modo_operacion = "CFB";
        String texto_en_claro = "Mi nombre es Aaron Garcia";
        String texto_cifrado= DES.encrypt(texto_en_claro, clave, modo_operacion);
        String texto_descifrado = DES.decrypt(texto_cifrado, clave, modo_operacion);

        System.out.println(texto_cifrado);
        System.out.println(texto_descifrado);
    }
}
