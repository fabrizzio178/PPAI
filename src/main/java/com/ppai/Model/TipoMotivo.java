package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter

public class TipoMotivo {

    private static ArrayList<TipoMotivo> tiposMotivos = new ArrayList<TipoMotivo>();

    private String descripcion;

    public TipoMotivo(
            String descripcion
    ){
        this.descripcion = descripcion;
        tiposMotivos.add(this);
    }
}