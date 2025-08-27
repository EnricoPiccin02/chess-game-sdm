package unittest.chessgame.gamelogic.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.domain.CompositeMovementPattern;

@DisplayName("CompositeMovementPattern")
class CompositeMovementPatternTest {

    @Test
    @DisplayName("should provide all eight L-shaped directions")
    void shouldProvideAllEightLShapedDirections() {
        List<List<ChessboardDirection>> compositeDirections = CompositeMovementPattern.L_SHAPED.getCompositeDirections();
        assertEquals(8, compositeDirections.size());
    }

    @Test
    @DisplayName("each L-shaped direction should contain exactly 3 steps")
    void shouldContainThreeStepsEach() {
        List<List<ChessboardDirection>> compositeDirections = CompositeMovementPattern.L_SHAPED.getCompositeDirections();
        assertTrue(compositeDirections.stream().allMatch(list -> list.size() == 3));
    }

    @Test
    @DisplayName("should contain known L-shape direction")
    void shouldContainKnownLShape() {
        List<ChessboardDirection> upUpRight = List.of(
            ChessboardDirection.UP,
            ChessboardDirection.UP,
            ChessboardDirection.RIGHT
        );
        List<List<ChessboardDirection>> directions = CompositeMovementPattern.L_SHAPED.getCompositeDirections();
        assertTrue(directions.contains(upUpRight));
    }
}