package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MotivoFueraServicio {
    private String descripcion;
    private TipoMotivo tipoMotivo;

    public MotivoFueraServicio(
            String descripcion,
            TipoMotivo tipoMotivo
    ) {
        this.descripcion = descripcion;
        this.tipoMotivo = tipoMotivo;
    }
}