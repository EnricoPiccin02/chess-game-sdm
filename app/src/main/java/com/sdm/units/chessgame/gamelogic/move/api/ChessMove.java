package com.sdm.units.chessgame.gamelogic.move.api;

import java.util.List;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;

public interface ChessMove {
    
    void executeOn(Chessboard board);

    List<MoveComponent> getMoveComponents();
}