
package com.example.battleship.controllers;

import com.example.battleship.AI.EasyAI;
import com.example.battleship.board.Position;
import com.example.battleship.board.SelectionGrid;
import com.example.battleship.board.Ship;
import com.example.battleship.board.StatusPanel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.event.EventHandler;

public class GamePanel extends Pane {

    public enum GameState { PlacingShips, FiringShots, GameOver }

    private StatusPanel statusPanel;
    private SelectionGrid computer; // grid the player sees and attacks
    private SelectionGrid player;
    private EasyAI easyAI;
    private Ship placingShip;
    private Position tempPlacingPosition;
    private int placingShipIndex;
    private GameState gameState;
    public static boolean debugModeActive;

    private final Canvas canvas;

    public GamePanel(int aiChoice) {
        computer = new SelectionGrid(0, 0);
        statusPanel = new StatusPanel(new Position(0, computer.getHeight() + 1), computer.getWidth(), 49);
        player = new SelectionGrid(0, computer.getHeight() + 50);
        setStyle("-fx-background-color: #2A88A3;");

        canvas = new Canvas(500, computer.getHeight() + player.getHeight() + statusPanel.getHeight());
        getChildren().add(canvas);

        addEventHandler(MouseEvent.MOUSE_RELEASED, new MouseReleasedHandler());
        addEventHandler(MouseEvent.MOUSE_MOVED, new MouseMovedHandler());

        if (aiChoice == 0) {
            easyAI = new EasyAI(player);
        }

        restart();
        draw();
    }

