package unittest.chessgame.gamecontrol.flow;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamecontrol.flow.EventFactory;
import com.sdm.units.chessgame.gamecontrol.flow.GameFlowService;
import com.sdm.units.chessgame.gamecontrol.flow.ScoreKeeper;
import com.sdm.units.chessgame.gamecontrol.flow.TurnManager;
import com.sdm.units.chessgame.gamecontrol.state.GameReason;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.result.CaptureResult;
import com.sdm.units.chessgame.gamelogic.move.result.MoveResult;
import com.sdm.units.chessgame.gui.controller.events.ChessGameEvent;
import com.sdm.units.chessgame.gui.controller.events.ChessGameEventListener;

@DisplayName("GameFlowService")
class GameFlowServiceTest {

    private GameFlowService service;
    private ScoreKeeper spyScores;
    private TurnManager spyTurns;
    private EventFactory factory;
    private ChessGameEventListener spyListener;

    @BeforeEach
    void setUp() {
        spyScores = mock(ScoreKeeper.class);
        spyTurns = mock(TurnManager.class);
        factory = mock(EventFactory.class);
        service = new GameFlowService(spyScores, spyTurns, factory);
        spyListener = mock(ChessGameEventListener.class);
        service.addChessGameEventListener(spyListener);
    }

    @Nested
    @DisplayName("when game starts")
    class WhenGameStarts {

        @BeforeEach
        void init() {
            when(spyTurns.current()).thenReturn(ChessPieceColor.WHITE);
        }

        @Test
        @DisplayName("should begin with White as the first player")
        void shouldBeginWithWhiteAsFirstPlayer() {
            service.onGameStart();
            verify(spyTurns).start();
        }

        @Test
        @DisplayName("should clear all previous scores")
        void shouldClearPreviousScores() {
            service.onGameStart();
            verify(spyScores).reset();
        }

        @Test
        @DisplayName("should announce that the game has started")
        void shouldAnnounceGameStarted() {
            ChessGameEvent event = mock(ChessGameEvent.class);
            when(factory.gameStarted(ChessPieceColor.WHITE)).thenReturn(event);

            service.onGameStart();

            verify(spyListener).onChessGameEvent(event);
        }
    }

    @Nested
    @DisplayName("when a move is applied")
    class WhenMoveApplied {

        private MoveResult resultStub;

        @BeforeEach
        void init() {
            ReversibleMove dummyMove = mock(ReversibleMove.class);
            resultStub = new MoveResult(dummyMove, new CaptureResult(Optional.empty()));
            when(spyTurns.current()).thenReturn(ChessPieceColor.WHITE);
        }

        @Test
        @DisplayName("should update the score for the moving player")
        void shouldUpdateScoreForMovingPlayer() {
            service.onMoveApplied(resultStub);
            verify(spyScores).apply(resultStub, ChessPieceColor.WHITE);
        }

        @Test
        @DisplayName("should announce that a move has been made")
        void shouldAnnounceMoveApplied() {
            ChessGameEvent event = mock(ChessGameEvent.class);
            when(factory.moveApplied(resultStub, spyTurns, spyScores)).thenReturn(event);

            service.onMoveApplied(resultStub);

            verify(spyListener).onChessGameEvent(event);
        }

        @Test
        @DisplayName("should give the turn to the opponent")
        void shouldGiveTurnToOpponent() {
            service.onMoveApplied(resultStub);
            verify(spyTurns).swap();
        }
    }

    @Nested
    @DisplayName("when a move is undone")
    class WhenMoveUndone {

        private MoveResult resultStub;

        @BeforeEach
        void init() {
            ReversibleMove dummyMove = mock(ReversibleMove.class);
            resultStub = new MoveResult(dummyMove, new CaptureResult(Optional.empty()));
            when(spyTurns.opponent()).thenReturn(ChessPieceColor.BLACK);
        }

        @Test
        @DisplayName("should restore the previous score state")
        void shouldRestorePreviousScoreState() {
            service.onMoveUndone(resultStub);
            verify(spyScores).revert(resultStub, ChessPieceColor.BLACK);
        }

        @Test
        @DisplayName("should announce that a move was undone")
        void shouldAnnounceMoveUndone() {
            ChessGameEvent event = mock(ChessGameEvent.class);
            when(factory.moveUndone(resultStub, spyTurns, spyScores)).thenReturn(event);

            service.onMoveUndone(resultStub);

            verify(spyListener).onChessGameEvent(event);
        }

        @Test
        @DisplayName("should return the turn to the previous player")
        void shouldReturnTurnToPreviousPlayer() {
            service.onMoveUndone(resultStub);
            verify(spyTurns).swap();
        }
    }

    @Nested
    @DisplayName("when a move is rejected")
    class WhenMoveRejected {

        @Test
        @DisplayName("should announce that the move was rejected")
        void shouldAnnounceMoveRejected() {
            ChessGameEvent event = mock(ChessGameEvent.class);
            when(factory.moveRejected(GameReason.ILLEGAL_MOVE)).thenReturn(event);

            service.onMoveRejected(GameReason.ILLEGAL_MOVE);

            verify(spyListener).onChessGameEvent(event);
        }

        @Test
        @DisplayName("should not affect scores or turns")
        void shouldNotAffectScoresOrTurns() {
            service.onMoveRejected(GameReason.ILLEGAL_MOVE);

            verifyNoInteractions(spyScores);
            verifyNoInteractions(spyTurns);
        }
    }

    @Nested
    @DisplayName("when a player wins")
    class WhenPlayerWins {

        @Test
        @DisplayName("should announce the winner")
        void shouldAnnounceWinner() {
            ChessGameEvent event = mock(ChessGameEvent.class);
            when(factory.playerWon(spyTurns, ChessPieceColor.WHITE, GameReason.CHECKMATE)).thenReturn(event);

            service.onPlayerWon(ChessPieceColor.WHITE, GameReason.CHECKMATE);

            verify(spyListener).onChessGameEvent(event);
        }

        @Test
        @DisplayName("should not affect scores or turns")
        void shouldNotAffectScoresOrTurns() {
            service.onPlayerWon(ChessPieceColor.WHITE, GameReason.CHECKMATE);

            verifyNoInteractions(spyScores);
            verifyNoInteractions(spyTurns);
        }
    }

    @Nested
    @DisplayName("when the game ends")
    class WhenGameEnds {

        @Test
        @DisplayName("should announce that the game has ended")
        void shouldAnnounceGameEnded() {
            ChessGameEvent event = mock(ChessGameEvent.class);
            when(factory.gameEnded()).thenReturn(event);

            service.onGameEnd();

            verify(spyListener).onChessGameEvent(event);
        }

        @Test
        @DisplayName("should not affect scores or turns")
        void shouldNotAffectScoresOrTurns() {
            service.onGameEnd();

            verifyNoInteractions(spyScores);
            verifyNoInteractions(spyTurns);
        }
    }
}