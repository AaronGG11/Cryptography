package com.example.demospringfileupload.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class DataModel {
    private MultipartFile texto;
    private MultipartFile clave;
}
