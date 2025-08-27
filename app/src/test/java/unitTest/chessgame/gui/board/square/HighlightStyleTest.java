package unittest.chessgame.gui.board.square;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sdm.units.chessgame.gui.board.square.HighlightColor;
import com.sdm.units.chessgame.gui.board.square.HighlightStyle;
import com.sdm.units.chessgame.gui.board.square.SquareClickHandler;

import unittest.chessgame.gui.testdoubles.ChessboardSquareHandlerSpy;

@DisplayName("HighlightStyle")
class HighlightStyleTest {

    private ChessboardSquareHandlerSpy squareHandlerSpy;
    private SquareClickHandler dummyClickHandler;

    @BeforeEach
    void setUp() {
        squareHandlerSpy = new ChessboardSquareHandlerSpy();
        dummyClickHandler = Mockito.mock(SquareClickHandler.class);
    }

    @Test
    @DisplayName("should highlight square as selectable")
    void shouldHighlightSquareAsSelectable() {
        HighlightStyle.SELECTABLE.apply(squareHandlerSpy, dummyClickHandler);
        assertEquals(HighlightColor.SELECTABLE, squareHandlerSpy.getAppliedHighlight());
    }

    @Test
    @DisplayName("should attach click handler when selectable")
    void shouldAttachClickHandlerWhenSelectable() {
        HighlightStyle.SELECTABLE.apply(squareHandlerSpy, dummyClickHandler);
        assertTrue(squareHandlerSpy.isClickHandlerAttached());
    }

    @Test
    @DisplayName("should clear highlight when none")
    void shouldClearHighlightWhenNone() {
        HighlightStyle.NONE.apply(squareHandlerSpy, dummyClickHandler);
        assertTrue(squareHandlerSpy.isCleared());
    }

    @Test
    @DisplayName("should remove click handler when none")
    void shouldRemoveClickHandlerWhenNone() {
        HighlightStyle.NONE.apply(squareHandlerSpy, dummyClickHandler);
        assertTrue(squareHandlerSpy.isClickHandlerRemoved());
    }

    @Test
    @DisplayName("should highlight square on hover")
    void shouldHighlightSquareOnHover() {
        HighlightStyle.HOVER.apply(squareHandlerSpy, dummyClickHandler);
        assertEquals(HighlightColor.HOVER, squareHandlerSpy.getAppliedHighlight());
    }

    @Test
    @DisplayName("should highlight square on click")
    void shouldHighlightSquareOnClick() {
        HighlightStyle.CLICKED.apply(squareHandlerSpy, dummyClickHandler);
        assertEquals(HighlightColor.CLICKED, squareHandlerSpy.getAppliedHighlight());
    }
}