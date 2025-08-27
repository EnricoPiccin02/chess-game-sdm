package unittest.chessgame.gamelogic.board.state;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sdm.units.chessgame.gamelogic.board.state.MoveHistory;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;

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
        @DisplayName("should not get any move when asking for last move")
        void shouldNotGetAnyMoveForLastMove() {
            assertThat(history.getLastMove()).isEmpty();
        }

        @Test
        @DisplayName("should not extract any move")
        void shouldNotExtractAnyMove() {
            assertThat(history.popMove()).isEmpty();
        }

        @Test
        @DisplayName("should contain no moves after clearing")
        void shouldContainNoMovesAfterClearing() {
            history.clear();
            assertThat(history.getLastMove()).isEmpty();
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
        @DisplayName("should get that move as last move")
        void shouldGetLastMove() {
            assertThat(history.getLastMove()).contains(dummyMove);
        }

        @Test
        @DisplayName("should extract that move")
        void shouldExtractThatMove() {
            assertThat(history.popMove()).contains(dummyMove);
        }

        @Test
        @DisplayName("should be empty after extracting the move")
        void shouldBeEmptyAfterExtracting() {
            history.popMove();
            assertThat(history.getLastMove()).isEmpty();
        }

        @Test
        @DisplayName("should contain no moves after clearing")
        void shouldContainNoMovesAfterClearing() {
            history.clear();
            assertThat(history.getLastMove()).isEmpty();
        }
    }

    @Nested
    @DisplayName("when multiple moves recorded")
    class MultipleMoves {

        private ReversibleMove firstMove;
        private ReversibleMove secondMove;
        private ReversibleMove thirdMove;

        @BeforeEach
        void recordMultipleMoves() {
            firstMove = Mockito.mock(ReversibleMove.class);
            secondMove = Mockito.mock(ReversibleMove.class);
            thirdMove = Mockito.mock(ReversibleMove.class);
            history.pushMove(firstMove);
            history.pushMove(secondMove);
            history.pushMove(thirdMove);
        }

        @Test
        @DisplayName("should get last inserted move as last move")
        void shouldGetLastInsertedMove() {
            assertThat(history.getLastMove()).contains(thirdMove);
        }

        @Test
        @DisplayName("should extract last inserted move first")
        void shouldExtractLastInsertedMoveFirst() {
            assertThat(history.popMove()).contains(thirdMove);
        }

        @Test
        @DisplayName("should extract moves in reverse insertion order")
        void shouldExtractMovesInReverseInsertionOrder() {
            history.popMove();
            assertThat(history.popMove()).contains(secondMove);
            assertThat(history.popMove()).contains(firstMove);
        }

        @Test
        @DisplayName("should contain no moves after all moves popped")
        void shouldContainNoMovesAfterAllMovesPopped() {
            history.popMove();
            history.popMove();
            history.popMove();
            assertThat(history.getLastMove()).isEmpty();
        }

        @Test
        @DisplayName("should contain no moves after clearing")
        void shouldContainNoMovesAfterClearing() {
            history.clear();
            assertThat(history.getLastMove()).isEmpty();
        }
    }
}