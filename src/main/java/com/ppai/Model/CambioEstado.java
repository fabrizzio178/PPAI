package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CambioEstado {
    private LocalDateTime fechaHoraFin;
    private LocalDateTime fechaHoraInicio;
    private Estado estado;
    private Empleado responsableInspeccion;
    private List<MotivoFueraServicio> motivoFueraServicio;

    // Constructor principal
    public CambioEstado(
            LocalDateTime fechaHoraInicio,
            LocalDateTime fechaHoraFin,
            Estado estado
    ) {
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.estado = estado;
        this.motivoFueraServicio = new ArrayList<>();
    }

    // Verifica si este estado es el actual (es decir, no tiene fecha de fin)
    public boolean sosActual() {
        return this.fechaHoraFin == null;
    }

    // Asigna motivos
    public void setMotivos(List<MotivoFueraServicio> motivos) {
        this.motivoFueraServicio = new ArrayList<>(motivos);
    }

    // Asigna responsable
    public void setResponsable(Empleado empleado) {
        this.responsableInspeccion = empleado;
    }
}