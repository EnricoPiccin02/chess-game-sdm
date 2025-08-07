package com.sdm.units.chessgame.gamelogic.movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public class SlidingPieceMovementHelper implements SlidingMovementResolver {

    public List<ChessboardPosition> getSlidingMoves(Chessboard board, ChessboardPosition fromPosition, ChessPieceColor playerColor, List<ChessboardDirection> directions) {
        List<ChessboardPosition> legalMoves = new ArrayList<>();

        directions.forEach(direction -> {
            Optional<ChessboardPosition> currentPosition = fromPosition.nextPosition(direction);

            while (currentPosition.isPresent()) {
                ChessboardPosition pos = currentPosition.get();

                if (board.isPositionVacant(pos)) {
                    legalMoves.add(pos);
                } else {
                    if (board.isOpponentAt(playerColor, pos)) {
                        legalMoves.add(pos); // Capture
                    }
                    break; // Exit after hitting a piece
                }

                currentPosition = pos.nextPosition(direction);
            }
        });

        return legalMoves;
    }
}