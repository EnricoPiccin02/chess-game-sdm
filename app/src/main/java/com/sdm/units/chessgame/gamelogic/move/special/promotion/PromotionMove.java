package com.sdm.units.chessgame.gamelogic.move.special.promotion;

import com.sdm.units.chessgame.gamelogic.move.core.CompositeMove;
import com.sdm.units.chessgame.gamelogic.move.core.MoveComponent;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;

public final class PromotionMove extends CompositeMove {
    
    private final ReversibleMove pawnMove;

    public PromotionMove(ReversibleMove pawnMove, ReversibleMove pawnPromotion) {
        super(pawnMove, pawnPromotion);
        this.pawnMove = pawnMove;
    }

    @Override
    public MoveComponent getPrimaryMoveComponent() {
        return pawnMove.getPrimaryMoveComponent();
    }

    @Override
    public String toString() {
        return super.toString() + " (Promotion)";
    }
}