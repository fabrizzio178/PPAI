package com.ppai.Service;

import com.ppai.Model.*;
import com.ppai.View.Monitor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

@Setter
@Getter
public class GestorResultado {
    // Arrays con todas las instancias de cada clase que el gestor conoce
    private ArrayList<Sismografo> todosSismografos;
    private ArrayList<OrdenDeInspeccion> todosOrdenInspeccion;
    private ArrayList<Empleado> todosEmpleados;
    private Sesion sesionActual;
    private ArrayList<Estado> todosEstados;
    private ArrayList<TipoMotivo> todosTipoMotivo;
    private Monitor monitor;
    private String ordenInspSeleccionada;
    private String observacion;
    private ArrayList<String> tiposMotivosFueraDeServicio;

    public void hardcodearTipoMotivo() {
        TipoMotivo tipoMotivo = new TipoMotivo("NO ANDA");
        this.todosTipoMotivo = new ArrayList<>();
        this.todosTipoMotivo.add(tipoMotivo);

    }

    public void hardcodearSesion() {
        this.sesionActual = new Sesion(
                LocalDateTime.now(),
                null,
                new Usuario(
                        "1234",
                        "xX_Pipo_Xx",
                        "TETA",
                        1,
                        new Empleado(
                                "Llabot",
                                "Ignacio",
                                "sex@gmail.com",
                                "1234567890",
                                new Rol(
                                        "Programador de juegos porno 3D",
                                        "Programador"
                                )
                        )
                )
        );
    }

    public void hardcodearOrden () {
        this.todosOrdenInspeccion = new ArrayList<>();
        OrdenDeInspeccion nuevaOrden = new OrdenDeInspeccion(
                        100,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        "No anda",
                        "Mantenimiento",
                        new EstacionSismologica(
                                50,
                                "Documento",
                                LocalDateTime.now(),
                                80.00,
                                60.00,
                                "Estacion Gay",
                                20),
                        new Estado(
                                "Estacion",
                                "Descompuesto"),
                        new Empleado(
                                "Llabot",
                                "Ignacio",
                                "sex@gmail.com",
                                "1234567890",
                                new Rol ("Programador de juegos porno 3D",
                                        "Programador")
                        )
        );

        this.todosOrdenInspeccion.add(nuevaOrden);

    }

    //Atributos del gestor
    private Empleado usuarioLogueado;
    private ArrayList<OrdenDeInspeccion> ordenesInsp;
    private ArrayList<ArrayList<Object>> datosOrdenInsp;

    // 2 - Funcion que va a disparar todas las funciones del gestor (creo).
    public void opCerrarOrdenInspeccion() {
        this.hardcodearSesion();
        this.hardcodearOrden();
        this.hardcodearTipoMotivo();

        this.buscarRILogueado();
        this.buscarOrdenesInsp();
        ordenarPorFechaFinalizacion();
        this.mostrarDatos(this.todosSismografos);
        monitor.solicitarSeleccionOrdenesInspeccionRealizadas(datosOrdenInsp);
        pedirObservacion();
        permitirActualizarSismografoComoFS(); // ?????????
        buscarMotivos();
        monitor.mostrarTiposMotivos(tiposMotivosFueraDeServicio);
        monitor.solicitarSelTipoMotivo(tiposMotivosFueraDeServicio);

    }
    // 3 - Busca al responsable de inspeccion logueado (le pregunta a la sesion actual).

    public void buscarRILogueado() {
        this.usuarioLogueado = sesionActual.getUsuarioLogueado();
    }

    // 4 - Busca las ordenes de inspeccion del responsable de inspeccion logueado, recorre TODAS las ordenes y
    // pregunta las 2 condiciones, las que cumple las añade al array creado.

    public void buscarOrdenesInsp() {
        this.ordenesInsp = new ArrayList<>(); // porque carajo crea esto perdon todavia no se
        for(OrdenDeInspeccion orden : todosOrdenInspeccion) {
            if(orden.sosDeEmpleado(this.usuarioLogueado) && orden.sosCompletamenteRealizada()){
                ordenesInsp.add(orden);
            }
        }
        ;
    }

    // 5 - Las ordena y listo.

    public void ordenarPorFechaFinalizacion() {
        ordenesInsp.sort(Comparator.comparing(OrdenDeInspeccion::getFechaHoraFinalizacion));
    }

    // 6 - Se llama mostrar datos y hace de todo menos mostrar los datos, lo que hace es guardar los datos de las ordenes --> POR QUE ES VERDE???
    // en un array y como resultado final te da un array de arrays de objetos, terrible.
    public void mostrarDatos (ArrayList<Sismografo> todosSismografos) {
        this.datosOrdenInsp = new ArrayList<>();    // Tengo dudas by: salva
        for(OrdenDeInspeccion orden : ordenesInsp) {
            datosOrdenInsp.add(orden.getDatosOI(todosSismografos));
        };
    }

    // 9 - Toma la seleccion del usuario cuando se ejecuta previamente la funcion del monitor de pedir que seleccione.

    public void tomarSeleccionOrdenInsp(String ordenInsp) {
        this.ordenInspSeleccionada = ordenInsp;
    }

    // 10 - Dispara al monitor para que pida una observacion.

    public void pedirObservacion() {
        monitor.pedirObservacion();
    }

    // 13 - Toma la observacion del usuario ingreada por teclado en el monitor
    public void tomarObservacion(String observacion){
        this.observacion = observacion;
    }

    // 14 - Permite acutalizar??? ¯\_(ツ)_/¯
    public void permitirActualizarSismografoComoFS(){

    }

    // 15 - Buscar y mostrar tipos motivos.

    public void buscarMotivos() {
        ArrayList<String> tiposMotivosFueraDeServicio = new ArrayList<String>();
        for (TipoMotivo tipoMotivo : todosTipoMotivo ) {
            tiposMotivosFueraDeServicio.add(tipoMotivo.getDescripcion());
        }
    }
}
