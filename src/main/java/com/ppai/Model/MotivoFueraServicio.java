package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter

public class MotivoFueraServicio {

    private static ArrayList<MotivoFueraServicio> motivosFueraServicio = new ArrayList<>();

    private String comentario;
    private TipoMotivo motivoTipo;

    public MotivoFueraServicio(
            String comentario,
            TipoMotivo motivoTipo
    ){
        this.comentario = comentario;
        this.motivoTipo = motivoTipo;
        motivosFueraServicio.add(this);
    }
}