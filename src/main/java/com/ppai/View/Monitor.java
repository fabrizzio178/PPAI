package com.ppai.View;
import com.ppai.Service.GestorResultado;

public class Monitor {
    private GestorResultado gestorResultado;

    public void cerrarOrdenInsp() {
        this.habilitarVentana();
    }

    public void habilitarVentana() {
        this.gestorResultado = new GestorResultado();
        this.gestorResultado.opCerrarOrdenInspeccion();
    }
}
