package com.sdm.units.chessgame.gamelogic.move.special.castling;

import com.sdm.units.chessgame.gamelogic.move.core.CompositeMove;
import com.sdm.units.chessgame.gamelogic.move.core.MoveComponent;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;

public final class CastlingMove extends CompositeMove {

    private final ReversibleMove kingMove;

    public CastlingMove(ReversibleMove kingMove, ReversibleMove rookMove) {
        super(kingMove, rookMove);
        this.kingMove = kingMove;
    }
    
    @Override
    public MoveComponent getPrimaryMoveComponent() {
        return kingMove.getPrimaryMoveComponent();
    }

    @Override
    public String toString() {
        return super.toString() + " (Castling)";
    }
}