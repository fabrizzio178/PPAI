package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Rol {
    private String descripcion;
    private String nombre;

    public Rol(
            String descripcion,
            String nombre
    ){
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    public Boolean sosResponsableReparaciones(String nombreRol) {
        return this.nombre.equalsIgnoreCase(nombreRol);
    }
}
