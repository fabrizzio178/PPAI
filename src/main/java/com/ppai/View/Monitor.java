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
import java.util.ResourceBundle;

@Component
public class Monitor implements Initializable {
    private GestorResultado gestorResultado;

    @FXML private VBox contenedorOrdenes;
    @FXML private ToggleGroup grupoSeleccionOrden = new ToggleGroup();
    @FXML private Label labelObservacion;
    @FXML private TextField campoObservacion;
    @FXML private Button botonConfirmarObservacion;
    @FXML private VBox contenedorMotivos;
    @FXML private Button botonConfirmarMotivos;
    @FXML private Label labelComentario;
    @FXML private TextField campoComentario;
    @FXML private Button botonConfirmarComentario;

    private ArrayList<CheckBox> checkboxesMotivos = new ArrayList<>();
    private ArrayList<String> motivosSeleccionados = new ArrayList<>();
    private int indiceMotivoActual = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    public void seleccionaOrdenInsp() {
        RadioButton seleccionado = (RadioButton) grupoSeleccionOrden.getSelectedToggle();
        if (seleccionado != null) {
            String nroSeleccionado = seleccionado.getUserData().toString();
            gestorResultado.tomarSeleccionOrdenInsp(nroSeleccionado);

            labelObservacion.setVisible(true);
            campoObservacion.setVisible(true);
            botonConfirmarObservacion.setVisible(true);
        }
    }

    public void pedirObservacion() {
        ingresarObservacion();
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

        gestorResultado.permitirActualizarSismografoComoFS();
        gestorResultado.buscarMotivos();
        mostrarTiposMotivos(gestorResultado.getTiposMotivosFueraDeServicio());
    }

    public void mostrarTiposMotivos(ArrayList<String> tiposMotivos) {
        contenedorMotivos.setVisible(true);
        botonConfirmarMotivos.setVisible(true);
        contenedorMotivos.getChildren().clear();
        checkboxesMotivos.clear();

        for (String tipo : tiposMotivos) {
            CheckBox checkBox = new CheckBox(tipo);
            contenedorMotivos.getChildren().add(checkBox);
            checkboxesMotivos.add(checkBox);
        }
    }

    @FXML
    public void solicitarSelTipoMotivo() {
        motivosSeleccionados.clear();
        for (CheckBox cb : checkboxesMotivos) {
            if (cb.isSelected()) {
                motivosSeleccionados.add(cb.getText());
            }
        }

        if (motivosSeleccionados.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText(null);
            alerta.setContentText("Debe seleccionar al menos un motivo.");
            alerta.showAndWait();
        } else {
            contenedorMotivos.setVisible(false);
            botonConfirmarMotivos.setVisible(false);
            indiceMotivoActual = 0;
            mostrarCampoComentario();
        }
    }

    private void mostrarCampoComentario() {
        labelComentario.setText("Comentario para: " + motivosSeleccionados.get(indiceMotivoActual));
        labelComentario.setVisible(true);
        campoComentario.setVisible(true);
        botonConfirmarComentario.setVisible(true);
    }

    @FXML
    public void confirmarComentario() {
        String comentario = campoComentario.getText();
        if (comentario.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText(null);
            alerta.setContentText("Debe ingresar un comentario.");
            alerta.showAndWait();
            return;
        }

        gestorResultado.tomarTipoMotivo(motivosSeleccionados.get(indiceMotivoActual));
        gestorResultado.tomarComentario(comentario);
        campoComentario.clear();

        indiceMotivoActual++;
        if (indiceMotivoActual < motivosSeleccionados.size()) {
            mostrarCampoComentario();
        } else {
            labelComentario.setVisible(false);
            campoComentario.setVisible(false);
            botonConfirmarComentario.setVisible(false);
            solicitarConfirmacion();
        }
    }

    public void solicitarConfirmacion() {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmación");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("¿Está seguro que desea cerrar la orden?");

        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                gestorResultado.getFechaHoraActual();
                gestorResultado.BuscarEstadoCerradaOI();
                gestorResultado.buscarFueraDeServicio();
                gestorResultado.cerrarOrdenInspeccion();
                gestorResultado.ponerSismografoFueraDeServicio();

                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Éxito");
                success.setHeaderText(null);
                success.setContentText("¡Orden cerrada con éxito!");
                success.showAndWait();

                enviarMail();
            } else {
                Alert cancel = new Alert(Alert.AlertType.INFORMATION);
                cancel.setTitle("Cancelado");
                cancel.setHeaderText(null);
                cancel.setContentText("Se canceló el cierre de la orden.");
                cancel.showAndWait();
            }
        });
    }

    public void pedirComentario() {
        mostrarCampoComentario();
    }

    public void enviarMail() {
        Alert mail = new Alert(Alert.AlertType.INFORMATION);
        mail.setTitle("Correo enviado");
        mail.setHeaderText(null);
        mail.setContentText("Mail enviado.");
        mail.showAndWait();
    }
}
