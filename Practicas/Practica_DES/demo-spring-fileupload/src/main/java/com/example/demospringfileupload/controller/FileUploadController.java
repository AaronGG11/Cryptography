package com.example.demospringfileupload.controller;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.example.demospringfileupload.crypto.AES;
import com.example.demospringfileupload.model.DESmodel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;

@Controller
public class FileUploadController {
	@GetMapping("/index")
	public String index() {
		return "index";
	}

	@GetMapping("/encrypt")
	public String encrypt() {
		return "encrypt";
	}

	@GetMapping("/decrypt")
	public String decrypt() {
		return "decrypt";
	}

	@PostMapping("/e_upload")
	public String uploadFileE(@ModelAttribute("DES") DESmodel des, RedirectAttributes attributes) throws Exception
	{
		DESmodel aux = new DESmodel();
		aux.setImagen(des.getImagen());
		aux.setClave(des.getClave());
		aux.setEcb(des.getEcb());
		aux.setCbc(des.getCbc());
		aux.setCfb(des.getCfb());
		aux.setOfb(des.getOfb());



		// Making path
		StringBuilder builder = new StringBuilder();
		builder.append("..");
		builder.append(File.separator);
		builder.append("resultados");
		builder.append(File.separator);
		builder.append("img_saved.bmp");

		BufferedImage img = ImageIO.read(aux.getImagen().getInputStream());

		// Guardar imagen ya procesada
		File outputfile = new File(builder.toString());
		ImageIO.write(img, "bmp", outputfile);



		attributes.addFlashAttribute("message", "Archivo descifrado correctamente ");
		attributes.addFlashAttribute("content", img);

		return "redirect:/status";
	}


	@PostMapping("/d_upload")
	public String uploadFileD(@ModelAttribute("DES") DESmodel des, RedirectAttributes attributes) throws Exception
	{
		return "redirect:/status";
	}

	@GetMapping("/status")
	public String status() {
		return "status";
	}

}
