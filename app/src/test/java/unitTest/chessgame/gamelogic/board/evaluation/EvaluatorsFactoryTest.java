package unittest.chessgame.gamelogic.board.evaluation;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.board.evaluation.AttackDetectorService;
import com.sdm.units.chessgame.gamelogic.board.evaluation.CheckmateService;
import com.sdm.units.chessgame.gamelogic.board.evaluation.EvaluatorsFactory;
import com.sdm.units.chessgame.gamelogic.board.evaluation.GameOutcomeEvaluator;
import com.sdm.units.chessgame.gamelogic.board.evaluation.MoveSimulationService;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.move.core.MoveRuleSet;

@DisplayName("EvaluatorsFactory")
class EvaluatorsFactoryTest {

    private ChessboardOrientation orientationDummy;
    private EvaluatorsFactory factory;
    private MoveRuleSet ruleSetDummy1;
    private MoveRuleSet ruleSetDummy2;

    @BeforeEach
    void setUp() {
        orientationDummy = ChessboardOrientation.BLACK_BOTTOM;
        factory = new EvaluatorsFactory(orientationDummy);

        ruleSetDummy1 = new MoveRuleSet(List.of());
        ruleSetDummy2 = new MoveRuleSet(List.of());
    }

    @Nested
    @DisplayName("attack detector")
    class AttackDetector {

        @Test
        @DisplayName("should create the detector on first request")
        void shouldCreateDetectorOnFirstRequest() {
            AttackDetectorService detector = factory.attackDetector(ruleSetDummy1);

            assertNotNull(detector);
        }

        @Test
        @DisplayName("should initialize only once even if requested multiple times")
        void shouldInitializeOnlyOnceEvenIfRequestedMultipleTimes() {
            AttackDetectorService first = factory.attackDetector(ruleSetDummy1);
            AttackDetectorService second = factory.attackDetector(ruleSetDummy1);

            assertSame(first, second);
        }

        @Test
        @DisplayName("should create different detectors for different rule sets")
        void shouldReturnDifferentDetectorsForDifferentRuleSets() {
            AttackDetectorService first = factory.attackDetector(ruleSetDummy1);
            AttackDetectorService second = factory.attackDetector(ruleSetDummy2);

            assertNotSame(first, second);
        }
    }

    @Nested
    @DisplayName("move simulation service")
    class MoveSimulationServiceTests {

        @Test
        @DisplayName("should create the required service on first request")
        void shouldCreateServiceOnFirstRequest() {
            MoveSimulationService simulator = factory.moveSimulationService(ruleSetDummy1);

            assertNotNull(simulator);
        }

        @Test
        @DisplayName("should initialize only once even if requested multiple times")
        void shouldInitializeOnlyOnceEvenIfRequestedMultipleTimes() {
            MoveSimulationService first = factory.moveSimulationService(ruleSetDummy1);
            MoveSimulationService second = factory.moveSimulationService(ruleSetDummy1);

            assertSame(first, second);
        }

        @Test
        @DisplayName("should create different services for different rule sets")
        void shouldCreateDifferentServicesForDifferentRuleSets() {
            MoveSimulationService first = factory.moveSimulationService(ruleSetDummy1);
            MoveSimulationService second = factory.moveSimulationService(ruleSetDummy2);

            assertNotSame(first, second);
        }
    }

    @Nested
    @DisplayName("checkmate service")
    class CheckmateServiceTests {

        @Test
        @DisplayName("should create the required service on first request")
        void shouldCreateServiceOnFirstRequest() {
            CheckmateService service = factory.checkmateService(ruleSetDummy1);

            assertNotNull(service);
        }

        @Test
        @DisplayName("should initialize only once even if requested multiple times")
        void shouldInitializeOnlyOnceEvenIfRequestedMultipleTimes() {
            CheckmateService first = factory.checkmateService(ruleSetDummy1);
            CheckmateService second = factory.checkmateService(ruleSetDummy1);

            assertSame(first, second);
        }

        @Test
        @DisplayName("should create different services for different rule sets")
        void shouldCreateDifferentServicesForDifferentRuleSets() {
            CheckmateService first = factory.checkmateService(ruleSetDummy1);
            CheckmateService second = factory.checkmateService(ruleSetDummy2);

            assertNotSame(first, second);
        }
    }

    @Nested
    @DisplayName("game outcome evaluator")
    class GameOutcomeEvaluatorTests {

        @Test
        @DisplayName("should create a new game outcome evaluator each time")
        void shouldCreateNewGameOutcomeEvaluatorEachTime() {
            GameOutcomeEvaluator first = factory.gameOutcomeEvaluator(ruleSetDummy1);
            GameOutcomeEvaluator second = factory.gameOutcomeEvaluator(ruleSetDummy1);

            assertNotSame(first, second);
        }
    }
}