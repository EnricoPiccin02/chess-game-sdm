package unittest.chessgame.gui.controller.interaction;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gui.board.square.HighlightColor;
import com.sdm.units.chessgame.gui.board.square.SquareClickHandler;
import com.sdm.units.chessgame.gui.controller.interaction.DefaultSquareInteractionManager;

import unittest.chessgame.gui.testdoubles.ChessboardSquareHandlerSpy;

@DisplayName("DefaultSquareInteractionManager")
class DefaultSquareInteractionManagerTest {

    private DefaultSquareInteractionManager manager;
    private ChessboardSquareHandlerSpy squareSpy;
    private SquareClickHandler dummyHandler;

    @BeforeEach
    void setUp() {
        manager = new DefaultSquareInteractionManager();
        squareSpy = new ChessboardSquareHandlerSpy();
        dummyHandler = pos -> {};
    }

    @Nested
    @DisplayName("when setting a square to none")
    class NoneState {

        @BeforeEach
        void act() {
            manager.setNone(squareSpy);
        }

        @Test
        @DisplayName("should clear highlight from square")
        void shouldClearHighlightFromSquare() {
            assertThat(squareSpy.isCleared()).isTrue();
        }

        @Test
        @DisplayName("should remove click handler from square")
        void shouldRemoveClickHandlerFromSquare() {
            assertThat(squareSpy.isClickHandlerRemoved()).isTrue();
        }
    }

    @Nested
    @DisplayName("when setting a square to selectable")
    class SelectableState {

        @BeforeEach
        void act() {
            manager.setSelectable(squareSpy, dummyHandler);
        }

        @Test
        @DisplayName("should mark square as selectable")
        void shouldMarkSquareAsSelectable() {
            assertThat(squareSpy.getAppliedHighlight()).isEqualTo(HighlightColor.SELECTABLE);
        }

        @Test
        @DisplayName("should attach click handler to square")
        void shouldAttachClickHandlerToSquare() {
            assertThat(squareSpy.isClickHandlerAttached()).isTrue();
        }
    }

    @Nested
    @DisplayName("when setting a square to hover")
    class HoverState {

        @BeforeEach
        void act() {
            manager.setHover(squareSpy);
        }

        @Test
        @DisplayName("should mark square as hover")
        void shouldMarkSquareAsHover() {
            assertThat(squareSpy.getAppliedHighlight()).isEqualTo(HighlightColor.HOVER);
        }
    }

    @Nested
    @DisplayName("when setting a square to clicked")
    class ClickedState {

        @BeforeEach
        void act() {
            manager.setClicked(squareSpy);
        }

        @Test
        @DisplayName("should mark square as clicked")
        void shouldMarkSquareAsClicked() {
            assertThat(squareSpy.getAppliedHighlight()).isEqualTo(HighlightColor.CLICKED);
        }
    }
}