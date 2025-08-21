package com.sdm.units.chessgame.gamelogic.move.special.castling;

import com.sdm.units.chessgame.gamelogic.move.core.MoveRulePriority;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMoveRule;

public class CastlingRule extends SpecialMoveRule<CastlingCandidate> {

    public CastlingRule(CastlingPattern pattern, CastlingEligibility eligibility, CastlingMoveFactory factory) {
        super(pattern, eligibility, factory);
    }

    @Override
    public int getPriority() {
        return MoveRulePriority.MEDIUM_PRIORITY.getPriority();
    }
}