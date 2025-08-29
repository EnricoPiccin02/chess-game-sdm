package unittest.chessgame.gamecontrol.assembler.domain;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamecontrol.assembler.domain.RulesFactory;
import com.sdm.units.chessgame.gamelogic.board.evaluation.EvaluatorsFactory;
import com.sdm.units.chessgame.gamelogic.board.state.MoveRecorder;
import com.sdm.units.chessgame.gamelogic.move.SingleRuleFactory;
import com.sdm.units.chessgame.gamelogic.move.core.MoveRuleSet;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingRule;
import com.sdm.units.chessgame.gamelogic.move.special.enpassant.EnPassantRule;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionPieceSelector;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionRule;
import com.sdm.units.chessgame.gamelogic.move.standard.StandardMoveRule;

@DisplayName("RulesFactory")
class RulesFactoryTest {

    private MoveRecorder<ReversibleMove> dummyRecorder;
    private PromotionPieceSelector dummySelector;
    private SingleRuleFactory ruleFactorySpy;
    private EvaluatorsFactory dummyEvaluatorsFactory;
    private RulesFactory factory;

    private PromotionRule dummyPromotionRule;
    private EnPassantRule dummyEnPassantRule;
    private StandardMoveRule dummyStandardRule;
    private CastlingRule dummyCastlingRule;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        dummyRecorder = mock(MoveRecorder.class);
        dummySelector = mock(PromotionPieceSelector.class);
        dummyEvaluatorsFactory = mock(EvaluatorsFactory.class);
        dummyPromotionRule = mock(PromotionRule.class);
        dummyEnPassantRule = mock(EnPassantRule.class);
        dummyStandardRule = mock(StandardMoveRule.class);
        dummyCastlingRule = mock(CastlingRule.class);

        ruleFactorySpy = spy(new SingleRuleFactory(dummyRecorder, dummySelector));
        doReturn(dummyPromotionRule).when(ruleFactorySpy).promotionRule();
        doReturn(dummyEnPassantRule).when(ruleFactorySpy).enPassantRule();
        doReturn(dummyStandardRule).when(ruleFactorySpy).standardRule();
        doReturn(dummyCastlingRule).when(ruleFactorySpy).castlingRule(any(), any());

        factory = new RulesFactory(ruleFactorySpy, dummyEvaluatorsFactory);
    }

    @Nested
    @DisplayName("when creating complete rules")
    class CreateCompleteRules {

        @Test
        @DisplayName("should include promotion rule")
        void shouldIncludePromotionRule() {
            MoveRuleSet rules = factory.createCompleteRules();

            assertTrue(rules.rules().contains(dummyPromotionRule));
        }

        @Test
        @DisplayName("should include en passant rule")
        void shouldIncludeEnPassantRule() {
            MoveRuleSet rules = factory.createCompleteRules();

            assertTrue(rules.rules().contains(dummyEnPassantRule));
        }

        @Test
        @DisplayName("should include standard rule")
        void shouldIncludeStandardRule() {
            MoveRuleSet rules = factory.createCompleteRules();

            assertTrue(rules.rules().contains(dummyStandardRule));
        }

        @Test
        @DisplayName("should include castling rule")
        void shouldIncludeCastlingRule() {
            MoveRuleSet rules = factory.createCompleteRules();

            assertTrue(rules.rules().contains(dummyCastlingRule));
        }
    }

    @Nested
    @DisplayName("when building base rules")
    class BaseRulesInitialization {

        @Test
        @DisplayName("should initialize only once even if requested multiple times")
        void shouldInitializeBaseRulesOnlyOnce() {
            MoveRuleSet first = factory.createCompleteRules();
            MoveRuleSet second = factory.createCompleteRules();

            assertSame(first.rules().get(0), second.rules().get(0));
        }
    }
}