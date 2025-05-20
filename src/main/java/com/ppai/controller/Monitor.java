package com.ppai.controller;
import com.ppai.app.*;


import com.ppai.Service.GestorResultados;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class Monitor {
    private GestorResultados gestor;

    public void cerrarOrdenInsp() {
        // Simula la interacción del actor con Monitor
        habilitarVentana();  // Se ajusta al orden del diagrama
    }

    public void habilitarVentana() {
        this.gestor = new GestorResultados();
        this.gestor.setMonitor(this);
        this.gestor.opCerrarOrdenInspeccion();  // Llamada reflejada en el diagrama
    }


    public void solicitarSeleccionOrdenesInspeccionRealizada() {
//        ArrayList<ArrayList<Object>> arrayDatosOi = this.gestor.getDatosOrdenes();
//        for (ArrayList<Object> oi : arrayDatosOi) {
//            int nroOrden = (int) oi.get(0);
//            LocalDateTime fechaHoraInicio = (LocalDateTime) oi.get(1);
//            String nombreEstacion = (String) oi.get(2);
//            String identificadorSismografo = (String) oi.get(3);
//
//            System.out.println("Numero de orden: " + nroOrden + " Fecha y hora inicio: " + fechaHoraInicio + " Nombre estacion: " + nombreEstacion + " Identificador del sismografo: " + identificadorSismografo);
//        }
    

    }
    public void seleccionaOrdenInsp(int nroOrden) {
        gestor.tomarSeleccionOrdenInsp(nroOrden);
        System.out.println("Orden seleccionada: " + gestor.getOrdenSeleccionada().getNumeroOrden());
    }
}

