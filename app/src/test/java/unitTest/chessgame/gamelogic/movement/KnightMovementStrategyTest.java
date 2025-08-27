package unittest.chessgame.gamelogic.movement;

import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.B;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.C;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.D;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.E;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.F;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.FIVE;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.FOUR;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.SIX;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.THREE;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.TWO;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.Knight;

import unittest.chessgame.gamelogic.testdoubles.ChessboardStub;

@DisplayName("Knight Movement")
class KnightMovementStrategyTest {

    private ChessboardStub board;
    private Knight knight;
    private ChessboardPosition knightPosition;

    @BeforeEach
    void setUp() {
        board = new ChessboardStub();
        knight = new Knight(ChessPieceColor.WHITE);
        knightPosition = new ChessboardPosition(D, FOUR);
        board.placePiece(knight, knightPosition);
    }

    @Test
    @DisplayName("should move on all 8 L-shaped landing squares when board is empty")
    void shouldMoveOnAllLShapedLandingSquaresWhenBoardIsEmpty() {
        Set<ChessboardPosition> landingPositions = Set.of(
            new ChessboardPosition(B, THREE),
            new ChessboardPosition(B, FIVE),
            new ChessboardPosition(C, TWO),
            new ChessboardPosition(C, SIX),
            new ChessboardPosition(E, TWO),
            new ChessboardPosition(E, SIX),
            new ChessboardPosition(F, THREE),
            new ChessboardPosition(F, FIVE)
        );

        board.vacant(landingPositions.toArray(ChessboardPosition[]::new));

        Set<ChessboardPosition> moves = knight.getLegalDestinations(board, knightPosition);

        assertThat(moves).containsExactlyInAnyOrderElementsOf(landingPositions);
    }

    @Test
    @DisplayName("should exclude squares occupied by friendly pieces")
    void shouldExcludeSquaresWithFriendlyPieces() {
        ChessboardPosition friendPos = new ChessboardPosition(B, THREE);
        board.placeFriendly(friendPos, ChessPieceColor.WHITE);

        Set<ChessboardPosition> moves = knight.getLegalDestinations(board, knightPosition);

        assertThat(moves).doesNotContain(friendPos);
    }

    @Test
    @DisplayName("should include squares occupied by opponent pieces")
    void shouldIncludeSquaresWithOpponentPieces() {
        ChessboardPosition enemyPos = new ChessboardPosition(F, FIVE);
        board.placeOpponent(enemyPos, ChessPieceColor.BLACK);

        Set<ChessboardPosition> moves = knight.getLegalDestinations(board, knightPosition);

        assertThat(moves).contains(enemyPos);
    }
}