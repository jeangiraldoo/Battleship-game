package com.example.battleship.board;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.io.Serializable;

public class Marker extends Rectangle implements Serializable {
    private static final long serialVersionUID = 1L;

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

    /**
     * Resets the marker to its default state.
     */
    public void reset() {
        showMarker = false;
        shipAtMarker = null;

    }

    /**
     * Marks a square.
     */
    public void mark() {
        if (!showMarker && isShip()) {
            shipAtMarker.destroySection();
        }
        showMarker = true;
    }

    /**
     * Returns if the marker has a ship on it
     * @return True or False
     */
    public boolean isShip() {
        return shipAtMarker != null;
    }

    /**
     * Retuns if the marker is painted or not.
     * @return showMarker
     */
    public boolean isMarked() {
        return showMarker;
    }

    /**
     * Relate a ship to the marker.
     * @param ship Ship related to the marker.
     */
    public void setAsShip(Ship ship) {
        this.shipAtMarker = ship;
    }

    /**
     * Returns the ship placed on top of the marker.
     * @return ship.
     */
    public Ship getAssociatedShip() {
        return shipAtMarker;
    }

    /**
     * Paints a square on the board.
     * @param gc
     */
    public void paint(GraphicsContext gc) {
        if (!showMarker) return;
        if (isShip()) {
            if (shipAtMarker.isDestroyed()) {
                gc.setFill(Color.DARKRED); // Barco destruido
            } else {
                gc.setFill(Color.ORANGE); // Acierto
            }
        } else {
            gc.setFill(Color.DARKBLUE); // Fallo
        }
        gc.fillRect(position.x + PADDING + 1, position.y + PADDING + 1, width - PADDING * 2, height - PADDING * 2);
    }
}
