package com.example.demospringfileupload;

import com.example.demospringfileupload.crypto.DES;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        final String clave = "abcd1234";
        String texto_en_claro = "Mi nombre es Aaron Garcia";

        ArrayList<String> modos_operacion = new ArrayList<>();
        modos_operacion.add("ECB");
        modos_operacion.add("CBC");
        modos_operacion.add("CFB");
        modos_operacion.add("OFB");

        for(String modo_operacion : modos_operacion){
            String texto_cifrado= DES.encrypt(texto_en_claro, clave, modo_operacion);
            String texto_descifrado = DES.decrypt(texto_cifrado, clave, modo_operacion);

            System.out.println(modo_operacion);
            System.out.println(texto_cifrado);
            System.out.println(texto_descifrado);
        }
    }
}
