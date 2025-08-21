package unitTest.chessgame.gamelogic.board.state;

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

import unitTest.chessgame.gamelogic.testdoubles.ChessboardFake;
import unitTest.chessgame.gamelogic.testdoubles.ReversibleMoveStub;

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
        @DisplayName("should execute move on board, record it, and return correct MoveResult")
        void shouldExecuteMoveRecordItAndReturnResult() {
            ReversibleMoveStub move = new ReversibleMoveStub(CaptureResult.none(), null);

            MoveResult result = executor.executeMove(board, move);

            assertThat(move.wasExecuted()).isTrue();
            verify(historyMock).pushMove(move);
            assertThat(result.move()).isEqualTo(move);
            assertThat(result.captureResult()).isEqualTo(CaptureResult.none());
        }
    }

    @Nested
    @DisplayName("when undoing a move")
    class UndoMove {

        @Test
        @DisplayName("should return empty when no moves to undo")
        void shouldReturnEmptyWhenNoMovesInHistory() {
            when(historyMock.popMove()).thenReturn(Optional.empty());

            Optional<MoveResult> result = executor.undoLastMove(board);

            assertThat(result).isEmpty();
            verify(historyMock).popMove();
        }

        @Test
        @DisplayName("should undo last move and return correct MoveResult")
        void shouldUndoLastMoveAndReturnResult() {
            ReversibleMoveStub move = new ReversibleMoveStub(CaptureResult.none(), null);
            when(historyMock.popMove()).thenReturn(Optional.of(move));

            Optional<MoveResult> result = executor.undoLastMove(board);

            assertThat(move.wasUndone()).isTrue();
            verify(historyMock).popMove();
            assertThat(result).contains(MoveResult.of(move, CaptureResult.none()));
        }
    }
}