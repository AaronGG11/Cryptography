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
		Vigenere.encrypt("abcdefghijklmnopqrstuvwxyz","aaron");
	}
}
