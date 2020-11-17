package com.example.demospringfileupload.crypto;

import com.example.demospringfileupload.crypto.modularArithmetic.AritmeticaModular;

public class Affine {
    // Verifica si dado un alpha y un anillo, el mcd(alpha, anillo) = 1
    public static boolean IsValidKey(int alpha, int alphabet_length)
    {
        return AritmeticaModular.mcd(alpha, alphabet_length)[0] == 1;
    }

    /*
    * Funcion que cifra con base a una llave alpha valida
    *
    *  aplicando la formula de cifrado: C_n = alpha * P_n + beta mod longitud_del_alfabeto, donde:
    *  C_n, es el carácter del texto cifrado resultante
    *  P_n, es la letra de la posicion n del texto en claro
    *  alpha, es el valor multiplicativo de la llave
    *  beta, es el valor aditivo de la llave
    * */
    public static String Encrypt(String plain_text, int alphabet_length, int alpha, int beta)
    {
        StringBuilder cipher_text = new StringBuilder();

        for (int i=0; i<plain_text.length(); i++)
        {
            int P_n = (int)plain_text.charAt(i);
            int C_n = ((alpha * P_n) + beta) % alphabet_length;
            cipher_text.append((char)C_n);
        }

        return cipher_text.toString();
    }

     /*
     *  Aplicando la formula de des cifrado: P_n = alpha^-1 * [ C_n + (-beta) ] mod longitud_del_alfabeto, donde:
     *  C_n, es el carácter del texto en claro descifrado
     *  P_n, es la letra de la posicion n del texto cifrado
     *  alpha, es el valor multiplicativo de la llave en inverso multiplicativo
     *  beta, es el valor aditivo de la llave en inverso aditivo
     * */
    public static String Decrypt(String cipher_text, int alphabet_length, int alpha, int beta)
    {
        StringBuilder decipher_text = new StringBuilder();

        for (int i=0; i<cipher_text.length(); i++)
        {
            int C_n = (int)cipher_text.charAt(i);
            int alpha_inverso_multiplicativo = AritmeticaModular.InversoMultiplicativo(alpha, alphabet_length);
            int beta_inverso_aditivo = AritmeticaModular.InversoAditivo(beta, alphabet_length);
            int P_n = ( alpha_inverso_multiplicativo * ( C_n + beta_inverso_aditivo ) ) % alphabet_length;

            decipher_text.append((char)P_n);
        }
        return decipher_text.toString();
    }
}
