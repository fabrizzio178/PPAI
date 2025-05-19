package com.ppai.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class OrdenDeInspeccion {
    private Integer numeroOrden;
    private LocalDateTime fechaHoraFinalizacion;
    private LocalDateTime fechaHoraCierre;
    private LocalDateTime fechaHoraInicio;
    private String observacionCierre;
    private EstacionSismologica estacionSismologica;
    private Estado estado;
    private Empleado empleado;

    public OrdenDeInspeccion(
            Integer numeroOrden,
            LocalDateTime fechaHoraFinalizacion,
            LocalDateTime fechaHoraCierre,
            LocalDateTime fechaHoraInicio,
            String observacionCierre,
            EstacionSismologica estacionSismologica,
            Estado estado,
            Empleado empleado
    ){
        this.numeroOrden = numeroOrden;
        this.fechaHoraFinalizacion = fechaHoraFinalizacion;
        this.fechaHoraCierre = fechaHoraCierre;
        this.fechaHoraInicio = fechaHoraInicio;
        this.observacionCierre = observacionCierre;
        this.estacionSismologica = estacionSismologica;
        this.estado = estado;
        this.empleado = empleado;
    }

    public Boolean sosDeEmpleado(Empleado empleado) {
        return this.empleado.getNombre().equalsIgnoreCase(empleado.getNombre()) &&
                this.empleado.getApellido().equalsIgnoreCase(empleado.getApellido());
    }

    public Boolean sosCompletamenteRealizada() {
        return this.estado.getNombreEstado().equalsIgnoreCase("completamente realizada");
    }

    public ArrayList<Object> getDatosIO() {
        ArrayList<Object> datos = new ArrayList<>();
        datos.add(this.numeroOrden);
        datos.add(this.fechaHoraFinalizacion);
        return datos;
    };

    public String buscarNombreEstacion() {
        return this.estacionSismologica.getNombre();
    }

    public void setFechaHoraCierre() {
        this.fechaHoraCierre = LocalDateTime.now();
    }

    public void setEstado(String nombreEstado, List<Estado> estados) {  //nombre del estado, lista con todas las instancias de Estado
        for (Estado estado : estados) {
            if (estado.getNombreEstado().equalsIgnoreCase(nombreEstado)) {
                this.estado = estado;
                break;
            }
        }
    }

    public void cerrar() {
        List<Estado> estados = new ArrayList<>(); //creo lista vacia de estados porque no se como CARAJO acceder a todos los estados desde acá
        this.setFechaHoraCierre();
        this.setEstado("Cerrada", estados);
    }
}
