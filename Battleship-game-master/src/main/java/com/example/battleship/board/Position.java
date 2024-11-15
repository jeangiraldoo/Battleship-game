package com.example.battleship.board;

public class Position {

    public static final Position DOWN = new Position(0,1);

    public static final Position UP = new Position(0,-1);

    public static final Position RIGHT = new Position(1,0);

    public static final Position LEFT = new Position(-1,0);

    public static final Position ZERO = new Position(0,0);

    public int x;

    public int y;



    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public Position(Position positionToCopy) {
        this.x = positionToCopy.x;
        this.y = positionToCopy.y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void add(Position otherPosition) {
        this.x += otherPosition.x;
        this.y += otherPosition.y;
    }

    public double distanceTo(Position otherPosition) {
        return Math.sqrt(Math.pow(this.x - otherPosition.x, 2) + Math.pow(this.y - otherPosition.y, 2));
    }

    public void multiply(int amount){
        this.x *= amount;
        this.y *= amount;
    }

    public void substract(Position otherPosition) {
        this.x -= otherPosition.x;
        this.y -= otherPosition.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
