package test.chessgame.gamelogic.board.evaluation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.board.evaluation.AttackDetector;
import com.sdm.units.chessgame.gamelogic.board.evaluation.MoveSimulationService;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.core.MoveValidator;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;

import test.chessgame.gamelogic.testdoubles.ChessboardFake;

@DisplayName("MoveSimulationService")
class MoveSimulationServiceTest {

    private MoveValidator moveValidator;
    private AttackDetector attackDetector;
    private ChessboardOrientation orientation;
    private MoveSimulationService service;
    private ChessboardFake board;

    @BeforeEach
    void setUp() {
        moveValidator = mock(MoveValidator.class);
        attackDetector = mock(AttackDetector.class);
        orientation = ChessboardOrientation.WHITE_BOTTOM;
        service = new MoveSimulationService(moveValidator, attackDetector, orientation);
        board = new ChessboardFake();
    }

    @Nested
    @DisplayName("resolvesCheck")
    class ResolvesCheck {

        private final ChessboardPosition from = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
        private final ChessboardPosition to = new ChessboardPosition(ChessboardFile.B, ChessboardRank.TWO);

        @Test
        @DisplayName("should return false when move is invalid")
        void shouldReturnFalseWhenMoveInvalid() {
            when(moveValidator.validateAndCreate(board, from, to, orientation)).thenReturn(Optional.empty());

            boolean result = service.resolvesCheck(board, from, to, ChessPieceColor.WHITE);

            assertThat(result).isFalse();
            verify(moveValidator).validateAndCreate(board, from, to, orientation);
            verifyNoInteractions(attackDetector);
        }

        @Test
        @DisplayName("should return true when move resolves check")
        void shouldReturnTrueWhenMoveResolvesCheck() {
            ReversibleMove move = mock(ReversibleMove.class);
            when(moveValidator.validateAndCreate(board, from, to, orientation)).thenReturn(Optional.of(move));
            when(attackDetector.isUnderAttack(board, ChessPieceColor.WHITE)).thenReturn(false);

            boolean result = service.resolvesCheck(board, from, to, ChessPieceColor.WHITE);

            assertThat(result).isTrue();
            verify(move).executeOn(board);
            verify(move).undoOn(board);
        }

        @Test
        @DisplayName("should return false when move does not resolve check")
        void shouldReturnFalseWhenMoveDoesNotResolveCheck() {
            ReversibleMove move = mock(ReversibleMove.class);
            when(moveValidator.validateAndCreate(board, from, to, orientation)).thenReturn(Optional.of(move));
            when(attackDetector.isUnderAttack(board, ChessPieceColor.WHITE)).thenReturn(true);

            boolean result = service.resolvesCheck(board, from, to, ChessPieceColor.WHITE);

            assertThat(result).isFalse();
            verify(move).executeOn(board);
            verify(move).undoOn(board);
        }
    }

    @Nested
    @DisplayName("isPathSafe")
    class IsPathSafe {

        private final ChessboardPosition from = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
        private final ChessboardPosition step1 = new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO);
        private final ChessboardPosition step2 = new ChessboardPosition(ChessboardFile.A, ChessboardRank.THREE);

        @Test
        @DisplayName("should return false when first step is invalid")
        void shouldReturnFalseWhenFirstStepInvalid() {
            when(moveValidator.validateAndCreate(board, from, step1, orientation)).thenReturn(Optional.empty());

            boolean result = service.isPathSafe(board, from, List.of(step1, step2), ChessPieceColor.BLACK);

            assertThat(result).isFalse();
            verify(moveValidator).validateAndCreate(board, from, step1, orientation);
            verifyNoInteractions(attackDetector);
        }

        @Test
        @DisplayName("should return false when path leads into check")
        void shouldReturnFalseWhenPathLeadsIntoCheck() {
            ReversibleMove move1 = mock(ReversibleMove.class);
            ReversibleMove move2 = mock(ReversibleMove.class);

            when(moveValidator.validateAndCreate(board, from, step1, orientation)).thenReturn(Optional.of(move1));
            when(moveValidator.validateAndCreate(board, step1, step2, orientation)).thenReturn(Optional.of(move2));
            when(attackDetector.isUnderAttack(board, ChessPieceColor.BLACK))
                    .thenReturn(false)  // after move1
                    .thenReturn(true);  // after move2

            boolean result = service.isPathSafe(board, from, List.of(step1, step2), ChessPieceColor.BLACK);

            assertThat(result).isFalse();
            verify(move1).executeOn(board);
            verify(move1).undoOn(board);
            verify(move2).executeOn(board);
            verify(move2).undoOn(board);
        }

        @Test
        @DisplayName("should return true when entire path is safe")
        void shouldReturnTrueWhenEntirePathSafe() {
            ReversibleMove move1 = mock(ReversibleMove.class);
            ReversibleMove move2 = mock(ReversibleMove.class);

            when(moveValidator.validateAndCreate(board, from, step1, orientation)).thenReturn(Optional.of(move1));
            when(moveValidator.validateAndCreate(board, step1, step2, orientation)).thenReturn(Optional.of(move2));
            when(attackDetector.isUnderAttack(board, ChessPieceColor.BLACK)).thenReturn(false);

            boolean result = service.isPathSafe(board, from, List.of(step1, step2), ChessPieceColor.BLACK);

            assertThat(result).isTrue();
            verify(move1).executeOn(board);
            verify(move2).executeOn(board);
            verify(move1).undoOn(board);
            verify(move2).undoOn(board);
        }
    }
}