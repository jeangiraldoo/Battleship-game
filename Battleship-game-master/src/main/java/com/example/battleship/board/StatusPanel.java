package com.example.battleship.board;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StatusPanel extends Rectangle {

    private Font font = new Font("Arial", 15);

    private final String placingShipLine1 = "Place your boats below";
    private final String placingShipLine2 = "Press X to rotate";
    private final String gameOverLossLine = "Game Over, better luck next time!";
    private final String gameOverWinLine = "You won, congratulations!";
    private final String gameOverBottonLine = "Press R to restart";

    private String topLine;

    private String bottomLine;


    public StatusPanel(Position position, int width, int height) {
        super(position, width, height);
        reset();
    }

    /**
     * Resets the status panel to its default state.
     */
    public void reset() {
        topLine = placingShipLine1;
        bottomLine = placingShipLine2;
    }

    /**
     * Show game over message on the status panel.
     * @param playerWon Victory or failure value.
     */
    public void showGameOver(boolean playerWon) {
        topLine = (playerWon) ? gameOverWinLine : gameOverLossLine;
        bottomLine = gameOverBottonLine;
    }

    /**
     * Text to set at the top of the status panel
     * @param message Text to set.
     */
    public void setTopLine(String message) {
        topLine = message;
    }

    /**
     * Sets the text at the bottom of the status panel
     * @param message Text to set.
     */
    public void setBottomLine(String message) {
        bottomLine = message;
    }

    /**
    private double calculateTextWidth(String text, Font font) {
        Text tempText = new Text(text);
        tempText.setFont(font);
        return tempText.getLayoutBounds().getWidth();
    }

    /**
     * Paints the rectangle.
     * @param gc
     */
    public void paint(GraphicsContext gc) {
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(position.x, position.y, width, height);

        gc.setFill(Color.BLACK);
        gc.setFont(font);
        double topTextWidth = calculateTextWidth(topLine, font);
        gc.fillText(topLine, position.x + width / 2 - topTextWidth / 2, position.y + 20);

        double bottomTextWidth = calculateTextWidth(bottomLine, font);
        gc.fillText(bottomLine, position.x + width / 2 - bottomTextWidth / 2, position.y + 40);
    }


}
