package com.sdm.units.chessgame.gamelogic.movement;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public class KingMovementStrategy implements MovementStrategy {

    private final DirectionProvider directionProvider;

    public KingMovementStrategy(DirectionProvider directionProvider) {
        this.directionProvider = directionProvider;
    }

    @Override
    public Set<ChessboardPosition> getLegalDestinations(Chessboard board, ChessboardPosition fromPosition, ChessPieceColor playerColor) {
        Set<ChessboardPosition> legalDestinations = new HashSet<>();

        directionProvider.getDirections().forEach(direction -> {
            Optional<ChessboardPosition> currentPosition = fromPosition.nextPosition(direction);

            if (currentPosition.isPresent()) {
                ChessboardPosition pos = currentPosition.get();

                if (board.isUnoccupiedSquare(pos)) {
                    legalDestinations.add(pos);
                } else {
                    if (board.isOpponentAt(playerColor, pos)) {
                        legalDestinations.add(pos); // Capture
                    }
                }
            }
        });

        return legalDestinations;
    }
}