package com.sdm.units.chessgame.gamelogic.move.special.enpassant;

import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.board.MoveRecorder;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.move.core.MoveComponent;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMoveEligibility;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public class EnPassantEligibility implements SpecialMoveEligibility<EnPassantCandidate> {

    private final MoveRecorder<ReversibleMove> moveRecorder;

    public EnPassantEligibility(MoveRecorder<ReversibleMove> moveRecorder) {
        this.moveRecorder = moveRecorder;
    }

    private boolean isPawn(ChessPiece piece) {
        return piece.pieceInfo() == ChessPieceInfo.PAWN;
    }

    private boolean areOpponentPawns(ChessPiece movingPawn, ChessPiece targetPawn) {
        return movingPawn.pieceColor().opponent().equals(targetPawn.pieceColor());
    }

    private boolean isLandingPosClear(Chessboard board, EnPassantCandidate candidate) {
        return board.getPieceAt(candidate.to()).isEmpty();
    }

    public boolean isValidPawnLastMove(Chessboard board, ChessPiece targetPawn, ChessboardOrientation orientation) {
        Optional<ReversibleMove> lastMove = moveRecorder.getLastMove();
        if (lastMove.isEmpty()) return false;
                
        MoveComponent component = lastMove.get().getPrimaryMoveComponent();
        if (!targetPawn.equals(board.getPieceAt(component.to()).orElse(null))) return false;
        
        return isTwoSquarePawnMove(component.from(), component.to(), targetPawn.pieceColor(), orientation);
    }

    private boolean isTwoSquarePawnMove(ChessboardPosition from, ChessboardPosition to, ChessPieceColor color, ChessboardOrientation orientation) {
        if (!from.file().equals(to.file())) return false;
        if (!from.rank().equals(orientation.getPawnRank(color))) return false;

        return from.rank()
            .nextRank(orientation.pawnForwardDirection(color))
            .flatMap(r -> r.nextRank(orientation.pawnForwardDirection(color)))
            .filter(expected -> expected.equals(to.rank()))
            .isPresent();
    }

    @Override
    public boolean canExecute(Chessboard board, EnPassantCandidate candidate, ChessboardOrientation orientation) {
        ChessPiece movingPawn = candidate.movingPawn();
        ChessPiece targetPawn = candidate.capturedPawn();

        return isPawn(targetPawn) &&
            areOpponentPawns(movingPawn, targetPawn) &&
            isLandingPosClear(board, candidate) &&
            isValidPawnLastMove(board, targetPawn, orientation);
    }
}