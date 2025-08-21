package com.sdm.units.chessgame.gamelogic.move.special.promotion;

import com.sdm.units.chessgame.gamelogic.move.core.MoveRulePriority;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMoveRule;

public class PromotionRule extends SpecialMoveRule<PromotionCandidate> {

    public PromotionRule(PromotionPattern pattern, PromotionEligibility eligibility, PromotionMoveFactory factory) {
        super(pattern, eligibility, factory);
    }

    @Override
    public int getPriority() {
        return MoveRulePriority.HIGH_PRIORITY.getPriority();
    }
}