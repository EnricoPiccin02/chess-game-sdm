package test.chessgame.gamelogic.move.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.move.core.MoveRulePriority;

@DisplayName("MoveRulePriority")
class MoveRulePriorityTest {

    @Nested
    @DisplayName("getPriority")
    class GetPriority {

        @Test
        @DisplayName("should return 2 for LOW_PRIORITY")
        void shouldReturnTwoForLowPriority() {
            assertThat(MoveRulePriority.LOW_PRIORITY.getPriority()).isEqualTo(2);
        }

        @Test
        @DisplayName("should return 1 for MEDIUM_PRIORITY")
        void shouldReturnOneForMediumPriority() {
            assertThat(MoveRulePriority.MEDIUM_PRIORITY.getPriority()).isEqualTo(1);
        }

        @Test
        @DisplayName("should return 0 for HIGH_PRIORITY")
        void shouldReturnZeroForHighPriority() {
            assertThat(MoveRulePriority.HIGH_PRIORITY.getPriority()).isEqualTo(0);
        }
    }
}