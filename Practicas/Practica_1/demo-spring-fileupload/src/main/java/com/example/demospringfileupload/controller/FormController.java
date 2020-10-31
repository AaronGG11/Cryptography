package com.example.demospringfileupload.controller;

import java.io.*;
import java.util.Random;

import com.example.demospringfileupload.Model.ModelVigenereDecrypt;
import com.example.demospringfileupload.Model.ModelVigenereEncrypt;
import com.example.demospringfileupload.crypto.Vigenere;
import com.example.demospringfileupload.crypto.utilities.Utilities;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FormController {

	@GetMapping("/encrypt_vigenere")
	public String encryptVigenere() {
		return "encrypt_vigenere";
	}

	@GetMapping("/decrypt_vigenere")
	public String decryptVigenere() {
		return "decrypt_vigenere";
	}

	@GetMapping("/encrypt_affine")
	public String encryptAffine() { return "encrypt_affine"; }

	@GetMapping("/decrypt_affine")
	public String decryptAffine() {
		return "decrypt_affine";
	}

	@GetMapping("/status")
	public String status()
	{
		return "status";
	}



	@PostMapping("/ev_upload")
	public String uploadFileE(@ModelAttribute("vigenere") ModelVigenereEncrypt vigenere, RedirectAttributes attributes) throws IOException {
		ModelVigenereEncrypt aux = new ModelVigenereEncrypt();

		aux.setArchivo(vigenere.getArchivo());
		aux.setClave(vigenere.getClave());
		aux.setLongitud_alfabeto(vigenere.getLongitud_alfabeto());
		aux.setEsAleatorio(vigenere.isEsAleatorio());

		// El archivo no válido
		if (aux.getArchivo().getBytes().toString() == null || aux.getArchivo().getBytes().toString().isEmpty()) {
			attributes.addFlashAttribute("message", "Por favor seleccione un archivo válido.");
			return "redirect:status";
		} // Clave no válida, porque es no aleatoria y es vacia o no se lleno el campo
		else if (!aux.isEsAleatorio() && (aux.getClave().isEmpty() || (aux.getClave().length() <= 0))) {
			attributes.addFlashAttribute("message", "Clave no válida.");
			return "redirect:status";
		} // Alfabeto no válido, porque es de longitud 0 o menor
		else if(aux.getLongitud_alfabeto() <= 0){
			attributes.addFlashAttribute("message", "Alfabeto no válido.");
			return "redirect:status";
		} // Los argumentos son correctos
		else{
			// Making path
			StringBuilder builder = new StringBuilder();
			builder.append(System.getProperty("user.home"));
			builder.append(File.separator);
			builder.append("Ejemplos");
			builder.append(File.separator);
			builder.append(aux.getArchivo().getOriginalFilename().replace(".txt","_E.vig.txt"));

			// Verificar si es llave aleatoria y no
			String secret_key;
			if(aux.isEsAleatorio()){
				// llave de lontigud aleatoria entre 64 y 128 caracteres
				secret_key = Utilities.GenerarLLave(aux.getLongitud_alfabeto(), new Random().nextInt(65) + 128);
			}
			else {
				secret_key = aux.getClave();
			}

			// Section encryption
			String plain_text = new String(aux.getArchivo().getBytes());
			String encrypted_text = Vigenere.Encrypt(plain_text, secret_key, aux.getLongitud_alfabeto());

			// writing the file
			File archivo = new File(builder.toString());
			BufferedWriter bw;
			bw = new BufferedWriter(new FileWriter(archivo));
			bw.write(encrypted_text);
			bw.close();

			attributes.addFlashAttribute("message", "Archivo cifrado correctamente ["+builder.toString()+"]\nLlave: " + secret_key);
			attributes.addFlashAttribute("content", encrypted_text);

			return "redirect:/status";
		}
	}


	@PostMapping("/dv_upload")
	public String uploadFileD(@ModelAttribute("vigenere") ModelVigenereDecrypt vigenere, RedirectAttributes attributes) throws IOException {
		ModelVigenereDecrypt aux = new ModelVigenereDecrypt();

		aux.setArchivo(vigenere.getArchivo());
		aux.setClave(vigenere.getClave());
		aux.setLongitud_alfabeto(vigenere.getLongitud_alfabeto());

		// El archivo no válido
		if (aux.getArchivo().getBytes().toString() == null || aux.getArchivo().getBytes().toString().isEmpty()) {
			attributes.addFlashAttribute("message", "Por favor seleccione un archivo válido.");
			return "redirect:status";
		} // Clave no válida, porque es no aleatoria y es vacia o no se lleno el campo
		else if (aux.getClave().isEmpty() || (aux.getClave().length() <= 0)) {
			attributes.addFlashAttribute("message", "Clave no válida.");
			return "redirect:status";
		} // Alfabeto no válido, porque es de longitud 0 o menor
		else if(aux.getLongitud_alfabeto() <= 0){
			attributes.addFlashAttribute("message", "Alfabeto no válido.");
			return "redirect:status";
		} // Los argumentos son correctos
		else{
			// Making path
			StringBuilder builder = new StringBuilder();
			builder.append(System.getProperty("user.home"));
			builder.append(File.separator);
			builder.append("Ejemplos");
			builder.append(File.separator);
			builder.append(aux.getArchivo().getOriginalFilename().replace("_E.vig.txt","_D.vig.txt"));

			// Section decryption
			String secret_key = aux.getClave();
			String cipher_text = new String(aux.getArchivo().getBytes());
			String plain_text = Vigenere.Decrypt(cipher_text, secret_key, aux.getLongitud_alfabeto());

			// writing the file
			File archivo = new File(builder.toString());
			BufferedWriter bw;
			bw = new BufferedWriter(new FileWriter(archivo));
			bw.write(plain_text);
			bw.close();

			attributes.addFlashAttribute("message", "Archivo descifrado correctamente ["+builder.toString()+"]\nLlave: " + secret_key);
			attributes.addFlashAttribute("content", plain_text);

			return "redirect:/status";
		}
	}
}
