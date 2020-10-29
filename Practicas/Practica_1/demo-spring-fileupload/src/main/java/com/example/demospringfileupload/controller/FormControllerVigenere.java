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
	public String uploadFileE(@ModelAttribute("vigenere") ModelVigenereEncrypt vigenere) throws IOException {

		String clave = new String(vigenere.getClave().getBytes());
		int longitud_alfabeto = vigenere.getLongitud_alfabeto();
		String originalString = new String(vigenere.getArchivo().getBytes());


		System.out.println(originalString);
		System.out.println(clave);
		System.out.println(longitud_alfabeto);
		System.out.println(vigenere.isEsAleatorio());

		return "redirect:/status";
	}


	@PostMapping("/d_upload")
	public String uploadFileD()
	{

		return "redirect:/status";
	}
}
