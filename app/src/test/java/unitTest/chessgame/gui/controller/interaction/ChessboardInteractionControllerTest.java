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
    @DisplayName("when enabling selectable pieces")
    class EnableSelectablePieces {

        private final Set<ChessboardPosition> positions = Set.of(
            new ChessboardPosition(ChessboardFile.E, ChessboardRank.TWO),
            new ChessboardPosition(ChessboardFile.D, ChessboardRank.FOUR)
        );

        @Test
        @DisplayName("should highlight given positions")
        void shouldHighlightGivenPositions() {
            controller.enableSelectablePieces(positions);

            assertThat(viewSpy.getUpdatedSquares()).containsExactlyInAnyOrderElementsOf(positions);
        }

        @Test
        @DisplayName("should mark highlights as selected")
        void shouldMarkHighlightsAsSelected() {
            controller.enableSelectablePieces(positions);

            assertThat(viewSpy.getLastHighlightType()).isEqualTo(HighlightColor.SELECTED);
        }

        @Test
        @DisplayName("should attach listener to positions")
        void shouldAttachListenerToPositions() {
            controller.enableSelectablePieces(positions);

            assertThat(viewSpy.isListenerAttached()).isTrue();
        }
    }

    @Nested
    @DisplayName("when enabling legal destinations")
    class EnableLegalDestinations {

        private final Set<ChessboardPosition> positions = Set.of(
            new ChessboardPosition(ChessboardFile.A, ChessboardRank.THREE),
            new ChessboardPosition(ChessboardFile.B, ChessboardRank.FOUR)
        );

        @Test
        @DisplayName("should highlight given destinations")
        void shouldHighlightGivenDestinations() {
            controller.enableLegalDestinations(positions);

            assertThat(viewSpy.getUpdatedSquares()).containsExactlyInAnyOrderElementsOf(positions);
        }

        @Test
        @DisplayName("should mark highlights as legal destinations")
        void shouldMarkHighlightsAsLegalDestinations() {
            controller.enableLegalDestinations(positions);

            assertThat(viewSpy.getLastHighlightType()).isEqualTo(HighlightColor.LEGAL_DESTINATION);
        }

        @Test
        @DisplayName("should attach listener to destinations")
        void shouldAttachListenerToDestinations() {
            controller.enableLegalDestinations(positions);

            assertThat(viewSpy.isListenerAttached()).isTrue();
        }
    }

    @Nested
    @DisplayName("when clearing interaction")
    class ClearInteraction {

        @BeforeEach
        void prepareBoard() {
            controller.enableSelectablePieces(Set.of(
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