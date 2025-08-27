package unittest.chessgame.gamelogic.board.state;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sdm.units.chessgame.gamelogic.board.state.MoveExecutorService;
import com.sdm.units.chessgame.gamelogic.board.state.MoveRecorder;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.result.CaptureResult;
import com.sdm.units.chessgame.gamelogic.move.result.MoveResult;

import unittest.chessgame.gamelogic.testdoubles.ChessboardFake;
import unittest.chessgame.gamelogic.testdoubles.ReversibleMoveStub;

@DisplayName("MoveExecutorService")
class MoveExecutorServiceTest {

    private MoveRecorder<ReversibleMove> historyMock;
    private MoveExecutorService executor;
    private ChessboardFake board;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        historyMock = (MoveRecorder<ReversibleMove>) Mockito.mock(MoveRecorder.class);
        executor = new MoveExecutorService(historyMock);
        board = new ChessboardFake();
    }

    @Nested
    @DisplayName("when executing a move")
    class ExecuteMove {

        @Test
        @DisplayName("should execute the move on the board")
        void shouldExecuteMoveOnBoard() {
            ReversibleMoveStub move = new ReversibleMoveStub(CaptureResult.none(), null);

            executor.executeMove(board, move);

            assertThat(move.wasExecuted()).isTrue();
        }

        @Test
        @DisplayName("should record the move in history")
        void shouldRecordMoveInHistory() {
            ReversibleMoveStub move = new ReversibleMoveStub(CaptureResult.none(), null);

            executor.executeMove(board, move);

            verify(historyMock).pushMove(move);
        }

        @Test
        @DisplayName("should provide the move result after execution")
        void shouldProvideMoveResult() {
            ReversibleMoveStub move = new ReversibleMoveStub(CaptureResult.none(), null);

            MoveResult result = executor.executeMove(board, move);

            assertThat(result).isEqualTo(MoveResult.of(move, CaptureResult.none()));
        }
    }

    @Nested
    @DisplayName("when undoing a move")
    class UndoMove {

        @Test
        @DisplayName("should not extract any move when there are no moves to undo")
        void shouldNotExtractAnyMoveWhenNoMovesExist() {
            when(historyMock.popMove()).thenReturn(Optional.empty());

            Optional<MoveResult> result = executor.undoLastMove(board);

            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("should retrieve last move from history when undoing")
        void shouldRetrieveLastMoveFromHistory() {
            ReversibleMoveStub move = new ReversibleMoveStub(CaptureResult.none(), null);
            when(historyMock.popMove()).thenReturn(Optional.of(move));

            executor.undoLastMove(board);

            verify(historyMock).popMove();
        }

        @Test
        @DisplayName("should undo the last move on the board")
        void shouldUndoLastMoveOnBoard() {
            ReversibleMoveStub move = new ReversibleMoveStub(CaptureResult.none(), null);
            when(historyMock.popMove()).thenReturn(Optional.of(move));

            executor.undoLastMove(board);

            assertThat(move.wasUndone()).isTrue();
        }

        @Test
        @DisplayName("should provide the move result after undoing")
        void shouldProvideMoveResultAfterUndoing() {
            ReversibleMoveStub move = new ReversibleMoveStub(CaptureResult.none(), null);
            when(historyMock.popMove()).thenReturn(Optional.of(move));

            Optional<MoveResult> result = executor.undoLastMove(board);

            assertThat(result).contains(MoveResult.of(move, CaptureResult.none()));
        }
    }
}