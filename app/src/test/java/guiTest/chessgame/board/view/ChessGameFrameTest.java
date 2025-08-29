package guitest.chessgame.board.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.clock.ChessClockView;
import com.sdm.units.chessgame.gui.board.view.ChessGameFrame;
import com.sdm.units.chessgame.gui.board.view.GameToolbarView;
import com.sdm.units.chessgame.gui.controller.events.ChessGameEvent;

import guitest.chessgame.testdoubles.ChessClockViewSpy;
import guitest.chessgame.testdoubles.ChessGameToolbarDummy;
import guitest.chessgame.testdoubles.ChessboardViewSpy;
import guitest.chessgame.testdoubles.MoveHistoryAreaFake;

@DisplayName("ChessGameFrame")
class ChessGameFrameTest {

    private ChessboardViewSpy boardSpy;
    private MoveHistoryAreaFake fakeTextArea;
    private EnumMap<ChessPieceColor, ChessClockView> fakeClocks;
    private GameToolbarView dummyToolBar;
    private ChessGameFrame frame;

    @BeforeEach
    void setUp() {
        boardSpy = new ChessboardViewSpy();
        fakeTextArea = new MoveHistoryAreaFake();

        fakeClocks = new EnumMap<>(ChessPieceColor.class);
        fakeClocks.put(ChessPieceColor.WHITE, new ChessClockViewSpy());
        fakeClocks.put(ChessPieceColor.BLACK, new ChessClockViewSpy());

        dummyToolBar = new ChessGameToolbarDummy();

        frame = new ChessGameFrame("TestChess", boardSpy, fakeClocks, fakeTextArea, dummyToolBar);
    }

    @Nested
    @DisplayName("when initializing the game frame")
    class InitializeFrame {

        @Test
        @DisplayName("should make the frame visible")
        void shouldMakeFrameVisible() {
            frame.initialize();
            assertTrue(frame.isVisible());
        }
    }

    @Nested
    @DisplayName("when rendering the chessboard")
    class RenderChessboard {

        @Test
        @DisplayName("should delegate rendering to the board view")
        void shouldDelegateRenderingToBoardView() {
            Chessboard board = mock(Chessboard.class);

            frame.setChessboard(board);

            assertTrue(boardSpy.isRenderCalled());
        }

        @Test
        @DisplayName("should provide the board to the board view")
        void shouldProvideBoardToBoardView() {
            Chessboard board = mock(Chessboard.class);

            frame.setChessboard(board);

            assertEquals(board, boardSpy.getLastBoard());
        }
    }

    @Nested
    @DisplayName("when recording moves")
    class RecordMoves {

        @Test
        @DisplayName("should add moves to the move history")
        void shouldAddMovesToHistory() {
            frame.recordMessage("e2 to e4");

            assertTrue(frame.getMoveHistoryText().contains("e2 to e4"));
        }
    }

    @Nested
    @DisplayName("when resetting the game")
    class ResetGame {

        @Test
        @DisplayName("should clear the move history")
        void shouldClearMoveHistory() {
            frame.recordMessage("some move");
            frame.reset();

            assertEquals("", frame.getMoveHistoryText());
        }

        @Test
        @DisplayName("should reset the white clock")
        void shouldResetWhiteClock() {
            frame.reset();

            assertTrue(((ChessClockViewSpy) fakeClocks.get(ChessPieceColor.WHITE)).isResetCalled());
        }

        @Test
        @DisplayName("should reset the black clock")
        void shouldResetBlackClock() {
            frame.reset();

            assertTrue(((ChessClockViewSpy) fakeClocks.get(ChessPieceColor.BLACK)).isResetCalled());
        }
    }

    @Nested
    @DisplayName("when controlling clocks")
    class ControlClocks {

        @Test
        @DisplayName("should start the white clock")
        void shouldStartWhiteClock() {
            frame.startClock(ChessPieceColor.WHITE);

            assertTrue(((ChessClockViewSpy) fakeClocks.get(ChessPieceColor.WHITE)).isStartCalled());
        }

        @Test
        @DisplayName("should stop the black clock")
        void shouldStopBlackClock() {
            frame.stopClock(ChessPieceColor.BLACK);

            assertTrue(((ChessClockViewSpy) fakeClocks.get(ChessPieceColor.BLACK)).isStopCalled());
        }
    }

    @Nested
    @DisplayName("when closing the game frame")
    class CloseFrame {

        @Test
        @DisplayName("should dispose the frame")
        void shouldDisposeFrame() {
            frame.initialize();
            frame.close();

            assertFalse(frame.isDisplayable());
        }
    }

    @Nested
    @DisplayName("when handling game events")
    class HandleEvents {

        @Test
        @DisplayName("should delegate handling to the event")
        void shouldDelegateHandlingToEvent() {
            ChessGameEvent event = mock(ChessGameEvent.class);

            frame.onChessGameEvent(event);

            verify(event).handleOn(frame);
        }
    }
}