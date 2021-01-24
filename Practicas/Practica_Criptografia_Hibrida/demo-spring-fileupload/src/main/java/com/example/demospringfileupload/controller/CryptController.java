package com.example.demospringfileupload.controller;

import java.io.*;

import com.example.demospringfileupload.model.RSAmodel;
import com.example.demospringfileupload.service.DigitalSignature;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CryptController {
	@GetMapping("/sign")
	public String sign() {
		return "sign";
	}

	@GetMapping("/verify")
	public String verify() {
		return "verify";
	}

	@GetMapping("/encrypt")
	public String encrypt() {
		return "encrypt";
	}

	@GetMapping("/decrypt")
	public String decrypt() {
		return "decrypt";
	}

	@GetMapping("/encrypt_sign")
	public String encryptSign() {
		return "encrypt_sign";
	}

	@GetMapping("/decrypt_verify")
	public String decryptVerify() {
		return "decrypt_verify";
	}


	@PostMapping("/s_upload")
	public String uploadFileE(@ModelAttribute("rsa_model") RSAmodel rsa_model, RedirectAttributes attributes) throws Exception
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
		builder.append("..");
		builder.append(File.separator);
		builder.append("resultados");
		builder.append(File.separator);
		builder.append(rsa_model.getTexto().getOriginalFilename().replace(".txt","_Signed.txt"));

		// Digital signature
		String digital_signature = DigitalSignature.sign(rsa_model.getTexto(), rsa_model.getClave());

		// writing the file
		File archivo = new File(builder.toString());
		BufferedWriter bw;
		bw = new BufferedWriter(new FileWriter(archivo));
		bw.write(digital_signature);
		bw.close();

		// Enviar status de operacion
		attributes.addFlashAttribute("message", "Archivo firmado correctamente ["+builder.toString()+"]");
		attributes.addFlashAttribute("content", digital_signature);

		return "redirect:/status";
	}


	@PostMapping("/v_upload")

	public String uploadFileD(@ModelAttribute("rsa_model") RSAmodel rsa_model, RedirectAttributes attributes) throws Exception
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
		builder.append("..");
		builder.append(File.separator);
		builder.append("resultados");
		builder.append(File.separator);
		builder.append(rsa_model.getTexto().getOriginalFilename().replace(".txt","_D.txt"));

		// Get verification
		Boolean is_valid = DigitalSignature.verify(rsa_model.getTexto(), rsa_model.getClave());

		if(is_valid){
			attributes.addFlashAttribute("message", "Autenticación correcta");
		}else{
			attributes.addFlashAttribute("message", "Autenticación no correcta");
		}

		attributes.addFlashAttribute("content", "");

		return "redirect:/status";
	}

	@GetMapping("/status")
	public String status() {
		return "status";
	}

}
