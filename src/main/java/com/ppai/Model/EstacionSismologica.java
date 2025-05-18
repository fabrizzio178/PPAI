package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstacionSismologica {
    private String codigoEstacion;
    private String documentoCertificacionAdq;
    private String fechaSolicitudCerificacion;
    private Integer latitud;
    private Integer longitud;
    private String nombre;
    private Integer numeroCertificacionAdquisicion;


    public EstacionSismologica(String codigoEstacion, String documentoCertificacionAdq, String fechaSolicitudCerificacion, Integer latitud, Integer longitud, String nombre, Integer numeroCertificacionAdquisicion){
        this.codigoEstacion = codigoEstacion;
        this.documentoCertificacionAdq = documentoCertificacionAdq;
        this.fechaSolicitudCerificacion = fechaSolicitudCerificacion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombre = nombre;
        this.numeroCertificacionAdquisicion = numeroCertificacionAdquisicion;
    }
}


