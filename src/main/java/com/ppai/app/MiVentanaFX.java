package com.ppai.app;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MiVentanaFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Contenedor principal
        StackPane root = new StackPane();

        // Escena con el contenedor y dimensiones
        Scene scene = new Scene(root, 400, 300);

        // Configuración de la ventana (Stage)
        primaryStage.setTitle("Mi Ventana Vacía JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Inicia la aplicación JavaFX
    }
}
