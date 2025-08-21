package com.sdm.units.chessgame.gamelogic.move.special.enpassant;

import com.sdm.units.chessgame.gamelogic.move.core.CompositeMove;
import com.sdm.units.chessgame.gamelogic.move.core.MoveComponent;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;

public class EnPassantMove extends CompositeMove {

    private final MoveComponent moveComponent;

    public EnPassantMove(ReversibleMove captureAdjacentMove, ReversibleMove forwardMove) {
        super(captureAdjacentMove, forwardMove);
        this.moveComponent = new MoveComponent(
            captureAdjacentMove.getPrimaryMoveComponent().from(),
            forwardMove.getPrimaryMoveComponent().to()
        );
    }

    @Override
    public MoveComponent getPrimaryMoveComponent() {
        return moveComponent;
    }

    @Override
    public String toString() {
        return moveComponent + " (EnPassant)";
    }
}