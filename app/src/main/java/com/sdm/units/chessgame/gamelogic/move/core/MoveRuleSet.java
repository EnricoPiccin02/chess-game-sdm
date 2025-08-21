package com.sdm.units.chessgame.gamelogic.move.core;

import java.util.List;

public final class MoveRuleSet {
    
    private final List<ComposedMoveRule> rules;

    public MoveRuleSet(List<ComposedMoveRule> rules) {
        this.rules = rules;
    }

    public MoveValidator asValidator() {
        return new CompositeMoveValidator(rules);
    }

    public MoveGenerator asGenerator() {
        return new CompositeMoveGenerator(rules);
    }

    public List<ComposedMoveRule> rules() {
        return rules;
    }
}