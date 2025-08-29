package unittest.chessgame.gamecontrol.flow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamecontrol.flow.ChessGameEventFactory;
import com.sdm.units.chessgame.gamecontrol.flow.ScoreKeeper;
import com.sdm.units.chessgame.gamecontrol.flow.TurnManager;
import com.sdm.units.chessgame.gamecontrol.state.GameReason;
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
    @DisplayName("when the game starts")
    class GameStarted {

        @Test
        @DisplayName("should assemble game starting events")
        void shouldAssembleStartEvents() {
            ChessGameEvent event = factory.gameStarted(ChessPieceColor.WHITE);

            assertThat(event).isInstanceOf(CompositeChessGameEvent.class);
            CompositeChessGameEvent composite = (CompositeChessGameEvent) event;

            assertThat(composite.events()).hasExactlyElementsOfTypes(
                UpdateChessboardEvent.class,
                GameStartedEvent.class,
                ClockStartEvent.class
            );
        }
    }

    @Nested
    @DisplayName("when a move is applied")
    class MoveApplied {

        private TurnManager dummyTurns;
        private ScoreKeeper dummyScores;
        private ReversibleMove dummyMove;
        private MoveResult result;

        @BeforeEach
        void init() {
            dummyTurns = mock(TurnManager.class);
            dummyScores = mock(ScoreKeeper.class);
            dummyMove = mock(ReversibleMove.class);
            result = new MoveResult(dummyMove, new CaptureResult(Optional.empty()));
        }

        @Test
        @DisplayName("should assemble move applied events")
        void shouldAssembleMoveAppliedEvents() {
            ChessGameEvent event = factory.moveApplied(result, dummyTurns, dummyScores);

            assertThat(event).isInstanceOf(CompositeChessGameEvent.class);
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
    @DisplayName("when a move is rejected")
    class MoveRejected {

        @Test
        @DisplayName("should assemble move rejected events")
        void shouldAssembleMoveRejectedEvent() {
            ChessGameEvent event = factory.moveRejected(GameReason.ILLEGAL_MOVE);

            assertThat(event).isInstanceOf(CompositeChessGameEvent.class);
            CompositeChessGameEvent composite = (CompositeChessGameEvent) event;

            assertThat(composite.events()).hasExactlyElementsOfTypes(
                UpdateChessboardEvent.class,
                GameMessageEvent.class
            );
        }
    }

    @Nested
    @DisplayName("when a move is undone")
    class MoveUndone {

        private TurnManager dummyTurns;
        private ScoreKeeper dummyScores;
        private ReversibleMove dummyMove;
        private MoveResult result;

        @BeforeEach
        void init() {
            dummyTurns = new TurnManager();
            dummyScores = new ScoreKeeper();
            dummyMove = mock(ReversibleMove.class);
            result = new MoveResult(dummyMove, new CaptureResult(Optional.empty()));
        }

        @Test
        @DisplayName("should assemble move undone events")
        void shouldAssembleMoveUndoneEvents() {
            ChessGameEvent event = factory.moveUndone(result, dummyTurns, dummyScores);

            assertThat(event).isInstanceOf(CompositeChessGameEvent.class);
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
    @DisplayName("when a player wins")
    class PlayerWon {

        private TurnManager dummyTurns;

        @BeforeEach
        void init() {
            dummyTurns = mock(TurnManager.class);
        }

        @Test
        @DisplayName("should assemble player won events")
        void shouldAssemblePlayerWonEvents() {
            ChessGameEvent event = factory.playerWon(dummyTurns, ChessPieceColor.WHITE, GameReason.CHECKMATE);

            assertThat(event).isInstanceOf(CompositeChessGameEvent.class);
            CompositeChessGameEvent composite = (CompositeChessGameEvent) event;

            assertThat(composite.events()).hasExactlyElementsOfTypes(
                UpdateChessboardEvent.class,
                ClockStopEvent.class,
                ClockStopEvent.class,
                GameMessageEvent.class
            );
        }
    }

    @Nested
    @DisplayName("when the game ends")
    class GameEnded {

        @Test
        @DisplayName("should assemble game ended events with exit message")
        void shouldReturnGameEndedEvent() {
            ChessGameEvent event = factory.gameEnded(GameReason.GAME_ENDED);

            assertThat(event).isInstanceOf(GameEndedEvent.class);
            GameEndedEvent ended = (GameEndedEvent) event;

            assertThat(ended.getMessage()).contains(GameReason.GAME_ENDED.getDescription());
        }
    }
}