package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

@Getter
@Setter

// private ArrayList<Clase> atributo

public class CambioEstado {
    private String fechaHoraFin;
    private String fechaHoraInicio;
    private Estado estado;
    private Empleado responsableInspeccion;
    private ArrayList<MotivoFueraServicio> motivoFueraServicio;

    public CambioEstado(
            String fechaHoraFin,
            String fechaHoraInicio,
            Estado estado,
            Empleado responsableInspeccion,
            ArrayList<MotivoFueraServicio> motivoFueraServicio
    ){
        this.fechaHoraFin = fechaHoraFin;
        this.fechaHoraInicio = fechaHoraInicio;
        this.estado = estado;
        this.responsableInspeccion = responsableInspeccion;
        this.motivoFueraServicio = motivoFueraServicio;
    }
}

