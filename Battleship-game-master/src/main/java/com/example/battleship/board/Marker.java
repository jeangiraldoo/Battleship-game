package com.example.battleship.board;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Marker extends Rectangle {

    private final Color HIT_COLOR = Color.ORANGE;

    private final Color MISS_COLOR = Color.DARKBLUE;

    private final Color DESTROYED_COLOR = Color.DARKRED;

    private final int PADDING = 3;

    private boolean showMarker;

    private Ship shipAtMarker;

    public Marker(int x, int y, int width, int height) {
        super(x, y, width, height);
        reset();
    }

    public void reset() {
        showMarker = false;
        shipAtMarker = null;

    }

    public void mark(){
        if(!showMarker && isShip()){
            shipAtMarker.destroySection();
        }
        showMarker = true;
    }

    public boolean isShip(){
        return shipAtMarker != null;
    }
    public boolean isMarked(){
        return showMarker;
    }

    public void setAsShip(Ship ship){
        this.shipAtMarker = ship;
    }

    public Ship getAssociatedShip(){
        return shipAtMarker;
    }

    public void paint(GraphicsContext gc) {
        if (!showMarker) return;

        if (isShip()) {
            if (shipAtMarker.isDestroyed()) {
                gc.setFill(DESTROYED_COLOR);
            } else {
                gc.setFill(HIT_COLOR);
            }
        } else {
            gc.setFill(MISS_COLOR);
        }
        gc.fillRect(position.x + PADDING + 1, position.y + PADDING + 1, width - PADDING * 2, height - PADDING * 2);
    }

}
