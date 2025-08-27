package unittest.chessgame.gamelogic.movement;

import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.C;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.D;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.E;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.FIVE;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.FOUR;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.THREE;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.King;

import unittest.chessgame.gamelogic.testdoubles.ChessboardStub;

@DisplayName("King Movement")
class KingMovementStrategyTest {

    private ChessboardStub board;
    private King king;
    private ChessboardPosition kingPosition;

    @BeforeEach
    void setUp() {
        board = new ChessboardStub();
        king = new King(ChessPieceColor.WHITE);
        kingPosition = new ChessboardPosition(D, FOUR);
        board.placePiece(king, kingPosition);
    }

    @Test
    @DisplayName("should move one square in all directions if square is vacant")
    void shouldMoveOneSquareInAllDirectionsIfVacant() {
        Set<ChessboardPosition> landingPositions = Set.of(
            new ChessboardPosition(C, THREE),
            new ChessboardPosition(C, FOUR),
            new ChessboardPosition(C, FIVE),
            new ChessboardPosition(D, THREE),
            new ChessboardPosition(D, FIVE),
            new ChessboardPosition(E, THREE),
            new ChessboardPosition(E, FOUR),
            new ChessboardPosition(E, FIVE)
        );

        board.vacant(landingPositions.toArray(ChessboardPosition[]::new));

        Set<ChessboardPosition> moves = king.getLegalDestinations(board, kingPosition);

        assertThat(moves).containsExactlyInAnyOrderElementsOf(landingPositions);
    }

    @Test
    @DisplayName("should not move to square occupied by friendly piece")
    void shouldNotMoveToSquareWithFriendlyPiece() {
        ChessboardPosition friendPos = new ChessboardPosition(C, THREE);
        board.placeFriendly(friendPos, ChessPieceColor.WHITE);

        Set<ChessboardPosition> moves = king.getLegalDestinations(board, kingPosition);

        assertThat(moves).doesNotContain(friendPos);
    }

    @Test
    @DisplayName("should allow capturing opponent in adjacent square")
    void shouldCaptureAdjacentOpponent() {
        ChessboardPosition enemyPos = new ChessboardPosition(E, FOUR);
        board.placeOpponent(enemyPos, ChessPieceColor.BLACK);

        Set<ChessboardPosition> moves = king.getLegalDestinations(board, kingPosition);

        assertThat(moves).contains(enemyPos);
    }
}