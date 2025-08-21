package com.sdm.units.chessgame.gamelogic.movement;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public class KnightMovementStrategy implements MovementStrategy {

    private final CompositeDirectionProvider directionProvider;

    public KnightMovementStrategy(CompositeDirectionProvider directionProvider) {
        this.directionProvider = directionProvider;
    }

    @Override
    public Set<ChessboardPosition> getLegalDestinations(Chessboard board, ChessboardPosition fromPosition, ChessPieceColor playerColor) {
        Set<ChessboardPosition> legalDestinations = new HashSet<>();

        for (List<ChessboardDirection> path : directionProvider.getCompositeDirections()) {
            Optional<ChessboardPosition> target = fromPosition.nextPosition(path);
            target.ifPresent(pos -> {
                if (board.isUnoccupiedSquare(pos) || board.isOpponentAt(playerColor, pos)) {
                    legalDestinations.add(pos);
                }
            });
        }

        return legalDestinations;
    }
}