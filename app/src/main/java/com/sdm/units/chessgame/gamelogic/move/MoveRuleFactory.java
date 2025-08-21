package com.sdm.units.chessgame.gamelogic.move;

import com.sdm.units.chessgame.gamelogic.board.evaluation.AttackDetector;
import com.sdm.units.chessgame.gamelogic.board.evaluation.PathSafetySimulator;
import com.sdm.units.chessgame.gamelogic.board.state.MoveRecorder;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingEligibility;
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingMoveFactory;
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingPattern;
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingRule;
import com.sdm.units.chessgame.gamelogic.move.special.enpassant.EnPassantEligibility;
import com.sdm.units.chessgame.gamelogic.move.special.enpassant.EnPassantMoveFactory;
import com.sdm.units.chessgame.gamelogic.move.special.enpassant.EnPassantPattern;
import com.sdm.units.chessgame.gamelogic.move.special.enpassant.EnPassantRule;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionEligibility;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionMoveFactory;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionPattern;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionPieceSelector;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionRule;
import com.sdm.units.chessgame.gamelogic.move.standard.StandardMoveRule;

public final class MoveRuleFactory {

    private final MoveRecorder<ReversibleMove> recorder;
    private final PromotionPieceSelector selector;
    private final CastlingPattern castlingPattern;

    public MoveRuleFactory(MoveRecorder<ReversibleMove> recorder, PromotionPieceSelector selector) {
        this.recorder = recorder;
        this.selector = selector;
        this.castlingPattern = new CastlingPattern();
    }

    public PromotionRule promotionRule() {
        return new PromotionRule(new PromotionPattern(),
            new PromotionEligibility(),
            new PromotionMoveFactory(selector));
    }

    public EnPassantRule enPassantRule() {
        return new EnPassantRule(new EnPassantPattern(),
            new EnPassantEligibility(recorder),
            new EnPassantMoveFactory());
    }

    public CastlingRule castlingRule(PathSafetySimulator simulator, AttackDetector attackDetector) {
        return new CastlingRule(castlingPattern,
            new CastlingEligibility(simulator, attackDetector, castlingPattern),
            new CastlingMoveFactory());
    }

    public StandardMoveRule standardRule() {
        return new StandardMoveRule();
    }
}