package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class Sesion {
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Usuario usuario;

    public Sesion(LocalDateTime fechaInicio, LocalDateTime fechaFin, Usuario usuario) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.usuario = usuario;
    }

    public Empleado getUsuarioLogueado () {
       return this.usuario.getRILogueado();
    }
}
