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
    ){
        this.ambito = ambito;
        this.nombreEstado = nombreEstado;
    }

    public Boolean sosCompletamenteRealizada() {
        return this.nombreEstado.equalsIgnoreCase("completamente realizada");
    }

    public Boolean sosOICerrada() {
        return this.nombreEstado.equalsIgnoreCase("Cerrada");
    }

    public Boolean sosFueraDeServicio() {
        return this.nombreEstado.equalsIgnoreCase("Fuera de servicio");
    }

    public Boolean sosAmbitoOrdenInspeccion() {
        return this.ambito.equalsIgnoreCase("Orden Inspeccion");
    }

    public Boolean sosAmbitoSismografo() {
        return this.ambito.equalsIgnoreCase("Sismografo");
    }
}
