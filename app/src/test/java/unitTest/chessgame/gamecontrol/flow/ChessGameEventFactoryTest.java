package unittest.chessgame.gamecontrol.flow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamecontrol.flow.ChessGameEventFactory;
import com.sdm.units.chessgame.gamecontrol.flow.ScoreKeeper;
import com.sdm.units.chessgame.gamecontrol.flow.TurnManager;
import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.result.CaptureResult;
import com.sdm.units.chessgame.gamelogic.move.result.MoveResult;
import com.sdm.units.chessgame.gui.controller.events.ChessGameEvent;
import com.sdm.units.chessgame.gui.controller.events.ClockStartEvent;
import com.sdm.units.chessgame.gui.controller.events.ClockStopEvent;
import com.sdm.units.chessgame.gui.controller.events.CompositeChessGameEvent;
import com.sdm.units.chessgame.gui.controller.events.GameEndedEvent;
import com.sdm.units.chessgame.gui.controller.events.GameMessageEvent;
import com.sdm.units.chessgame.gui.controller.events.GameRecordEvent;
import com.sdm.units.chessgame.gui.controller.events.GameStartedEvent;
import com.sdm.units.chessgame.gui.controller.events.UpdateChessboardEvent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Optional;

@DisplayName("ChessGameEventFactory")
class ChessGameEventFactoryTest {

    private Chessboard dummyBoard;
    private ChessGameEventFactory factory;

    @BeforeEach
    void setUp() {
        dummyBoard = mock(Chessboard.class);
        factory = new ChessGameEventFactory(dummyBoard);
    }

    @Nested
    @DisplayName("gameStarted")
    class GameStarted {

        @Test
        @DisplayName("should assemble a GameStartedEvent, UpdateChessboardEvent and ClockStartEvent")
        void shouldAssembleStartEvents() {
            ChessGameEvent event = factory.gameStarted(ChessPieceColor.WHITE);

            assertThat(event).isInstanceOf(CompositeChessGameEvent.class);
            CompositeChessGameEvent composite = (CompositeChessGameEvent) event;

            assertThat(composite.events()).hasExactlyElementsOfTypes(
                GameStartedEvent.class,
                UpdateChessboardEvent.class,
                ClockStartEvent.class
            );
        }
    }

    @Nested
    @DisplayName("moveApplied")
    class MoveApplied {

        private TurnManager turns;
        private ScoreKeeper scores;
        private ReversibleMove dummyMove;
        private MoveResult result;

        @BeforeEach
        void init() {
            turns = new TurnManager();
            scores = new ScoreKeeper();
            dummyMove = mock(ReversibleMove.class);
            result = new MoveResult(dummyMove, new CaptureResult(Optional.empty()));
        }

        @Test
        @DisplayName("should assemble a record, update, stop clock and start clock events")
        void shouldAssembleMoveAppliedEvents() {
            ChessGameEvent event = factory.moveApplied(result, turns, scores);

            CompositeChessGameEvent composite = (CompositeChessGameEvent) event;

            assertThat(composite.events()).hasExactlyElementsOfTypes(
                GameRecordEvent.class,
                UpdateChessboardEvent.class,
                ClockStopEvent.class,
                ClockStartEvent.class
            );
        }
    }

    @Nested
    @DisplayName("moveRejected")
    class MoveRejected {

        @Test
        @DisplayName("should assemble a GameMessageEvent with rejection reason")
        void shouldAssembleMoveRejectedEvent() {
            ChessGameEvent event = factory.moveRejected("illegal move");

            assertThat(event).isInstanceOf(GameMessageEvent.class);
            GameMessageEvent messageEvent = (GameMessageEvent) event;

            assertThat(messageEvent.getMessage()).contains("illegal move");
        }
    }

    @Nested
    @DisplayName("playerInCheck")
    class PlayerInCheck {

        @Test
        @DisplayName("should assemble update and check message events")
        void shouldAssembleCheckEvents() {
            ChessGameEvent event = factory.playerInCheck(ChessPieceColor.BLACK);

            CompositeChessGameEvent composite = (CompositeChessGameEvent) event;

            assertThat(composite.events()).hasExactlyElementsOfTypes(
                UpdateChessboardEvent.class,
                GameMessageEvent.class
            );
        }
    }

    @Nested
    @DisplayName("gameEnded")
    class GameEnded {

        @Test
        @DisplayName("should return a GameEndedEvent with exit message")
        void shouldReturnGameEndedEvent() {
            ChessGameEvent event = factory.gameEnded();

            assertThat(event).isInstanceOf(GameEndedEvent.class);
            GameEndedEvent ended = (GameEndedEvent) event;

            assertThat(ended.getMessage()).contains("Exiting");
        }
    }
}