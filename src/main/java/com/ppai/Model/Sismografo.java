package com.ppai.Model;
import java.time.LocalDateTime;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Sismografo {
    private LocalDateTime fechaAdquisicion;
    private Integer identificadorSismografo;
    private String numeroDeSerie;
    private String serieTemporal;
    private String modelo;
    private Boolean reparacion; // está o no en reparacion
    private EstacionSismologica estacionSismologica;
    private Estado estadoActual;
    private ArrayList<CambioEstado> cambiosEstado;
    
    public Sismografo(
            LocalDateTime fechaAdquisicion,
            Integer identificadorSismografo,
            String numeroDeSerie,
            String serieTemporal,
            String modelo,
            Boolean reparacion,
            EstacionSismologica estacionSismologica,
            Estado estadoActual,
            ArrayList<CambioEstado> cambiosEstado
    ) {
        if (cambiosEstado == null || cambiosEstado.isEmpty()) {
            throw new IllegalArgumentException("Un sismografo debe tener como minimo un cambio de estado");
        } // Este if es por la relacion 1..*
        this.fechaAdquisicion = fechaAdquisicion;
        this.identificadorSismografo = identificadorSismografo;
        this.numeroDeSerie = numeroDeSerie;
        this.serieTemporal = serieTemporal;
        this.modelo = modelo;
        this.reparacion = reparacion;
        this.estacionSismologica = estacionSismologica;
        this.estadoActual = estadoActual;
        this.cambiosEstado = cambiosEstado;
    }
}