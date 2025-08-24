package com.sdm.units.chessgame.gamelogic.board.evaluation;

import java.util.HashMap;
import java.util.Map;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.move.core.MoveRuleSet;

public final class EvaluatorsFactory {
    
    private final ChessboardOrientation orientation;
    private final Map<MoveRuleSet, AttackDetectorService> detectors = new HashMap<>();
    private final Map<MoveRuleSet, MoveSimulationService> simulators = new HashMap<>();
    private final Map<MoveRuleSet, CheckmateService> checkmates = new HashMap<>();

    public EvaluatorsFactory(ChessboardOrientation orientation) {
        this.orientation = orientation;
    }

    public AttackDetectorService attackDetector(MoveRuleSet ruleSet) {
        return detectors.computeIfAbsent(ruleSet,
            rs -> new AttackDetectorService(rs.asValidator(), orientation));
    }

    public MoveSimulationService moveSimulationService(MoveRuleSet ruleSet) {
        return simulators.computeIfAbsent(ruleSet,
            rs -> new MoveSimulationService(rs.asValidator(), attackDetector(rs), orientation));
    }

    public CheckmateService checkmateService(MoveRuleSet ruleSet) {
        return checkmates.computeIfAbsent(ruleSet,
            rs -> new CheckmateService(rs.asGenerator(), moveSimulationService(rs), attackDetector(rs), orientation));
    }

    public GameOutcomeEvaluator gameOutcomeEvaluator(MoveRuleSet ruleSet) {
        return new DefaultGameOutcomeEvaluator(attackDetector(ruleSet), checkmateService(ruleSet));
    }
}