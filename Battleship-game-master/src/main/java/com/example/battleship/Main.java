package com.example.battleship;

import com.example.battleship.controllers.GamePanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class Main extends Application {
    private GamePanel gamePanel;
    private String playerName;

    @Override
    public void start(Stage primaryStage) {
        Button newGameButton = new Button("Iniciar nuevo juego");
        Button continueGameButton = new Button("Continuar partida anterior");

        newGameButton.setOnAction(e -> {
            playerName = getPlayerNameFromUser();
            if (playerName != null && !playerName.isEmpty()) {
                gamePanel = new GamePanel(0);
                gamePanel.saveGameState(playerName);
                startGame(primaryStage, playerName);
            }
        });

        continueGameButton.setOnAction(e -> {
            gamePanel = new GamePanel(0);
            gamePanel.loadGameState();
            startGame(primaryStage, gamePanel.getPlayerName()); // Obtener el nombre del jugador cargado
        });

        VBox vbox = new VBox(newGameButton, continueGameButton);
        Scene scene = new Scene(vbox, 200, 100);
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Asks the user for their name and returns it.
     * @return user name.
     */
    private String getPlayerNameFromUser() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nombre del Jugador");
        dialog.setHeaderText("Introduce tu nombre:");
        dialog.setContentText("Nombre:");

        Optional<String> result = dialog.showAndWait();
        return result.orElse("defaultPlayerName");
    }
    /**
     * Starts the game after asking the user for their name.
     * @param primaryStage The stage used for the UI.
     * @param playerName The name of the user.
     */
    private void startGame(Stage primaryStage, String playerName) {
        Scene gameScene = new Scene(new Pane(gamePanel), 550, 655);
        gameScene.setOnKeyPressed(event -> gamePanel.handleInput(event.getCode()));
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }
}
