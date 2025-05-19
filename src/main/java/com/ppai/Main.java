package com.ppai;

import com.ppai.Model.OrdenDeInspeccion;
import com.ppai.Model.EstacionSismologica;
import com.ppai.Model.Empleado;
import com.ppai.Service.GestorResultadosMUERTO;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestorResultadosMUERTO gestor = new GestorResultadosMUERTO();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--- Cierre de Orden de Inspección ---\n");
        gestor.mostrarOrdenesDisponibles();

        System.out.print("Seleccione una orden por número: ");
        int seleccion = scanner.nextInt();
        scanner.nextLine(); // limpiar buffer

        OrdenDeInspeccion orden = gestor.seleccionarOrdenPorNumero(seleccion);

        if (orden != null) {
            // Mostrar detalles completos de la orden
            System.out.println("\n--- Detalles de la Orden ---");
            System.out.println("Número de orden: " + orden.getNumeroOrden());
            System.out.println("Fecha/Hora de inicio: " + orden.getFechaHoraInicio());
            System.out.println("Fecha/Hora de finalización: " + orden.getFechaHoraFinalizacion());
            System.out.println("Estado actual: " + orden.getEstado().getNombreEstado());

            EstacionSismologica est = orden.getEstacionSismologica();
            System.out.println("\n--- Estación ---");
            System.out.println("Nombre: " + est.getNombre());
            System.out.println("Código: " + est.getCodigoEstacion());
            System.out.println("Ubicación: (" + est.getLatitud() + ", " + est.getLongitud() + ")");
            System.out.println("Certificación: " + est.getNumeroCertificacionAdquisicion() + " (" + est.getDocumentoCertificacionAdq() + ")");

            Empleado emp = orden.getEmpleado();
            System.out.println("\n--- Empleado Responsable ---");
            System.out.println("Nombre: " + emp.getNombre());
            System.out.println("Apellido: " + emp.getApellido());
            System.out.println("Email: " + emp.getMail());
            System.out.println("Teléfono: " + emp.getTelefono());

            // Ingreso de observación
            System.out.print("\nIngrese observación de cierre: ");
            String observacion = scanner.nextLine();
            gestor.ingresarObservacion(orden, observacion);

            // Confirmar cierre
            gestor.confirmarCierre(orden);
        } else {
            System.out.println("Orden no encontrada.");
        }
    }
}


