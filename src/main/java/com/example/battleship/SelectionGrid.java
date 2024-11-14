package com.example.battleship;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class SelectionGrid extends Rectangle {

    public static final int CELL_SIZE = 30;
    public static final int GRID_WIDTH = 10; // width and height are in terms of markers
    public static final int GRID_HEIGHT = 10;
    public static final int[] BOAT_SIZES = {5,4,3,3,2};
    private Marker[][] markers = new Marker[GRID_WIDTH][GRID_HEIGHT];
    private List<Ship> ships;
    private Random rand;
    private boolean showShips;
    private boolean allShipsDestroyed;

    public SelectionGrid(int x, int y) {
        super(x, y, CELL_SIZE*GRID_WIDTH, CELL_SIZE*GRID_HEIGHT);
        createMarkerGrid();
        ships = new ArrayList<Ship>();
        rand = new Random();
        showShips = false;

    }

    private void createMarkerGrid() {
        for(int x = 0; x < GRID_WIDTH; x++){
            for(int y = 0; y < GRID_HEIGHT; y++){
                markers[x][y] = new Marker(position.x + x * CELL_SIZE, position.y + y * CELL_SIZE, CELL_SIZE, CELL_SIZE );
            }
        }
    }

    public void paint(GraphicsContext gc){
        for(Ship ship : ships){
            if(showShips || ship.isDestroyed()){
                ship.paint(gc);
            }
        }
        drawMarkers(gc);
        drawGrid(gc);
    }

    public void setShowShips(boolean showShips){
        this.showShips = showShips;
    }

    public void reset(){
        for(int x = 0; x < GRID_WIDTH; x++){
            for(int y = 0; y < GRID_HEIGHT; y++){
                markers[x][y].reset();
            }
        }
        ships.clear();
        showShips = false;
        allShipsDestroyed = false;
    }

    public boolean markPosition(Position pos){
        markers[pos.x][pos.y].mark();
        allShipsDestroyed = true;
        for(Ship ship : ships){
            if(!ship.isDestroyed()){
                allShipsDestroyed = false;
                break;
            }
        }
        return markers[pos.x][pos.y].isShip();

    }

    public boolean areAllShipsDestroyed(){
        return allShipsDestroyed;
    }
    public boolean isPositionMarked(Position pos){
        return markers[pos.x][pos.y].isMarked();
    }

    public Marker getMarkerAtPosition(Position pos){
        return markers[pos.x][pos.y];
    }
    public Position getPositionInGrid(int mouseX, int mouseY){
        if(!isPositionInside(new Position(mouseX, mouseY))){
            return new Position(-1, -1);
        }

        return new Position((mouseX - position.x)/CELL_SIZE, (mouseY - position.y)/CELL_SIZE);
    }

    public boolean canPlaceShipAt(int gridX, int gridY, int segments, boolean sideways){
        if (gridX < 0 || gridY < 0) return false;

        if (sideways){
            if(gridY > GRID_HEIGHT || gridX + segments > GRID_WIDTH) return false;
            for(int x = 0; x < segments; x++){
                if(markers[gridX + x][gridY].isShip()){
                    return false;
                }
            }
        } else {
            if(gridY + segments > GRID_HEIGHT || gridX < GRID_WIDTH) return false;
            for(int y = 0; y < segments; y++){
                if(markers[gridX][gridY+y].isShip()){
                    return false;
                }
            }
        }
        return true;
    }

    private void drawMarkers(GraphicsContext gc) {
        for(int x = 0; x < GRID_WIDTH; x++){
            for(int y = 0; y < GRID_HEIGHT; y++){
                markers[x][y].paint(gc);
            }
        }
    }

    private void drawGrid(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);

        // Coordenadas de los límites en el eje Y
        double y2 = position.y;
        double y1 = position.y + height;

        // Dibujar las líneas verticales
        for (int x = 0; x <= GRID_WIDTH; x++) {
            double xPos = position.x + x * CELL_SIZE;
            gc.strokeLine(xPos, y1, xPos, y2);
        }

        // Coordenadas de los límites en el eje X
        double x2 = position.x;
        double x1 = position.x + width;

        // Dibujar las líneas horizontales
        for (int y = 0; y <= GRID_HEIGHT; y++) {
            double yPos = position.y + y * CELL_SIZE;
            gc.strokeLine(x1, yPos, x2, yPos);
        }
    }



    public void populateShips(){
        ships.clear();
        for(int i = 0; i < BOAT_SIZES.length; i++){
            boolean sideways = rand.nextBoolean();
            int gridX,gridY;
            do {
                gridX = rand.nextInt(sideways?GRID_WIDTH-BOAT_SIZES[i]:GRID_WIDTH);
                gridY = rand.nextInt(sideways?GRID_HEIGHT:GRID_HEIGHT-BOAT_SIZES[i]);
            } while (!canPlaceShipAt(gridX, gridY, BOAT_SIZES[i], sideways));
            placeShip(gridX, gridY, BOAT_SIZES[i], sideways);
        }
    }

    private void placeShip(int gridX, int gridY, int segments, boolean sideways) {
        placeShip(new Ship(new Position(gridX, gridY),
                new Position(position.x+gridX*CELL_SIZE, position.y+gridY*CELL_SIZE),
                segments, sideways), gridX, gridY);
    }

    public void placeShip(Ship ship, int gridX, int gridY) {
        ships.add(ship);
        if(ship.isSideWays()) { // If the ship is horizontal
            for(int x = 0; x < ship.getSegments(); x++) {
                markers[gridX+x][gridY].setAsShip(ships.get(ships.size()-1));
            }
        } else { // If the ship is vertical
            for(int y = 0; y < ship.getSegments(); y++) {
                markers[gridX][gridY+y].setAsShip(ships.get(ships.size()-1));
            }
        }
    }

}
