package com.example.demospringfileupload.controller;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;

import com.example.demospringfileupload.crypto.AES;
import com.example.demospringfileupload.crypto.RSA;
import com.example.demospringfileupload.model.DataModel;
import com.example.demospringfileupload.model.DataComplete;
import com.example.demospringfileupload.service.DigitalSignature;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CryptController {
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

	@PostMapping("/e_upload")
	public String uploadFileEncrypt(@ModelAttribute("aes_model") DataModel model, RedirectAttributes attributes) throws Exception
	{
		// Verifying files
		if(model.getTexto() == null || model.getTexto().isEmpty() ){
			attributes.addFlashAttribute("message", "Por favor seleccione un archivo de texto a cifrar");
			return "redirect:status";
		}else if(model.getClave() == null || model.getClave().isEmpty() ){
			attributes.addFlashAttribute("message", "Por favor seleccione un archivo de texto con la clave simétrica");
			return "redirect:status";
		}

		// Making path
		StringBuilder builder = new StringBuilder();
		builder.append("..");
		builder.append(File.separator);
		builder.append("resultados");
		builder.append(File.separator);
		builder.append(model.getTexto().getOriginalFilename().replace(".txt","_Encrypted.txt"));


		// Section encryption
		final String symmetric_key = new String(model.getClave().getBytes(), StandardCharsets.UTF_8);
		String original_string = new String(model.getTexto().getBytes(), StandardCharsets.UTF_8);
		String encrypted_string = AES.encrypt(original_string, symmetric_key);


		// writing the file
		File archivo = new File(builder.toString());
		BufferedWriter bw;
		bw = new BufferedWriter(new FileWriter(archivo));
		bw.write(encrypted_string);
		bw.close();

		// Enviar status de operacion
		attributes.addFlashAttribute("message", "Archivo cifrado correctamente ["+builder.toString()+"]");
		attributes.addFlashAttribute("content", encrypted_string);

		return "redirect:/status";
	}


	@PostMapping("/d_upload")
	public String uploadFileDecrypt(@ModelAttribute("aes_model") DataModel model, RedirectAttributes attributes) throws Exception
	{
		// Verifying files
		if(model.getTexto() == null || model.getTexto().isEmpty() ){
			attributes.addFlashAttribute("message", "Por favor seleccione un archivo de texto a descifrar");
			return "redirect:status";
		}else if(model.getClave() == null || model.getClave().isEmpty() ){
			attributes.addFlashAttribute("message", "Por favor seleccione un archivo de texto con la clave simétrica");
			return "redirect:status";
		}

		// Making path
		StringBuilder builder = new StringBuilder();
		builder.append("..");
		builder.append(File.separator);
		builder.append("resultados");
		builder.append(File.separator);
		builder.append(model.getTexto().getOriginalFilename().replace(".txt","_Decrypted.txt"));


		try{
			// Section encryption
			final String secretKey = new String(model.getClave().getBytes(), StandardCharsets.UTF_8);
			String originalString = new String(model.getTexto().getBytes(), StandardCharsets.UTF_8);
			String encryptedString = AES.decrypt(originalString, secretKey);

			// writing the file
			File archivo = new File(builder.toString());
			BufferedWriter bw;
			bw = new BufferedWriter(new FileWriter(archivo));
			bw.write(encryptedString);
			bw.close();

			// Enviar status de operacion
			attributes.addFlashAttribute("message", "Archivo descifrado correctamente ["+builder.toString()+"]");
			attributes.addFlashAttribute("content", encryptedString);
		}catch (Exception e){
			// Enviar status de operacion
			attributes.addFlashAttribute("message", "Archivo no descifrado");
			attributes.addFlashAttribute("content", "Fallo confidencialidad, clave incorrecta");
		}



		return "redirect:/status";
	}


	@PostMapping("/es_upload")
	public String uploadFileEncryptSign(@ModelAttribute("model") DataComplete model, RedirectAttributes attributes) throws Exception
	{
		// Verifying files
		if(model.getTexto() == null || model.getTexto().isEmpty() ){
			attributes.addFlashAttribute("message", "Por favor seleccione un archivo de texto plano válido.");
			return "redirect:status";
		} else if (model.getClave_simetrica() == null || model.getClave_simetrica().isEmpty()){
			attributes.addFlashAttribute("message", "Por favor seleccione una clave simétrica válida.");
			return "redirect:status";
		} else if (model.getClave_privada() == null || model.getClave_privada().isEmpty()) {
			attributes.addFlashAttribute("message", "Por favor seleccione una clave privada válida.");
			return "redirect:status";
		} else if (model.getClave_publica() == null || model.getClave_publica().isEmpty()) {
			attributes.addFlashAttribute("message", "Por favor seleccione una clave pública válida.");
			return "redirect:status";
		}

		// Making path
		StringBuilder builder = new StringBuilder();
		builder.append("..");
		builder.append(File.separator);
		builder.append("resultados");
		builder.append(File.separator);
		builder.append(model.getTexto().getOriginalFilename().replace(".txt","_SignEncrypt.txt"));

		// Digital signature
		String digital_signature = DigitalSignature.sign(model.getTexto(), model.getClave_privada());

		// Cipher text with AES CBC MODE 128
		final String symmetric_key = new String(model.getClave_simetrica().getBytes(), StandardCharsets.UTF_8);
		String original_string = new String(model.getTexto().getBytes(), StandardCharsets.UTF_8);
		String encrypted_text = AES.encrypt(original_string, symmetric_key);

		// Cipher symmetric key with RSA
		String sym_key = new String(model.getClave_simetrica().getBytes(), StandardCharsets.UTF_8);

		// Instantiate class
		RSA cifrador = new RSA();
			// Set private key
		PublicKey public_key = cifrador.getPublic2(model.getClave_publica().getBytes());
			// encrypt
		String encrypted_symmetric_key = cifrador.encryptText(sym_key, public_key);


		System.out.println(digital_signature.length() + " : " + digital_signature);
		System.out.println(encrypted_text.length() + " : " + encrypted_text);
		System.out.println(encrypted_symmetric_key.length() + " : " + encrypted_symmetric_key);

		String file_to_send = digital_signature + encrypted_symmetric_key + encrypted_text;

		// writing the file
		File archivo = new File(builder.toString());
		BufferedWriter bw;
		bw = new BufferedWriter(new FileWriter(archivo));
		bw.write(file_to_send);
		bw.close();


		// Enviar status de operacion
		attributes.addFlashAttribute("message", "Archivo firmado y cifrado correctamente ["+builder.toString()+"]");
		attributes.addFlashAttribute("content", file_to_send);

		return "redirect:/status";
	}


	@PostMapping("/dv_upload")
	public String uploadFileD(@ModelAttribute("model") DataComplete model, RedirectAttributes attributes) throws Exception
	{
		Boolean is_ok;
		StringBuilder message = new StringBuilder();


		// Verifying files
		if(model.getTexto() == null || model.getTexto().isEmpty() ){
			attributes.addFlashAttribute("message", "Por favor seleccione un archivo de texto plano válido.");
			return "redirect:status";
		} else if (model.getClave_privada() == null || model.getClave_privada().isEmpty()) {
			attributes.addFlashAttribute("message", "Por favor seleccione una clave privada válida.");
			return "redirect:status";
		} else if (model.getClave_publica() == null || model.getClave_publica().isEmpty()) {
			attributes.addFlashAttribute("message", "Por favor seleccione una clave pública válida.");
			return "redirect:status";
		}

		// Making path
		StringBuilder builder = new StringBuilder();
		builder.append("..");
		builder.append(File.separator);
		builder.append("resultados");
		builder.append(File.separator);
		builder.append(model.getTexto().getOriginalFilename().replace(".txt","_VerifyDecrypt.txt"));

		// separate digital_signature 172, encrypted_symmetric_key 172, encrypted_text
		String complete_document = new String(model.getTexto().getBytes(), StandardCharsets.UTF_8);
		String cipher_digital_signature = complete_document.substring(0,172);
		String cipher_symmetric_key = complete_document.substring(172, 344);
		String cipher_text = complete_document.substring(344);


		RSA cifrador = new RSA();
		// Set private key
		PrivateKey private_key = cifrador.getPrivate2(model.getClave_privada().getBytes());
		// encrypt


		String decipher_symmetric_key = cifrador.decryptText(cipher_symmetric_key, private_key);

		String decipher_text = null;

		try{
			decipher_text = AES.decrypt(cipher_text, decipher_symmetric_key);
		} catch (Exception e){
			message.append("Fallo servicio de integridad de datos");
		}

		String sha256hex = null;

		try{
			sha256hex = Hashing.sha256().hashString(decipher_text, StandardCharsets.UTF_8).toString();
		}catch (Exception e){
			message.append("Fallo servicio de integridad de datos");
		}


		// Set private key

		PublicKey public_key = cifrador.getPublic2(model.getClave_publica().getBytes());

		String decipher_digital_signature = null;
		try{
			decipher_digital_signature = decipher_digital_signature = cifrador.decryptText(cipher_digital_signature, public_key);
		} catch (Exception e){
			message.append("Fallo servicio de autenticación");
		}

		// Verification
		if(decipher_digital_signature != null && decipher_text != null && sha256hex != null) {


			if (decipher_digital_signature.equals(sha256hex)) {
				// writing the file
				File archivo = new File(builder.toString());
				BufferedWriter bw;
				bw = new BufferedWriter(new FileWriter(archivo));
				bw.write(decipher_text);
				bw.close();

				message.append("Se descifro y verifo correctamente, archivo almacenado en: " + builder.toString());
			}
		}


		// Send operation status
		attributes.addFlashAttribute("message", message.toString());
		attributes.addFlashAttribute("content", "");
		return "redirect:/status";
	}

	@GetMapping("/status")
	public String status() {
		return "status";
	}
}
