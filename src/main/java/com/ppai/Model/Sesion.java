package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class Sesion {
    private Date fechaHoraFin;
    private Date fechaHoraInicio;
    private Usuario usuario;

    public Sesion(Date fechaHoraFin, Date fechaHoraInicio, Usuario usuario) {
        this.fechaHoraFin = fechaHoraFin;
        this.fechaHoraInicio = fechaHoraInicio;
        this.usuario = usuario;
    }
}
