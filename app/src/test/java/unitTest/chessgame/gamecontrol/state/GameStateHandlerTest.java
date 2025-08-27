package unittest.chessgame.gamecontrol.state;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamecontrol.flow.GameFlowController;
import com.sdm.units.chessgame.gamecontrol.flow.TurnManager;
import com.sdm.units.chessgame.gamecontrol.state.GameReason;
import com.sdm.units.chessgame.gamecontrol.state.GameStateHandler;
import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.result.CaptureResult;
import com.sdm.units.chessgame.gamelogic.move.result.MoveResult;

import unittest.chessgame.gamecontrol.testdoubles.LegalMoveFinderStub;
import unittest.chessgame.gamecontrol.testdoubles.MoveExecutorStub;
import unittest.chessgame.gamecontrol.testdoubles.OutcomeEvaluatorStub;

@DisplayName("GameStateHandler")
class GameStateHandlerTest {

    private Chessboard board;
    private MoveExecutorStub executor;
    private LegalMoveFinderStub moveFinder;
    private TurnManager stubTurns;
    private OutcomeEvaluatorStub outcomeEvaluator;
    private GameFlowController flowController;

    private GameStateHandler handler;
    private ReversibleMove dummyMove;
    private MoveResult result;

    @BeforeEach
    void setUp() {
        board = mock(Chessboard.class);
        executor = new MoveExecutorStub();
        moveFinder = new LegalMoveFinderStub();
        stubTurns = mock(TurnManager.class);
        when(stubTurns.current()).thenReturn(ChessPieceColor.WHITE);
        when(stubTurns.opponent()).thenReturn(ChessPieceColor.BLACK);
        outcomeEvaluator = new OutcomeEvaluatorStub();
        flowController = mock(GameFlowController.class);

        handler = new GameStateHandler(board, executor, moveFinder, stubTurns, outcomeEvaluator, flowController);

        dummyMove = mock(ReversibleMove.class);
        result = new MoveResult(dummyMove, new CaptureResult(Optional.empty()));
        executor.setNextResult(result);
    }

    @Nested
    @DisplayName("when game starts")
    class WhenGameStarts {

        @Test
        @DisplayName("should prepare the board for a new game")
        void shouldPrepareBoardForNewGame() {
            handler.start();
            verify(board).resetBoard();
        }

        @Test
        @DisplayName("should clear move history")
        void shouldClearMoveHistory() {
            handler.start();
            assertTrue(executor.wasReset());
        }

        @Test
        @DisplayName("should announce game start")
        void shouldAnnounceGameStart() {
            handler.start();
            verify(flowController).onGameStart();
        }
    }

    @Nested
    @DisplayName("when a move is made")
    class WhenMoveMade {

        private final ChessboardPosition from = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
        private final ChessboardPosition to = new ChessboardPosition(ChessboardFile.B, ChessboardRank.TWO);

        @Test
        @DisplayName("should reject move when not allowed")
        void shouldRejectMoveWhenNotAllowed() {
            moveFinder.setMove(Optional.empty());

            handler.makeMove(from, to);

            verify(flowController).onMoveRejected(GameReason.ILLEGAL_MOVE);
        }

        @Test
        @DisplayName("should apply a valid move")
        void shouldApplyValidMove() {
            moveFinder.setMove(Optional.of(dummyMove));

            handler.makeMove(from, to);

            verify(flowController).onMoveApplied(result);
        }

        @Test
        @DisplayName("should reject move that leaves player under attack")
        void shouldRejectMoveThatLeavesPlayerUnderAttack() {
            moveFinder.setMove(Optional.of(dummyMove));
            outcomeEvaluator.setIllegalBecauseOfCheck(true);

            handler.makeMove(from, to);

            verify(flowController).onMoveRejected(GameReason.UNDER_ATTACK);
        }

        @Test
        @DisplayName("should revert move when it leaves player under attack")
        void shouldRevertMoveLeavingPlayerUnderAttack() {
            moveFinder.setMove(Optional.of(dummyMove));
            outcomeEvaluator.setIllegalBecauseOfCheck(true);

            handler.makeMove(from, to);

            assertTrue(executor.wasUndone());
        }

        @Test
        @DisplayName("should proclaim winner when move checkmates opponent")
        void shouldProclaimWinnerWhenOpponentIsCheckmated() {
            moveFinder.setMove(Optional.of(dummyMove));
            outcomeEvaluator.setCheckmate(true);

            handler.makeMove(from, to);

            verify(flowController).onPlayerWon(ChessPieceColor.WHITE, GameReason.CHECKMATE);
        }
    }

    @Nested
    @DisplayName("when a move is undone")
    class WhenMoveUndone {

        @Test
        @DisplayName("should undo the last move")
        void shouldUndoLastMove() {
            executor.setUndoResult(Optional.of(result));

            handler.undoMove();

            verify(flowController).onMoveUndone(result);
        }

        @Test
        @DisplayName("should reject undo when no moves are available")
        void shouldRejectUndoWhenNoMovesAvailable() {
            executor.setUndoResult(Optional.empty());

            handler.undoMove();

            verify(flowController).onMoveRejected(GameReason.NO_UNDO);
        }
    }

    @Nested
    @DisplayName("when the game ends")
    class WhenGameEnds {

        @Test
        @DisplayName("should announce game end")
        void shouldAnnounceGameEnd() {
            handler.end();
            verify(flowController).onGameEnd(GameReason.GAME_ENDED);
        }
    }
}