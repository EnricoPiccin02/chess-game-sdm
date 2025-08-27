package unittest.chessgame.gamelogic.board.evaluation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sdm.units.chessgame.gamelogic.board.evaluation.AttackDetector;
import com.sdm.units.chessgame.gamelogic.board.evaluation.CheckEscapeSimulator;
import com.sdm.units.chessgame.gamelogic.board.evaluation.CheckmateService;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.core.MoveComponent;
import com.sdm.units.chessgame.gamelogic.move.core.MoveGenerator;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;

import unittest.chessgame.gamelogic.testdoubles.ChessboardStub;
import unittest.chessgame.gamelogic.testdoubles.ReversibleMoveStub;

@DisplayName("CheckmateService")
class CheckmateServiceTest {

    private MoveGenerator moveGenerator;
    private CheckEscapeSimulator simulator;
    private AttackDetector attackDetector;
    private ChessboardOrientation orientation;
    private CheckmateService service;
    private ChessboardStub board;

    private final ChessPieceColor defender = ChessPieceColor.WHITE;

    @BeforeEach
    void setUp() {
        moveGenerator = Mockito.mock(MoveGenerator.class);
        simulator = Mockito.mock(CheckEscapeSimulator.class);
        attackDetector = Mockito.mock(AttackDetector.class);
        orientation = ChessboardOrientation.WHITE_BOTTOM;
        service = new CheckmateService(moveGenerator, simulator, attackDetector, orientation);
        board = new ChessboardStub();
    }

    @Nested
    @DisplayName("when defender is not under attack")
    class NotUnderAttack {

        @Test
        @DisplayName("should not detect the attack if defender is not in check")
        void shouldNotDetectAttackWhenDefenderNotInCheck() {
            Mockito.when(attackDetector.isUnderAttack(board, defender)).thenReturn(false);

            boolean result = service.isCheckmate(board, defender);

            assertThat(result).isFalse();
            Mockito.verifyNoInteractions(moveGenerator, simulator);
        }
    }

    @Nested
    @DisplayName("when defender is under attack")
    class UnderAttack {

        private final ChessboardPosition from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.ONE);
        private final ChessboardPosition escapeSquare = new ChessboardPosition(ChessboardFile.E, ChessboardRank.TWO);

        @BeforeEach
        void setUp() {
            board.placeFriendly(from, defender);
            Mockito.when(attackDetector.isUnderAttack(board, defender)).thenReturn(true);
        }

        @Test
        @DisplayName("should detect checkmate if no legal moves are generated")
        void shouldDetectCheckmateIfNoLegalMovesGenerated() {
            Mockito.when(moveGenerator.generateMovesFrom(board, from, orientation)).thenReturn(List.of());

            boolean result = service.isCheckmate(board, defender);

            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("should detect checkmate if moves exist but none resolve the check")
        void shouldDetectCheckmateIfMovesDoNotResolveCheck() {
            MoveComponent component = new MoveComponent(from, escapeSquare);
            ReversibleMove moveStub = new ReversibleMoveStub(null, component);

            Mockito.when(moveGenerator.generateMovesFrom(board, from, orientation)).thenReturn(List.of(moveStub));
            Mockito.when(simulator.resolvesCheck(board, from, escapeSquare, defender)).thenReturn(false);

            boolean result = service.isCheckmate(board, defender);

            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("should not detect checkmate if a move resolves the check")
        void shouldNotDetectCheckmateIfAnyMoveResolvesCheck() {
            MoveComponent component = new MoveComponent(from, escapeSquare);
            ReversibleMove moveStub = new ReversibleMoveStub(null, component);

            Mockito.when(moveGenerator.generateMovesFrom(board, from, orientation)).thenReturn(List.of(moveStub));
            Mockito.when(simulator.resolvesCheck(board, from, escapeSquare, defender)).thenReturn(true);

            boolean result = service.isCheckmate(board, defender);

            assertThat(result).isFalse();
        }
    }
}