package com.sdm.units.chessgame.gamelogic.move.special.promotion;

import java.util.List;
import java.util.function.Supplier;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.move.api.MoveComponent;
import com.sdm.units.chessgame.gamelogic.move.api.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public final class PromotionMove implements ReversibleMove {

    private final ChessboardPosition pawnPosition;
    private final ChessboardPosition promotionPosition;
    private final ChessPiece originalPawn;
    private final Supplier<? extends ChessPiece> promotedPieceSupplier;
    private ChessPiece promotedPiece;

    public PromotionMove(ChessboardPosition pawnPosition, ChessboardPosition promotionPosition, ChessPiece originalPawn, Supplier<? extends ChessPiece> promotedPieceSupplier) {
        this.pawnPosition = pawnPosition;
        this.promotionPosition = promotionPosition;
        this.originalPawn = originalPawn;
        this.promotedPieceSupplier = promotedPieceSupplier;
    }

    @Override
    public void executeOn(Chessboard board) {
        board.removePieceAt(pawnPosition);
        promotedPiece = promotedPieceSupplier.get();
        promotedPiece.markAsMoved();
        board.putPieceAt(promotionPosition, promotedPiece);
    }

    @Override
    public void undoOn(Chessboard board) {
        board.removePieceAt(promotionPosition);
        board.putPieceAt(pawnPosition, originalPawn);
    }

    @Override
    public List<MoveComponent> getMoveComponents() {
        return List.of(new MoveComponent(pawnPosition, promotionPosition));
    }

    @Override
    public String toString() {
        return pawnPosition + " → " + promotionPosition + " (Promotion)";
    }
}