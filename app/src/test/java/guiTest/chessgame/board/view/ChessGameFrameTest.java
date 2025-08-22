package guitest.chessgame.board.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.clock.ChessClockPanel;
import com.sdm.units.chessgame.gui.board.view.ChessGameFrame;
import com.sdm.units.chessgame.gui.controller.events.ChessGameEvent;

import guitest.chessgame.testdoubles.ChessClockPanelSpy;
import guitest.chessgame.testdoubles.ChessGameToolbarDummy;
import guitest.chessgame.testdoubles.ChessboardViewSpy;
import guitest.chessgame.testdoubles.MoveHistoryAreaFake;

@DisplayName("ChessGameFrame")
class ChessGameFrameTest {

    private ChessboardViewSpy boardSpy;
    private MoveHistoryAreaFake fakeTextArea;
    private EnumMap<ChessPieceColor, ChessClockPanel> fakeClocks;
    private ChessGameToolbarDummy dummyToolBar;
    private ChessGameFrame frame;

    @BeforeEach
    void setUp() {
        boardSpy = new ChessboardViewSpy();
        fakeTextArea = new MoveHistoryAreaFake();

        fakeClocks = new EnumMap<>(ChessPieceColor.class);
        fakeClocks.put(ChessPieceColor.WHITE, new ChessClockPanelSpy(ChessPieceColor.WHITE));
        fakeClocks.put(ChessPieceColor.BLACK, new ChessClockPanelSpy(ChessPieceColor.BLACK));

        dummyToolBar = new ChessGameToolbarDummy();

        frame = new ChessGameFrame("TestChess", boardSpy, fakeClocks, fakeTextArea, dummyToolBar);
    }

    @Test
    @DisplayName("should display the frame")
    void shouldDisplayFrame() {
        frame.display();
        assertTrue(frame.isVisible());
    }

    @Test
    @DisplayName("should render chessboard when setChessboard is called")
    void shouldRenderChessboard() {
        Chessboard board = mock(Chessboard.class);

        frame.setChessboard(board);

        assertTrue(boardSpy.isRenderCalled());
        assertEquals(board, boardSpy.getLastBoard());
    }

    @Test
    @DisplayName("should record messages in move history")
    void shouldRecordMessage() {
        frame.recordMessage("e2 to e4");
        assertTrue(frame.getMoveHistoryText().contains("e2 to e4"));
    }

    @Test
    @DisplayName("should reset clocks and move history")
    void shouldResetFrame() {
        frame.recordMessage("some move");
        frame.reset();

        assertEquals("", frame.getMoveHistoryText());
        assertTrue(((ChessClockPanelSpy) fakeClocks.get(ChessPieceColor.WHITE)).isResetCalled());
        assertTrue(((ChessClockPanelSpy) fakeClocks.get(ChessPieceColor.BLACK)).isResetCalled());
    }

    @Test
    @DisplayName("should start and stop the correct clocks")
    void shouldControlClocks() {
        frame.startClock(ChessPieceColor.WHITE);
        assertTrue(((ChessClockPanelSpy) fakeClocks.get(ChessPieceColor.WHITE)).isStartClockCalled());

        frame.stopClock(ChessPieceColor.BLACK);
        assertTrue(((ChessClockPanelSpy) fakeClocks.get(ChessPieceColor.BLACK)).isStopClockCalled());
    }

    @Test
    @DisplayName("should close the frame")
    void shouldCloseFrame() {
        frame.display();
        frame.close();
        assertFalse(frame.isDisplayable());
    }

    @Test
    @DisplayName("should handle game events by delegating to event")
    void shouldHandleChessGameEvent() {
        ChessGameEvent event = mock(ChessGameEvent.class);

        frame.onChessGameEvent(event);

        verify(event).handleOn(frame);
    }
}