package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Sismografo {
    private String fechaAdquisicion;
    private String identificadorSismografo;
    private String numeroDeSerie;
    private String serieTemporal;
    private String modelo;
    private String reparacion;
    private EstacionSismologica estacionSismologica;
    private Estado estadoActual;
    private CambioEstado cambioEstado;

    public Sismografo(String fechaAdquisicion, String identificadorSismografo, String numeroDeSerie, String serieTemporal, String modelo, String reparacion, EstacionSismologica estacionSismologica, Estado estadoActual, CambioEstado cambioEstado){
        this.fechaAdquisicion = fechaAdquisicion;
        this.identificadorSismografo = identificadorSismografo;
        this.numeroDeSerie = numeroDeSerie;
        this.serieTemporal = serieTemporal;
        this.modelo = modelo;
        this.reparacion = reparacion;
        this.estacionSismologica = estacionSismologica;
        this.estadoActual = estadoActual;
        this.cambioEstado = cambioEstado;
    }
}
