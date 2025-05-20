package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Estado {
    private String ambito;
    private String nombreEstado;

    public Estado(
            String ambito,
            String nombreEstado
    ) {
        this.ambito = ambito;
        this.nombreEstado = nombreEstado;
    }
}
