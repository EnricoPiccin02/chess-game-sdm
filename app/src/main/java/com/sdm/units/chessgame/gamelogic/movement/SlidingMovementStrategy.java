package com.sdm.units.chessgame.gamelogic.movement;

import java.util.Set;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public class SlidingMovementStrategy implements MovementStrategy {
    
    private final DirectionProvider directionProvider;
    private final SlidingMovementResolver slidingResolver;

    public SlidingMovementStrategy(DirectionProvider directionProvider) {
        this.directionProvider = directionProvider;
        this.slidingResolver = new SlidingPieceMovementHelper();
    }
    
    @Override
    public Set<ChessboardPosition> getLegalDestinations(Chessboard board, ChessboardPosition fromPosition, ChessPieceColor playerColor) {
        return slidingResolver.getSlidingDestinations(
            board,
            fromPosition,
            playerColor,
            directionProvider.getDirections()
        );
    }
}