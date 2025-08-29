package com.sdm.units.chessgame.gamecontrol.assembler.domain;

import java.util.ArrayList;
import java.util.List;

import com.sdm.units.chessgame.gamelogic.board.evaluation.EvaluatorsFactory;
import com.sdm.units.chessgame.gamelogic.move.SingleRuleFactory;
import com.sdm.units.chessgame.gamelogic.move.core.ComposedMoveRule;
import com.sdm.units.chessgame.gamelogic.move.core.MoveRuleSet;
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingRule;

public class RulesFactory {

    private final SingleRuleFactory ruleFactory;
    private final EvaluatorsFactory evaluatorsFactory;
    private MoveRuleSet baseRules;

    public RulesFactory(SingleRuleFactory ruleFactory, EvaluatorsFactory evaluatorsFactory) {
        this.ruleFactory = ruleFactory;
        this.evaluatorsFactory = evaluatorsFactory;
    }

    private MoveRuleSet baseRules() {
        if (baseRules == null) {
            baseRules = new MoveRuleSet(List.of(
                ruleFactory.promotionRule(),
                ruleFactory.enPassantRule(),
                ruleFactory.standardRule()
            ));
        }
        return baseRules;
    }

    private CastlingRule castlingRule() {        
        return ruleFactory.castlingRule(
            evaluatorsFactory.moveSimulationService(baseRules()),
            evaluatorsFactory.attackDetector(baseRules())
        );
    }

    public MoveRuleSet createCompleteRules() {
        List<ComposedMoveRule> rules = new ArrayList<>(baseRules().rules());
        rules.add(castlingRule());
        return new MoveRuleSet(rules);
    }
}