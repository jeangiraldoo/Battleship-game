package com.example.battleship.AI;

import com.example.battleship.board.Position;
import com.example.battleship.board.SelectionGrid;

import java.util.Collections;

public class EasyAI extends BaseAI {

    public EasyAI(SelectionGrid selectionGrid) {
        super(selectionGrid);
        Collections.shuffle(validMoves);

    }
    @Override
    public void reset(){
        super.reset();
        Collections.shuffle(validMoves);
    }

    @Override
    public Position selectMove(){
        Position nextMove = validMoves.get(0);
        System.out.println("lenght");
        System.out.println(validMoves.size());
        validMoves.remove(0);
        System.out.println(validMoves.size());
        return nextMove;
    }


}
