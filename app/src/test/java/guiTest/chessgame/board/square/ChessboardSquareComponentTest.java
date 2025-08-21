package guiTest.chessgame.board.square;

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

import guiTest.chessgame.testdoubles.ChessPieceViewRegistryStub;
import guiTest.chessgame.testdoubles.HighlightRendererSpy;
import unitTest.chessgame.gamelogic.testdoubles.PieceDummy;

@DisplayName("ChessboardSquareComponent")
class ChessboardSquareComponentTest {

    private ChessboardPosition position;
    private ChessPieceViewRegistryStub registryStub;
    private JComponent fakePieceComponent;
    private ChessboardSquareComponent square;

    @BeforeEach
    void setUp() {
        position = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
        fakePieceComponent = new JLabel("piece");
        registryStub = new ChessPieceViewRegistryStub(fakePieceComponent);
        square = new ChessboardSquareComponent(position, registryStub, Mockito.mock(HighlightRenderer.class));
    }

    @Nested
    @DisplayName("Piece rendering")
    class PieceRendering {

        @Test
        @DisplayName("should create piece component using registry and add it to square")
        void shouldCreatePieceComponentUsingRegistryAndAddItToSquare() {
            ChessPiece piece = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.PAWN);

            square.setPiece(Optional.of(piece));

            assertEquals(fakePieceComponent, square.getPiece());
            assertEquals(fakePieceComponent, square.getComponent(0));
        }

        @Test
        @DisplayName("should remove piece when empty one is provided")
        void shouldRemovePieceWhenEmptyGiven() {
            square.setPiece(Optional.empty());

            assertEquals(0, square.getComponentCount());
            assertNull(square.getPiece());
        }
    }

    @Nested
    @DisplayName("Highlighting")
    class Highlighting {

        private HighlightRendererSpy highlightSpy;
        private ChessboardSquareComponent squareWithSpy;

        @BeforeEach
        void setUp() {
            highlightSpy = new HighlightRendererSpy();
            squareWithSpy = new ChessboardSquareComponent(position, registryStub, highlightSpy);
        }

        @Test
        @DisplayName("should apply highlight with given type")
        void shouldApplyHighlightWithGivenType() {
            squareWithSpy.highlight(HighlightColor.SELECTED);
            assertEquals(Optional.of(HighlightColor.SELECTED), highlightSpy.getLastApplied());
        }

        @Test
        @DisplayName("should override existing highlight")
        void shouldOverrideExistingHighlight() {
            squareWithSpy.highlight(HighlightColor.SELECTED);
            squareWithSpy.highlight(HighlightColor.HOVER);
            
            assertEquals(Optional.of(HighlightColor.HOVER), highlightSpy.getLastApplied());
        }

        @Test
        @DisplayName("should clear highlight and apply empty state")
        void shouldClearHighlightAndApplyEmptyState() {
            squareWithSpy.highlight(HighlightColor.SELECTED);
            squareWithSpy.clearHighlight();

            assertEquals(Optional.empty(), highlightSpy.getLastApplied());
        }
    }

    @Nested
    @DisplayName("Click Handler")
    class ClickHandler {

        private SquareClickHandler handler;

        @BeforeEach
        void setUp() {
            handler = Mockito.mock(SquareClickHandler.class);
            square.attachClickHandler(handler);
        }

        @Test
        @DisplayName("should attach click handler and set listener")
        void shouldAttachClickHandlerAndSetListener() {
            assertNotNull(square.getClickListener());
            assertEquals(1, square.getMouseListeners().length);
        }

        @Test
        @DisplayName("should remove click handler and listener")
        void shouldRemoveClickHandlerAndListener() {
            square.removeClickHandler();
            assertNull(square.getClickListener());
            assertEquals(0, square.getMouseListeners().length);
        }
    }

    @Test
    @DisplayName("should set background color based on square position")
    void shouldSetBackgroundColorBasedOnSquarePosition() {
        Color expected = SquareColor.fromPosition(position).getColor();
        assertEquals(expected, square.getBackground());
    }
}