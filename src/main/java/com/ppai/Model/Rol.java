package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter

public class Rol {

    private static ArrayList<Rol> roles = new ArrayList<>();

    private String descripcion;
    private String nombre;

    public Rol(
            String descripcion,
            String nombre
    ){
        this.descripcion = descripcion;
        this.nombre = nombre;
        roles.add(this);
    }

    public Boolean sosResponsableReparaciones() {
        return this.nombre.equalsIgnoreCase("Responsable de reparaciones");
    }
}
