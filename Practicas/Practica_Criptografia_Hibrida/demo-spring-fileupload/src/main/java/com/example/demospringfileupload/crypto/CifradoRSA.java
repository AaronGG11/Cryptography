package com.example.demospringfileupload.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

public class CifradoRSA {
    private Cipher cipher;
    private static final String algoritmo = "RSA";

    public CifradoRSA() throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.cipher = Cipher.getInstance(algoritmo);
    }

    public PrivateKey getPrivate2(byte[] archivo) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] llaveBytes = archivo;
        PKCS8EncodedKeySpec spec= new PKCS8EncodedKeySpec(llaveBytes);
        KeyFactory kf = KeyFactory.getInstance(algoritmo);
        return kf.generatePrivate(spec);
    }

    public PrivateKey getPrivate(String archivo) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] llaveBytes = Files.readAllBytes(new File(archivo).toPath());
        PKCS8EncodedKeySpec spec= new PKCS8EncodedKeySpec(llaveBytes);
        KeyFactory kf = KeyFactory.getInstance(algoritmo);
        return kf.generatePrivate(spec);
    }

    public PublicKey getPublic2(byte[] archivo) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] llaveBytes = archivo;
        X509EncodedKeySpec spec = new X509EncodedKeySpec(llaveBytes);
        KeyFactory kf = KeyFactory.getInstance(algoritmo);
        return kf.generatePublic(spec);
    }


    public PublicKey getPublic(String archivo) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] llaveBytes = Files.readAllBytes(new File(archivo).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(llaveBytes);
        KeyFactory kf = KeyFactory.getInstance(algoritmo);
        return kf.generatePublic(spec);
    }

    private void writeToFile(File output, byte[] toWrite)
            throws IllegalBlockSizeException, BadPaddingException, IOException {
        FileOutputStream fos = new FileOutputStream(output);
        fos.write(toWrite);
        fos.flush();
        fos.close();
    }

    public void cifrarArchivo(byte[] input, File output, PrivateKey key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        this.cipher.init(Cipher.ENCRYPT_MODE, key);
        writeToFile(output, this.cipher.doFinal(input));
    }

    public void descifrarArchivo(byte [] input, File output, PublicKey key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        this.cipher.init(Cipher.DECRYPT_MODE, key);
        writeToFile(output, this.cipher.doFinal(input));
    }

    public String encryptText(String msg, PrivateKey key)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            UnsupportedEncodingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException {
        this.cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.encodeBase64String(cipher.doFinal(msg.getBytes("UTF-8")));
    }

    public String decryptText(String msg, PublicKey key)
            throws InvalidKeyException, UnsupportedEncodingException,
            IllegalBlockSizeException, BadPaddingException {
        this.cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.decodeBase64(msg)), "UTF-8");
    }

    public byte[] getFileInBytes(File f) throws IOException {
        FileInputStream fis = new FileInputStream(f);
        byte[] fbytes = new byte[(int) f.length()];
        fis.read(fbytes);
        fis.close();
        return fbytes;
    }

    public static void main(String[] args) throws Exception, NoSuchPaddingException {
        CifradoRSA cifrador = new CifradoRSA();
        PrivateKey privateKey = cifrador.getPrivate("KeyPair/privateKey.txt");
        PublicKey publicKey = cifrador.getPublic("KeyPair/publicKey.txt");

        String msg = "Hola mundo con RSA!";
        String encrypted_msg = cifrador.encryptText(msg, privateKey);
        String decrypted_msg = cifrador.decryptText(encrypted_msg, publicKey);
        System.out.println("Original Message: " + msg +
                "\nEncrypted Message: " + encrypted_msg
                + "\nDecrypted Message: " + decrypted_msg);

        if (new File("KeyPair/text.txt").exists()) {
            cifrador.cifrarArchivo(cifrador.getFileInBytes(new File("KeyPair/text.txt")),
                    new File("KeyPair/text_encrypted.txt"),privateKey);
            cifrador.descifrarArchivo(cifrador.getFileInBytes(new File("KeyPair/text_encrypted.txt")),
                    new File("KeyPair/text_decrypted.txt"), publicKey);
        } else {
            System.out.println("Create a file text.txt under folder KeyPair");
        }
    }
}