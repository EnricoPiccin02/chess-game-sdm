package unitTest.chessgame.gamelogic.board.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sdm.units.chessgame.gamelogic.board.state.MoveHistory;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("MoveHistory")
class MoveHistoryTest {

    private MoveHistory history;

    @BeforeEach
    void setUp() {
        history = new MoveHistory();
    }

    @Nested
    @DisplayName("when no moves recorded")
    class NoMoves {

        @Test
        @DisplayName("should return empty for last move")
        void shouldReturnEmptyForLastMoveWhenHistoryIsEmpty() {
            Optional<ReversibleMove> result = history.getLastMove();
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("should return empty for pop move")
        void shouldReturnEmptyForPopMoveWhenHistoryIsEmpty() {
            Optional<ReversibleMove> result = history.popMove();
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("should return empty for clear move history")
        void shouldReturnEmptyForClearMoveHistory() {
            history.clear();
            Optional<ReversibleMove> result = history.getLastMove();
            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("when one move recorded")
    class OneMove {

        private ReversibleMove dummyMove;

        @BeforeEach
        void recordOneMove() {
            dummyMove = Mockito.mock(ReversibleMove.class);
            history.pushMove(dummyMove);
        }

        @Test
        @DisplayName("should return that move as last move")
        void shouldReturnLastMoveWhenOneMoveRecorded() {
            Optional<ReversibleMove> result = history.getLastMove();
            assertThat(result).contains(dummyMove);
        }

        @Test
        @DisplayName("should pop that move and then be empty")
        void shouldPopMoveAndThenBeEmpty() {
            Optional<ReversibleMove> popped = history.popMove();
            assertThat(popped).contains(dummyMove);

            Optional<ReversibleMove> after = history.getLastMove();
            assertThat(after).isEmpty();
        }

        @Test
        @DisplayName("should return empty for clear move history")
        void shouldReturnEmptyForClearMoveHistory() {
            history.clear();
            Optional<ReversibleMove> result = history.getLastMove();
            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("when multiple moves recorded")
    class MultipleMoves {

        private ReversibleMove dummyMove1;
        private ReversibleMove dummyMove2;
        private ReversibleMove dummyMove3;

        @BeforeEach
        void recordMultipleMoves() {
            dummyMove1 = Mockito.mock(ReversibleMove.class);
            dummyMove2 = Mockito.mock(ReversibleMove.class);
            dummyMove3 = Mockito.mock(ReversibleMove.class);
            history.pushMove(dummyMove1);
            history.pushMove(dummyMove2);
            history.pushMove(dummyMove3);
        }

        @Test
        @DisplayName("should return last pushed move as last move")
        void shouldReturnLastPushedMove() {
            Optional<ReversibleMove> result = history.getLastMove();
            assertThat(result).contains(dummyMove3);
        }

        @Test
        @DisplayName("should pop moves in reverse push order (LIFO)")
        void shouldPopMovesInLifoOrder() {
            assertThat(history.popMove()).contains(dummyMove3);
            assertThat(history.popMove()).contains(dummyMove2);
            assertThat(history.popMove()).contains(dummyMove1);
            assertThat(history.popMove()).isEmpty();
        }

        @Test
        @DisplayName("should return empty for clear move history")
        void shouldReturnEmptyForClearMoveHistory() {
            history.clear();
            Optional<ReversibleMove> result = history.getLastMove();
            assertThat(result).isEmpty();
        }
    }
}