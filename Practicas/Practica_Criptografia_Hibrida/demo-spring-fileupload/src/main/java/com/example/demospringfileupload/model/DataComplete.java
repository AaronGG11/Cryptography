package com.example.demospringfileupload.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class DataComplete {
    private MultipartFile texto;
    private MultipartFile clave_publica;
    private MultipartFile clave_privada;
    private MultipartFile clave_simetrica;
}


