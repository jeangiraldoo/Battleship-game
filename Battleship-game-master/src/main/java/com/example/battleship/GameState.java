package com.example.battleship;

import com.example.battleship.board.Ship;
import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Ship> playerShips;
    private List<Ship> computerShips;
    private boolean[][] playerShots;
    private boolean[][] computerShots;
    private String playerName;
    private com.example.battleship.controllers.GamePanel.GamePhase gamePhase; // Añadir estado del juego

    // Getters y Setters

    /**
     * Returns the list of ships used by the user.
     * @return playerShips
     */
    public List<Ship> getPlayerShips() { return playerShips; }

    /**
     * Sets the ships used by the player.
     * @param playerShips
     */
    public void setPlayerShips(List<Ship> playerShips) { this.playerShips = playerShips; }

    /**
     * Returns the list of ships used by the AI.
     * @return computerShips
     */
    public List<Ship> getComputerShips() { return computerShips; }

    /**
     * Sets the list of ship objects chosen by the AI.
     * @param computerShips List of ships
     */
    public void setComputerShips(List<Ship> computerShips) { this.computerShips = computerShips; }

    /**
     * Returns the matrix with shots made by the user.
     * @return playerShots
     */
    public boolean[][] getPlayerShots() { return playerShots; }

    /**
     * Sets the matrix with shots made by the user.
     * @param playerShots Shots made by the user.
     */
    public void setPlayerShots(boolean[][] playerShots) { this.playerShots = playerShots; }

    /**
     * Returns the shots made by the computer.
     * @return boolean matrix Shots made by the user.
     */
    public boolean[][] getComputerShots() { return computerShots; }

    /**
     * Sets the matrix with the shots made by the computer.
     * @param computerShots
     */
    public void setComputerShots(boolean[][] computerShots) { this.computerShots = computerShots; }

    /**
     * Returns the name of the player.
     * @return playerName Name of the player.
     */
    public String getPlayerName() { return playerName; }

    /**
     * Sets the name of the player.
     * @param playerName Name of the person playing the game.
     */
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    /**
     * Returns the current game phase.
     * @return Enum that indicates the current game phase.
     */
    public com.example.battleship.controllers.GamePanel.GamePhase getGamePhase() { return gamePhase; }

    /**
     * Sets the current game state.
     * @param gamePhase Enum that indicates the current game phase.
     */
    public void setGamePhase(com.example.battleship.controllers.GamePanel.GamePhase gamePhase) { this.gamePhase = gamePhase; }
}
