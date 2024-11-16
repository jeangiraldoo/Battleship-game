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
    public List<Ship> getPlayerShips() { return playerShips; }
    public void setPlayerShips(List<Ship> playerShips) { this.playerShips = playerShips; }

    public List<Ship> getComputerShips() { return computerShips; }
    public void setComputerShips(List<Ship> computerShips) { this.computerShips = computerShips; }

    public boolean[][] getPlayerShots() { return playerShots; }
    public void setPlayerShots(boolean[][] playerShots) { this.playerShots = playerShots; }

    public boolean[][] getComputerShots() { return computerShots; }
    public void setComputerShots(boolean[][] computerShots) { this.computerShots = computerShots; }

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public com.example.battleship.controllers.GamePanel.GamePhase getGamePhase() { return gamePhase; }
    public void setGamePhase(com.example.battleship.controllers.GamePanel.GamePhase gamePhase) { this.gamePhase = gamePhase; }
}
