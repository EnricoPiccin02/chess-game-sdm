package com.sdm.units.chessgame.gamecontrol.assembler.domain;

import java.util.ArrayList;
import java.util.List;

import com.sdm.units.chessgame.gamelogic.board.evaluation.EvaluatorsFactory;
import com.sdm.units.chessgame.gamelogic.board.state.MoveRecorder;
import com.sdm.units.chessgame.gamelogic.move.MoveRuleFactory;
import com.sdm.units.chessgame.gamelogic.move.core.ComposedMoveRule;
import com.sdm.units.chessgame.gamelogic.move.core.MoveRuleSet;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingRule;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionPieceSelector;

public final class RulesFactory {

    private final MoveRuleFactory ruleFactory;
    private final EvaluatorsFactory evaluatorsFactory;
    private MoveRuleSet baseRules;

    public RulesFactory(MoveRecorder<ReversibleMove> recorder, PromotionPieceSelector selector, EvaluatorsFactory evaluatorsFactory) {
        this.ruleFactory = new MoveRuleFactory(recorder, selector);
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