package com.sdm.units.chessgame.gamelogic.move.standard;

import java.util.List;
import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.move.core.ComposedMoveRule;
import com.sdm.units.chessgame.gamelogic.move.core.MoveRulePriority;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;

public class StandardMoveRule implements ComposedMoveRule {

    @Override
    public List<ReversibleMove> generateMovesFrom(Chessboard board, ChessboardPosition from, ChessboardOrientation orientation) {
        return board.getPieceAt(from)
                .map(piece -> piece.getLegalMoves(board, from).stream()
                        .map(to -> validateAndCreate(board, from, to, orientation))
                        .flatMap(Optional::stream)
                        .toList())
                .orElse(List.of());
    }

    @Override
    public Optional<ReversibleMove> validateAndCreate(Chessboard board, ChessboardPosition from, ChessboardPosition to, ChessboardOrientation orientation) {
        return board.getPieceAt(from)
                .filter(piece -> piece.getLegalMoves(board, from).contains(to))
                .map(piece -> new StandardMove(from, to, piece, board.getPieceAt(to)));
    }

    @Override
    public int getPriority() {
        return MoveRulePriority.LOW_PRIORITY.getPriority();
    }
}