package unittest.chessgame.gui.controller.interaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gui.board.square.SquareClickHandler;
import com.sdm.units.chessgame.gui.controller.interaction.ChessboardInteractionController;

import unittest.chessgame.gui.testdoubles.ChessboardViewSpy;
import unittest.chessgame.gui.testdoubles.SquareInteractionManagerSpy;

@DisplayName("ChessboardInteractionController")
class ChessboardInteractionControllerTest {

    private ChessboardViewSpy boardViewSpy;
    private ChessboardInteractionController controller;
    private SquareClickHandler dummyHandler;
    private SquareInteractionManagerSpy interactionManagerSpy;

    @BeforeEach
    void setUp() {
        boardViewSpy = new ChessboardViewSpy();
        interactionManagerSpy = new SquareInteractionManagerSpy();
        dummyHandler = pos -> {};
        controller = new ChessboardInteractionController(boardViewSpy, interactionManagerSpy);
        controller.setClickHandler(dummyHandler);
    }

    @Nested
    @DisplayName("when enabling selectable squares")
    class EnableSelectableSquares {

        private final Set<ChessboardPosition> positions = Set.of(
            new ChessboardPosition(ChessboardFile.E, ChessboardRank.TWO),
            new ChessboardPosition(ChessboardFile.D, ChessboardRank.FOUR)
        );

        @Test
        @DisplayName("should highlight only the given squares")
        void shouldHighlightOnlyTheGivenSquares() {
            controller.enableSelectableSquares(positions);

            assertThat(boardViewSpy.getUpdatedSquares())
                .containsExactlyInAnyOrderElementsOf(positions);
        }

        @Test
        @DisplayName("should delegate interaction setup to manager")
        void shouldDelegateInteractionSetupToManager() {
            controller.enableSelectableSquares(positions);

            assertTrue(interactionManagerSpy.isSelectableCalled());
        }
    }

    @Nested
    @DisplayName("when clearing interaction")
    class ClearInteraction {

        @BeforeEach
        void prepareBoard() {
            controller.enableSelectableSquares(Set.of(
                new ChessboardPosition(ChessboardFile.C, ChessboardRank.FIVE)
            ));
        }

        @Test
        @DisplayName("should clear all square highlights")
        void shouldClearAllSquareHighlights() {
            controller.clear();

            assertThat(boardViewSpy.isUpdateAllCalled()).isTrue();
        }

        @Test
        @DisplayName("should delegate clearing to manager")
        void shouldDelegateClearingToManager() {
            controller.clear();

            assertTrue(interactionManagerSpy.isNoneCalled());
        }
    }
}