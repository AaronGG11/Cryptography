package com.example.demospringfileupload.crypto.utilities;

import java.util.Random;

public class Utilities {
    public static String GenerarLLave(int longitud_alfabeto, int longitud_llave){
        StringBuilder llave = new StringBuilder();

        for(int i=0; i<longitud_llave; i++){
            int index;

            if(longitud_alfabeto == 26){
                index = new Random().nextInt(26) + 97;
            }else{
                index = new Random().nextInt(longitud_alfabeto+1);
            }

            llave.append((char)index);
        }

        return llave.toString();
    }
}
