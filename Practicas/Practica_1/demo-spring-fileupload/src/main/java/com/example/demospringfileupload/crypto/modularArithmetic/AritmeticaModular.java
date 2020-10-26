package com.example.demospringfileupload.crypto.modularArithmetic;

public class AritmeticaModular {
    public static Integer inversoAditivo(Integer longitud_de_alfabeto, Integer numero)
    {
        if(numero > 0) {
            return (longitud_de_alfabeto - numero);
        }
        else {
            return longitud_de_alfabeto + numero;
        }
    }

    public static Integer inversoMultiplicativo()
    {
        return null;
    }
}
