package com.ppai.Service;

import com.ppai.Model.*;
import com.ppai.View.Monitor;
import lombok.Getter;
import lombok.Setter;
import com.ppai.DAO.*;


import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;


@Setter
@Getter
public class GestorResultado {
    // Arrays con todas las instancias de cada clase que el gestor conoce
    private ArrayList<Sismografo> todosSismografos; // ✅
    private ArrayList<OrdenDeInspeccion> todosOrdenInspeccion; // ✅
    private ArrayList<Empleado> todosEmpleados; // ✅
    private Sesion sesionActual;
    private ArrayList<Estado> todosEstados; // ✅
    private ArrayList<TipoMotivo> todosTipoMotivo; // ✅
    private Monitor monitor; // monitor
    // Atributos del gestor de ordenes de inspeccion
    private String ordenInspSeleccionada;
    private OrdenDeInspeccion ordenInspSeleccionadaObj;
    private String observacion;
    private ArrayList<String> tiposMotivosFueraDeServicio;
    private ArrayList<String> tiposMotivosFueraDeServicioSeleccionados;
    private ArrayList<String> comentarios;
    private String estadoOrdenInspeccionCerrada;
    private Estado estadoOrdenInspeccionCerradaObj;
    private LocalDateTime fechaHoraActual;
    private String estadoSismografoFueraDeServicio;
    private Empleado usuarioLogueado;
    private ArrayList<OrdenDeInspeccion> ordenesInsp;
    private ArrayList<ArrayList<Object>> datosOrdenInsp;

    private Boolean banderaSeleccion;



