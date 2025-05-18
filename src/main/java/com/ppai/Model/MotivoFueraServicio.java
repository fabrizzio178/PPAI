package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MotivoFueraServicio {
    private String comentario;
    private String motivoTipo;

    public MotivoFueraServicio(
            String comentario,
            String motivoTipo
    ){
        this.comentario = comentario;
        this.motivoTipo = motivoTipo;
    }
}