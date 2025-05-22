package com.ppai.View;
import com.ppai.Service.GestorResultado;
import lombok.Getter;
import lombok.Setter;
import com.ppai.DAO.*;
import com.ppai.Model.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
@Setter
@Getter

public class Monitor {
    private GestorResultado gestorResultado;



    // 0 - Arranca todo el programa DIOS POR QUE ES VERDE ESTO
    public void cerrarOrdenInsp() throws SQLException {
        this.habilitarVentana();

    }

    // 1 - Habilita la ventana creando el gestor de resultados y le dispara la opcion cerrar orden inspeccion
    public void habilitarVentana() throws SQLException {
        this.gestorResultado = new GestorResultado();
        this.gestorResultado.setMonitor(this);


        this.gestorResultado.opCerrarOrdenInspeccion();
    }

    // 7 - Mas que solicitar le muestra el resultado de las busquedas previas como string, y despues
    // dispara la funcion que le pide que seleccione.
    public void solicitarSeleccionOrdenesInspeccionRealizadas(ArrayList<ArrayList<Object>> ordenes) {
        for (ArrayList<Object> fila : ordenes) {
            String nroOrden = fila.get(0).toString();
            LocalDateTime fechaHoraFinalizacion = (LocalDateTime) fila.get(1);
            String nombreEstacion = fila.get(2).toString();
            String idSismografo = fila.get(3).toString();

            System.out.println("Nro Orden: " + nroOrden + ", Fecha Finalizacion: " + fechaHoraFinalizacion +
                    ", Nombre estación: " + nombreEstacion + ", Identificador Sismografo: " + idSismografo);
        }
        seleccionaOrdenInsp();
    }
    // 8 - Toma por teclado la opcion y se la manda al gestor.
    public void seleccionaOrdenInsp() {
        Scanner scanner = new Scanner(System.in);  // SIMULA UNA INTERFAZ ME TOMA LOS DATOS POR TECLADO

        System.out.print("Ingrese el numero de la orden: ");
        String numeroOrden = scanner.nextLine();

        gestorResultado.tomarSeleccionOrdenInsp(numeroOrden);
    }
    // 11 - Dispara la 13.
    public void pedirObservacion() {
        ingresarObservacion();
    }
    // 12 - Toma por teclado la observacion y la manda al gestor.
    public void ingresarObservacion() {
        Scanner scanner = new Scanner(System.in);  // SIMULA UNA INTERFAZ ME TOMA LOS DATOS POR TECLADO

        System.out.print("Ingrese su observación: ");
        String observacion = scanner.nextLine();

        gestorResultado.tomarObservacion(observacion);
    }

    // 16 - Mostrar tipos motivos al usuario.

    public void mostrarTiposMotivos(ArrayList<String> tiposMotivos) {
        for (String tipo : tiposMotivos) {
            System.out.println("Motivo: " + tipo);
        };
    }
    // 17 - Solicitar que seleccione uno o varios motivos y que por cada uno de ellos agregue un comentario.


    public void solicitarSelTipoMotivo(ArrayList<String> tiposMotivos) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese la cantidad de motivos que desea seleccionar: ");
        Integer cant = scanner.nextInt();
        scanner.nextLine();

        // ArrayList<String> motivosSeleccionados = new ArrayList<>(); NO HACE FALTA
        for (int i = 0 ;i < cant; i++) {
            // motivosSeleccionados.add(selTipoMotivo()); NO HACE FALTA
            gestorResultado.tomarTipoMotivo(selTipoMotivo());
        }
            //return motivosSeleccionados; NO HACE FALTA PORQUE LA CAMBIE POR VOID ANTES ERA ARRAYLIST DE STRINGS
    }
    //18 - Esto es seleccionar un solo motivo y la llamo en el ciclo de arriba.
    public String selTipoMotivo(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el motivo: ");
        return scanner.nextLine();
    }

    // 20 - Pide un comentario al usuario por cada tipo motivo.

    public void pedirComentario() {

        System.out.println("Ingrese el comentario: ");
        gestorResultado.tomarComentario(tomarComentario());
        }

    // 21 - Funcion pelotuda que solo la hago para cumplir el diagrama de secuencia.
    public String tomarComentario (){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    // 23 - Solicita que el usuario confirme la accion.
    public void solicitarConfirmacion(){
        System.out.println("Esta seguro que desea cerrar la orden? (S/N)");
        confirmarCierre();
    }

    // 24 - Toma la confirmacion del usuario si acepta,
    public void confirmarCierre() {
        Scanner scanner = new Scanner(System.in);
        String confirmacion = scanner.nextLine();
        if (confirmacion.equalsIgnoreCase("s")) {
            System.out.println("Orden cerrada con exito!");
        } else {
            System.out.println("UY ME CAGUE");
        }
    }

}
