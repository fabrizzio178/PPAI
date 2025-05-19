package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EstacionSismologica {
    private String codigoEstacion;
    private String documentoCertificacionAdq;
    private LocalDateTime fechaSolicitudCerificacion;
    private Integer latitud;
    private Integer longitud;
    private String nombre;
    private Integer numeroCertificacionAdquisicion;


    public EstacionSismologica(
            String codigoEstacion,
            String documentoCertificacionAdq,
            LocalDateTime fechaSolicitudCerificacion,
            Integer latitud, Integer longitud,
            String nombre,
            Integer numeroCertificacionAdquisicion
    ){
        this.codigoEstacion = codigoEstacion;
        this.documentoCertificacionAdq = documentoCertificacionAdq;
        this.fechaSolicitudCerificacion = fechaSolicitudCerificacion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombre = nombre;
        this.numeroCertificacionAdquisicion = numeroCertificacionAdquisicion;
    }
    public String buscarNumeroSismografo(Sismografo sismografo) {
        if(sismografo != null){
            return sismografo.getIdentificadorSismografo();
        } else {
            return "Sin sismografo asignado";
        }
    }
}


