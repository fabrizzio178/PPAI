package com.ppai.View;

import com.ppai.Service.GestorResultado;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class Monitor implements Initializable {
    private GestorResultado gestorResultado;

    @FXML private VBox contenedorOrdenes;
    @FXML private ToggleGroup cmbOrdenesInspeccionesRealizadas = new ToggleGroup();
    @FXML private Label labelObservacion;
    @FXML private TextField campoObservacion;
    @FXML private Button botonConfirmarObservacion;
    @FXML private VBox contenedorMotivos;
    @FXML private Button botonConfirmarMotivos;
    @FXML private Label labelComentario;
    @FXML private TextField campoComentario;
    @FXML private Button botonConfirmarComentario;
    @FXML private Button botonContinuar;

    @FXML private ComboBox<String> comboMotivos;



    @FXML private ToggleGroup grupoMotivos = new ToggleGroup(); // Nuevo campo
    @FXML private ArrayList<RadioButton> radioButtonsMotivos = new ArrayList<>(); // Reemplaza checkboxesMotivos


    private ArrayList<CheckBox> checkboxesMotivos = new ArrayList<>();
    private ArrayList<String> motivosSeleccionados = new ArrayList<>();
    private int indiceMotivoActual = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            try {
                this.gestorResultado = new GestorResultado();
                this.gestorResultado.setMonitor(this);
                this.gestorResultado.opCerrarOrdenInspeccion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

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
            rb.setToggleGroup(cmbOrdenesInspeccionesRealizadas);
            rb.setUserData(nroOrden);

            HBox filaUI = new HBox(label, rb);
            filaUI.setSpacing(10);
            filaUI.setPrefWidth(550);
            contenedorOrdenes.getChildren().add(filaUI);
        }
    }

    public void seleccionaOrdenInsp() {
        RadioButton seleccionado = (RadioButton) cmbOrdenesInspeccionesRealizadas.getSelectedToggle();
        if (seleccionado != null) {
            String nroSeleccionado = seleccionado.getUserData().toString();
            gestorResultado.tomarSeleccionOrdenInsp(nroSeleccionado);
            botonContinuar.setVisible(false);
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText(null);
            alerta.setContentText("Debe seleccionar una orden.");
            alerta.showAndWait();
        }

    }

    public void pedirObservacion() {
        labelObservacion.setVisible(true);
        campoObservacion.setVisible(true);
        botonConfirmarObservacion.setVisible(true);
    }

    public void alertaFaltaObservacion() {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("ALERTA");
        alerta.setHeaderText(null);
        alerta.setContentText("No se introdujo una observacion");
        alerta.showAndWait();

        // no se como hacer que vuelva al flujo
        // porque si le pido la observacion de vuelta, despues aparece la pantalla de seleccion de motivos
        // y ahi si o si va a tener que seleccionar un motivo mas para continuar
    }

    public void alertaFaltaMotivo() {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("ALERTA");
        alerta.setHeaderText(null);
        alerta.setContentText("No se seleccionó por lo menos un motivo");
        alerta.showAndWait();
        ArrayList<String> lista = new ArrayList<>(comboMotivos.getItems());
        this.solicitarSelTipoMotivo(lista);

        // acá tampoco se como retomar el flujo
        // aunque creo que como está hecha la interfaz no hace falta esto??
        // fixme NO SE
    }

    public void ingresarObservacion() {
        String observacion = campoObservacion.getText();
        gestorResultado.tomarObservacion(observacion);

        labelObservacion.setVisible(false);
        campoObservacion.setVisible(false);
        botonConfirmarObservacion.setVisible(false);

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText("Actualización como Fuera de Servicio permitida");
        alerta.showAndWait();
    }

    public void mostrarTiposMotivos(ArrayList<String> tiposMotivos) {
        contenedorMotivos.setVisible(true);
        contenedorMotivos.getChildren().clear();

        // Por cada tipo de motivo, crear un Label y agregarlo al contenedor
        for (String tipo : tiposMotivos) {
            Label labelMotivo = new Label(tipo);
            // Opcional: dar estilo al label
            labelMotivo.setStyle("-fx-font-size: 14px;");
            contenedorMotivos.getChildren().add(labelMotivo);
        }
    }

    public void solicitarSelTipoMotivo(ArrayList<String> tiposMotivos) {
        contenedorMotivos.getChildren().removeIf(node -> !(node instanceof Label));
        botonConfirmarMotivos.setVisible(true);
        // Crear el ComboBox
        ComboBox<String> comboMotivos = new ComboBox<>();
        this.comboMotivos = comboMotivos;
        // Agregar todos los tipos de motivos
        comboMotivos.getItems().addAll(tiposMotivos);
        // Establecer un prompt text (texto que se muestra cuando no hay selección)
        comboMotivos.setPromptText("Seleccione un motivo");
        // Agregar el ComboBox al contenedor
        contenedorMotivos.getChildren().add(comboMotivos);
    }



    @FXML
    public void seleccionaTipoMotivo() { // lee las checkbox
        String motivoSeleccionado = comboMotivos.getValue();

        if (motivoSeleccionado != null) {
            this.gestorResultado.tomarTipoMotivo(motivoSeleccionado);
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("ALERTA");
            alerta.setHeaderText(null);
            alerta.setContentText("Por favor, seleccione un motivo");
            alerta.showAndWait();
        }
    }

    public void pedirComentario() {
        labelComentario.setText("Comentario para: " + comboMotivos.getValue());
        labelComentario.setVisible(true);
        campoComentario.setVisible(true);
        botonConfirmarComentario.setVisible(true);
        botonConfirmarMotivos.setVisible(false);
    }

    @FXML
    public void tomarComentario() {
        String comentario = campoComentario.getText();
        if (comentario.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText(null);
            alerta.setContentText("Debe ingresar un comentario.");
            alerta.showAndWait();
            return;
        }

        gestorResultado.tomarComentario(comentario);

    }

    public void solicitarConfirmacion() {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmación");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("¿Desea cerrar la orden?");

        Button cancelButton = (Button) confirmAlert.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setText("Seguir con la selección de motivos");


        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                contenedorMotivos.setVisible(false);
                labelComentario.setVisible(false);
                campoComentario.setVisible(false);
                botonConfirmarComentario.setVisible(false);

                gestorResultado.tomarConfirmacion();

                // llamar a lo que sigue ACÁ SALE DEL BUCLE
            } else if (response == ButtonType.CANCEL)  {
                ArrayList<String> lista = new ArrayList<>(comboMotivos.getItems()); // en el NO
                this.solicitarSelTipoMotivo(lista); // en el NO
                labelComentario.setVisible(false);
                campoComentario.setVisible(false);
                campoComentario.clear();
                botonConfirmarComentario.setVisible(false);
                //deberia repetir el bucle //SI LO HACE JARVIS SI LO HACE TODO SOY VERDEEEEEEEEEEEEEEEEEEEEE
            }
        });
    }



    public void enviarMail() {
        Alert mail = new Alert(Alert.AlertType.INFORMATION);
        mail.setTitle("Correo enviado");
        mail.setHeaderText(null);
        mail.setContentText("Mail enviado.");
        mail.showAndWait();
    }

    public void mostrarFinCU () {
        Alert fin = new Alert(Alert.AlertType.INFORMATION);
        fin.setTitle("FIN CU");
        fin.setHeaderText(null);
        fin.setContentText("SE TERMINÓ EL CU CARAJO.");
        fin.showAndWait();
    }
}
