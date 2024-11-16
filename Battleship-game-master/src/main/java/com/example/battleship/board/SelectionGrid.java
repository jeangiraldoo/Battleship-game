
package com.example.battleship.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SelectionGrid extends Rectangle implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int CELL_SIZE = 30;
    public static final int GRID_WIDTH = 10; // width and height are in terms of markers
    public static final int GRID_HEIGHT = 10;
    public static final int[] BOAT_SIZES = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
    private Marker[][] markers = new Marker[GRID_WIDTH][GRID_HEIGHT];
    private List<Ship> ships;
    private Random rand;
    private boolean showShips;
    private boolean allShipsDestroyed;

    public SelectionGrid(int x, int y) {
        super(x, y, CELL_SIZE * GRID_WIDTH, CELL_SIZE * GRID_HEIGHT);
        createMarkerGrid();
        ships = new ArrayList<>();
        rand = new Random();
        showShips = false;
    }

    /**
     * Creates a grid with a marker in each cell
     */
    private void createMarkerGrid() {
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                markers[x][y] = new Marker(position.x + x * CELL_SIZE, position.y + y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    /**
     * Paints the grid
     * @param gc
     */
    public void paint(GraphicsContext gc) {
        for (Ship ship : ships) {
            if (showShips || ship.isDestroyed()) {
                ship.paint(gc);
            }
        }
        drawMarkers(gc);
        drawGrid(gc);
    }

    /**
     * Sets wether or not ships are going to be shown.
     * @param showShips boolean that indicates if the ships shall be shown or not.
     */
    public void setShowShips(boolean showShips) {
        this.showShips = showShips;
    }

    /**
     * Returns if the ships are being shown or not.
     * @return boolean
     */
    public boolean isShowingShips() {
        return showShips;
    }

    /**
     * Resets the grid to its default state
     */
    public void reset() {
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                markers[x][y].reset();
            }
        }
        ships.clear();
        showShips = false;
        allShipsDestroyed = false;
    }

    /**
     * Returns if there is a ship at a specific position or not.
     * @param pos position to check.
     * @return boolean
     */
    public boolean markPosition(Position pos) {
        Marker marker = markers[pos.x][pos.y];
        if (marker.isMarked()) {
            return false; // Ya está marcado
        }
        marker.mark();
        allShipsDestroyed = true;
        for (Ship ship : ships) {
            if (!ship.isDestroyed()) {
                allShipsDestroyed = false;
                break;
            }
        }
        return marker.isShip();
    }

    public boolean areAllShipsDestroyed() {
        for (Ship ship : ships) {
            if (!ship.isDestroyed()) {
                return false;
            }
        }
        return true;
    }


    public boolean isPositionMarked(Position pos) {
        return markers[pos.x][pos.y].isMarked();
    }

    /**
     * Returns a marker object at a specified position
     * @param pos position
     * @return marker object
     */
    public Marker getMarkerAtPosition(Position pos) {
        return markers[pos.x][pos.y];
    }

    /**
     * Returns a position object that representes a set of coordinates
     * @param mouseX position of the mouse on the screen on the x axis.
     * @param mouseY position of the mouse on the screen on the y axis.
     * @return position object
     */
    public Position getPositionInGrid(int mouseX, int mouseY) {
        if (!isPositionInside(new Position(mouseX, mouseY))) {
            return new Position(-1, -1);
        }

        int gridX = Math.max(0, Math.min((mouseX - position.x) / CELL_SIZE, GRID_WIDTH - 1));
        int gridY = Math.max(0, Math.min((mouseY - position.y) / CELL_SIZE, GRID_HEIGHT - 1));

        return new Position(gridX, gridY);
    }

    /**
     * Validates if a ship can be placed at a specific position
     * @param gridX coordinate x
     * @param gridY coordinate y
     * @param segments segments or blocks the ship takes
     * @param sideways if the ship is sideways or not
     * @return
     */
    public boolean canPlaceShipAt(int gridX, int gridY, int segments, boolean sideways) {
        // Generar una lista de todas las celdas que el barco ocuparía
        List<Position> occupiedCells = new ArrayList<>();

        for (int i = 0; i < segments; i++) {
            if (sideways) {
                // Barco horizontal: Aumentar X en cada segmento.
                occupiedCells.add(new Position(gridX + i, gridY));
            } else {
                // Barco vertical: Aumentar Y en cada segmento.
                occupiedCells.add(new Position(gridX, gridY + i));
            }
        }

        // Validar cada celda ocupada
        for (Position cell : occupiedCells) {
            // Verificar si está fuera de los límites del tablero.
            if (cell.x < 0 || cell.x >= GRID_WIDTH || cell.y < 0 || cell.y >= GRID_HEIGHT) {
                return false; // Celda fuera del tablero.
            }

            // Verificar si la celda ya está ocupada por otro barco.
            if (markers[cell.x][cell.y].isShip()) {
                return false; // Celda ya ocupada.
            }
        }

        // Si todas las celdas son válidas, el barco puede colocarse.
        return true;
    }

    /**
     * Draws the markers inside each cell of the grid on the canvas.
     * @param gc
     */
    private void drawMarkers(GraphicsContext gc) {
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                markers[x][y].paint(gc);
            }
        }
    }

    /**
     * Draws the board grid on the canvas
     * @param gc
     */
    private void drawGrid(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);

        double y2 = position.y;
        double y1 = position.y + height;

        for (int x = 0; x <= GRID_WIDTH; x++) {
            double xPos = position.x + x * CELL_SIZE;
            gc.strokeLine(xPos, y1, xPos, y2);
        }

        double x2 = position.x;
        double x1 = position.x + width;

        for (int y = 0; y <= GRID_HEIGHT; y++) {
            double yPos = position.y + y * CELL_SIZE;
            gc.strokeLine(x1, yPos, x2, yPos);
        }
    }

    /**
     * Populates the boards with ships
     */
    public void populateShips() {
        ships.clear();
        for (int i = 0; i < BOAT_SIZES.length; i++) {
            boolean sideways = rand.nextBoolean();
            int gridX, gridY;
            do {
                gridX = rand.nextInt(sideways ? GRID_WIDTH - BOAT_SIZES[i] : GRID_WIDTH);
                gridY = rand.nextInt(sideways ? GRID_HEIGHT : GRID_HEIGHT - BOAT_SIZES[i]);
            } while (!canPlaceShipAt(gridX, gridY, BOAT_SIZES[i], sideways));
            placeShip(gridX, gridY, BOAT_SIZES[i], sideways);
        }
    }

    /**
     * Places a ship sidebays on the board.
     * @param gridX coordinate x.
     * @param gridY coordinate y.
     * @param segments how many segments or blocks the ship takes.
     * @param sideways if the ship must go sideways.
     */
    private void placeShip(int gridX, int gridY, int segments, boolean sideways) {
        placeShip(new Ship(new Position(gridX, gridY),
                new Position(position.x + gridX * CELL_SIZE, position.y + gridY * CELL_SIZE),
                segments, sideways), gridX, gridY);
    }

    /**
     * Places a ship on a specific coordinate on the board.
     * @param ship ship object.
     * @param gridX coordinate x.
     * @param gridY coordinate y.
     */
    public void placeShip(Ship ship, int gridX, int gridY) {
        ships.add(ship);
        if (ship.isSideWays()) {
            for (int x = 0; x < ship.getSegments(); x++) {
                markers[gridX + x][gridY].setAsShip(ships.get(ships.size() - 1));
            }
        } else {
            for (int y = 0; y < ship.getSegments(); y++) {
                markers[gridX][gridY + y].setAsShip(ships.get(ships.size() - 1));
            }
        }
    }

    // Nuevo método para obtener los barcos
    public List<Ship> getShips() {
        return ships;
    }

    /**
     * Sets ships at specified coordinates.
     * @param ships list of ships
     */
    public void setShips(List<Ship> ships) {
        this.ships = ships;
        for (Ship ship : ships) {
            for (Position pos : ship.getOccupiedCoordinates()) {
                markers[pos.x][pos.y].setAsShip(ship);
            }
        }
    }

    // Nuevo método para obtener los marcadores

    /**
     * Returns the available markers
     * @return boolean matrix of marker states
     */
    public boolean[][] getMarkers() {
        boolean[][] markerStates = new boolean[GRID_WIDTH][GRID_HEIGHT];
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                markerStates[x][y] = markers[x][y].isMarked();
            }
        }
        return markerStates;
    }

    /**
     * Sets a marker to every position in the board.
     * @param markerStates current states of the markers
     * @param gc
     */
    public void setMarkers(boolean[][] markerStates, GraphicsContext gc) {
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                markers[x][y].reset();
                if (markerStates[x][y]) {
                    markers[x][y].mark();
                    // Aquí debes asociar el marcador al barco correspondiente
                    for (Ship ship : ships) {
                        if (ship.getOccupiedCoordinates().contains(new Position(x, y))) {
                            markers[x][y].setAsShip(ship);
                        }
                    }
                    markers[x][y].paint(gc);
                }
            }
        }
    }
}
