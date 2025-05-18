package com.ppai.Service;

import com.ppai.Model.Empleado;
import com.ppai.Model.EstacionSismologica;
import com.ppai.Model.Estado;
import com.ppai.Model.OrdenDeInspeccion;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GestorResultados {
    private List<OrdenDeInspeccion> ordenes;

    public GestorResultados() {
        this.ordenes = new ArrayList<>();
        cargarOrdenesHardCodeadas();
    }

    private void cargarOrdenesHardCodeadas() {
        EstacionSismologica estacion1 = new EstacionSismologica(
                "SISMO01",
                "DOC123",
                "2024-03-15",
                -31,
                -64,
                "Estación Norte",
                1001
        );

        EstacionSismologica estacion2 = new EstacionSismologica(
                "SISMO02",
                "DOC456",
                "2024-04-10",
                -32,
                -63,
                "Estación Sur",
                1002
        );

        Empleado empleadoDummy = new Empleado("12345678", "Pipo", "Pipo123@gmail.com", "351392212");
        Estado estadoInicial = new Estado("OrdenInspeccion", "OICerrada");

        ordenes.add(new OrdenDeInspeccion(
                101,
                "2025-05-01 10:00",
                null,
                "2025-04-30 09:00",
                null,
                estacion1,
                estadoInicial,
                empleadoDummy
        ));

        ordenes.add(new OrdenDeInspeccion(
                102,
                "2025-05-01 12:00",
                null,
                "2025-04-30 11:00",
                null,
                estacion2,
                estadoInicial,
                empleadoDummy
        ));
    }

    public void mostrarOrdenesDisponibles() {
        for (OrdenDeInspeccion orden : ordenes) {
            System.out.println(
                    "Número: " + orden.getNumeroOrden()
                            + " | Estación: " + orden.getEstacionSismologica().getNombre()
                            + " | Finalización: " + orden.getFechaHoraFinalizacion()
            );
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
