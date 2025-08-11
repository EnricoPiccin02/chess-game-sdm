package com.sdm.units.chessgame.gamelogic.move.special.promotion;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.move.api.ComposedMoveRule;
import com.sdm.units.chessgame.gamelogic.move.api.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public class PromotionRule implements ComposedMoveRule {

    private final PromotionPieceSelector pieceSelector;

    public PromotionRule(PromotionPieceSelector pieceSelector) {
        this.pieceSelector = pieceSelector;
    }

    @Override
    public List<ReversibleMove> generateMovesFrom(Chessboard board, ChessboardPosition from, ChessboardOrientation orientation) {
        return board.getPieceAt(from)
            .filter(this::isPawn)
            .map(pawn -> generatePromotionMoves(board, from, pawn, orientation))
            .orElse(List.of());
    }

    @Override
    public Optional<ReversibleMove> validateAndCreate(Chessboard board, ChessboardPosition from, ChessboardPosition to, ChessboardOrientation orientation) {
        return board.getPieceAt(from)
            .filter(this::isPawn)
            .filter(pawn -> isSingleStep(from, to, pawn.pieceColor(), orientation))
            .filter(pawn -> isAtPromotionRank(to, pawn.pieceColor(), orientation))
            .map(pawn -> createPromotionMove(from, to, pawn));
    }

    private boolean isPawn(ChessPiece piece) {
        return piece.pieceInfo() == ChessPieceInfo.PAWN;
    }

    private List<ReversibleMove> generatePromotionMoves(Chessboard board, ChessboardPosition from, ChessPiece pawn, ChessboardOrientation orientation) {
        return pawn.getLegalMoves(board, from).stream()
            .filter(to -> isAtPromotionRank(to, pawn.pieceColor(), orientation))
            .map(to -> createPromotionMove(from, to, pawn))
            .toList();
    }

    private boolean isSingleStep(ChessboardPosition from, ChessboardPosition to, ChessPieceColor color, ChessboardOrientation orientation) {
        return from.rank()
            .nextRank(orientation.pawnForwardDirection(color))
            .filter(next -> next.equals(to.rank()))
            .isPresent();
    }

    private boolean isAtPromotionRank(ChessboardPosition to, ChessPieceColor color, ChessboardOrientation orientation) {
        return to.rank().equals(orientation.promotionRank(color));
    }

    private ReversibleMove createPromotionMove(ChessboardPosition from, ChessboardPosition to, ChessPiece pawn) {
        Supplier<? extends ChessPiece> supplier = () -> pieceSelector.selectPromotionPiece(pawn.pieceColor());
        return new PromotionMove(from, to, pawn, supplier);
    }

    @Override
    public int getPriority() {
        return 0;
    }
}