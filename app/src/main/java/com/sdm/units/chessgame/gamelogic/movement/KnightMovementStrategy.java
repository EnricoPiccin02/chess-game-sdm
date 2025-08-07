package com.sdm.units.chessgame.gamelogic.movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public class KnightMovementStrategy implements MovementStrategy {

    private final CompositeDirectionProvider directionProvider;

    public KnightMovementStrategy(CompositeDirectionProvider directionProvider) {
        this.directionProvider = directionProvider;
    }

    @Override
    public List<ChessboardPosition> getLegalMoves(Chessboard board, ChessboardPosition fromPosition, ChessPieceColor playerColor) {
        List<ChessboardPosition> legalMoves = new ArrayList<>();

        for (List<ChessboardDirection> path : directionProvider.getCompositeDirections()) {
            Optional<ChessboardPosition> target = fromPosition.nextPosition(path);
            target.ifPresent(pos -> {
                if (board.isPositionVacant(pos) || board.isOpponentAt(playerColor, pos)) {
                    legalMoves.add(pos);
                }
            });
        }

        return legalMoves;
    }
}