package com.example.demospringfileupload.controller;

import java.io.*;
import java.security.GeneralSecurityException;

import com.example.demospringfileupload.crypto.RSA;
import model.RSAmodel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.crypto.Cipher;

@Controller
public class FileUploadController {
	@GetMapping("/encrypt")
	public String encrypt() {
		return "encrypt";
	}

	@GetMapping("/decrypt")
	public String decrypt() {
		return "decrypt";
	}

	@PostMapping("/e_upload")
	public String uploadFileE(@ModelAttribute("RSA") RSAmodel rsa_model, RedirectAttributes attributes) throws Exception
	{
		// Verifying files
		if(rsa_model.getTexto() == null || rsa_model.getTexto().isEmpty() ){
			attributes.addFlashAttribute("message", "Por favor seleccione un archivo de texto a cifrar");
			return "redirect:status";
		}else if(rsa_model.getClave() == null || rsa_model.getClave().isEmpty() ){
			attributes.addFlashAttribute("message", "Por favor seleccione un archivo de texto con la clave privada");
			return "redirect:status";
		}

		// Making path
		StringBuilder builder = new StringBuilder();
		builder.append(".");
		builder.append(File.separator);
		builder.append("resultados");
		builder.append(File.separator);
		builder.append(rsa_model.getTexto().getOriginalFilename().replace(".txt","_C.txt"));

		// Section encryption

			// Get plain text
		String plain_text = new String(rsa_model.getTexto().getBytes());

			// Tnstantiate class
		RSA rsa = new RSA();

			// Get public key
		rsa.openFromParameterPublicKey(rsa_model.getClave());

			// Encrypt plain text
		String cipher_text = rsa.Encrypt(plain_text);

		// writing the file
		File archivo = new File(builder.toString());
		BufferedWriter bw;
		bw = new BufferedWriter(new FileWriter(archivo));
		bw.write(cipher_text);
		bw.close();

		// Enviar status de operacion
		attributes.addFlashAttribute("message", "Archivo cifrado correctamente ["+builder.toString()+"]");
		attributes.addFlashAttribute("content", cipher_text);

		return "redirect:/status";
	}


	@PostMapping("/d_upload")
	public String uploadFileD(@ModelAttribute("RSA") RSAmodel rsa_model, RedirectAttributes attributes) throws Exception
	{
		// Verifying files
		if(rsa_model.getTexto() == null || rsa_model.getTexto().isEmpty() ){
			attributes.addFlashAttribute("message", "Por favor seleccione un archivo de texto a cifrar");
			return "redirect:status";
		}else if(rsa_model.getClave() == null || rsa_model.getClave().isEmpty() ){
			attributes.addFlashAttribute("message", "Por favor seleccione un archivo de texto con la clave privada");
			return "redirect:status";
		}

		// Makinh path
		StringBuilder builder = new StringBuilder();
		builder.append(".");
		builder.append(File.separator);
		builder.append("resultados");
		builder.append(File.separator);
		builder.append(rsa_model.getTexto().getOriginalFilename().replace(".txt","_D.txt"));

		// Section decryption

			// Get cipher text
		String cipher_text = new String(rsa_model.getTexto().getBytes());

			// Tnstantiate class
		RSA rsa = new RSA();

			// Get private key
		rsa.openFromParameterPrivateKey(rsa_model.getClave());

			// Decrypt cipher text

		try{
			String decipher_text = rsa.Decrypt(cipher_text);

			// writing the file
			File archivo = new File(builder.toString());
			BufferedWriter bw;
			bw = new BufferedWriter(new FileWriter(archivo));
			bw.write(decipher_text);
			bw.close();

			// Sending status operation
			attributes.addFlashAttribute("message", "Archivo descifrado correctamente ["+builder.toString()+"]");
			attributes.addFlashAttribute("content", decipher_text);
		}catch (GeneralSecurityException gse){
			// Sending status operation
			attributes.addFlashAttribute("message", "Archivo descifrado correctamente ["+builder.toString()+"]");
			attributes.addFlashAttribute("content", "Texto cifrado corrupto");
		}



		return "redirect:/status";
	}

	@GetMapping("/status")
	public String status() {
		return "status";
	}

}
