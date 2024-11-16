package com.example.battleship.board;

public class Rectangle {

    protected Position position;
    protected int height;
    protected int width;

    public Rectangle(Position position, int width, int height) {
        this.position = position;
        this.height = height;
        this.width = width;
    }

    public Rectangle(int x, int y, int width, int height) {
        this(new Position(x, y), width, height);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Position getPosition() {
        return position;
    }
    public boolean isPositionInside(Position targetPosition) {
        return targetPosition.x >= position.x && targetPosition.y >= position.y
                && targetPosition.x < position.x + width && targetPosition.y < position.y + height;
    }
}
