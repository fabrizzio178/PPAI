package com.ppai;

import com.ppai.Model.OrdenDeInspeccion;
import com.ppai.Service.GestorResultados;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestorResultados gestor = new GestorResultados();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--- Cierre de Orden de Inspección ---\n");
        gestor.mostrarOrdenesDisponibles();

        System.out.print("Seleccione una orden por número: ");
        int seleccion = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer

        OrdenDeInspeccion orden = gestor.seleccionarOrdenPorNumero(seleccion);

        if (orden != null) {
            System.out.println("Seleccionada: Orden " + orden.getNumeroOrden() + " de la estación " + orden.getEstacion().getNombre());
            System.out.print("Ingrese observación de cierre: ");
            String observacion = scanner.nextLine();

            gestor.ingresarObservacion(orden, observacion);

            // Simulación simplificada del resto del CU (se puede expandir)
            gestor.confirmarCierre(orden);
        } else {
            System.out.println("Orden no encontrada.");
        }
    }
}
