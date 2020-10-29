package com.example.demospringfileupload.Model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ModelVigenereEncrypt {
    private String clave;
    private int longitud_alfabeto;
    private int esLlaveAleatoria;
    private MultipartFile archivo;
}
