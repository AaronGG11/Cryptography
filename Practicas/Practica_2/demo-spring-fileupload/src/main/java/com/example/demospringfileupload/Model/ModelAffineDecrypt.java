package com.example.demospringfileupload.Model;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ModelAffineDecrypt {
    private MultipartFile archivo;
    private int alpha;
    private int beta;
    private int longitud_alfabeto;

    public MultipartFile getArchivo() {
        return archivo;
    }

    public void setArchivo(MultipartFile archivo) {
        this.archivo = archivo;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getBeta() {
        return beta;
    }

    public void setBeta(int beta) {
        this.beta = beta;
    }

    public int getLongitud_alfabeto() {
        return longitud_alfabeto;
    }

    public void setLongitud_alfabeto(int longitud_alfabeto) {
        this.longitud_alfabeto = longitud_alfabeto;
    }
}
