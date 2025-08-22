package unittest.chessgame.gamelogic.domain;

import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.domain.CompositeDirectionSet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CompositeDirectionSet")
class CompositeDirectionSetTest {

    @Nested
    @DisplayName("getCompositeDirections()")
    class GetCompositeDirections {

        @Test
        @DisplayName("should return 8 L-shaped directions")
        void shouldReturnEightLShapedDirections() {
            List<List<ChessboardDirection>> compositeDirections = CompositeDirectionSet.L_SHAPED.getCompositeDirections();
            assertEquals(8, compositeDirections.size());
        }

        @Test
        @DisplayName("each L-shaped direction should contain exactly 3 ChessboardDirection elements")
        void shouldContainThreeStepsEach() {
            List<List<ChessboardDirection>> compositeDirections = CompositeDirectionSet.L_SHAPED.getCompositeDirections();
            assertTrue(compositeDirections.stream().allMatch(list -> list.size() == 3));
        }

        @Test
        @DisplayName("should contain known L-shape direction UP, UP, RIGHT")
        void shouldContainKnownLShape() {
            List<ChessboardDirection> upUpRight = List.of(
                ChessboardDirection.UP,
                ChessboardDirection.UP,
                ChessboardDirection.RIGHT
            );
            List<List<ChessboardDirection>> directions = CompositeDirectionSet.L_SHAPED.getCompositeDirections();
            assertTrue(directions.contains(upUpRight));
        }

        @Test
        @DisplayName("should return unmodifiable list")
        void shouldReturnUnmodifiableList() {
            List<List<ChessboardDirection>> directions = CompositeDirectionSet.L_SHAPED.getCompositeDirections();
            assertThrows(UnsupportedOperationException.class, () -> directions.add(List.of(ChessboardDirection.UP)));
        }
    }
}