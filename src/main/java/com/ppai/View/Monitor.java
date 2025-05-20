package com.ppai.View;
import com.ppai.Service.GestorResultado;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
@Setter
@Getter

public class Monitor {
    private GestorResultado gestorResultado;

    // 0 - Arranca todo el programa DIOS POR QUE ES VERDE ESTO
    public void cerrarOrdenInsp() {
        this.habilitarVentana();

    }

    // 1 - Habilita la ventana creando el gestor de resultados y le dispara la opcion cerrar orden inspeccion
    public void habilitarVentana() {
        this.gestorResultado = new GestorResultado();
        this.gestorResultado.setMonitor(this);
        this.gestorResultado.opCerrarOrdenInspeccion();
    }

    // 7 - Mas que solicitar le muestra el resultado de las busquedas previas como string, y despues
    // dispara la funcion que le pide que seleccione.
    public void solicitarSeleccionOrdenesInspeccionRealizadas(ArrayList<ArrayList<Object>> ordenes) {
        for (ArrayList<Object> fila : ordenes) {
            String nroOrden = (String) fila.get(0);
            LocalDateTime fechaHoraFinalizacion = (LocalDateTime) fila.get(1);
            String nombreEstacion = (String) fila.get(2);
            String idSismografo = (String) fila.get(3);

            System.out.println("Nro Orden: " + nroOrden + ", Fecha Finalizacion: " + fechaHoraFinalizacion +
                    ", Nombre estación: " + nombreEstacion + ", Identificador Sismografo: " + idSismografo);
        }
        seleccionaOrdenInsp();
    }
    // 8 - Toma por teclado la opcion y se la manda al gestor.
    public void seleccionaOrdenInsp() {
        Scanner scanner = new Scanner(System.in);  // SIMULA UNA INTERFAZ ME TOMA LOS DATOS POR TECLADO

        System.out.print("Ingrese el nombre de la orden: ");
        String nombreOrden = scanner.nextLine();

        gestorResultado.tomarSeleccionOrdenInsp(nombreOrden);
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


    public ArrayList<String> solicitarSelTipoMotivo(ArrayList<String> tiposMotivos) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese la cantidad de motivos que desea seleccionar: ");
        Integer cant = scanner.nextInt();

        ArrayList<String> motivosSeleccionados = new ArrayList<>();
        for (int i = 0 ;i < cant; i++ ) {
            System.out.println("Ingrese el motivo: ");
            String motivo = scanner.nextLine();
            motivosSeleccionados.add(motivo);
        }
        return motivosSeleccionados;

    }
}
