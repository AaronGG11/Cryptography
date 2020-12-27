package com.example.demospringfileupload.controller;

import java.io.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String uploadFileE(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) throws Exception
	{

		if (file == null || file.isEmpty()) {
			attributes.addFlashAttribute("message", "Por favor seleccione un archivo");
			return "redirect:status";
		}

		// Making path
		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.home"));
		builder.append(File.separator);
		builder.append("Ejemplos");
		builder.append(File.separator);
		builder.append(file.getOriginalFilename().replace(".","_C."));

		// Section encryption


		// writing the file


		// Enviar status de operacion
		attributes.addFlashAttribute("message", "Archivo cifrado correctamente ["+builder.toString()+"]");
		//attributes.addFlashAttribute("content", encryptedString);

		return "redirect:/status";
	}


	@PostMapping("/d_upload")
	public String uploadFileD(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) throws Exception
	{

		if (file == null || file.isEmpty()) {
			attributes.addFlashAttribute("message", "Por favor seleccione un archivo");
			return "redirect:status";
		}

		// Makinh path
		StringBuilder builder = new StringBuilder();
		builder.append(System.getProperty("user.home"));
		builder.append(File.separator);
		builder.append("Ejemplos");
		builder.append(File.separator);
		builder.append(file.getOriginalFilename().replace("_C.","_D."));

		// Section decryption


		// writing the file


		// Sending status operation
		attributes.addFlashAttribute("message", "Archivo descifrado correctamente ["+builder.toString()+"]");
		//attributes.addFlashAttribute("content", decryptedString);

		return "redirect:/status";
	}

	@GetMapping("/status")
	public String status() {
		return "status";
	}

}
