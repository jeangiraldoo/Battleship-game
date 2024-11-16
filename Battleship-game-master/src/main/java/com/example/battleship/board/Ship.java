package com.example.battleship.board;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ship implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum ShipPlacementColour implements Serializable {
        Valid, Invalid, Placed
    }

    private Position gridPosition;
    private Position drawPosition;
    private ShipPlacementColour shipPlacementColour;
    private int segments;
    private boolean isSideWays;
    private int destroyedSections;

    public Ship(Position gridPosition, Position drawPosition, int segments, boolean isSideWays) {
        this.gridPosition = gridPosition;
        this.drawPosition = drawPosition;
        this.segments = segments;
        this.isSideWays = isSideWays;
        this.destroyedSections = 0;
        shipPlacementColour = ShipPlacementColour.Placed;
    }

    public void paint(GraphicsContext gc) {
        if (shipPlacementColour == ShipPlacementColour.Placed) {
            gc.setFill(isDestroyed() ? Color.BLUE : Color.DARKGRAY);
        } else {
            gc.setFill(shipPlacementColour == ShipPlacementColour.Valid ? Color.GREEN : Color.RED);
        }

        if (isSideWays) {
            paintHorizontal(gc);
        } else {
            paintVertical(gc);
        }
    }

    public void setShipPlacementColour(ShipPlacementColour shipPlacementColour) {
        this.shipPlacementColour = shipPlacementColour;
    }

    public void toggleSideways() {
        isSideWays = !isSideWays;
    }

    public void destroySection() {
        destroyedSections++;
    }

    public boolean isDestroyed() {
        boolean value = destroyedSections >= segments;
        return value;
    }

    public void setDrawPosition(Position gridPosition, Position drawPosition) {
        this.gridPosition = gridPosition;
        this.drawPosition = drawPosition;
    }

    public boolean isSideWays() {
        return isSideWays;
    }

    public List<Position> getOccupiedCoordinates() {
        List<Position> coordinates = new ArrayList<>();
        if (isSideWays) {
            for (int x = 0; x < segments; x++) {
                coordinates.add(new Position(gridPosition.x + x, gridPosition.y));
            }
        } else {
            for (int y = 0; y < segments; y++) {
                coordinates.add(new Position(gridPosition.x, gridPosition.y + y));
            }
        }
        return coordinates;
    }

    public int getSegments() {
        return segments;
    }

    public void paintVertical(GraphicsContext gc) {
        int boatWidth = (int) (30 * 0.8);
        int boatLeftX = drawPosition.x + 30 / 2 - boatWidth / 2;
        gc.fillPolygon(new double[]{drawPosition.x + 30 / 2, boatLeftX, boatLeftX + boatWidth}, new double[]{drawPosition.y + 30 / 4, drawPosition.y + 30, drawPosition.y + 30}, 3);
        gc.fillRect(boatLeftX, drawPosition.y + 30, boatWidth, 30 * (segments - 1.2));
    }

    public void paintHorizontal(GraphicsContext gc) {
        int boatWidth = (int) (30 * 0.8);
        int boatTopY = drawPosition.y + 30 / 2 - boatWidth / 2;
        gc.fillPolygon(new double[]{drawPosition.x + 30 / 4, drawPosition.x + 30, drawPosition.x + 30}, new double[]{drawPosition.y + 30 / 2, boatTopY, boatTopY + boatWidth}, 3);
        gc.fillRect(drawPosition.x + 30, boatTopY, 30 * (segments - 1.2), boatWidth);
    }
}
