package com.ppai.Model;
import java.time.LocalDateTime;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Sismografo {

    private static ArrayList<Sismografo> sismografos = new ArrayList<>();

    private LocalDateTime fechaAdquisicion;
    private String identificadorSismografo;
    private String numeroDeSerie;
    private String serieTemporal;
    private String modelo;
    private String reparacion;
    private EstacionSismologica estacionSismologica;
    private Estado estadoActual;
    private ArrayList<CambioEstado> cambioEstado;

    public Sismografo(
            LocalDateTime fechaAdquisicion,
            String identificadorSismografo,
            String numeroDeSerie,
            String serieTemporal,
            String modelo,
            String reparacion,
            EstacionSismologica estacionSismologica,
            Estado estadoActual,
            ArrayList<CambioEstado> cambioEstado
    ){
        this.fechaAdquisicion = fechaAdquisicion;
        this.identificadorSismografo = identificadorSismografo;
        this.numeroDeSerie = numeroDeSerie;
        this.serieTemporal = serieTemporal;
        this.modelo = modelo;
        this.reparacion = reparacion;
        this.estacionSismologica = estacionSismologica;
        this.estadoActual = estadoActual;

        if (cambioEstado == null || cambioEstado.isEmpty()) {
            throw new IllegalArgumentException("Un sismografo debe tener al menos un cambio de estado.");
        }
        this.cambioEstado = cambioEstado;
    }

    public void fueraDeServicio(ArrayList<TipoMotivo> motivos, ArrayList<String> comentarios, LocalDateTime fechaHoraInicio, Estado estadoCerrada, Empleado responsable) {
        // Cierra el cambio de estado actual
        for (CambioEstado cambio : cambioEstado) {
            if (cambio.sosActual()) {
                cambio.setFechaHoraFin(fechaHoraInicio); // Le setea su fechahora
            }
        }

        CambioEstado nuevoEstado = new CambioEstado(null, fechaHoraInicio, estadoCerrada, responsable, null);
        nuevoEstado.cargarMotivos(motivos, comentarios);
        cambioEstado.add(nuevoEstado);
    }
}