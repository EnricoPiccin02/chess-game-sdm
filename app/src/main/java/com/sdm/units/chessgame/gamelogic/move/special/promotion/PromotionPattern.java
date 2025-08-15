package com.sdm.units.chessgame.gamelogic.move.special.promotion;

import java.util.List;
import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMovePattern;

public class PromotionPattern implements SpecialMovePattern<PromotionCandidate> {

    @Override
    public List<PromotionCandidate> findCandidates(Chessboard board, ChessboardPosition from, ChessboardOrientation orientation) {
        return board.getPieceAt(from)
            .map(pawn -> pawn.getLegalMoves(board, from).stream()
                .map(to -> new PromotionCandidate(from, to, pawn, board.getPieceAt(to)))
                .toList())
            .orElse(List.of());
    }

    @Override
    public Optional<PromotionCandidate> buildCandidate(Chessboard board, ChessboardPosition from, ChessboardPosition to) {
        return board.getPieceAt(from)
            .map(pawn -> new PromotionCandidate(from, to, pawn, board.getPieceAt(to)));
    }   
}