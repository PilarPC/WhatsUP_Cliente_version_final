package com.example.paquete;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Paquete implements Serializable {
    private String mensaje;
    private String  emisor = "Santiago";
    private String tipiM = "";


    private int puertoE;
    private int puertoR;
    private  String tiempo;

    private String Firma;
    private int llaveCifrada;



    private int numeroDeCertificado;
    private String rutaCertificadoReceptor;



    public Paquete(String mensaje, int puertoE, int getPuertoR) {
        this.mensaje = mensaje;
        this.puertoE = puertoE;
        this.puertoR = getPuertoR;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor= emisor;
    }

    public int getPuertoE() {
        return puertoE;
    }

    public void setPuertoE(int puertoE) {
        this.puertoE = puertoE;
    }

    public int getpPuertoR() {
        return puertoR;
    }

    public void setPuertoR(int getPuertoR) {
        this.puertoR = getPuertoR;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo() {
        this.tiempo = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm").format(LocalDateTime.now());
    }
    public void setNuevoTiempo(String tiempo){
        this.tiempo = tiempo;
    }
    public String getTipiM() {
        return tipiM;
    }

    public void setTipiM(String tipiM) {
        this.tipiM = tipiM;
    }


    public String getFirma() {
        return Firma;
    }

    public void setFirma(String firma) {
        Firma = firma;
    }

    public int getLlaveCifrada() {
        return llaveCifrada;
    }

    public void setLlaveCifrada(int llaveCifrada) {
        this.llaveCifrada = llaveCifrada;
    }

    public int getNumeroDeCertificado() {
        return numeroDeCertificado;
    }

    public void setNumeroDeCertificado(int numeroDeCertificado) {
        this.numeroDeCertificado = numeroDeCertificado;
    }

    public String getRutaCertificadoReceptor() {
        return rutaCertificadoReceptor;
    }

    public void setRutaCertificadoReceptor(String rutaCertificadoReceptor) {
        this.rutaCertificadoReceptor = rutaCertificadoReceptor;
    }
}
