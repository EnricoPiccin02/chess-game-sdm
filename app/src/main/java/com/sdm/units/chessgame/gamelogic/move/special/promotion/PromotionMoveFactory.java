package com.sdm.units.chessgame.gamelogic.move.special.promotion;

import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMoveFactory;
import com.sdm.units.chessgame.gamelogic.move.standard.StandardMove;

public class PromotionMoveFactory implements SpecialMoveFactory<PromotionCandidate> {

    private final PromotionPieceSelector selector;

    public PromotionMoveFactory(PromotionPieceSelector selector) {
        this.selector = selector;
    }

    @Override
    public ReversibleMove create(PromotionCandidate candidate) {
        ReversibleMove pawnMove = new StandardMove(
            candidate.from(),
            candidate.to(),
            candidate.movingPawn(),
            candidate.capturedPiece());

        ReversibleMove pawnPromotion = new PieceReplacementMove(
            candidate.to(),
            selector.selectPromotionPiece(candidate.movingPawn().pieceColor()));

        return new PromotionMove(pawnMove, pawnPromotion);
    }
}