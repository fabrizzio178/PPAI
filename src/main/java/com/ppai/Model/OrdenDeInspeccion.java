package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class OrdenDeInspeccion {
    private Integer numeroOrden;
    private String fechaHoraFinalizacion;
    private String fechaHoraCierre;
    private String fechaHoraInicio;
    private String observacionCierre;
    private EstacionSismologica estacionSismologica;
    private Estado estado;
    private Empleado empleado;

    public OrdenDeInspeccion(
            Integer numeroOrden,
            String fechaHoraFinalizacion,
            String fechaHoraCierre,
            String fechaHoraInicio,
            String observacionCierre,
            EstacionSismologica estacionSismologica,
            Estado estado,
            Empleado empleado
    ){
        this.numeroOrden = numeroOrden;
        this.fechaHoraFinalizacion = fechaHoraFinalizacion;
        this.fechaHoraCierre = fechaHoraCierre;
        this.fechaHoraInicio = fechaHoraInicio;
        this.observacionCierre = observacionCierre;
        this.estacionSismologica = estacionSismologica;
        this.estado = estado;
        this.empleado = empleado;
    }
}
