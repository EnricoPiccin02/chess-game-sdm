package unittest.chessgame.gamelogic.movement;

import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.D;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.E;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.FOUR;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.THREE;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.TWO;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.Pawn;

import unittest.chessgame.gamelogic.testdoubles.ChessboardStub;

@DisplayName("Pawn Movement")
class PawnMovementStrategyTest {

    private ChessboardStub board;
    private Pawn pawn;
    private ChessboardPosition pawnPosition;

    @BeforeEach
    void setUp() {
        board = new ChessboardStub();
        pawn = new Pawn(ChessPieceColor.WHITE, ChessboardOrientation.WHITE_BOTTOM);
        pawnPosition = new ChessboardPosition(D, TWO);
        board.placePiece(pawn, pawnPosition);
    }

    @Test
    @DisplayName("should move one step forward if square is vacant")
    void shouldMoveOneStepForwardIfVacant() {
        ChessboardPosition forward = new ChessboardPosition(D, THREE);
        board.vacant(forward);

        Set<ChessboardPosition> moves = pawn.getLegalDestinations(board, pawnPosition);

        assertThat(moves).containsExactly(forward);
    }

    @Test
    @DisplayName("should move two steps forward if first move and both squares are vacant")
    void shouldMoveTwoStepsForwardIfFirstMoveAndSquaresVacant() {
        ChessboardPosition oneStep = new ChessboardPosition(D, THREE);
        ChessboardPosition twoSteps = new ChessboardPosition(D, FOUR);
        board.vacant(oneStep, twoSteps);

        Set<ChessboardPosition> moves = pawn.getLegalDestinations(board, pawnPosition);

        assertThat(moves).containsExactlyInAnyOrder(oneStep, twoSteps);
    }

    @Test
    @DisplayName("should capture diagonally if opponent is present")
    void shouldCaptureDiagonallyIfOpponentPresent() {
        ChessboardPosition target = new ChessboardPosition(E, THREE);
        board.placeOpponent(target, ChessPieceColor.BLACK);

        Set<ChessboardPosition> moves = pawn.getLegalDestinations(board, pawnPosition);

        assertThat(moves).containsExactly(target);
    }

    @Test
    @DisplayName("should not capture diagonally if no opponent is present")
    void shouldNotCaptureDiagonallyIfEmpty() {
        ChessboardPosition target = new ChessboardPosition(E, THREE);
        board.vacant(target);

        Set<ChessboardPosition> moves = pawn.getLegalDestinations(board, pawnPosition);

        assertThat(moves).doesNotContain(target);
    }
}