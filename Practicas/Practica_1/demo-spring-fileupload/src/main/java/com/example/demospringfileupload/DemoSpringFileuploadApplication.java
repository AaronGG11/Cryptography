package com.example.demospringfileupload;

import com.example.demospringfileupload.crypto.Vigenere;
import com.example.demospringfileupload.crypto.Affine;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.demospringfileupload.crypto.modularArithmetic.AritmeticaModular;

//@SpringBootApplication
public class DemoSpringFileuploadApplication
{
	public static void main(String[] args)
	{
		//SpringApplication.run(DemoSpringFileuploadApplication.class, args);
		System.out.println(AritmeticaModular.InversoMultiplicativo(83,173));
	}
}
