package com.example.battleship.AI;

import com.example.battleship.board.Position;
import com.example.battleship.board.SelectionGrid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EasyAI {
    private SelectionGrid playerGrid;
    private Random random;
    private List<Position> possibleMoves;

    public EasyAI(SelectionGrid playerGrid) {
        this.playerGrid = playerGrid;
        this.random = new Random();
        this.possibleMoves = generateAllPossibleMoves();
    }

    private List<Position> generateAllPossibleMoves() {
        List<Position> moves = new ArrayList<>();
        for (int x = 0; x < SelectionGrid.GRID_WIDTH; x++) {
            for (int y = 0; y < SelectionGrid.GRID_HEIGHT; y++) {
                moves.add(new Position(x, y));
            }
        }
        return moves;
    }

    public void reset() {
        this.possibleMoves = generateAllPossibleMoves();
    }

    public Position selectMove() {
        if (possibleMoves.isEmpty()) {
            throw new IllegalStateException("No more possible moves available");
        }
        int index = random.nextInt(possibleMoves.size());
        Position move = possibleMoves.remove(index);
        return move;
    }
}
