package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter

public class CambioEstado {
    private LocalDateTime fechaHoraFin;
    private LocalDateTime fechaHoraInicio;
    private Estado estado;
    private ArrayList<MotivoFueraServicio> motivoFueraServicio;  // 0 o MAS MOTIVOS FUERA SERVICIO
    private Empleado responsable;


    public CambioEstado(
            LocalDateTime fechaHoraInicio,
            LocalDateTime fechaHoraFin,
            Estado estado,
            Empleado responsable
    )
    {
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.estado = estado;
        this.motivoFueraServicio = new ArrayList<>();
        this.responsable = responsable;
    }

    public Boolean sosActual(){
        return this.fechaHoraFin == null;
    }

    public void cargarMotivos(ArrayList<TipoMotivo> motivos, ArrayList<String> comentarios){
        for (int i = 0; i < motivos.size(); i++) {
            MotivoFueraServicio motivoFueraServicio = new MotivoFueraServicio(comentarios.get(i),motivos.get(i));
            this.motivoFueraServicio.add(motivoFueraServicio);
        }
    }

}

