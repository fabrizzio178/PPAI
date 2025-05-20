package com.ppai.Service;
import com.ppai.Model.*;

import com.ppai.controller.Monitor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;


@Getter
@Setter

public class GestorResultados {
    private Empleado usuarioLogueado; // usado funcion1  ----> guarda el puntero al empleado
    private ArrayList<OrdenDeInspeccion> ordenesInsp; // usado fucion 1-2-3-5 ---> guarda las Ord de Insp del empleado y completamente realizadas //PREDEFINIDO
    private OrdenDeInspeccion ordenSeleccionada; // usado funcion 5-7-18-19 ----> guarda 1 OI seleccionada por el RI
    private ArrayList<ArrayList<Object>> datosOrdenes;
    private String observacion;
    private ArrayList<TipoMotivo> tiposMotivosFueraDeServicio; // usado funcion 10-14-19----> guarda 1 o mas motivos seleccionado por el usuario
    private Estado estadoOrdenInspeccionCerrada; // usado funcion  15-18 ----> guarda el nombre del estado de la OI (cerrada)
    private ArrayList<String> comentarios; // usado funcion 11-19 ----> guarda 1 comntario ingresado por el usuario para 1 tipo seleccionado
    private Boolean confirmacion;
    private LocalDateTime fechaHoraActual; // usado funcion 16-18-19 ----> guarda la fecha y la hora actual
    private Estado estadoSismografoFueraDeServicio; // usado funcion 17-19 ----> guarda el nombre del estado del Sismografo (fuera de servicio)
    private ArrayList<String> mailsEmpleados; //PREFEDINIDO
    private ArrayList<Usuario> usuarios; //TODOS LOS USUARIOS //PREFEDINIDO
    private ArrayList<Sismografo> sismografos; //TODOS LOS SISMOGRAFOS usado funcion 19 //PREFEDINIDO
    private Sesion sesion; //PREFEDINIDO
    private ArrayList<Empleado> empleados; //PREFEDINIDO
    private Monitor monitor;

