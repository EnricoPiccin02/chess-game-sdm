package com.sdm.units.chessgame.gamelogic.move.special.enpassant;

import com.sdm.units.chessgame.gamelogic.move.core.MoveRulePriority;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMoveEligibility;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMoveFactory;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMovePattern;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMoveRule;

public class EnPassantRule extends SpecialMoveRule<EnPassantCandidate> {

    public EnPassantRule(SpecialMovePattern<EnPassantCandidate> pattern, SpecialMoveEligibility<EnPassantCandidate> eligibility, SpecialMoveFactory<EnPassantCandidate> factory) {
        super(pattern, eligibility, factory);
    }

    @Override
    public int getPriority() {
        return MoveRulePriority.MEDIUM_PRIORITY.getPriority();
    }
}