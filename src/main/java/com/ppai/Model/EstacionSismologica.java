package com.ppai.Model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstacionSismologica {
    private String idEstacion;
    private String nombre;

    public EstacionSismologica(String idEstacion, String nombre){
        this.idEstacion = idEstacion;
        this.nombre = nombre;
    }
}
