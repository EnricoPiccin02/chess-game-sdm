package unittest.chessgame.gui.controller.interaction;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gui.board.square.HighlightColor;
import com.sdm.units.chessgame.gui.board.square.SquareClickHandler;
import com.sdm.units.chessgame.gui.controller.interaction.ChessboardInteractionController;

import unittest.chessgame.gui.testdoubles.ChessboardViewSpy;

@DisplayName("ChessboardInteractionController")
class ChessboardInteractionControllerTest {

    private ChessboardViewSpy viewSpy;
    private ChessboardInteractionController controller;
    private SquareClickHandler dummyHandler;

    @BeforeEach
    void setUp() {
        viewSpy = new ChessboardViewSpy();
        dummyHandler = pos -> {};
        controller = new ChessboardInteractionController(viewSpy);
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
        @DisplayName("should highlight given squares")
        void shouldHighlightGivenSquares() {
            controller.enableSelectableSquares(positions);

            assertThat(viewSpy.getUpdatedSquares()).containsExactlyInAnyOrderElementsOf(positions);
        }

        @Test
        @DisplayName("should mark highlights as selected")
        void shouldMarkHighlightsAsSelected() {
            controller.enableSelectableSquares(positions);

            assertThat(viewSpy.getLastHighlightType()).isEqualTo(HighlightColor.SELECTABLE);
        }

        @Test
        @DisplayName("should attach listener to squares")
        void shouldAttachListenerToSquares() {
            controller.enableSelectableSquares(positions);

            assertThat(viewSpy.isListenerAttached()).isTrue();
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
        @DisplayName("should clear all highlights")
        void shouldClearAllHighlights() {
            controller.clear();

            assertThat(viewSpy.isUpdateAllCalled()).isTrue();
        }

        @Test
        @DisplayName("should remove all listeners")
        void shouldRemoveAllListeners() {
            controller.clear();

            assertThat(viewSpy.isListenerRemoved()).isTrue();
        }
    }
}