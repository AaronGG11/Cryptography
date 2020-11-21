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

		// Recibiendo imagen del formulario
		BufferedImage img = ImageIO.read(aux.getImagen().getInputStream());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( img, "bmp", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();

		byte[] finalBytes = new byte[(int)imageInByte.length];
		byte[] encryptedBytes = new byte[(int)imageInByte.length - header_size];

		// Trabajando cabecera y payload
		for(int i = 0, j=0 ; i < imageInByte.length; i++) {
			if(i < header_size) {
				finalBytes[i] = imageInByte[i];
			}else {
				encryptedBytes[j++] = imageInByte[i];
			}
		}

		if(aux.getEcb()!=null){
			byte[] imageModified = DES.encrypt(encryptedBytes, aux.getClave(), "ECB");

			// Making path
			StringBuilder builder = new StringBuilder();
			builder.append("..");
			builder.append(File.separator);
			builder.append("resultados");
			builder.append(File.separator);
			builder.append(aux.getImagen().getOriginalFilename().replace(".bmp","_E_ECB.bmp"));

			for(int i = header_size, j = 0; i < imageInByte.length; i++, j++) {
				finalBytes[i] = imageModified[j];
			}

			// Guardar imagen ya procesada
			InputStream in = new ByteArrayInputStream(finalBytes);
			BufferedImage bImageFromConvert = ImageIO.read(in);
			ImageIO.write(bImageFromConvert, "bmp", new File(builder.toString()));
		}

		if(aux.getCbc()!=null){
			byte[] imageModified = DES.encrypt(encryptedBytes, aux.getClave(), "CBC");

			// Making path
			StringBuilder builder = new StringBuilder();
			builder.append("..");
			builder.append(File.separator);
			builder.append("resultados");
			builder.append(File.separator);
			builder.append(aux.getImagen().getOriginalFilename().replace(".bmp","_E_CBC.bmp"));

			for(int i = header_size, j = 0; i < imageInByte.length; i++, j++) {
				finalBytes[i] = imageModified[j];
			}

			// Guardar imagen ya procesada
			InputStream in = new ByteArrayInputStream(finalBytes);
			BufferedImage bImageFromConvert = ImageIO.read(in);
			ImageIO.write(bImageFromConvert, "bmp", new File(builder.toString()));
		}

		if(aux.getCfb()!=null){
			byte[] imageModified = DES.encrypt(encryptedBytes, aux.getClave(), "CFB");

			// Making path
			StringBuilder builder = new StringBuilder();
			builder.append("..");
			builder.append(File.separator);
			builder.append("resultados");
			builder.append(File.separator);
			builder.append(aux.getImagen().getOriginalFilename().replace(".bmp","_E_CFB.bmp"));

			for(int i = header_size, j = 0; i < imageInByte.length; i++, j++) {
				finalBytes[i] = imageModified[j];
			}

			// Guardar imagen ya procesada
			InputStream in = new ByteArrayInputStream(finalBytes);
			BufferedImage bImageFromConvert = ImageIO.read(in);
			ImageIO.write(bImageFromConvert, "bmp", new File(builder.toString()));
		}

		if(aux.getOfb()!=null){
			byte[] imageModified = DES.encrypt(encryptedBytes, aux.getClave(), "OFB");

			// Making path
			StringBuilder builder = new StringBuilder();
			builder.append("..");
			builder.append(File.separator);
			builder.append("resultados");
			builder.append(File.separator);
			builder.append(aux.getImagen().getOriginalFilename().replace(".bmp","_E_OFB.bmp"));

			for(int i = header_size, j = 0; i < imageInByte.length; i++, j++) {
				finalBytes[i] = imageModified[j];
			}

			// Guardar imagen ya procesada
			InputStream in = new ByteArrayInputStream(finalBytes);
			BufferedImage bImageFromConvert = ImageIO.read(in);
			ImageIO.write(bImageFromConvert, "bmp", new File(builder.toString()));
		}

		attributes.addFlashAttribute("message", "Archivo cifrado correctamente ");
		attributes.addFlashAttribute("content",
				"Cifrado: Clave: " +
						aux.getClave() +
						", Modos de operación usados: " +
						", ECB: " + aux.getEcb() +
						", CBC: " + aux.getCbc() +
						", CFB: " + aux.getCfb() +
						", OFB: " + aux.getOfb()
				);

		return "redirect:/status";
	}

	@PostMapping("/d_upload")
	public String uploadFileD(@ModelAttribute("DES") DESmodel des, RedirectAttributes attributes) throws Exception
	{
		Integer header_size = 54;

		DESmodel aux = new DESmodel();
		aux.setImagen(des.getImagen());
		aux.setClave(des.getClave());
		aux.setEcb(des.getEcb());
		aux.setCbc(des.getCbc());
		aux.setCfb(des.getCfb());
		aux.setOfb(des.getOfb());

		// Recibiendo imagen del formulario
		BufferedImage img = ImageIO.read(aux.getImagen().getInputStream());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( img, "bmp", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();

		byte[] finalBytes = new byte[(int)imageInByte.length];
		byte[] encryptedBytes = new byte[(int)imageInByte.length - header_size];

		// Trabajando cabecera y payload
		for(int i = 0, j=0 ; i < imageInByte.length; i++) {
			if(i < header_size) {
				finalBytes[i] = imageInByte[i];
			}else {
				encryptedBytes[j++] = imageInByte[i];
			}
		}

		if(aux.getEcb()!=null){
			byte[] imageModified = DES.decrypt(encryptedBytes, aux.getClave(), "ECB");

			// Making path
			StringBuilder builder = new StringBuilder();
			builder.append("..");
			builder.append(File.separator);
			builder.append("resultados");
			builder.append(File.separator);
			builder.append(aux.getImagen().getOriginalFilename().replace(".bmp","_D_ECB.bmp"));

			for(int i = header_size, j = 0; i < imageInByte.length; i++, j++) {
				finalBytes[i] = imageModified[j];
			}

			// Guardar imagen ya procesada
			InputStream in = new ByteArrayInputStream(finalBytes);
			BufferedImage bImageFromConvert = ImageIO.read(in);
			ImageIO.write(bImageFromConvert, "bmp", new File(builder.toString()));
		}

		if(aux.getCbc()!=null){
			byte[] imageModified = DES.decrypt(encryptedBytes, aux.getClave(), "CBC");

			// Making path
			StringBuilder builder = new StringBuilder();
			builder.append("..");
			builder.append(File.separator);
			builder.append("resultados");
			builder.append(File.separator);
			builder.append(aux.getImagen().getOriginalFilename().replace(".bmp","_D_CBC.bmp"));

			for(int i = header_size, j = 0; i < imageInByte.length; i++, j++) {
				finalBytes[i] = imageModified[j];
			}

			// Guardar imagen ya procesada
			InputStream in = new ByteArrayInputStream(finalBytes);
			BufferedImage bImageFromConvert = ImageIO.read(in);
			ImageIO.write(bImageFromConvert, "bmp", new File(builder.toString()));
		}

		if(aux.getCfb()!=null){
			byte[] imageModified = DES.decrypt(encryptedBytes, aux.getClave(), "CFB");

			// Making path
			StringBuilder builder = new StringBuilder();
			builder.append("..");
			builder.append(File.separator);
			builder.append("resultados");
			builder.append(File.separator);
			builder.append(aux.getImagen().getOriginalFilename().replace(".bmp","_d_CFB.bmp"));

			for(int i = header_size, j = 0; i < imageInByte.length; i++, j++) {
				finalBytes[i] = imageModified[j];
			}

			// Guardar imagen ya procesada
			InputStream in = new ByteArrayInputStream(finalBytes);
			BufferedImage bImageFromConvert = ImageIO.read(in);
			ImageIO.write(bImageFromConvert, "bmp", new File(builder.toString()));
		}

		if(aux.getOfb()!=null){
			byte[] imageModified = DES.decrypt(encryptedBytes, aux.getClave(), "OFB");

			// Making path
			StringBuilder builder = new StringBuilder();
			builder.append("..");
			builder.append(File.separator);
			builder.append("resultados");
			builder.append(File.separator);
			builder.append(aux.getImagen().getOriginalFilename().replace(".bmp","_D_OFB.bmp"));

			for(int i = header_size, j = 0; i < imageInByte.length; i++, j++) {
				finalBytes[i] = imageModified[j];
			}

			// Guardar imagen ya procesada
			InputStream in = new ByteArrayInputStream(finalBytes);
			BufferedImage bImageFromConvert = ImageIO.read(in);
			ImageIO.write(bImageFromConvert, "bmp", new File(builder.toString()));
		}

		attributes.addFlashAttribute("message", "Archivo descifrado correctamente ");
		attributes.addFlashAttribute("content",
				"Cifrado: Clave: " +
						aux.getClave() +
						", Modos de operación usados: " +
						", ECB: " + aux.getEcb() +
						", CBC: " + aux.getCbc() +
						", CFB: " + aux.getCfb() +
						", OFB: " + aux.getOfb()
		);
		return "redirect:/status";
	}

	@GetMapping("/status")
	public String status() {
		return "status";
	}

}
