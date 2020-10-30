package com.example.demospringfileupload.controller;

import java.io.*;

import com.example.demospringfileupload.Model.ModelVigenereEncrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FormControllerVigenere {

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



	@PostMapping("/e_upload")
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
			builder.append(aux.getArchivo().getOriginalFilename());
			builder.append(".vig");

			// Section encryption

			// Verificar si es llave aleatoria y no
			if(aux.isEsAleatorio()){

			}
			else {
				final String secretKey = aux.getClave();
			}
			
			String originalString = new String(file.getBytes());
			String encryptedString = AES.encrypt(originalString, secretKey);



		}






		return "redirect:/status";
	}


	@PostMapping("/d_upload")
	public String uploadFileD()
	{

		return "redirect:/status";
	}
}
