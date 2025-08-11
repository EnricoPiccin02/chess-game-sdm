package com.sdm.units.chessgame.gamelogic.movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public class KingMovementStrategy implements MovementStrategy {

    private final DirectionProvider directionProvider;

    public KingMovementStrategy(DirectionProvider directionProvider) {
        this.directionProvider = directionProvider;
    }

    @Override
    public List<ChessboardPosition> getLegalMoves(Chessboard board, ChessboardPosition fromPosition, ChessPieceColor playerColor) {
        List<ChessboardPosition> legalMoves = new ArrayList<>();

        directionProvider.getDirections().forEach(direction -> {
            Optional<ChessboardPosition> currentPosition = fromPosition.nextPosition(direction);

            if (currentPosition.isPresent()) {
                ChessboardPosition pos = currentPosition.get();

                if (board.isUnoccupiedSquare(pos)) {
                    legalMoves.add(pos);
                } else {
                    if (board.isOpponentAt(playerColor, pos)) {
                        legalMoves.add(pos); // Capture
                    }
                }
            }
        });

        return legalMoves;
    }
}