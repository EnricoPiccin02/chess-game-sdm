package com.sdm.units.chessgame.gamelogic.move.special.promotion;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMoveEligibility;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public class PromotionEligibility implements SpecialMoveEligibility<PromotionCandidate> {

    private boolean isPawn(ChessPiece piece) {
        return piece.pieceInfo() == ChessPieceInfo.PAWN;
    }

    private boolean isForwardOneStep(ChessboardPosition from, ChessboardPosition to, ChessPieceColor color, ChessboardOrientation orientation) {
        return from.rank()
            .nextRank(orientation.pawnForwardDirection(color))
            .filter(next -> next.equals(to.rank()))
            .isPresent();
    }

    private boolean isAtPromotionRank(ChessboardPosition to, ChessPieceColor color, ChessboardOrientation orientation) {
        return to.rank().equals(orientation.promotionRank(color));
    }

    @Override
    public boolean canExecute(Chessboard board, PromotionCandidate candidate, ChessboardOrientation orientation) {
        ChessboardPosition from = candidate.from();
        ChessboardPosition to = candidate.to();
        ChessPiece pawn = candidate.movingPawn();
        
        return isPawn(pawn) &&
            isForwardOneStep(from, to, pawn.pieceColor(), orientation) &&
            isAtPromotionRank(to, pawn.pieceColor(), orientation);
    }
}