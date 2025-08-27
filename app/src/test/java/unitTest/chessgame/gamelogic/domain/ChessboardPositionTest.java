package unittest.chessgame.gamelogic.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;

@DisplayName("ChessboardPosition")
class ChessboardPositionTest {

    private ChessboardPosition d4;
    private ChessboardPosition e5;
    private ChessboardPosition h8;

    @BeforeEach
    void setUp() {
        d4 = new ChessboardPosition(ChessboardFile.D, ChessboardRank.FOUR);
        e5 = new ChessboardPosition(ChessboardFile.E, ChessboardRank.FIVE);
        h8 = new ChessboardPosition(ChessboardFile.H, ChessboardRank.EIGHT);
    }

    @Nested
    @DisplayName("navigating the board in one direction")
    class SingleDirectionNavigation {

        @Test
        @DisplayName("should move a piece upwards from d4 to d5")
        void shouldMovePieceUpwards() {
            Optional<ChessboardPosition> result = d4.nextPosition(ChessboardDirection.UP);
            assertEquals(new ChessboardPosition(ChessboardFile.D, ChessboardRank.FIVE), result.orElseThrow());
        }

        @Test
        @DisplayName("should move a piece diagonally up-right from d4 to e5")
        void shouldMovePieceDiagonallyUpRight() {
            Optional<ChessboardPosition> result = d4.nextPosition(ChessboardDirection.UP_RIGHT);
            assertEquals(e5, result.orElseThrow());
        }

        @Test
        @DisplayName("should stop movement at the edge of the board when moving beyond h8")
        void shouldStopAtBoardEdge() {
            Optional<ChessboardPosition> result = h8.nextPosition(ChessboardDirection.UP_RIGHT);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("should ignore movement when position has no valid coordinates")
        void shouldIgnoreInvalidPosition() {
            ChessboardPosition invalid = new ChessboardPosition(null, ChessboardRank.FOUR);
            Optional<ChessboardPosition> result = invalid.nextPosition(ChessboardDirection.UP);
            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("navigating the board through multiple directions")
    class MultipleDirectionNavigation {

        @Test
        @DisplayName("should follow a sequence of moves from d4 to e5 via up then right")
        void shouldFollowMoveSequence() {
            Optional<ChessboardPosition> result = d4.nextPosition(List.of(
                ChessboardDirection.UP, ChessboardDirection.RIGHT));
            assertEquals(e5, result.orElseThrow());
        }

        @Test
        @DisplayName("should halt navigation when a move goes beyond the board")
        void shouldHaltWhenStepExceedsBoard() {
            Optional<ChessboardPosition> result = h8.nextPosition(List.of(
                ChessboardDirection.UP, ChessboardDirection.RIGHT));
            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("measuring the distance between positions")
    class DistanceMeasurement {

        @Test
        @DisplayName("should compute Manhattan distance of 2 between d4 and e5")
        void shouldComputeManhattanDistance() {
            OptionalInt distance = d4.distance(e5);
            assertEquals(2, distance.orElseThrow());
        }

        @Test
        @DisplayName("should produce no distance when comparing with a missing position")
        void shouldProduceNoDistanceWithMissingPosition() {
            assertTrue(d4.distance(null).isEmpty());
        }

        @Test
        @DisplayName("should produce no distance when one position lacks coordinates")
        void shouldProduceNoDistanceWithIncompletePosition() {
            ChessboardPosition incomplete = new ChessboardPosition(null, ChessboardRank.FOUR);
            assertTrue(incomplete.distance(d4).isEmpty());
        }
    }

    @Nested
    @DisplayName("comparing positions")
    class PositionComparison {

        @Test
        @DisplayName("should treat two identical positions as equal")
        void shouldTreatIdenticalPositionsAsEqual() {
            assertEquals(0, d4.compareTo(d4));
        }

        @Test
        @DisplayName("should order d4 before e5")
        void shouldOrderEarlierPositionBeforeLater() {
            assertTrue(d4.compareTo(e5) > 0);
        }

        @Test
        @DisplayName("should order e5 after d4")
        void shouldOrderLaterPositionAfterEarlier() {
            assertTrue(e5.compareTo(d4) < 0);
        }

        @Test
        @DisplayName("should treat comparison with an incomplete or missing position as equal")
        void shouldTreatMissingOrIncompleteAsEqual() {
            ChessboardPosition incomplete = new ChessboardPosition(null, ChessboardRank.ONE);
            assertEquals(0, incomplete.compareTo(d4));
            assertEquals(0, d4.compareTo(null));
        }
    }
}