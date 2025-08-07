package test.chessgame.gamelogic.domain;

import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirectionSet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ChessboardDirectionSet")
class ChessboardDirectionSetTest {

    @Nested
    @DisplayName("getDirections()")
    class GetDirections {

        @Test
        @DisplayName("should return four orthogonal directions")
        void shouldReturnOrthogonalDirections() {
            List<ChessboardDirection> directions = ChessboardDirectionSet.ORTHOGONAL.getDirections();
            assertEquals(4, directions.size());
            assertTrue(directions.containsAll(List.of(
                ChessboardDirection.UP,
                ChessboardDirection.DOWN,
                ChessboardDirection.LEFT,
                ChessboardDirection.RIGHT
            )));
        }

        @Test
        @DisplayName("should return four diagonal directions")
        void shouldReturnDiagonalDirections() {
            List<ChessboardDirection> directions = ChessboardDirectionSet.DIAGONAL.getDirections();
            assertEquals(4, directions.size());
            assertTrue(directions.containsAll(List.of(
                ChessboardDirection.UP_LEFT,
                ChessboardDirection.UP_RIGHT,
                ChessboardDirection.DOWN_LEFT,
                ChessboardDirection.DOWN_RIGHT
            )));
        }

        @Test
        @DisplayName("should return all 8 directions for ORTHOGONAL_AND_DIAGONAL")
        void shouldReturnAllDirections() {
            List<ChessboardDirection> directions = ChessboardDirectionSet.ORTHOGONAL_AND_DIAGONAL.getDirections();
            assertEquals(8, directions.size());
            assertTrue(directions.containsAll(EnumSet.allOf(ChessboardDirection.class)));
        }

        @Test
        @DisplayName("should return immutable list")
        void shouldReturnImmutableList() {
            List<ChessboardDirection> directions = ChessboardDirectionSet.ORTHOGONAL.getDirections();
            assertThrows(UnsupportedOperationException.class, () -> directions.add(ChessboardDirection.UP_LEFT));
        }
    }
}