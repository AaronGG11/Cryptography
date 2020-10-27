package com.example.demospringfileupload.crypto;

import java.util.ArrayList;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.example.demospringfileupload.crypto.modularArithmetic.AritmeticaModular;


public class Vigenere {

    /* aplicando la formula de cifrado: C_n = P_n + K mod 256, donde:
            C_n, es el carácter del texto cifrado resultante
            P_n, es la letra de la posicion n del texto en claro
            K, es el valor del carácter de la llave
            256, es el tamanio del alfabeto
     */
    public static String Encrypt(String plain_text, String key)
    {
        StringBuilder cipher_text = new StringBuilder();

        int counter_key_value = 0;
        for (int i=0; i<plain_text.length(); i++)
        {
            if(counter_key_value >= key.length()){
                counter_key_value = 0;
            }

            int P_n = (int)plain_text.charAt(i);
            int K = (int)key.charAt(counter_key_value);
            int C_n = (P_n + K) % 256;

            cipher_text.append((char)C_n);

            counter_key_value += 1;
        }

        return cipher_text.toString();
    }


    /* aplicando la formula de des cifrado: P_n = C_n + (-K) mod 256, donde:
        P_n, es la letra del texto claro resultante
        C_n, es la letra de la posicion n del texto cifrado
        -K, es el inverso aditivo del carácter de la llave
        256, es el tamanio del alfabeto
 */
    public static String Decrypt(String cipher_text, String key)
    {
        // Cifrar la llave
        StringBuilder cipher_key = new StringBuilder();
        StringBuilder decipher_text = new StringBuilder();

        for(int i=0; i<key.length(); i++)
        {
            cipher_key.append((char) AritmeticaModular.InversoAditivo(256, (int)key.charAt(i)));
        }

        int counter_key_value = 0;
        for (int i=0; i<cipher_text.length(); i++)
        {
            if(counter_key_value >= key.length()){
                counter_key_value = 0;
            }

            int C_n = (int)cipher_text.charAt(i);
            int K = AritmeticaModular.InversoAditivo((int)key.charAt(counter_key_value), 256);
            int P_n = (C_n + K) % 256;

            decipher_text.append((char)P_n);

            counter_key_value += 1;
        }

        return decipher_text.toString();
    }
}