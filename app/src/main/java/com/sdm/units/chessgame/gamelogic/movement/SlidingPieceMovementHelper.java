package com.sdm.units.chessgame.gamelogic.movement;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public class SlidingPieceMovementHelper implements SlidingMovementResolver {

    public Set<ChessboardPosition> getSlidingDestinations(Chessboard board, ChessboardPosition fromPosition, ChessPieceColor playerColor, List<ChessboardDirection> directions) {
        Set<ChessboardPosition> legalDestinations = new HashSet<>();

        directions.forEach(direction -> {
            Optional<ChessboardPosition> currentPosition = fromPosition.nextPosition(direction);

            while (currentPosition.isPresent()) {
                ChessboardPosition pos = currentPosition.get();

                if (board.isUnoccupiedSquare(pos)) {
                    legalDestinations.add(pos);
                } else {
                    if (board.isOpponentAt(playerColor, pos)) {
                        legalDestinations.add(pos); // Capture
                    }
                    break; // Exit after hitting a piece
                }

                currentPosition = pos.nextPosition(direction);
            }
        });

        return legalDestinations;
    }
}