package guitest.chessgame.board.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gui.board.square.ChessboardSquareHandler;
import com.sdm.units.chessgame.gui.board.square.HighlightRenderer;
import com.sdm.units.chessgame.gui.board.view.ChessboardPanel;
import com.sdm.units.chessgame.gui.pieces.PieceViewFactory;

import guitest.chessgame.testdoubles.ChessboardSquareComponentSpy;
import unittest.chessgame.gamelogic.testdoubles.ChessboardFake;
import unittest.chessgame.gamelogic.testdoubles.PieceDummy;

@DisplayName("ChessboardPanel")
class ChessboardPanelTest {

    private PieceViewFactory dummyFactory;
    private HighlightRenderer dummyRenderer;
    private ChessboardPanel panel;

    @BeforeEach
    void setUp() {
        dummyFactory = mock(PieceViewFactory.class);
        dummyRenderer = mock(HighlightRenderer.class);
        panel = new ChessboardPanel(dummyFactory, dummyRenderer);
    }

    @Nested
    @DisplayName("when initializing squares")
    class InitializeSquares {

        @Test
        @DisplayName("should create a board containing exactly 64 squares")
        void shouldCreateBoardWith64Squares() {
            assertEquals(64, panel.getAllSquares().size());
        }

        @Test
        @DisplayName("should create a square for position A1")
        void shouldCreateSquareForA1() {
            assertNotNull(panel.getSquareAt(new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE)));
        }

        @Test
        @DisplayName("should create a square for position H8")
        void shouldCreateSquareForH8() {
            assertNotNull(panel.getSquareAt(new ChessboardPosition(ChessboardFile.H, ChessboardRank.EIGHT)));
        }
    }

    @Nested
    @DisplayName("when rendering a board state")
    class RenderBoardState {

        @Test
        @DisplayName("should update a square with the piece placed on that position")
        void shouldUpdateSquareWithPlacedPiece() {
            ChessboardPosition position = new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO);
            ChessPiece pawn = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.PAWN);

            Chessboard board = new ChessboardFake();
            board.putPieceAt(position, pawn);

            ChessboardSquareComponentSpy squareSpy = new ChessboardSquareComponentSpy();
            panel.replaceSquare(position, squareSpy);

            panel.renderChessboardState(board);

            assertEquals(Optional.of(pawn), squareSpy.lastPiece());
        }

        @Test
        @DisplayName("should reset the placed piece exactly once when updating a square")
        void shouldResetPlacedPieceOnceWhenUpdatingSquare() {
            ChessboardPosition position = new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO);
            ChessPiece pawn = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.PAWN);

            Chessboard board = new ChessboardFake();
            board.putPieceAt(position, pawn);

            ChessboardSquareComponentSpy squareSpy = new ChessboardSquareComponentSpy();
            panel.replaceSquare(position, squareSpy);

            panel.renderChessboardState(board);

            assertEquals(1, squareSpy.timesCalledSetPiece());
        }
    }

    @Nested
    @DisplayName("when updating specific squares")
    class UpdateSquares {

        @Test
        @SuppressWarnings("unchecked")
        @DisplayName("should apply the given operation to the selected square only")
        void shouldApplyOperationToSelectedSquare() {
            ChessboardPosition a1 = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
            ChessboardSquareHandler squareHandler = panel.getSquareAt(a1);

            Consumer<ChessboardSquareHandler> spyOperation = spy(Consumer.class);

            panel.updateSquaresAt(Set.of(a1), spyOperation);

            verify(spyOperation).accept(squareHandler);
        }
    }

    @Nested
    @DisplayName("when updating all squares")
    class UpdateAllSquares {

        @Test
        @SuppressWarnings("unchecked")
        @DisplayName("should apply the given operation to every square on the board")
        void shouldApplyOperationToEverySquare() {
            Consumer<ChessboardSquareHandler> spyOperation = spy(Consumer.class);

            panel.updateAllSquares(spyOperation);

            verify(spyOperation, times(64)).accept(any(ChessboardSquareHandler.class));
        }
    }
}