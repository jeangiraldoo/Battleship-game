package com.example.battleship;

import com.example.battleship.controllers.GamePanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
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
        Scene scene = new Scene(new Pane(gamePanel), 550, 655);
        System.out.println("Mostrando");
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(scene);
        primaryStage.show();


        // Añade el controlador de teclas
        scene.setOnKeyPressed(event -> gamePanel.handleInput(event.getCode()));
    }

}
