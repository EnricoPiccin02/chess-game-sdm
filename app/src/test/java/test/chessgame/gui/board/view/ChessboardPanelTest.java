package test.chessgame.gui.board.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gui.board.square.HighlightRenderer;
import com.sdm.units.chessgame.gui.board.square.ChessboardSquareComponent;
import com.sdm.units.chessgame.gui.board.view.ChessboardPanel;
import com.sdm.units.chessgame.gui.pieces.ChessPieceViewRegistry;

import test.chessgame.gui.testdoubles.ChessboardSquareComponentSpy;

@DisplayName("ChessboardPanel")
class ChessboardPanelTest {

    private ChessPieceViewRegistry dummyRegistry;
    private HighlightRenderer dummyRenderer;
    private ChessboardPanel panel;

    @BeforeEach
    void setUp() {
        dummyRegistry = mock(ChessPieceViewRegistry.class);
        dummyRenderer = mock(HighlightRenderer.class);
        panel = new ChessboardPanel(dummyRegistry, dummyRenderer);
    }

    @Nested
    @DisplayName("when initializing squares")
    class InitializeSquares {

        @Test
        @DisplayName("should initialize 64 squares covering all positions")
        void shouldInitializeAllSquares() {
            assertEquals(64, panel.getAllSquares().size());

            for (ChessboardFile file : ChessboardFile.values()) {
                for (ChessboardRank rank : ChessboardRank.values()) {
                    ChessboardPosition pos = new ChessboardPosition(file, rank);
                    assertNotNull(panel.getSquareAt(pos));
                }
            }
        }
    }

    @Nested
    @DisplayName("when rendering a board state")
    class RenderBoardState {

        @Test
        @DisplayName("should call setPiece() on the square when rendering a board state")
        void shouldDelegatePieceToSquare() {
            ChessboardPosition a2 = new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO);
            ChessPiece pawn = mock(ChessPiece.class);

            Chessboard board = mock(Chessboard.class);
            when(board.getPieceAt(a2)).thenReturn(Optional.of(pawn));

            ChessboardSquareComponentSpy componentSpy = new ChessboardSquareComponentSpy(a2, null);
            panel.replaceSquare(a2, componentSpy);

            panel.renderChessboardState(board);

            assertEquals(1, componentSpy.timesCalledSetPiece());
            assertEquals(Optional.of(pawn), componentSpy.lastPiece());
        }
    }

    @Nested
    @DisplayName("when updating specific squares")
    class UpdateSquares {

        @Test
        @SuppressWarnings("unchecked")
        @DisplayName("should apply given operation only to selected squares")
        void shouldApplyOperationToSelectedSquares() {
            ChessboardPosition a1 = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
            ChessboardSquareComponent square = panel.getSquareAt(a1);

            Consumer<ChessboardSquareComponent> spyOperation = spy(Consumer.class);
            Set<ChessboardPosition> positions = Set.of(a1);

            panel.updateSquaresAt(positions, spyOperation);

            verify(spyOperation).accept(square);
        }
    }

    @Nested
    @DisplayName("when updating all squares")
    class UpdateAllSquares {

        @Test
        @SuppressWarnings("unchecked")
        @DisplayName("should apply given operation to every square")
        void shouldApplyOperationToAllSquares() {
            Consumer<ChessboardSquareComponent> spyOperation = spy(Consumer.class);

            panel.updateAllSquares(spyOperation);

            verify(spyOperation, times(64)).accept(any(ChessboardSquareComponent.class));
        }
    }
}