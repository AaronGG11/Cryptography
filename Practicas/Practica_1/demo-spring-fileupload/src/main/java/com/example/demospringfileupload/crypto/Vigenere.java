package com.example.demospringfileupload.crypto;

import java.util.ArrayList;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class Vigenere {

    public static String encrypt(String plain_text, String key)
    {
        List<Integer> plain_text_value = plain_text
                .chars()
                .mapToObj(e -> (char)e)
                .map(e -> e+0)
                .collect(Collectors.toList());

        List<Integer> key_value = key
                .chars()
                .mapToObj(e -> (char)e)
                .map(e -> e+0)
                .collect(Collectors.toList());

        if(key_value.size() < plain_text_value.size()){
            int counter = 0;
            for(int i=0; i<(plain_text_value.size())- key_value.size(); i++){
                if(counter >= key_value.size()){
                    counter = 0;
                }
                System.out.println(key_value.get(counter));
                counter += 1;
            }
        }



        List<Integer> cipher_text_value =
                key_value.stream()
                        .flatMap( s1 -> plain_text_value.stream().map( s2 -> s1 + s2 ) )
                        .collect( Collectors.toList() );


        return null;
    }

    public static String decrypt(String plain_text, String key)
    {
        return null;
    }
}