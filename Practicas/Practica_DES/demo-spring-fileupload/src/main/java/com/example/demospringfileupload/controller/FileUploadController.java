package com.example.demospringfileupload.controller;

import java.awt.image.BufferedImage;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.example.demospringfileupload.crypto.DES;
import com.example.demospringfileupload.model.DESmodel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
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
		 Integer header_size = 54;

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

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( img, "bmp", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();

		byte[] finalBytes = new byte[(int)imageInByte.length];
		byte[] encryptedBytes = new byte[(int)imageInByte.length - header_size];

		for(int i = 0, j=0 ; i < imageInByte.length; i++) {
			if(i < header_size) {
				finalBytes[i] = imageInByte[i];
			}else {
				encryptedBytes[j++] = imageInByte[i];
			}
		}

		String clave = "abcd1234";

		/*
		SecretKeySpec secretKey;
		byte[] key;
		MessageDigest sha = null;
		key = clave.getBytes("UTF-8");
		sha = MessageDigest.getInstance("SHA-1");
		key = sha.digest(key);
		key = Arrays.copyOf(key, 8);
		secretKey = new SecretKeySpec(key, "DES");



		Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		*/

		byte[] imageModified = DES.decrypt(encryptedBytes, clave, "OFB");

		for(int i = header_size, j = 0; i < imageInByte.length; i++, j++) {
			finalBytes[i] = imageModified[j];
		}




		// Guardar imagen ya procesada
		//File outputfile = new File(builder.toString());
		//ImageIO.write(img, "bmp", outputfile);

		InputStream in = new ByteArrayInputStream(finalBytes);
		BufferedImage bImageFromConvert = ImageIO.read(in);
		ImageIO.write(bImageFromConvert, "bmp", new File(builder.toString()));




		attributes.addFlashAttribute("message", "Archivo descifrado correctamente ");
		attributes.addFlashAttribute("content", imageInByte);

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
