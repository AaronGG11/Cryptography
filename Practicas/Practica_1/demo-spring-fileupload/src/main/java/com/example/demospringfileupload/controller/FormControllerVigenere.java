package com.example.demospringfileupload.controller;

import java.io.*;

import com.example.demospringfileupload.Model.ModelVigenereEncrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

	@PostMapping("/e_upload")
	public String uploadFileE(ModelVigenereEncrypt vigenere, RedirectAttributes attributes) throws Exception
	{
		System.out.println(vigenere.toString());
		return "redirect:/status";
	}


	@PostMapping("/d_upload")
	public String uploadFileD() throws Exception
	{
		new ModelAndView("form").addObject("post", new ModelVigenereEncrypt());
		// verificar que hay datos
		/*
		if (file == null || file.isEmpty()) {
			attributes.addFlashAttribute("message", "Por favor seleccione un archivo");
			return "redirect:status";
		}*/



		return "redirect:/status";
	}

	@GetMapping("/status")
	public String status()
	{
		return "status";
	}

}
