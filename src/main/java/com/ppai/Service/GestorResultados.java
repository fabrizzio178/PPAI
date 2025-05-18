package com.ppai.Service;

import com.ppai.Model.EstacionSismologica;
import com.ppai.Model.OrdenDeInspeccion;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GestorResultados {
    private List<OrdenDeInspeccion> ordenes;

    public GestorResultados(){
        this.ordenes = new ArrayList<>();
        cargarOrdenesHardCodeadas();
    }

    private void cargarOrdenesHardCodeadas(){
        EstacionSismologica estacion1 = new EstacionSismologica("SISMO01", "Estacion Norte");
        EstacionSismologica estacion2 = new EstacionSismologica("SISMO02", "Estacion Sur");

        ordenes.add(new OrdenDeInspeccion(101, "2025-05-01", estacion1));
        ordenes.add(new OrdenDeInspeccion(102, "2025-05-01", estacion2));
    }

    public void mostrarOrdenesDisponibles(){
        for (OrdenDeInspeccion orden : ordenes){
            System.out.println("Numero: " + orden.getNumeroOrden() + " | Estación: " + orden.getEstacion().getNombre()
            + " | Finalizacion: " + orden.getFechaFinalizacion());
        }
    }

    public OrdenDeInspeccion seleccionarOrdenPorNumero(int numero) {
        for (OrdenDeInspeccion orden : ordenes) {
            if (orden.getNumeroOrden() == numero) return orden;
        }
        return null;
    }

    public void ingresarObservacion(OrdenDeInspeccion orden, String observacion) {
        orden.setObservacionCierre(observacion);
        System.out.println("Observación registrada.");
    }

    public void confirmarCierre(OrdenDeInspeccion orden) {
        String fechaHoraActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        orden.setFechaHoraCierre(fechaHoraActual);
        System.out.println("Orden cerrada con éxito. Fecha de cierre: " + fechaHoraActual);
    }

}
