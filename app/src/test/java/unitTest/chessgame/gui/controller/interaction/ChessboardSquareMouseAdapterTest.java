package unittest.chessgame.gui.controller.interaction;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sdm.units.chessgame.gui.board.square.SquareClickHandler;
import com.sdm.units.chessgame.gui.controller.interaction.ChessboardSquareMouseAdapter;

import unittest.chessgame.gui.testdoubles.ChessboardSquareHandlerSpy;
import unittest.chessgame.gui.testdoubles.SquareInteractionManagerSpy;

@DisplayName("ChessboardSquareMouseAdapter")
class ChessboardSquareMouseAdapterTest {

    private ChessboardSquareHandlerSpy squareSpy;
    private SquareInteractionManagerSpy interactionSpy;
    private SquareClickHandler clickHandler;
    private ChessboardSquareMouseAdapter adapter;

    @BeforeEach
    void setUp() {
        squareSpy = new ChessboardSquareHandlerSpy();
        interactionSpy = new SquareInteractionManagerSpy();
        clickHandler = Mockito.mock(SquareClickHandler.class);
        adapter = new ChessboardSquareMouseAdapter(squareSpy, clickHandler, interactionSpy);
    }

    @Test
    @DisplayName("should notify the click handler with the square position")
    void shouldNotifyClickHandlerWithSquarePosition() {
        adapter.mouseClicked(null);

        Mockito.verify(clickHandler).handleClick(squareSpy.getPosition());
    }

    @Test
    @DisplayName("should mark the square as hovered")
    void shouldMarkSquareAsHovered() {
        adapter.mouseEntered(null);

        assertThat(interactionSpy.isHoverCalled()).isTrue();
    }

    @Test
    @DisplayName("should mark the square as selectable")
    void shouldMarkSquareAsSelectable() {
        adapter.mouseExited(null);

        assertThat(interactionSpy.isSelectableCalled()).isTrue();
    }


    @Test
    @DisplayName("should mark the square as clicked")
    void shouldMarkSquareAsClicked() {
        adapter.mousePressed(null);

        assertThat(interactionSpy.isClickedCalled()).isTrue();
    }
}