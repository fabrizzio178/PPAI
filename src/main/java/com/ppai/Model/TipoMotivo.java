package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter

public class TipoMotivo {

    private static ArrayList<TipoMotivo> tiposMotivos = new ArrayList<TipoMotivo>();

    private String descripcion;
    private String nombreTipo;

    public TipoMotivo(
            String descripcion,
            String nombreTipo
    ){
        this.descripcion = descripcion;
        this.nombreTipo = nombreTipo;
        tiposMotivos.add(this);
    }
}