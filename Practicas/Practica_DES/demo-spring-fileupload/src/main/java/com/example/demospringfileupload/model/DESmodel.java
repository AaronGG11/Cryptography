package com.example.demospringfileupload.model;

import org.springframework.web.multipart.MultipartFile;

public class DESmodel {
    private MultipartFile imagen;
    private String clave;
    private Boolean ecb;
    private Boolean cbc;
    private Boolean cfb;
    private Boolean ofb;

    public MultipartFile getImagen() {
        return imagen;
    }

    public void setImagen(MultipartFile imagen) {
        this.imagen = imagen;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Boolean getEcb() {
        return ecb;
    }

    public void setEcb(Boolean ecb) {
        this.ecb = ecb;
    }

    public Boolean getCbc() {
        return cbc;
    }

    public void setCbc(Boolean cbc) {
        this.cbc = cbc;
    }

    public Boolean getCfb() {
        return cfb;
    }

    public void setCfb(Boolean cfb) {
        this.cfb = cfb;
    }

    public Boolean getOfb() {
        return ofb;
    }

    public void setOfb(Boolean ofb) {
        this.ofb = ofb;
    }
}
