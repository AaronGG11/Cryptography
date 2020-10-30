package com.example.demospringfileupload.Model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ModelVigenereDecrypt {
    private MultipartFile archivo;
    private String clave;
    private int longitud_alfabeto;

    public MultipartFile getArchivo() {
        return archivo;
    }

    public void setArchivo(MultipartFile archivo) {
        this.archivo = archivo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getLongitud_alfabeto() {
        return longitud_alfabeto;
    }

    public void setLongitud_alfabeto(int longitud_alfabeto) {
        this.longitud_alfabeto = longitud_alfabeto;
    }

}
