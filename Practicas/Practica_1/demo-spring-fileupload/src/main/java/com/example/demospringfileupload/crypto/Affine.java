package com.example.demospringfileupload.crypto;

import com.example.demospringfileupload.crypto.modularArithmetic.AritmeticaModular;

public class Affine {
    // Verifica si dado un alpha y un anillo, el mcd(alpha, anillo) = 1
    public static boolean IsValidKey(int alpha, int alphabet_length)
    {
        return AritmeticaModular.mcd(alpha, alphabet_length)[0] == 1;
    }


    public static String Encrypt(String plain_text, int alpha, int beta)
    {
        StringBuilder cipher_text = new StringBuilder();

        for (int i=0; i<plain_text.length(); i++)
        {
            
        }

        return null;
    }

    public static String Decrypt(String strToDecrypt, String secret)
    {
        return null;
    }
}
