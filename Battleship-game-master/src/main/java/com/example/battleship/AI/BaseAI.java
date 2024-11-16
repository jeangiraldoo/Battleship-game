package com.example.battleship.AI;

import com.example.battleship.board.Position;
import com.example.battleship.board.SelectionGrid;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAI {

    protected SelectionGrid playerGrid;

    protected List<Position> validMoves;

    public BaseAI(SelectionGrid playerGrid) {
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

    public abstract Position selectMove();
    public void reset(){
        createValidMoveList();
    }


}