    public void hardcodearUsuarios() {
        this.usuarios = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            // Crear Rol
            Rol rol = new Rol(
                    "Descripción del rol " + (i + 1),
                    "Nombre del rol " + (i + 1)
            );

            //Crear Empleado
            Empleado empleado = new Empleado(
                    "Apellido" + (i + 1),
                    "Nombre" + (i + 1),
                    "empleado" + (i + 1) + "@mail.com",
                    "351-" + (i + 1000),
                    rol
            );

            // Crear Usuario
            Usuario usuario = new Usuario(
                    "contraseña" + (i + 1),
                    "usuario" + (i + 1),
                    "perfil" + (i + 1),
                    "suscripcion" + (i + 1),
                    empleado
            );


            usuarios.add(usuario);
        }
        this.sesion = new Sesion(null, null, this.usuarios.get(0));;
    }


    public void hardcodearOrdenesDeInspeccion() {
        this.ordenesInsp = new ArrayList<>();

        Estado estadoCerrada = new Estado("Orden Inspeccion", "completamente realizada");
        for (int i = 0; i < 3; i++) {
            OrdenDeInspeccion ord = new OrdenDeInspeccion(
                    i,
                    LocalDateTime.now().plusDays(i),
                    null,
                    null,
                    "obs " + i,
                    new EstacionSismologica(
                            "" + i,
                            "",
                            LocalDateTime.now(),
                            0,
                            0,
                            "Estación " + (i + 1),
                            1
                    ),
                    estadoCerrada,
                    new Empleado(
                            "Apellido1",
                            "Nombre1",
                            "empleado" + i + "@mail.com",
                            "351-0000" + i,
                            null
                    )
            );

            // 🚨 Esto es lo que faltaba
            this.ordenesInsp.add(ord);
        }

    }

    public void hardCodearSismografos() {
        Estado estadoFueraDeServicio = new Estado("Sismografo", "Fuera de Servicio");
        ArrayList<CambioEstado> cambiosEstado = new ArrayList<>();

        CambioEstado cambioEstado = new CambioEstado(
                null,
                LocalDateTime.now(),
                estadoFueraDeServicio,
                this.usuarios.get(1).getRILogueado(),
                null
        );

        cambiosEstado.add(cambioEstado);

        EstacionSismologica estacionSismologica1 = new EstacionSismologica(
                "1",
                "",
                LocalDateTime.now(),
                0,
                0,
                "Estacion2",
                1
        );
        Sismografo sismografo1 = new Sismografo(
                null,
                "1",
                "1000",
                "",
                "S1000",
                "",
                estacionSismologica1,
                estadoFueraDeServicio,
                cambiosEstado

        );
        EstacionSismologica estacionSismologica2 = new EstacionSismologica(
                "456",
                "",
                LocalDateTime.now(),
                30,
                40,
                "Buenos Aires",
                2
        );
        Sismografo sismografo2 = new Sismografo(
                null,
                "2",
                "1001",
                "",
                "S2000",
                "",
                estacionSismologica2,
                estadoFueraDeServicio,
                cambiosEstado

        );
        this.sismografos = new ArrayList<>();
        this.sismografos.add(sismografo1);
        this.sismografos.add(sismografo2);
    }


    public void opCerrarOrdenInspeccion() {
        this.hardcodearUsuarios();
        this.hardCodearSismografos();
        this.hardcodearOrdenesDeInspeccion();



        this.buscarRIlogueado();
        this.buscarOrdenesInsp(this.ordenesInsp);
        this.ordenarPorFechaFinalizacion();
        this.mostrarDatos(this.ordenesInsp, this.sismografos);
        monitor.solicitarSeleccionOrdenesInspeccionRealizada();
    }

    // 1era función que busca el RI logueado
    public Empleado buscarRIlogueado() {
        return sesion.getUsuarioLogueado();
    }


    // 2
    public void buscarOrdenesInsp(ArrayList<OrdenDeInspeccion> ordenesInsp) { //es la mitad del loop grande, creo que el resto se puede implementar en el mostrar
        Empleado empleado = buscarRIlogueado();                             //si no hay que crear otro atributo para los datos (inneccesario) y el nombre de la estacion
        ArrayList<OrdenDeInspeccion> ordenesInspSel = new ArrayList<>();
        for (OrdenDeInspeccion orden : ordenesInsp) {
            Boolean esDeEmpleado = orden.sosDeEmpleado(empleado);
            Boolean esCompletamenteRealizada = orden.sosCompletamenteRealizada();

            if (esDeEmpleado && esCompletamenteRealizada) {
                ordenesInspSel.add(orden);
            }
        }
        this.ordenesInsp = ordenesInspSel;
    }
    // 3
    public void ordenarPorFechaFinalizacion() {
        this.ordenesInsp.sort(Comparator.comparing(OrdenDeInspeccion::getFechaHoraFinalizacion));
    }
    // 4
    public void mostrarDatos(ArrayList<OrdenDeInspeccion> ordenesInsp, ArrayList<Sismografo> todosSismografos) {
        ArrayList<ArrayList<Object>> datosOrdenes = new ArrayList<>();
        for (OrdenDeInspeccion orden : ordenesInsp) {
            ArrayList<Object> datosOrden = orden.getDatosOI(todosSismografos);
            if (datosOrden != null && !datosOrden.isEmpty()) {
                datosOrdenes.add(datosOrden);
            }
        }
        this.datosOrdenes = datosOrdenes;
        System.out.println(ordenesInsp.size() + " OI encontradas.");
    }
    // 5
    public void tomarSeleccionOrdenInsp(int numeroOrden) {
        for (OrdenDeInspeccion orden : ordenesInsp) {
            if (orden.getNumeroOrden() == numeroOrden) {
                this.ordenSeleccionada = orden;
                break;
            }
        }
    }
    // 6
    public void pedirObservacion(OrdenDeInspeccion orden, String observacion) {
        // disparador para pantalla, aca llama al metodo de pantalla
    }
    // 7
    public void tomarObservacion(String observacion) {
        this.ordenSeleccionada.setObservacionCierre(observacion);
        System.out.println("Observación registrada.");
    }
    // 8
    public void permitirActualizarSismografoComoFS() {
        System.out.println("Se ha permitido actualizar el sismografo como fuera de servicio.");
    }
    // 9
    public void buscarMotivos(ArrayList<TipoMotivo> tiposMotivo) {

        System.out.println("Motivos disponibles para marcar como fuera de servicio:");
        for (TipoMotivo tipo : tiposMotivo) {
            System.out.println(tipo.getNombreTipo() + " - " + tipo.getDescripcion());  // ESTO DEBE IR A PANTALLA NO A UN PRINT
        }
    }
    // 10
    public void tomarTiposMotivo(TipoMotivo selecTipoMotivo) {
        this.tiposMotivosFueraDeServicio.add(selecTipoMotivo);  //guarda la seleccion del usuario
    }
    // 11
    public void tomarComentarios(String comentario) {
        this.comentarios.add(comentario);  //guarda los comentarios del usuario
    }
    // 12
    public void tomarConfirmacion(OrdenDeInspeccion orden) {
        LocalDateTime fechaHoraActual = LocalDateTime.now(); //.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        orden.setFechaHoraCierre(fechaHoraActual);
    }
    // 13
    public boolean validarObservacionExistente(OrdenDeInspeccion orden) {
        String observacion = orden.getObservacionCierre(); // Consigue la observacion de la orden seleccionada
        return observacion != null && !observacion.trim().isEmpty(); // retorna TRUE si existe la observacion y no esta vacia
    }
    // 14
    public boolean validarMotivoExistente() {
        return !tiposMotivosFueraDeServicio.isEmpty(); //valida que se haya elegido por lo menos un motivo
    }
    // 15
    public void buscarEstadoCerradaOI(ArrayList<Estado> estados) {
        for(Estado estado : estados) {
            if(estado.sosAmbitoOrdenInspeccion() && estado.sosOICerrada()) {
                this.estadoOrdenInspeccionCerrada = estado;
            }
        }
    }
    // 16
    public void getFechaHoraActual() {
        this.fechaHoraActual = LocalDateTime.now();
    }
    // 17
    public void buscarFueraServicio(ArrayList<Estado> estados) {
        for (Estado estado : estados){
            if(estado.sosAmbitoSismografo() && estado.sosFueraDeServicio()){
                this.estadoSismografoFueraDeServicio = estado;
            }
        }
    }
    // 18
    public void cerrarOrdenInspeccion() {
        ordenSeleccionada.cerrar(fechaHoraActual, estadoOrdenInspeccionCerrada);
    }
    // 19
    public void ponerSismografoFueraDeServicio() {
        if (ordenSeleccionada != null) {
        for (Sismografo sismografo : sismografos) {
            if (ordenSeleccionada.getEstacionSismologica().sosMiSismografo(sismografo)) {
                sismografo.fueraDeServicio(this.tiposMotivosFueraDeServicio, this.comentarios, this.fechaHoraActual, this.estadoSismografoFueraDeServicio, buscarRIlogueado());
                break;
                }
            }
        }
    }
    public void notificarMonitor(){
        System.out.println("Monitor CCRS Notificado.");
    }
    //20
    public void enviarMail(ArrayList<Empleado> empleados){
        for (Empleado empleado : empleados) {
            if (empleado.sosResponsableReparaciones()){
                String mail = empleado.obtenerMail();
                // agregamos el mail al array de mails
                this.mailsEmpleados.add(mail);
            }
        }
        // Esto despues se cambia pero lo dejamos para que haya consistencia

        System.out.println("Mails enviado a los responsables de reparaciones.");
        notificarMonitor();
    }


}
/*
package com.ppai.Service;

import com.ppai.Model.*;
import com.ppai.controller.Monitor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

@Getter
@Setter
public class GestorResultados {
    // ... [atributos sin cambios] ...

    public void mostrarDatos(ArrayList<OrdenDeInspeccion> ordenesInsp, ArrayList<Sismografo> todosSismografos) {
        ArrayList<ArrayList<Object>> datosOrdenes = new ArrayList<>();
        for (OrdenDeInspeccion orden : ordenesInsp) {
            ArrayList<Object> datosOrden = orden.getDatosOI(todosSismografos);
            if (datosOrden != null && !datosOrden.isEmpty()) {
                datosOrdenes.add(datosOrden);
                // Mostrar datos en consola
                System.out.println("\nOrden de Inspección N°: " + datosOrden.get(0));
                System.out.println("Fecha de Fin: " + datosOrden.get(1));
                System.out.println("Nombre Estación: " + datosOrden.get(2));
                System.out.println("Estado Sismógrafo: " + datosOrden.get(3));
            }
        }
        this.datosOrdenes = datosOrdenes;
        System.out.println("Total de órdenes encontradas: " + ordenesInsp.size());
    }

    public void buscarMotivos(ArrayList<TipoMotivo> tiposMotivo) {
        System.out.println("\nMotivos disponibles para marcar como fuera de servicio:");
        for (TipoMotivo tipo : tiposMotivo) {
            System.out.println("- " + tipo.getNombreTipo() + ": " + tipo.getDescripcion());
        }
    }

    public void tomarObservacion(String observacion) {
        this.ordenSeleccionada.setObservacionCierre(observacion);
        System.out.println("\nObservación registrada correctamente.");
    }

    public void permitirActualizarSismografoComoFS() {
        System.out.println("\nSe ha habilitado la opción para actualizar el sismógrafo como fuera de servicio.");
    }

    public void tomarComentarios(String comentario) {
        this.comentarios.add(comentario);
        System.out.println("\nComentario guardado.");
    }

    public void notificarMonitor() {
        System.out.println("\nEl monitor del CCRS ha sido notificado.");
    }

    public void enviarMail(ArrayList<Empleado> empleados) {
        for (Empleado empleado : empleados) {
            if (empleado.sosResponsableReparaciones()) {
                String mail = empleado.obtenerMail();
                this.mailsEmpleados.add(mail);
                System.out.println("Correo enviado a: " + mail);
            }
        }
        notificarMonitor();
    }

    public void cerrarOrdenInspeccion() {
        ordenSeleccionada.cerrar(fechaHoraActual, estadoOrdenInspeccionCerrada);
        System.out.println("\nOrden de inspección cerrada correctamente.");
    }

    public void ponerSismografoFueraDeServicio() {
        if (ordenSeleccionada != null) {
            for (Sismografo sismografo : sismografos) {
                if (ordenSeleccionada.getEstacionSismologica().sosMiSismografo(sismografo)) {
                    sismografo.fueraDeServicio(this.tiposMotivosFueraDeServicio, this.comentarios, this.fechaHoraActual, this.estadoSismografoFueraDeServicio, buscarRIlogueado());
                    System.out.println("\nEl sismógrafo fue puesto fuera de servicio correctamente.");
                    break;
                }
            }
        }
    }

    // Puedes agregar prints similares en otros métodos según los necesites.
}

 */