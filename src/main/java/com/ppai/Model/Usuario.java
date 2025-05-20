package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Usuario {
    private String contrasena;
    private String nombreUsuario;
    private String perfil;
    private Boolean suscripcion; // tiene o no una suscripcion
    private Empleado empleado;

    public Usuario(
            String contrasena,
            String nombreUsuario,
            String perfil,
            Boolean suscripcion,
            Empleado empleado
    ) {
        this.contrasena = contrasena;
        this.nombreUsuario = nombreUsuario;
        this.perfil = perfil;
        this.suscripcion = suscripcion;
        this.empleado = empleado;
    }

    public Empleado getRILogueado () {
        return this.empleado;
    }
}
