package unittest.chessgame.gamelogic.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirectionGroup;

@DisplayName("ChessboardDirectionGroup")
class ChessboardDirectionGroupTest {

    @Test
    @DisplayName("should provide all four orthogonal directions")
    void shouldProvideAllOrthogonalDirections() {
        Set<ChessboardDirection> directions = ChessboardDirectionGroup.ORTHOGONAL.getDirections();
        assertEquals(4, directions.size());
        assertTrue(directions.containsAll(Set.of(
            ChessboardDirection.UP,
            ChessboardDirection.DOWN,
            ChessboardDirection.LEFT,
            ChessboardDirection.RIGHT
        )));
    }

    @Test
    @DisplayName("should provide all four diagonal directions")
    void shouldProvideAllDiagonalDirections() {
        Set<ChessboardDirection> directions = ChessboardDirectionGroup.DIAGONAL.getDirections();
        assertEquals(4, directions.size());
        assertTrue(directions.containsAll(Set.of(
            ChessboardDirection.UP_LEFT,
            ChessboardDirection.UP_RIGHT,
            ChessboardDirection.DOWN_LEFT,
            ChessboardDirection.DOWN_RIGHT
        )));
    }

    @Test
    @DisplayName("should provide all eight directions (orthogonal and diagonal)")
    void shouldProvideAllDirections() {
        Set<ChessboardDirection> directions = ChessboardDirectionGroup.ORTHOGONAL_AND_DIAGONAL.getDirections();
        assertEquals(8, directions.size());
        assertTrue(directions.containsAll(EnumSet.allOf(ChessboardDirection.class)));
    }
}