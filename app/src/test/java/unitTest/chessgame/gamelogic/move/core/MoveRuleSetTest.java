package unittest.chessgame.gamelogic.move.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.move.core.ComposedMoveRule;
import com.sdm.units.chessgame.gamelogic.move.core.CompositeMoveGenerator;
import com.sdm.units.chessgame.gamelogic.move.core.CompositeMoveValidator;
import com.sdm.units.chessgame.gamelogic.move.core.MoveGenerator;
import com.sdm.units.chessgame.gamelogic.move.core.MoveRuleSet;
import com.sdm.units.chessgame.gamelogic.move.core.MoveValidator;

@DisplayName("MoveRuleSet")
class MoveRuleSetTest {

    private ComposedMoveRule rule1;
    private ComposedMoveRule rule2;
    private MoveRuleSet ruleSet;

    @BeforeEach
    void setUp() {
        rule1 = mock(ComposedMoveRule.class);
        rule2 = mock(ComposedMoveRule.class);
        ruleSet = new MoveRuleSet(List.of(rule1, rule2));
    }

    @Test
    @DisplayName("should expose the same rules it was created with")
    void shouldExposeSameRulesItWasCreatedWith() {
        assertThat(ruleSet.rules()).containsExactly(rule1, rule2);
    }

    @Test
    @DisplayName("should create a validator that contains the same rules")
    void shouldCreateValidatorWithSameRules() {
        MoveValidator validator = ruleSet.asValidator();

        assertThat(((CompositeMoveValidator) validator).rules())
            .containsExactly(rule1, rule2);
    }

    @Test
    @DisplayName("should create a generator that contains the same rules")
    void shouldCreateGeneratorWithSameRules() {
        MoveGenerator generator = ruleSet.asGenerator();

        assertThat(((CompositeMoveGenerator) generator).rules())
            .containsExactly(rule1, rule2);
    }
}