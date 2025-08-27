package com.sdm.units.chessgame.gamelogic.move.special.enpassant;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMovePattern;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public class EnPassantPattern implements SpecialMovePattern<EnPassantCandidate> {

    public List<EnPassantCandidate> findCandidates(Chessboard board, ChessboardPosition from, ChessboardOrientation orientation) {
        List<EnPassantCandidate> candidates = new ArrayList<>();

        candidateForDirection(board, from, orientation, ChessboardDirection.LEFT).ifPresent(candidates::add);
        candidateForDirection(board, from, orientation, ChessboardDirection.RIGHT).ifPresent(candidates::add);

        return candidates;
    }

    public Optional<EnPassantCandidate> buildCandidate(Chessboard board, ChessboardPosition from, ChessboardPosition to) {
        if (!isDiagonalMove(from, to) || board.getPieceAt(to).isPresent()) return Optional.empty();

        ChessboardPosition capturedPawnPos = new ChessboardPosition(to.file(), from.rank());
        return board.getPieceAt(from)
            .flatMap(pawn -> board.getPieceAt(capturedPawnPos)
                .map(captured -> new EnPassantCandidate(from, to, capturedPawnPos, pawn, captured)));
    }

    private Optional<EnPassantCandidate> candidateForDirection(Chessboard board, ChessboardPosition from, ChessboardOrientation orientation, ChessboardDirection side) {
        return board.getPieceAt(from).flatMap(pawn -> 
            from.nextPosition(side)
                .flatMap(adjacent -> {
                    Optional<ChessPiece> capturedPiece = board.getPieceAt(adjacent);
                    if (capturedPiece.isEmpty()) return Optional.empty();
                    return adjacent.nextPosition(orientation.pawnForwardDirection(pawn.pieceColor()))
                        .map(landing -> new EnPassantCandidate(from, landing, adjacent, pawn, capturedPiece.get()));
                })
        );
    }

    private boolean isDiagonalMove(ChessboardPosition from, ChessboardPosition to) {
        return from.isDiagonalTo(to);
    }
}