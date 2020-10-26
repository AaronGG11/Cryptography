package com.example.demospringfileupload;

import com.example.demospringfileupload.crypto.Vigenere;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class DemoSpringFileuploadApplication
{
	public static void main(String[] args)
	{
		//SpringApplication.run(DemoSpringFileuploadApplication.class, args);
		String cipher_text =  Vigenere.encrypt("hola mundo","secreto");
		String decipher_text = Vigenere.decrypt(cipher_text,"secreto");

		System.out.println(decipher_text);
	}
}
