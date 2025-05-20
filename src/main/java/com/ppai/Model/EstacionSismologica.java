package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class EstacionSismologica {
    private Integer codigoEstacion;
    private String documentoCertificacionAdq;
    private LocalDateTime fechaSolicitudAdquisicion;
    private double latitud;
    private double longitud;
    private String nombre;
    private Integer numeroCertificacionAdquisicion;

    public EstacionSismologica (
            Integer codigoEstacion,
            String documentoCertificacionAdq,
            LocalDateTime fechaSolicitudAdquisicion,
            double latitud,
            double longitud,
            String nombre,
            Integer numeroCertificacionAdquisicion
    ) {
        this.codigoEstacion = codigoEstacion;
        this.documentoCertificacionAdq = documentoCertificacionAdq;
        this.fechaSolicitudAdquisicion = fechaSolicitudAdquisicion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombre = nombre;
        this.numeroCertificacionAdquisicion = numeroCertificacionAdquisicion;
    }
}


