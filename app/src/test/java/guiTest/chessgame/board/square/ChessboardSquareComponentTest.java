package guitest.chessgame.board.square;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.awt.Color;
import java.util.Optional;

import javax.swing.JComponent;
import javax.swing.JLabel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gui.board.square.ChessboardSquareComponent;
import com.sdm.units.chessgame.gui.board.square.HighlightColor;
import com.sdm.units.chessgame.gui.board.square.HighlightRenderer;
import com.sdm.units.chessgame.gui.board.square.SquareClickHandler;
import com.sdm.units.chessgame.gui.board.square.SquareColor;
import com.sdm.units.chessgame.gui.controller.interaction.SquareInteractionManager;

import guitest.chessgame.testdoubles.ChessPieceViewFactoryStub;
import guitest.chessgame.testdoubles.HighlightRendererSpy;
import unittest.chessgame.gamelogic.testdoubles.PieceDummy;

@DisplayName("ChessboardSquareComponent")
class ChessboardSquareComponentTest {

    private ChessboardPosition position;
    private ChessPieceViewFactoryStub factoryStub;
    private JComponent fakePieceComponent;
    private ChessboardSquareComponent square;

    @BeforeEach
    void setUp() {
        position = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
        fakePieceComponent = new JLabel("piece");
        factoryStub = new ChessPieceViewFactoryStub(fakePieceComponent);
        square = new ChessboardSquareComponent(position, factoryStub, Mockito.mock(HighlightRenderer.class));
    }

    @Nested
    @DisplayName("when rendering pieces")
    class PieceRendering {

        @Test
        @DisplayName("should display a piece component created by the factory")
        void shouldDisplayPieceComponentCreatedByFactory() {
            ChessPiece piece = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.PAWN);

            square.setPiece(Optional.of(piece));

            assertEquals(fakePieceComponent, square.getPiece());
        }

        @Test
        @DisplayName("should place the piece component inside the square")
        void shouldPlacePieceComponentInsideSquare() {
            ChessPiece piece = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.PAWN);

            square.setPiece(Optional.of(piece));

            assertEquals(fakePieceComponent, square.getComponent(0));
        }

        @Test
        @DisplayName("should remove the piece when no piece is given")
        void shouldRemovePieceWhenNoPieceIsGiven() {
            square.setPiece(Optional.empty());

            assertNull(square.getPiece());
        }

        @Test
        @DisplayName("should leave the square empty when no piece is given")
        void shouldLeaveSquareEmptyWhenNoPieceIsGiven() {
            square.setPiece(Optional.empty());

            assertEquals(0, square.getComponentCount());
        }
    }

    @Nested
    @DisplayName("when highlighting the square")
    class Highlighting {

        private HighlightRendererSpy highlightSpy;
        private ChessboardSquareComponent squareWithSpy;

        @BeforeEach
        void setUp() {
            highlightSpy = new HighlightRendererSpy();
            squareWithSpy = new ChessboardSquareComponent(position, factoryStub, highlightSpy);
        }

        @Test
        @DisplayName("should apply a highlight with the given type")
        void shouldApplyHighlightWithGivenType() {
            squareWithSpy.highlight(HighlightColor.SELECTABLE);

            assertEquals(HighlightColor.SELECTABLE, highlightSpy.getLastApplied());
        }

        @Test
        @DisplayName("should override an existing highlight with a new one")
        void shouldOverrideExistingHighlight() {
            squareWithSpy.highlight(HighlightColor.SELECTABLE);
            squareWithSpy.highlight(HighlightColor.HOVER);

            assertEquals(HighlightColor.HOVER, highlightSpy.getLastApplied());
        }

        @Test
        @DisplayName("should clear the highlight when requested")
        void shouldClearHighlightWhenRequested() {
            squareWithSpy.highlight(HighlightColor.SELECTABLE);
            squareWithSpy.clearHighlight();

            assertEquals(HighlightColor.NONE, highlightSpy.getLastApplied());
        }
    }

    @Nested
    @DisplayName("when attaching a click handler")
    class ClickHandler {

        private SquareClickHandler handler;
        private SquareInteractionManager manager;

        @BeforeEach
        void setUp() {
            handler = Mockito.mock(SquareClickHandler.class);
            manager = Mockito.mock(SquareInteractionManager.class);

            square.setClickHandler(handler, manager);
        }

        @Test
        @DisplayName("should store the attached click handler")
        void shouldStoreAttachedClickHandler() {
            assertNotNull(square.getClickListener());
        }

        @Test
        @DisplayName("should register the listener")
        void shouldRegisterListener() {
            assertEquals(1, square.getMouseListeners().length);
        }

        @Test
        @DisplayName("should remove the click handler when requested")
        void shouldRemoveClickHandlerWhenRequested() {
            square.removeClickHandler();

            assertNull(square.getClickListener());
        }

        @Test
        @DisplayName("should remove the listener when click handler is removed")
        void shouldRemoveListenerWhenClickHandlerIsRemoved() {
            square.removeClickHandler();

            assertEquals(0, square.getMouseListeners().length);
        }
    }

    @Nested
    @DisplayName("when setting background color")
    class BackgroundColor {

        @Test
        @DisplayName("should use the color defined by its position")
        void shouldUseColorDefinedByPosition() {
            Color expected = SquareColor.fromPosition(position).getColor();

            assertEquals(expected, square.getBackground());
        }
    }
}