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
        @DisplayName("should reset scores, set turns to WHITE and fire gameStarted event")
        void shouldResetScoresAndNotify() {
            ChessGameEvent event = mock(ChessGameEvent.class);
            when(factory.gameStarted(ChessPieceColor.WHITE)).thenReturn(event);

            service.onGameStart();

            verify(spyScores).reset();
            verify(spyTurns).start();
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
        @DisplayName("should update scores, fire moveApplied event and swap turns")
        void shouldUpdateScoresAndNotify() {
            ChessGameEvent event = mock(ChessGameEvent.class);
            when(factory.moveApplied(resultStub, spyTurns, spyScores)).thenReturn(event);

            service.onMoveApplied(resultStub);

            verify(spyScores).apply(resultStub, ChessPieceColor.WHITE);            
            verify(spyListener).onChessGameEvent(event);
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
        @DisplayName("should revert scores, fire moveUndone event and swap turns")
        void shouldRevertScoresAndNotify() {
            ChessGameEvent event = mock(ChessGameEvent.class);
            when(factory.moveUndone(resultStub, spyTurns, spyScores)).thenReturn(event);

            service.onMoveUndone(resultStub);

            verify(spyScores).revert(resultStub, ChessPieceColor.BLACK);            
            verify(spyListener).onChessGameEvent(event);
            verify(spyTurns).swap();
        }
    }

    @Nested
    @DisplayName("when a move is rejected")
    class WhenMoveRejected {

        @Test
        @DisplayName("should fire moveRejected event without touching scores or turns")
        void shouldFireRejectedEvent() {
            ChessGameEvent event = mock(ChessGameEvent.class);
            when(factory.moveRejected("illegal")).thenReturn(event);

            service.onMoveRejected("illegal");

            verify(spyListener).onChessGameEvent(event);
            verifyNoInteractions(spyScores);
            verifyNoInteractions(spyTurns);
        }
    }

    @Nested
    @DisplayName("when a player wins")
    class WhenPlayerWins {

        @Test
        @DisplayName("should fire playerWon event without resetting scores/turns")
        void shouldFireWinEventWithoutResetting() {
            ChessGameEvent event = mock(ChessGameEvent.class);
            when(factory.playerWon(spyTurns)).thenReturn(event);

            service.onPlayerWon();

            verify(spyListener).onChessGameEvent(event);
            verifyNoInteractions(spyScores);
            verifyNoInteractions(spyTurns);
        }
    }

    @Nested
    @DisplayName("when the game ends")
    class WhenGameEnds {

        @Test
        @DisplayName("should fire gameEnded event without resetting scores/turns")
        void shouldFireGameEndedEventWithoutResetting() {
            ChessGameEvent event = mock(ChessGameEvent.class);
            when(factory.gameEnded()).thenReturn(event);

            service.onGameEnd();

            verify(spyListener).onChessGameEvent(event);
            verifyNoInteractions(spyScores);
            verifyNoInteractions(spyTurns);
        }
    }
}