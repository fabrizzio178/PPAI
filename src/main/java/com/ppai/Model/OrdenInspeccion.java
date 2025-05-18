package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdenInspeccion {
    private int numeroOrden;
    private String fechaFinalizacion;
    private String fechaHoraCierre;
    private EstacionSismologica estacion;
    private String observacionCierre;

    public OrdenInspeccion(int numeroOrden, String fechaFinalizacion, EstacionSismologica estacion){
        this.numeroOrden = numeroOrden;
        this.fechaFinalizacion = fechaFinalizacion;
        this.estacion = estacion;
    }
}
