package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TipoMotivo {
    private String descripcion;

    public TipoMotivo(
            String descripcion
    ){
        this.descripcion = descripcion;
    }
}