    public GestorResultado() throws SQLException {
//        RolDAO rolDAO = new RolDAO();
//        ArrayList<Rol> roles = rolDAO.getAllRoles();

        // FALTA HACER LA SESION EN LA BDA

        TipoMotivoDAO tipoMotivoDAO = new TipoMotivoDAO();
        this.todosTipoMotivo = tipoMotivoDAO.getAllTiposMotivo();

        EstadoDAO estadoDAO = new EstadoDAO();
        this.todosEstados = estadoDAO.getAllEstados();

        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        this.todosEmpleados = empleadoDAO.getAllEmpleados();

        // CambioEstadoDAO cambioEstadoDAO = new CambioEstadoDAO();

        // EstacionSismologicaDAO estacionSismologicaDAO = new EstacionSismologicaDAO();

        // UsuarioDAO usuarioDAO = new UsuarioDAO();

        SismografoDAO sismografoDAO = new SismografoDAO();
        this.todosSismografos = sismografoDAO.getAllSismografos();

        OrdenDeInspeccionDAO ordenDeInspeccionDAO = new OrdenDeInspeccionDAO();
        this.todosOrdenInspeccion = ordenDeInspeccionDAO.getAllOrdenes();
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
                                "IL@gmail.com",
                                "11111111",
                                new Rol(
                                        "---",
                                        "Administrador de Red"
                                )
                        )
                )
        );
    }




    // 2 - Funcion que va a disparar todas las funciones del gestor (creo).
    public void opCerrarOrdenInspeccion() {
        this.hardcodearSesion();



        this.buscarRILogueado(); // 3
        this.buscarOrdenesInsp(); // 4
        ordenarPorFechaFinalizacion(); // 5
        this.mostrarDatos(this.todosSismografos); // 6
        monitor.solicitarSeleccionOrdenesInspeccionRealizadas(datosOrdenInsp); // 7 y 8
        //pedirObservacion();


        // monitor.solicitarSelTipoMotivo();

        //monitor.solicitarConfirmacion();
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
            if(orden.sosCompletamenteRealizada() && orden.sosDeEmpleado(this.usuarioLogueado)){
                ordenesInsp.add(orden);
            }
        }
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
        }
    }

    // 9 - Toma la seleccion del usuario cuando se ejecuta previamente la funcion del monitor de pedir que seleccione.

    public void tomarSeleccionOrdenInsp(String ordenInsp) {
        this.ordenInspSeleccionada = ordenInsp;

        // Buscar la orden real y setearla
        for (OrdenDeInspeccion orden : ordenesInsp) {
            if (String.valueOf(orden.getNumeroOrden()).equals(ordenInsp)) {
                this.ordenInspSeleccionadaObj = orden;
                break;
            }
        }

        this.pedirObservacion();
    }

    // 10 - Dispara al monitor para que pida una observacion.

    public void pedirObservacion() {
        monitor.pedirObservacion();
    }

    // 13 - Toma la observacion del usuario ingreada por teclado en el monitor
    public void tomarObservacion(String observacion){
        this.observacion = observacion;
        this.permitirActualizarSismografoComoFS();
        this.buscarMotivos();
    }

    // 14 - Permite acutalizar??? ¯\_(ツ)_/¯
    public void permitirActualizarSismografoComoFS(){

    }

    // 15 - Buscar y mostrar tipos motivos.

    public void buscarMotivos() {
        this.tiposMotivosFueraDeServicio = new ArrayList<>();
        for (TipoMotivo tipoMotivo : todosTipoMotivo ) {
            tiposMotivosFueraDeServicio.add(tipoMotivo.getDescripcion());
        }
        monitor.mostrarTiposMotivos(tiposMotivosFueraDeServicio);
        this.solicitarSelMotivos();
    }

    // 16** (NO SE SI ES 16)
    public void solicitarSelMotivos() {
        this.tiposMotivosFueraDeServicioSeleccionados = new ArrayList<>();
        this.comentarios = new ArrayList<>();
        this.banderaSeleccion = true;

        monitor.solicitarSelTipoMotivo(tiposMotivosFueraDeServicio);


        /* while (banderaSeleccion) {
            monitor.solicitarSelTipoMotivo();
        }
        monitor.solicitarConfirmacion(); */

        /*if (banderaSeleccion) {
            monitor.solicitarSelTipoMotivo();
        } else {
            monitor.solicitarConfirmacion();
        }*/
    }


    // 19 - Toma los tipos de datos ingresados en el loop del monitor.

    public void tomarTipoMotivo (String tipo) {

        this.tiposMotivosFueraDeServicioSeleccionados.add(tipo);
        monitor.pedirComentario();
    }

    // 22 - Toma el comentario ingreado por teclado en el monitor.

    public void tomarComentario(String comentario) {
        this.comentarios.add(comentario);

    }

    // 25 - Validar que la observacion no este vacia y haya elegido al menos un motivo.
    // Si esto da false es un caso alternativo.
    public boolean validarObservacionExistente() {
        if (observacion.isEmpty()) {
            return false;
        } else {
            return validarMotivoExistente(); // No se si esto deberia ir por fuera o no del metodo, por el diagrama de secuencia
        }
    }

    // 26- Validar motivo existente, podria ir en la funcion 25 pero hay que respetar el diagrama de secuencia.

    public boolean validarMotivoExistente (){
        return !tiposMotivosFueraDeServicioSeleccionados.isEmpty();
    }

    // 27 - Busca en todos los estados y pregunta si es ambito orden inspeccion y si es cerrada.
    public void BuscarEstadoCerradaOI() {
        for (Estado estado:todosEstados ){
            if (estado.sosAmbitoOrdenInspeccion() && estado.sosOICerrada()){ // Creo que aca hay una inconsistencia con el diagrama de secuencia.
                this.estadoOrdenInspeccionCerrada = estado.getNombreEstado();
            }
        }
    }

    // 28 - Toma la fecha actual y la almacena.
    public void getFechaHoraActual() {
        this.fechaHoraActual = LocalDateTime.now();
    }

    // 29 - Busca en todos los estados y pregunta si es ambito sismografo y si es fuera de servicio.

    public void buscarFueraDeServicio(){
        for (Estado estado: todosEstados ){
            if (estado.sosAmbitoSismografo() && estado.sosFueraDeServicio()){
                this.estadoSismografoFueraDeServicio = estado.getNombreEstado(); // Creo que aca esta la misma inconsistencia que en la anterior
            }
        }
    }

    // 30 - Llama al metodo cerrar de la orden de inspecion. cerrar() setea un estado y setea la fechaHoraCierre
    public void cerrarOrdenInspeccion() {
        this.ordenInspSeleccionadaObj.cerrar(estadoOrdenInspeccionCerradaObj);
    }


    // 31 - PonerSismografoFueraDeServicio
    // Este método esta asi porque no teniamos al sismografo previamente seleccionado. Ademas, se respetan las
    // relaciones dadas en la vista estatica de la realizacion de CU de análisis
    public void ponerSismografoFueraDeServicio(){
        this.ordenInspSeleccionadaObj.getEstacionSismologica().getSismografo(todosSismografos).fueraDeServicio(fechaHoraActual);
    }

}
