package com.example.demospringfileupload.crypto.modularArithmetic;

public class AritmeticaModular {
    public static int InversoAditivo(int numero, int longitud_de_alfabeto)
    {
        if(numero > 0) {
            return (longitud_de_alfabeto - numero);
        }
        else {
            return longitud_de_alfabeto + numero;
        }
    }

    /* Regresa array[d,a,b] donde, d = gcd(p,q), ap + bq = d
        donde a, de ser negativo no calcula el inverso aditivo.
    */
    public static int[] mcd(int p, int q) {
        if(q==0) {
            return new int[] {p,1,0};
        }
        int [] vals = mcd(q, p%q);
        int d = vals[0];
        int a = vals[2];
        int b = vals[1] - (p/q) * vals[2];

        return new int []{d,a,b};
    }


    public static int InversoMultiplicativo(int p, int n)
    {
        int inverso_multiplicativo = mcd(p, n)[1];

        if(inverso_multiplicativo<0)
            return InversoAditivo(inverso_multiplicativo, n);
        else
            return inverso_multiplicativo;
    }
}
