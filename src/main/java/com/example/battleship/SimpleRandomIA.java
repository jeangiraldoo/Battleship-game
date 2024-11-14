package com.example.battleship;

import java.util.Collections;

public class SimpleRandomIA extends BattleshipBot{

    public SimpleRandomIA(SelectionGrid selectionGrid) {
        super(selectionGrid);
        Collections.shuffle(validMoves);

    }
    @Override
    public void reset(){
        super.reset();
        Collections.shuffle(validMoves);
    }


    public Position selectMove(){
        Position nextMove = validMoves.get(0);
        validMoves.remove(0);
        return nextMove;
    }


}
