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

    /**
     * Returns the height of the rectangle.
     * @return height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the width of the rectangle.
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the position of the rectangle.
     * @return position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Returns True or False based on if the position falls between a certain range.
     * @param targetPosition position to validate.
     * @return True or False.
     */
    public boolean isPositionInside(Position targetPosition) {
        return targetPosition.x >= position.x && targetPosition.y >= position.y
                && targetPosition.x < position.x + width && targetPosition.y < position.y + height;
    }
}
