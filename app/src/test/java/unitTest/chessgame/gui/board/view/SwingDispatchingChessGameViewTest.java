package unittest.chessgame.gui.board.view;

import org.junit.jupiter.api.*;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.view.ChessGameView;
import com.sdm.units.chessgame.gui.board.view.SwingDispatcher;
import com.sdm.units.chessgame.gui.board.view.SwingDispatchingChessGameView;
import com.sdm.units.chessgame.gui.controller.interaction.InteractionContext;

import static org.mockito.Mockito.*;

@DisplayName("SwingDispatchingChessGameView")
class SwingDispatchingChessGameViewTest {

    private ChessGameView delegateView;
    private InteractionContext interactionContext;
    private SwingDispatchingChessGameView view;

    @BeforeEach
    void setUp() {
        delegateView = mock(ChessGameView.class);
        interactionContext = mock(InteractionContext.class);
        view = new SwingDispatchingChessGameView(delegateView, interactionContext, new ImmediateDispatcher());
    }

    @Nested
    @DisplayName("initialize")
    class Initialize {

        @Test
        @DisplayName("should initialize delegate view and interaction handler")
        void shouldInitializeDelegateViewAndInteractionHandler() {
            view.initialize();

            verify(delegateView).initialize();
            verify(interactionContext).initialize();
        }
    }

    @Nested
    @DisplayName("set the chessboard")
    class SetChessboard {

        @Test
        @DisplayName("should update the chessboard on the delegate view")
        void shouldUpdateChessboardOnDelegateView() {
            Chessboard board = mock(Chessboard.class);

            view.setChessboard(board);

            verify(delegateView).setChessboard(board);
        }
    }

    @Nested
    @DisplayName("display a message")
    class DisplayMessage {

        @Test
        @DisplayName("should display a message on the delegate view")
        void shouldDisplayMessageOnDelegateView() {
            String message = "Checkmate!";

            view.displayMessage(message);

            verify(delegateView).displayMessage(message);
        }
    }

    @Nested
    @DisplayName("record a message")
    class RecordMessage {

        @Test
        @DisplayName("should record a message on the delegate view")
        void shouldRecordMessageOnDelegateView() {
            String message = "Game started";

            view.recordMessage(message);

            verify(delegateView).recordMessage(message);
        }
    }

    @Nested
    @DisplayName("start the clock")
    class StartClock {

        @Test
        @DisplayName("should start the clock on the delegate view")
        void shouldStartClockOnDelegateView() {
            ChessPieceColor color = ChessPieceColor.WHITE;

            view.startClock(color);

            verify(delegateView).startClock(color);
        }
    }

    @Nested
    @DisplayName("stop the clock")
    class StopClock {

        @Test
        @DisplayName("should stop the clock on the delegate view")
        void shouldStopClockOnDelegateView() {
            ChessPieceColor color = ChessPieceColor.BLACK;

            view.stopClock(color);

            verify(delegateView).stopClock(color);
        }
    }

    @Nested
    @DisplayName("close")
    class Close {

        @Test
        @DisplayName("should close the delegate view")
        void shouldCloseDelegateView() {
            view.close();

            verify(delegateView).close();
        }
    }

    @Nested
    @DisplayName("reset")
    class Reset {

        @Test
        @DisplayName("should reset the delegate view")
        void shouldResetDelegateView() {
            view.reset();

            verify(delegateView).reset();
        }
    }

    private static class ImmediateDispatcher implements SwingDispatcher {
        
        @Override
        public void dispatch(Runnable task) {
            task.run();
        }
    }
}