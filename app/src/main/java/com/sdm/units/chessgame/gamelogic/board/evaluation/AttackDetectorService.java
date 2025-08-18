package com.sdm.units.chessgame.gamelogic.board.evaluation;

import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.board.state.KingLocator;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.move.core.MoveValidator;

public class AttackDetectorService implements AttackDetector {
    
    private final MoveValidator moveValidator;
    private final KingLocator kingLocator;
    private final ChessboardOrientation orientation;

    public AttackDetectorService(MoveValidator moveValidator, ChessboardOrientation orientation) {
        this.moveValidator = moveValidator;
        this.orientation = orientation;
        this.kingLocator = new KingLocator();
    }

    @Override
    public boolean isUnderAttack(Chessboard board, ChessPieceColor defenderColor) {
        Optional<ChessboardPosition> maybeKingPosition = kingLocator.locateFirstOf(board, defenderColor);

        if (maybeKingPosition.isPresent()) {
            return board.getOccupiedSquaresOf(defenderColor.opponent()).stream()
                .anyMatch(from -> moveValidator.validateAndCreate(board, from, maybeKingPosition.get(), orientation).isPresent());
        }
        
        return false;
    }
}