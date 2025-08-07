package com.sdm.units.chessgame.gamelogic.movement;

import java.util.List;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public class SlidingMovementStrategy implements MovementStrategy {
    
    private final DirectionProvider directionProvider;
    private final SlidingPieceMovementHelper slidingHelper;

    public SlidingMovementStrategy(DirectionProvider directionProvider) {
        this.directionProvider = directionProvider;
        this.slidingHelper = new SlidingPieceMovementHelper();
    }
    
    @Override
    public List<ChessboardPosition> getLegalMoves(Chessboard board, ChessboardPosition fromPosition, ChessPieceColor playerColor) {
        return slidingHelper.getSlidingMoves(
            board,
            fromPosition,
            playerColor,
            directionProvider.getDirections()
        );
    }
}