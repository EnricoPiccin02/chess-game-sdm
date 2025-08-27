package unittest.chessgame.gamelogic.board.evaluation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
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

import unittest.chessgame.gamelogic.testdoubles.ChessboardFake;

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
    @DisplayName("checking if any move resolves check state")
    class CheckingMovesResolveCheck {

        private final ChessboardPosition from = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
        private final ChessboardPosition to = new ChessboardPosition(ChessboardFile.B, ChessboardRank.TWO);

        @Test
        @DisplayName("should reject move when it is invalid")
        void shouldRejectInvalidMove() {
            when(moveValidator.validateAndCreate(board, from, to, orientation)).thenReturn(Optional.empty());

            boolean result = service.resolvesCheck(board, from, to, ChessPieceColor.WHITE);

            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("should accept move when it eliminates check")
        void shouldAcceptMoveThatEliminatesCheck() {
            ReversibleMove move = mock(ReversibleMove.class);
            when(moveValidator.validateAndCreate(board, from, to, orientation)).thenReturn(Optional.of(move));
            when(attackDetector.isUnderAttack(board, ChessPieceColor.WHITE)).thenReturn(false);

            boolean result = service.resolvesCheck(board, from, to, ChessPieceColor.WHITE);

            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("should reject move when it fails to eliminate check")
        void shouldRejectMoveThatFailsToEliminateCheck() {
            ReversibleMove move = mock(ReversibleMove.class);
            when(moveValidator.validateAndCreate(board, from, to, orientation)).thenReturn(Optional.of(move));
            when(attackDetector.isUnderAttack(board, ChessPieceColor.WHITE)).thenReturn(true);

            boolean result = service.resolvesCheck(board, from, to, ChessPieceColor.WHITE);

            assertThat(result).isFalse();
        }
    }

    @Nested
    @DisplayName("checking path safety")
    class CheckingPathSafety {

        private final ChessboardPosition from = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
        private final ChessboardPosition step1 = new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO);
        private final ChessboardPosition step2 = new ChessboardPosition(ChessboardFile.A, ChessboardRank.THREE);

        @Test
        @DisplayName("should reject path when first step is invalid")
        void shouldRejectPathWithInvalidFirstStep() {
            when(moveValidator.validateAndCreate(board, from, step1, orientation)).thenReturn(Optional.empty());

            boolean result = service.isPathSafe(board, from, List.of(step1, step2), ChessPieceColor.BLACK);

            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("should reject path when any step leads into check")
        void shouldRejectPathThatLeadsIntoCheck() {
            ReversibleMove move1 = mock(ReversibleMove.class);
            ReversibleMove move2 = mock(ReversibleMove.class);

            when(moveValidator.validateAndCreate(board, from, step1, orientation)).thenReturn(Optional.of(move1));
            when(moveValidator.validateAndCreate(board, step1, step2, orientation)).thenReturn(Optional.of(move2));
            when(attackDetector.isUnderAttack(board, ChessPieceColor.BLACK))
                    .thenReturn(false)  // safe after move1
                    .thenReturn(true);  // check after move2

            boolean result = service.isPathSafe(board, from, List.of(step1, step2), ChessPieceColor.BLACK);

            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("should accept path when all steps are safe")
        void shouldAcceptPathWhenAllStepsAreSafe() {
            ReversibleMove move1 = mock(ReversibleMove.class);
            ReversibleMove move2 = mock(ReversibleMove.class);

            when(moveValidator.validateAndCreate(board, from, step1, orientation)).thenReturn(Optional.of(move1));
            when(moveValidator.validateAndCreate(board, step1, step2, orientation)).thenReturn(Optional.of(move2));
            when(attackDetector.isUnderAttack(board, ChessPieceColor.BLACK)).thenReturn(false);

            boolean result = service.isPathSafe(board, from, List.of(step1, step2), ChessPieceColor.BLACK);

            assertThat(result).isTrue();
        }
    }
}