package com.sdm.units.chessgame.gamelogic.move.special.enpassant;

import com.sdm.units.chessgame.gamelogic.move.core.MoveRulePriority;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMoveRule;

public class EnPassantRule extends SpecialMoveRule<EnPassantCandidate> {

    public EnPassantRule(EnPassantPattern pattern, EnPassantEligibility eligibility, EnPassantMoveFactory factory) {
        super(pattern, eligibility, factory);
    }

    @Override
    public int getPriority() {
        return MoveRulePriority.MEDIUM_PRIORITY.getPriority();
    }
}