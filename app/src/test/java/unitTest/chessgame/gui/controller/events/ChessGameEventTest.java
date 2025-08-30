package unittest.chessgame.gui.controller.events;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.view.ChessGameView;
import com.sdm.units.chessgame.gui.controller.events.ChessGameEvent;
import com.sdm.units.chessgame.gui.controller.events.ClockStartEvent;
import com.sdm.units.chessgame.gui.controller.events.ClockStopEvent;
import com.sdm.units.chessgame.gui.controller.events.CompositeChessGameEvent;
import com.sdm.units.chessgame.gui.controller.events.GameEndedEvent;
import com.sdm.units.chessgame.gui.controller.events.GameMessageEvent;
import com.sdm.units.chessgame.gui.controller.events.GameRecordEvent;
import com.sdm.units.chessgame.gui.controller.events.GameStartedEvent;
import com.sdm.units.chessgame.gui.controller.events.UpdateChessboardEvent;

@DisplayName("ChessGameEvents")
class ChessGameEventTest {

    private ChessGameView view;
    private ChessGameEvent event;

    @BeforeEach
    void setUp() {
        view = mock(ChessGameView.class);
    }

    @Nested
    @DisplayName("when game started event is handled")
    class WhenGameStartedEvent {

        @BeforeEach
        void setUp() {
            event = new GameStartedEvent("Welcome");
        }

        @Test
        @DisplayName("should reset the view")
        void shouldResetViewWhenHandled() {
            event.handleOn(view);

            verify(view).reset();
        }

        @Test
        @DisplayName("should initialize the view")
        void shouldInitializeViewWhenHandled() {
            event.handleOn(view);

            verify(view).initialize();
        }

        @Test
        @DisplayName("should display start message")
        void shouldDisplayStartMessageWhenHandled() {
            event.handleOn(view);

            verify(view).displayMessage("Welcome");
        }
    }

    @Nested
    @DisplayName("when game ended event is handled")
    class WhenGameEndedEvent {

        @BeforeEach
        void setUp() {
            event = new GameEndedEvent("Goodbye");
        }

        @Test
        @DisplayName("should display end message")
        void shouldDisplayEndMessageWhenHandled() {
            event.handleOn(view);

            verify(view).displayMessage("Goodbye");
        }

        @Test
        @DisplayName("should close the view")
        void shouldCloseViewWhenHandled() {
            event.handleOn(view);

            verify(view).close();
        }
    }

    @Nested
    @DisplayName("when clock start event is handled")
    class WhenClockStartEvent {

        @Test
        @DisplayName("should start clock for given color")
        void shouldStartClockForColorWhenHandled() {
            event = new ClockStartEvent(ChessPieceColor.WHITE);

            event.handleOn(view);

            verify(view).startClock(ChessPieceColor.WHITE);
        }
    }

    @Nested
    @DisplayName("when clock stop event is handled")
    class WhenClockStopEvent {

        @Test
        @DisplayName("should stop clock for given color")
        void shouldStopClockForColorWhenHandled() {
            event = new ClockStopEvent(ChessPieceColor.BLACK);

            event.handleOn(view);

            verify(view).stopClock(ChessPieceColor.BLACK);
        }
    }

    @Nested
    @DisplayName("when game message event is handled")
    class WhenGameMessageEvent {

        @Test
        @DisplayName("should display message")
        void shouldDisplayMessageWhenHandled() {
            event = new GameMessageEvent("Some message");

            event.handleOn(view);

            verify(view).displayMessage("Some message");
        }
    }

    @Nested
    @DisplayName("when game record event is handled")
    class WhenGameRecordEvent {

        @Test
        @DisplayName("should record message")
        void shouldRecordMessageWhenHandled() {
            event = new GameRecordEvent("Record this");

            event.handleOn(view);

            verify(view).recordMessage("Record this");
        }
    }

    @Nested
    @DisplayName("when update chessboard event is handled")
    class WhenUpdateChessboardEvent {

        @Test
        @DisplayName("should update chessboard")
        void shouldUpdateChessboardWhenHandled() {
            Chessboard board = mock(Chessboard.class);
            event = new UpdateChessboardEvent(board);

            event.handleOn(view);

            verify(view).setChessboard(board);
        }
    }

    @Nested
    @DisplayName("when composite game event is handled")
    class WhenCompositeChessGameEvent {

        @Test
        @DisplayName("should handle all child events")
        void shouldHandleAllChildEventsWhenHandled() {
            ChessGameEvent first = mock(ChessGameEvent.class);
            ChessGameEvent second = mock(ChessGameEvent.class);
            CompositeChessGameEvent composite = new CompositeChessGameEvent(first, second);

            composite.handleOn(view);

            verify(first).handleOn(view);
            verify(second).handleOn(view);
        }
    }
}