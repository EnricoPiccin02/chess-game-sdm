package unittest.chessgame.gamelogic.board.evaluation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sdm.units.chessgame.gamelogic.board.evaluation.AttackDetectorService;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.core.MoveValidator;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;

import unittest.chessgame.gamelogic.testdoubles.ChessboardStub;
import unittest.chessgame.gamelogic.testdoubles.PieceDummy;

@DisplayName("AttackDetectorService")
class AttackDetectorServiceTest {

    private MoveValidator moveValidator;
    private ChessboardOrientation orientation;
    private AttackDetectorService detector;

    @BeforeEach
    void setUp() {
        moveValidator = Mockito.mock(MoveValidator.class);
        orientation = ChessboardOrientation.WHITE_BOTTOM;
        detector = new AttackDetectorService(moveValidator, orientation);
    }

    @Nested
    @DisplayName("when king is present")
    class KingPresent {

        @Test
        @DisplayName("should detect the attack if at least one opponent piece can attack the king")
        void shouldDetectAttackIfOpponentCanAttackKing() {
            ChessboardStub board = new ChessboardStub();
            ChessboardPosition kingPos = new ChessboardPosition(ChessboardFile.E, ChessboardRank.ONE);
            ChessboardPosition attackerPos = new ChessboardPosition(ChessboardFile.F, ChessboardRank.TWO);

            board.placePiece(new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.KING), kingPos);
            board.placeOpponent(attackerPos, ChessPieceColor.BLACK);

            Mockito.when(moveValidator.validateAndCreate(board, attackerPos, kingPos, orientation))
                .thenReturn(Optional.of(Mockito.mock(ReversibleMove.class)));

            boolean underAttack = detector.isUnderAttack(board, ChessPieceColor.WHITE);

            assertThat(underAttack).isTrue();
        }

        @Test
        @DisplayName("should not detect the attack if no opponent piece can attack the king")
        void shouldNotDetectAttackIfNoOpponentCanAttackKing() {
            ChessboardStub board = new ChessboardStub();
            ChessboardPosition kingPos = new ChessboardPosition(ChessboardFile.E, ChessboardRank.ONE);
            ChessboardPosition attackerPos = new ChessboardPosition(ChessboardFile.E, ChessboardRank.TWO);
            
            board.placePiece(new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.KING), kingPos);
            board.placeOpponent(attackerPos, ChessPieceColor.BLACK);

            Mockito.when(moveValidator.validateAndCreate(board, attackerPos, kingPos, orientation))
                .thenReturn(Optional.empty());

            boolean underAttack = detector.isUnderAttack(board, ChessPieceColor.WHITE);

            assertThat(underAttack).isFalse();
        }
    }

    @Nested
    @DisplayName("when no king is present")
    class NoKingPresent {

        @Test
        @DisplayName("should not detect the attack if no king is present")
        void shouldNotDetectAttackIfNoKingPresent() {
            ChessboardStub board = new ChessboardStub();
            ChessboardPosition attackerPos = new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT);
            board.placeOpponent(attackerPos, ChessPieceColor.BLACK);

            Mockito.when(moveValidator.validateAndCreate(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(Optional.of(Mockito.mock(ReversibleMove.class)));

            boolean underAttack = detector.isUnderAttack(board, ChessPieceColor.WHITE);

            assertThat(underAttack).isFalse();
        }
    }
}