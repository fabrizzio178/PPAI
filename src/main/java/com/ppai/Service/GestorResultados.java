package com.ppai.Service;

import com.ppai.Model.Empleado;
import com.ppai.Model.EstacionSismologica;
import com.ppai.Model.Estado;
import com.ppai.Model.OrdenDeInspeccion;
import com.ppai.Model.MotivoFueraServicio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestorResultados {
    private List<OrdenDeInspeccion> ordenes;
    private List<MotivoFueraServicio> motivosSeleccionados = new ArrayList<>();

    public GestorResultados() {
        this.ordenes = new ArrayList<>();
        cargarOrdenesHardCodeadas();
    }

    public List<OrdenDeInspeccion> getListaOrdenes() {
        return ordenes;
    }

    public List<MotivoFueraServicio> getListaMotivosDisponibles() {
        // simulación si aún no lo tenés
        return List.of(
                new MotivoFueraServicio("Sin energía", "NO ENERGY"),
                new MotivoFueraServicio("Mantenimiento", "MAINTENANCE"),
                new MotivoFueraServicio("Problema técnico", "TECNICAL PROBLEM")
        );
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

    public OrdenDeInspeccion solicitarSeleccionOrdenesInspeccionRealizadas(int numero) {
        for (OrdenDeInspeccion orden : ordenes) {
            if (orden.getNumeroOrden() == numero) return orden;
        }
        return null;
    }

    public void pedirObservacion(OrdenDeInspeccion orden, String observacion) {
        orden.setObservacionCierre(observacion);
        System.out.println("Observación registrada.");
    }

    public void buscarMotivos(){
        Scanner scanner = new Scanner(System.in);
        List<MotivoFueraServicio> motivos = getListaMotivosDisponibles();
        System.out.println("Motivos disponibles para marcar como fuera de servicio:");
        for (int i = 0; i < motivos.size(); i++){
            MotivoFueraServicio motivo = motivos.get(i);
            System.out.println((i+1) + ". " + motivo.getMotivoTipo() + " - " + motivo.getComentario());
        }

        System.out.println("Seleccione los motivos (numeros separados por espacio, por ejemplo: 1 2 3):");
        String[] entradas = scanner.nextLine().split(" ");

        for (String entrada: entradas){
            try{
                int index = Integer.parseInt(entrada) - 1;
                if (index >= 0 && index < motivos.size()){
                    MotivoFueraServicio motivo = motivos.get(index);
                    System.out.println("Ingrese comentario para '" + motivo.getMotivoTipo() +"':");
                    String comentario = scanner.nextLine();
                    motivo.setComentario(comentario);
                    motivosSeleccionados.add(motivo);
                }
            } catch (NumberFormatException ignored){
            }
        }

        System.out.println("Motivos seleccionados: ");
        for (MotivoFueraServicio motivoSeleccionado: motivosSeleccionados){
            System.out.println("- " + motivoSeleccionado.getMotivoTipo());
        }
    }
    public void tomarConfirmacion(OrdenDeInspeccion orden) {
        String fechaHoraActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        orden.setFechaHoraCierre(fechaHoraActual);
        System.out.println("Orden cerrada con éxito. Fecha de cierre: " + fechaHoraActual);
    }

    public void validarObservacionExistente(OrdenDeInspeccion orden){
        String observacion = orden.getObservacionCierre();
        if (observacion == null || observacion.trim().isEmpty()) {
            System.out.println("No se ingreso ninguna observación");
        } else{
            System.out.println("Observacion registrada: " + observacion);
        }
    }

    public void validarMotivoExistente(){
        if (motivosSeleccionados.isEmpty()){
            System.out.println("No se seleccionaron motivos.");
        } else{
            System.out.println("Hay motivos seleccionados");
        }
    }

    public void buscarEstadoCerradaOI(Estado estado){
        String nombre = estado.getNombreEstado();
        String ambito = estado.getAmbito();

        if ("OrdenInspeccion".equalsIgnoreCase(ambito) && "OICerrada".equalsIgnoreCase(nombre)){
            System.out.println("Estado válido: OICerrada en ámbito OrdenInspeccion");
        }else{
            System.out.println("Estado no coincide con OICerrada/OrdenInspeccion.");
        }
    }








}
