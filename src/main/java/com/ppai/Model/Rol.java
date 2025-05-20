package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Rol {
    private String descripcionRol;
    private String nombre;

    public Rol(String descripcionRol, String nombre) {
        this.descripcionRol = descripcionRol;
        this.nombre = nombre;
    }
}
