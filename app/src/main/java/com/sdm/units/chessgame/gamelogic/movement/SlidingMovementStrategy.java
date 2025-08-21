package com.sdm.units.chessgame.gamelogic.movement;

import java.util.Set;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
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
    public Set<ChessboardPosition> getLegalDestinations(Chessboard board, ChessboardPosition fromPosition, ChessPieceColor playerColor) {
        return slidingHelper.getSlidingDestinations(
            board,
            fromPosition,
            playerColor,
            directionProvider.getDirections()
        );
    }
}