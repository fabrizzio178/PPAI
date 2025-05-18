package com.ppai.Service;

import com.ppai.Model.EstacionSismologica;
import com.ppai.Model.OrdenInspeccion;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GestorResultados {
    private List<OrdenInspeccion> ordenes;

    public GestorResultados(){
        this.ordenes = new ArrayList<>();
        cargarOrdenesHardCodeadas();
    }

    private void cargarOrdenesHardCodeadas(){
        EstacionSismologica estacion1 = new EstacionSismologica("SISMO01", "Estacion Norte");
        EstacionSismologica estacion2 = new EstacionSismologica("SISMO02", "Estacion Sur");

        ordenes.add(new OrdenInspeccion(101, "2025-05-01", estacion1));
        ordenes.add(new OrdenInspeccion(102, "2025-05-01", estacion2));
    }

    public void mostrarOrdenesDisponibles(){
        for (OrdenInspeccion orden : ordenes){
            System.out.println("Numero: " + orden.getNumeroOrden() + " | Estación: " + orden.getEstacion().getNombre()
            + " | Finalizacion: " + orden.getFechaFinalizacion());
        }
    }

    public OrdenInspeccion seleccionarOrdenPorNumero(int numero) {
        for (OrdenInspeccion orden : ordenes) {
            if (orden.getNumeroOrden() == numero) return orden;
        }
        return null;
    }

    public void ingresarObservacion(OrdenInspeccion orden, String observacion) {
        orden.setObservacionCierre(observacion);
        System.out.println("Observación registrada.");
    }

    public void confirmarCierre(OrdenInspeccion orden) {
        String fechaHoraActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        orden.setFechaHoraCierre(fechaHoraActual);
        System.out.println("Orden cerrada con éxito. Fecha de cierre: " + fechaHoraActual);
    }

}
