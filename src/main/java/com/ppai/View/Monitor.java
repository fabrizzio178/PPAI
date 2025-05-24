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

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

@Setter
@Getter
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

    private ArrayList<CheckBox> checkboxesMotivos = new ArrayList<>();

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
            System.out.println("Seleccionaste la orden: " + nroSeleccionado);

            // Mostrar campo de observación
            labelObservacion.setVisible(true);
            campoObservacion.setVisible(true);
            botonConfirmarObservacion.setVisible(true);
        } else {
            System.out.println("No seleccionaste nada");
        }
    }

    public void pedirObservacion() {
        ingresarObservacion();
    }

    public void ingresarObservacion() {
        String observacion = campoObservacion.getText();
        gestorResultado.tomarObservacion(observacion);
        System.out.println("Observación ingresada: " + observacion);

        labelObservacion.setVisible(false);
        campoObservacion.setVisible(false);
        botonConfirmarObservacion.setVisible(false);

        // Mostrar alerta
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText("Actualización como Fuera de Servicio permitida");
        alerta.showAndWait();

        // Continuar con flujo
        gestorResultado.permitirActualizarSismografoComoFS();
        gestorResultado.buscarMotivos();
        this.mostrarTiposMotivos(gestorResultado.getTiposMotivosFueraDeServicio());
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
        boolean seleccionoAlMenosUno = false;

        for (CheckBox cb : checkboxesMotivos) {
            if (cb.isSelected()) {
                gestorResultado.tomarTipoMotivo(cb.getText());
                seleccionoAlMenosUno = true;
            }
        }

        if (seleccionoAlMenosUno) {
            contenedorMotivos.setVisible(false);
            botonConfirmarMotivos.setVisible(false);
            this.pedirComentario();
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText(null);
            alerta.setContentText("Debe seleccionar al menos un motivo.");
            alerta.showAndWait();
        }
    }

    public void pedirComentario() {
        System.out.println("Ingrese el comentario: ");
        gestorResultado.tomarComentario(tomarComentario());
    }

    public String tomarComentario() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void solicitarConfirmacion() {
        System.out.println("Está seguro que desea cerrar la orden? (S/N)");
        confirmarCierre();
    }

    public void confirmarCierre() {
        Scanner scanner = new Scanner(System.in);
        String confirmacion = scanner.nextLine();
        if (confirmacion.equalsIgnoreCase("s")) {
            System.out.println("Orden cerrada con éxito!");
        } else {
            System.out.println("UY ME CAGUE");
        }
    }
}
