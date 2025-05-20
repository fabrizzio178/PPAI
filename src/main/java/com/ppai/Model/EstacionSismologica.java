package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
public class EstacionSismologica {

    private static ArrayList<EstacionSismologica> estacionesSismologicas = new ArrayList<>();

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
    ) {
        this.codigoEstacion = codigoEstacion;
        this.documentoCertificacionAdq = documentoCertificacionAdq;
        this.fechaSolicitudCerificacion = fechaSolicitudCerificacion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombre = nombre;
        this.numeroCertificacionAdquisicion = numeroCertificacionAdquisicion;
        estacionesSismologicas.add(this);
    }


    public Boolean sosMiSismografo(Sismografo sismografo) {
        return this.nombre.equalsIgnoreCase(sismografo.getEstacionSismologica().getNombre());
    }

}