    private class MouseReleasedHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            Position mousePosition = new Position((int) event.getX(), (int) event.getY());
            if (gameState == GameState.PlacingShips && player.isPositionInside(mousePosition)) {
                tryPlaceShip(mousePosition);
            } else if (gameState == GameState.FiringShots && computer.isPositionInside(mousePosition)) {
                tryFireAtComputer(mousePosition);
            }
            draw();
        }
    }

    private class MouseMovedHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            if (gameState != GameState.PlacingShips) return;
            tryMovePlacingShip(new Position((int) event.getX(), (int) event.getY()));
            draw();
        }
    }

    public void handleInput(KeyCode keyCode) {
        if (keyCode == KeyCode.ESCAPE) {
            System.exit(1);
        } else if (keyCode == KeyCode.R) {
            restart();
        } else if (gameState == GameState.PlacingShips && keyCode == KeyCode.X) {
            placingShip.toggleSideways();
            updateShipPlacement(tempPlacingPosition);
        } else if (keyCode == KeyCode.D) {
            debugModeActive = !debugModeActive;
        } else if (keyCode == KeyCode.S && gameState == GameState.PlacingShips) {
            computer.setShowShips(!computer.isShowingShips());
        }
        draw();
    }

    public void restart() {
        computer.reset();
        player.reset();
        player.setShowShips(true);
        computer.setShowShips(false);
        easyAI.reset();
        tempPlacingPosition = new Position(0, 0);
        placingShip = new Ship(new Position(0, 0), new Position(player.getPosition().x, player.getPosition().y), SelectionGrid.BOAT_SIZES[0], true);
        placingShipIndex = 0;
        updateShipPlacement(tempPlacingPosition);
        computer.populateShips();
        debugModeActive = false;
        statusPanel.reset();
        gameState = GameState.PlacingShips;
        draw();
    }

    private void tryPlaceShip(Position mousePosition) {
        Position targetPosition = player.getPositionInGrid(mousePosition.x, mousePosition.y);
        updateShipPlacement(targetPosition);
        if (player.canPlaceShipAt(targetPosition.x, targetPosition.y, SelectionGrid.BOAT_SIZES[placingShipIndex], placingShip.isSideWays())) {
            placeShip(targetPosition);
        }
    }

    private void placeShip(Position targetPosition) {
        placingShip.setShipPlacementColour(Ship.ShipPlacementColour.Placed);
        player.placeShip(placingShip, tempPlacingPosition.x, tempPlacingPosition.y);
        placingShipIndex++;
        if (placingShipIndex < SelectionGrid.BOAT_SIZES.length) {
            placingShip = new Ship(new Position(targetPosition.x, targetPosition.y),
                    new Position(player.getPosition().x + targetPosition.x * SelectionGrid.CELL_SIZE,
                            player.getPosition().y + targetPosition.y * SelectionGrid.CELL_SIZE), SelectionGrid.BOAT_SIZES[placingShipIndex], true);
            updateShipPlacement(tempPlacingPosition);
        } else {
            gameState = GameState.FiringShots;
            statusPanel.setTopLine("Attack the Computer!");
            statusPanel.setBottomLine("Destroy all Ships to win!");
        }
        draw();
    }

    private void tryFireAtComputer(Position mousePosition) {
        Position targetPosition = computer.getPositionInGrid(mousePosition.x, mousePosition.y);
        if (!computer.isPositionMarked(targetPosition)) {
            doPlayerTurn(targetPosition);
            if (!computer.areAllShipsDestroyed()) {
                doAITurn();
            }
        }
        draw();
    }

    private void doPlayerTurn(Position targetPosition) {
        boolean hit = computer.markPosition(targetPosition);
        String hitMiss = hit ? "Hit" : "Missed";
        String destroyed = "";
        if (hit && computer.getMarkerAtPosition(targetPosition).getAssociatedShip().isDestroyed()) {
            destroyed = "(Destroyed)";
        }
        statusPanel.setTopLine("Player " + hitMiss + " " + targetPosition + destroyed);
        if (computer.areAllShipsDestroyed()) {
            gameState = GameState.GameOver;
            statusPanel.showGameOver(true);
        }
    }

    private void doAITurn() {
        Position aiMove = easyAI.selectMove();
        boolean hit = player.markPosition(aiMove);
        String hitMiss = hit ? "Hit" : "Missed";
        String destroyed = "";
        if (hit && player.getMarkerAtPosition(aiMove).getAssociatedShip().isDestroyed()) {
            destroyed = "(Destroyed)";
        }
        statusPanel.setBottomLine("Computer " + hitMiss + " " + aiMove + destroyed);
        if (player.areAllShipsDestroyed()) {
            gameState = GameState.GameOver;
            statusPanel.showGameOver(false);
        }
    }

    private void tryMovePlacingShip(Position mousePosition) {
        if (player.isPositionInside(mousePosition)) {
            Position targetPos = player.getPositionInGrid(mousePosition.x, mousePosition.y);

            // Ajuste la posición para asegurar que el barco no salga del tablero
            if (placingShip.isSideWays()) {
                targetPos.x = Math.max(0, Math.min(targetPos.x, SelectionGrid.GRID_WIDTH - placingShip.getSegments()));
            } else {
                targetPos.y = Math.max(0, Math.min(targetPos.y, SelectionGrid.GRID_HEIGHT - placingShip.getSegments()));
            }

            updateShipPlacement(targetPos);
        }
        draw();
    }


    private void updateShipPlacement(Position targetPos) {
        if (placingShip.isSideWays()) {
            targetPos.x = Math.min(targetPos.x, SelectionGrid.GRID_WIDTH - placingShip.getSegments());
        } else {
            targetPos.y = Math.min(targetPos.y, SelectionGrid.GRID_HEIGHT - placingShip.getSegments());
        }

        placingShip.setDrawPosition(
                new Position(targetPos),
                new Position(player.getPosition().x + targetPos.x * SelectionGrid.CELL_SIZE,
                        player.getPosition().y + targetPos.y * SelectionGrid.CELL_SIZE)
        );

        tempPlacingPosition = targetPos;

        if (player.canPlaceShipAt(tempPlacingPosition.x, tempPlacingPosition.y, placingShip.getSegments(), placingShip.isSideWays())) {
            placingShip.setShipPlacementColour(Ship.ShipPlacementColour.Valid);
        } else {
            placingShip.setShipPlacementColour(Ship.ShipPlacementColour.Invalid);
        }
    }


    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        computer.paint(gc);
        player.paint(gc);
        if (gameState == GameState.PlacingShips) {
            placingShip.paint(gc);
        }
        statusPanel.paint(gc);
    }

    // Métodos vacíos necesarios para implementar MouseListener y MouseMotionListener
    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
