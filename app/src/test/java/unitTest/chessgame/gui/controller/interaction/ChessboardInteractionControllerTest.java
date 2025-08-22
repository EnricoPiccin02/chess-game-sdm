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

import guitest.chessgame.testdoubles.ChessboardViewSpy;

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

        @Test
        @DisplayName("should highlight and attach listener on given positions")
        void shouldHighlightAndAttachListener() {
            Set<ChessboardPosition> positions = Set.of(
                new ChessboardPosition(ChessboardFile.E, ChessboardRank.TWO),
                new ChessboardPosition(ChessboardFile.D, ChessboardRank.FOUR)
            );

            controller.enableSelectablePieces(positions);

            assertThat(viewSpy.getUpdatedSquares()).containsExactlyInAnyOrderElementsOf(positions);
            assertThat(viewSpy.getLastHighlightType()).isEqualTo(HighlightColor.SELECTED);
            assertThat(viewSpy.isListenerAttached()).isTrue();
        }
    }

    @Nested
    @DisplayName("when enabling legal destinations")
    class EnableLegalDestinations {

        @Test
        @DisplayName("should highlight and attach listener on given destinations")
        void shouldHighlightAndAttachListener() {
            Set<ChessboardPosition> positions = Set.of(
                new ChessboardPosition(ChessboardFile.A, ChessboardRank.THREE),
                new ChessboardPosition(ChessboardFile.B, ChessboardRank.FOUR)
            );

            controller.enableLegalDestinations(positions);

            assertThat(viewSpy.getUpdatedSquares()).containsExactlyInAnyOrderElementsOf(positions);
            assertThat(viewSpy.getLastHighlightType()).isEqualTo(HighlightColor.LEGAL_DESTINATION);
            assertThat(viewSpy.isListenerAttached()).isTrue();
        }
    }

    @Nested
    @DisplayName("when clearing interaction")
    class Clear {

        @Test
        @DisplayName("should clear highlight and remove listeners on all squares")
        void shouldClearAllSquares() {
            controller.enableSelectablePieces(Set.of(
                new ChessboardPosition(ChessboardFile.C, ChessboardRank.FIVE)
            ));
            controller.clear();

            assertThat(viewSpy.isUpdateAllCalled()).isTrue();
            assertThat(viewSpy.isListenerRemoved()).isTrue();
        }
    }
}