package unittest.chessgame.gamelogic.move;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.board.evaluation.AttackDetector;
import com.sdm.units.chessgame.gamelogic.board.evaluation.PathSafetySimulator;
import com.sdm.units.chessgame.gamelogic.board.state.MoveRecorder;
import com.sdm.units.chessgame.gamelogic.move.SingleRuleFactory;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingRule;
import com.sdm.units.chessgame.gamelogic.move.special.enpassant.EnPassantRule;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionPieceSelector;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionRule;
import com.sdm.units.chessgame.gamelogic.move.standard.StandardMoveRule;

@DisplayName("SingleRuleFactory")
public class SingleRuleFactoryTest {

    private MoveRecorder<ReversibleMove> recorderDummy;
    private PromotionPieceSelector selectorDummy;
    private SingleRuleFactory factory;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        recorderDummy = mock(MoveRecorder.class);
        selectorDummy = mock(PromotionPieceSelector.class);
        factory = new SingleRuleFactory(recorderDummy, selectorDummy);
    }

    @Nested
    @DisplayName("when creating a promotion rule")
    class PromotionRuleCreation {

        @Test
        @DisplayName("should build a new promotion rule")
        void shouldBuildPromotionRule() {
            PromotionRule rule = factory.promotionRule();

            assertNotNull(rule);
        }

        @Test
        @DisplayName("should create independent promotion rules each time")
        void shouldCreateIndependentPromotionRules() {
            PromotionRule first = factory.promotionRule();
            PromotionRule second = factory.promotionRule();

            assertNotSame(first, second);
        }
    }

    @Nested
    @DisplayName("when creating an en passant rule")
    class EnPassantRuleCreation {

        @Test
        @DisplayName("should build a new en passant rule")
        void shouldBuildEnPassantRule() {
            EnPassantRule rule = factory.enPassantRule();

            assertNotNull(rule);
        }

        @Test
        @DisplayName("should create independent en passant rules each time")
        void shouldCreateIndependentEnPassantRules() {
            EnPassantRule first = factory.enPassantRule();
            EnPassantRule second = factory.enPassantRule();

            assertNotSame(first, second);
        }
    }

    @Nested
    @DisplayName("when creating a standard rule")
    class StandardRuleCreation {

        @Test
        @DisplayName("should build a new standard rule")
        void shouldBuildStandardRule() {
            StandardMoveRule rule = factory.standardRule();

            assertNotNull(rule);
        }

        @Test
        @DisplayName("should create independent standard rules each time")
        void shouldCreateIndependentStandardRules() {
            StandardMoveRule first = factory.standardRule();
            StandardMoveRule second = factory.standardRule();

            assertNotSame(first, second);
        }
    }

    @Nested
    @DisplayName("when creating a castling rule")
    class CastlingRuleCreation {

        private PathSafetySimulator simulatorDummy;
        private AttackDetector detectorDummy;

        @BeforeEach
        void setUp() {
            simulatorDummy = mock(PathSafetySimulator.class);
            detectorDummy = mock(AttackDetector.class);
        }

        @Test
        @DisplayName("should build a new castling rule")
        void shouldBuildCastlingRule() {
            CastlingRule rule = factory.castlingRule(simulatorDummy, detectorDummy);

            assertNotNull(rule);
        }

        @Test
        @DisplayName("should create independent castling rules each time")
        void shouldCreateIndependentCastlingRules() {
            CastlingRule first = factory.castlingRule(simulatorDummy, detectorDummy);
            CastlingRule second = factory.castlingRule(simulatorDummy, detectorDummy);

            assertNotSame(first, second);
        }
    }
}