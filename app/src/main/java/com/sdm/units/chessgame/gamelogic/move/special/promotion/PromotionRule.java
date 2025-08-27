package com.sdm.units.chessgame.gamelogic.move.special.promotion;

import com.sdm.units.chessgame.gamelogic.move.core.MoveRulePriority;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMoveEligibility;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMoveFactory;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMovePattern;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMoveRule;

public class PromotionRule extends SpecialMoveRule<PromotionCandidate> {

    public PromotionRule(SpecialMovePattern<PromotionCandidate> pattern, SpecialMoveEligibility<PromotionCandidate> eligibility, SpecialMoveFactory<PromotionCandidate> factory) {
        super(pattern, eligibility, factory);
    }

    @Override
    public int getPriority() {
        return MoveRulePriority.HIGH_PRIORITY.getPriority();
    }
}