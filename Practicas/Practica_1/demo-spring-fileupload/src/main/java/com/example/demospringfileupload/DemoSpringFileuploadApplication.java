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
		String plain_text = "hola mundo";
		String cipher_text = Affine.Encrypt(plain_text, 256, 3, 5);
		String decipher_text = Affine.Decrypt(cipher_text, 256, 3, 5);
		System.out.println(decipher_text);
	}
}
