package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

// private ArrayList<Clase> atributo

public class CambioEstado {
    private LocalDateTime fechaHoraFin;
    private LocalDateTime fechaHoraInicio;
    private Estado estado;
    private Empleado responsableInspeccion;
    private ArrayList<MotivoFueraServicio> motivoFueraServicio;

    public CambioEstado(
            LocalDateTime fechaHoraFin,
            LocalDateTime fechaHoraInicio,
            Estado estado,
            Empleado responsableInspeccion,
            ArrayList<MotivoFueraServicio> motivoFueraServicio
    ){
        this.fechaHoraFin = fechaHoraFin;
        this.fechaHoraInicio = fechaHoraInicio;
        this.estado = estado;
        this.responsableInspeccion = responsableInspeccion;
        this.motivoFueraServicio = motivoFueraServicio;
    };

    public boolean sosActual () {
        if (fechaHoraFin == null) return true;
        else return false;

    };

    public void setFechaHoraFin() {
        this.fechaHoraFin = LocalDateTime.now();
    };

    /*
      public void setMotivo(ArrayList<MotivoFueraServicio> motivoFueraServicio) {
        this.motivoFueraServicio = new ArrayList<>(motivoFueraServicio);
      }
      */

    public void cargarMotivos(List<TipoMotivo> tiposMotivosSeleccionados, List<String> comentarios) {

        for (int i = 0; i < tiposMotivosSeleccionados.size(); i++ ) {
            TipoMotivo tipo = tiposMotivosSeleccionados.get(i);
            String comentario = comentarios.get(i);
            MotivoFueraServicio motivo = new MotivoFueraServicio(comentario,tipo);
            this.motivoFueraServicio.add(motivo);
        };
    }

}

