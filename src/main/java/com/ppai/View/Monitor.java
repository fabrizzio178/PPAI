package com.ppai.View;
import com.ppai.Service.GestorResultado;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

@Setter
@Getter

@Component
public class Monitor implements Initializable {
    private GestorResultado gestorResultado;

    @FXML
    private VBox contenedorOrdenes;
    @FXML
    private ToggleGroup grupoSeleccionOrden = new ToggleGroup();
    @FXML
    private Label labelObservacion;
    @FXML
    private TextField campoObservacion;
    @FXML
    private Button botonConfirmarObservacion;
    @FXML
    private VBox contenedorMotivos;
    private ArrayList<CheckBox> checkboxesMotivos = new ArrayList<>();


    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        Platform.runLater(() -> {
            try {
                this.gestorResultado = new GestorResultado();
                this.gestorResultado.setMonitor(this);
                this.gestorResultado.hardcodearSesion();

                this.gestorResultado.buscarRILogueado();
                this.gestorResultado.buscarOrdenesInsp();
                this.gestorResultado.ordenarPorFechaFinalizacion();
                this.gestorResultado.mostrarDatos(this.gestorResultado.getTodosSismografos());

                this.solicitarSeleccionOrdenesInspeccionRealizadas(this.gestorResultado.getDatosOrdenInsp());

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }



    // 0 - Arranca todo el programa DIOS POR QUE ES VERDE ESTO
    public void cerrarOrdenInsp() throws SQLException {
        this.habilitarVentana();

    }

    // 1 - Habilita la ventana creando el gestor de resultados y le dispara la opcion cerrar orden inspeccion
    public void habilitarVentana() throws SQLException {
        this.gestorResultado = new GestorResultado();
        this.gestorResultado.setMonitor(this);
        this.gestorResultado.hardcodearSesion();
//        this.gestorResultado.setTodosTipoMotivo(tiposMotivos);

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

            String texto = "Nro Orden: " + nroOrden + ", Fecha Finalizacion: " + fechaHoraFinalizacion +
                    ", Nombre estación: " + nombreEstacion + ", Identificador Sismografo: " + idSismografo;
            Label label = new Label(texto);
            label.setWrapText(true);
            label.setPrefWidth(400);

            RadioButton rb = new RadioButton();
            rb.setToggleGroup(grupoSeleccionOrden);
            rb.setUserData(nroOrden);

            HBox filaUI = new HBox(label, rb);
            filaUI.setSpacing(10);
            filaUI.setPrefWidth(550);
            contenedorOrdenes.getChildren().add(filaUI);
        }
    }
    // 8 - Toma por teclado la opcion y se la manda al gestor.
    public void seleccionaOrdenInsp() {
        RadioButton seleccionado = (RadioButton) grupoSeleccionOrden.getSelectedToggle();
        if (seleccionado != null) {
            String nroSeleccionado = seleccionado.getUserData().toString();
            gestorResultado.tomarSeleccionOrdenInsp(nroSeleccionado);
            System.out.println("Seleccionaste la orden: " + nroSeleccionado);

            // Mostrar campo de observación
            labelObservacion.setVisible(true);
            campoObservacion.setVisible(true);
            botonConfirmarObservacion.setVisible(true);
        } else {
            System.out.println("No seleccionaste nada");
        }
    }
    // 11 - Dispara la 13.
    public void pedirObservacion() {
        ingresarObservacion();
    }
    // 12 - Toma por teclado la observacion y la manda al gestor.
    public void ingresarObservacion() {
        String observacion = campoObservacion.getText();
        gestorResultado.tomarObservacion(observacion);
        System.out.println("Observación ingresada: " + observacion);

        // Ocultar los campos
        labelObservacion.setVisible(false);
        campoObservacion.setVisible(false);
        botonConfirmarObservacion.setVisible(false);

        // Mostrar alerta
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText("Actualización como Fuera de Servicio permitida");
        alerta.showAndWait();

        // Continuar flujo
        gestorResultado.permitirActualizarSismografoComoFS();
        gestorResultado.buscarMotivos();
        this.mostrarTiposMotivos(gestorResultado.getTiposMotivosFueraDeServicio());
        this.solicitarSelTipoMotivo(gestorResultado.getTiposMotivosFueraDeServicio());
        this.solicitarConfirmacion();
    }

    // 16 - Mostrar tipos motivos al usuario.

    public void mostrarTiposMotivos(ArrayList<String> tiposMotivos) {
        contenedorMotivos.setVisible(true);
        contenedorMotivos.getChildren().clear();
        checkboxesMotivos.clear();

        for (String tipo : tiposMotivos) {
            CheckBox checkBox = new CheckBox(tipo);
            contenedorMotivos.getChildren().add(checkBox);
            checkboxesMotivos.add(checkBox);
        }
    }
    // 17 - Solicitar que seleccione uno o varios motivos y que por cada uno de ellos agregue un comentario.


    public void solicitarSelTipoMotivo(ArrayList<String> tiposMotivos) {
        for (CheckBox cb : checkboxesMotivos) {
            if (cb.isSelected()) {
                gestorResultado.tomarTipoMotivo(cb.getText());
            }
        }
        // Podés mostrar un campo nuevo para pedir el comentario ahora
        this.pedirComentario(); // si querés continuar con el flujo
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
