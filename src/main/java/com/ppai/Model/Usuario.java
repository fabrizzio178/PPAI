package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter

public class Usuario {

    private static ArrayList<Usuario> usuarios = new ArrayList<>();

    private String contrasena;
    private String nombreUsuario;
    private String perfil;
    private String suscripcion;
    private Empleado empleado;

    public Usuario(
            String contrasena,
            String nombreUsuario,
            String perfil,
            String suscripcion,
            Empleado empleado
    ){
        this.contrasena = contrasena;
        this.nombreUsuario = nombreUsuario;
        this.perfil = perfil;
        this.suscripcion = suscripcion;
        this.empleado = empleado;
        usuarios.add(this);
    }

    public String buscarEmpleado() {
        return this.empleado.getNombre() + " " + this.empleado.getApellido();
    }

    public Boolean estasLogueado() {
        return true;
    }

    public Empleado getRILogueado() {
        return this.empleado;
    }
}
