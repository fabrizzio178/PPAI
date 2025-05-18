package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Empleado {
    private String apellido;
    private String nombre;
    private String mail;
    private String telefono;

    public Empleado(String apellido, String nombre, String mail, String telefono) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.mail = mail;
        this.telefono = telefono;
    }
}
