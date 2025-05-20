package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class OrdenDeInspeccion {
    private Integer numeroOrden;
    private LocalDateTime fechaHoraFinalizacion;
    private LocalDateTime fechaHoraCierre;
    private LocalDateTime fechaHoraInicio;
    private String observacionCierre;
    private String tareaAsignada;
    private EstacionSismologica estacionSismologica;
    private Estado estado;
    private Empleado empleado;

    public OrdenDeInspeccion(
            Integer numeroOrden,
            LocalDateTime fechaHoraFinalizacion,
            LocalDateTime fechaHoraCierre,
            LocalDateTime fechaHoraInicio,
            String observacionCierre,
            String tareaAsignada,
            EstacionSismologica estacionSismologica,
            Estado estado,
            Empleado empleado
    ) {
        this.numeroOrden = numeroOrden;
        this.fechaHoraFinalizacion = fechaHoraFinalizacion;
        this.fechaHoraCierre = fechaHoraCierre;
        this.fechaHoraInicio = fechaHoraInicio;
        this.observacionCierre = observacionCierre;
        this.tareaAsignada = tareaAsignada;
        this.estacionSismologica = estacionSismologica;
        this.estado = estado;
        this.empleado = empleado;
    }


}
