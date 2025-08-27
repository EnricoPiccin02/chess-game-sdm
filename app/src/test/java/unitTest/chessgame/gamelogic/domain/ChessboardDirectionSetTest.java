package unittest.chessgame.gamelogic.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumSet;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirectionSet;

@DisplayName("ChessboardDirectionSet")
class ChessboardDirectionSetTest {

    @Nested
    @DisplayName("getDirections()")
    class GetDirections {

        @Test
        @DisplayName("should provide all four orthogonal directions")
        void shouldProvideAllOrthogonalDirections() {
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
        @DisplayName("should provide all four diagonal directions")
        void shouldProvideAllDiagonalDirections() {
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
        @DisplayName("should provide all eight directions (orthogonal and diagonal)")
        void shouldProvideAllDirections() {
            List<ChessboardDirection> directions = ChessboardDirectionSet.ORTHOGONAL_AND_DIAGONAL.getDirections();
            assertEquals(8, directions.size());
            assertTrue(directions.containsAll(EnumSet.allOf(ChessboardDirection.class)));
        }
    }
}