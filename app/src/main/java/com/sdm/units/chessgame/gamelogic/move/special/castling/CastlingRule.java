package com.sdm.units.chessgame.gamelogic.move.special.castling;

import com.sdm.units.chessgame.gamelogic.move.core.MoveRulePriority;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMoveEligibility;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMoveFactory;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMovePattern;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMoveRule;

public class CastlingRule extends SpecialMoveRule<CastlingCandidate> {

    public CastlingRule(SpecialMovePattern<CastlingCandidate> pattern, SpecialMoveEligibility<CastlingCandidate> eligibility, SpecialMoveFactory<CastlingCandidate> factory) {
        super(pattern, eligibility, factory);
    }

    @Override
    public int getPriority() {
        return MoveRulePriority.MEDIUM_PRIORITY.getPriority();
    }
}