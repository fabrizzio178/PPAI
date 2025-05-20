package com.ppai.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;


public class MiVentanaFX extends Application {

    // Agregar el método estático que falta
    @Setter
    private static ArrayList<ArrayList<String>> datosOrdenes;
    private final HashMap<CheckBox, ArrayList<String>> mapaChecksOrdenes = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);

        Label labelOrdenes = new Label("Órdenes de Inspección:");
        root.getChildren().add(labelOrdenes);

        if (datosOrdenes != null) {
            for (ArrayList<String> orden : datosOrdenes) {
                String texto = String.join(" | ", orden);
                CheckBox checkBox = new CheckBox(texto);
                mapaChecksOrdenes.put(checkBox, orden);
                root.getChildren().add(checkBox);
            }
        }

        Scene scene = new Scene(root, 500, 400);
        primaryStage.setTitle("Seleccionar Órdenes de Inspección");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}