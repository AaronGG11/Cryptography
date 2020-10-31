package com.example.demospringfileupload;

import com.example.demospringfileupload.crypto.Vigenere;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoSpringFileuploadApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(DemoSpringFileuploadApplication.class, args);
		/*
		String plain_text = "mi nombre es aaron";
		String key = "garcia";

		String cipher_text = Vigenere.Encrypt(plain_text,key,26);
		String decipher_text = Vigenere.Decrypt(cipher_text,key,26);

		System.out.println(cipher_text);
		System.out.println(decipher_text);
		System.out.println(plain_text);
		* */
	}
}
