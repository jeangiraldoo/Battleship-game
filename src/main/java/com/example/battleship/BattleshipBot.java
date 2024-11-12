package com.example.battleship;

import java.util.ArrayList;
import java.util.List;

public class BattleshipBot {

    protected SelectionGrid playerGrid;

    protected List<Position> validMoves;

    public BattleshipBot(SelectionGrid playerGrid) {
        this.playerGrid = playerGrid;
        createValidMoveList();
    }
    private void createValidMoveList() {
        validMoves = new ArrayList<>();
        for(int x = 0; x < SelectionGrid.GRID_WIDTH; x++){
            for(int y = 0; y < SelectionGrid.GRID_HEIGHT; y++){
                validMoves.add(new Position(x, y));
            }
        }
    }

    public Position selectionMove(){
        return  Position.ZERO;
    }
    public void reset(){
        createValidMoveList();
    }


}
