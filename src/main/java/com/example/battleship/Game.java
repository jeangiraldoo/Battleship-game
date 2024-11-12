package com.example.battleship;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game extends Application {
    private GamePanel gamePanel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        int difficultyChoice = 0;


        System.out.println(difficultyChoice);
        // Ahora inicializa el GamePanel con la dificultad seleccionada
        gamePanel = new GamePanel(difficultyChoice);
        Scene scene = new Scene(new VBox(gamePanel), 600, 400);
        System.out.println("Mostrando");
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(scene);
        primaryStage.show();


        // Añade el controlador de teclas
        scene.setOnKeyPressed(event -> gamePanel.handleInput(event.getCode()));
    }

}
