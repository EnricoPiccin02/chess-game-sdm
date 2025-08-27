package unittest.chessgame.gamelogic.movement;

import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.D;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.E;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.F;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.EIGHT;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.FIVE;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.FOUR;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.SEVEN;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.SIX;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.Bishop;
import com.sdm.units.chessgame.gamelogic.pieces.Queen;
import com.sdm.units.chessgame.gamelogic.pieces.Rook;

import unittest.chessgame.gamelogic.testdoubles.ChessboardStub;

@DisplayName("Sliding Movement")
class SlidingMovementStrategyTest {

    private ChessboardStub board;
    private ChessboardPosition start;

    @BeforeEach
    void setUp() {
        board = new ChessboardStub();
        start = new ChessboardPosition(D, FOUR);
    }

    @Test
    @DisplayName("should slide in direction until blocked")
    void shouldSlideUntilBlocked() {
        Queen queen = new Queen(ChessPieceColor.WHITE);
        board.placePiece(queen, start);

        Set<ChessboardPosition> landingPositions = Set.of(
            new ChessboardPosition(D, FIVE),
            new ChessboardPosition(D, SIX),
            new ChessboardPosition(D, SEVEN),
            new ChessboardPosition(D, EIGHT),
            new ChessboardPosition(E, FOUR),
            new ChessboardPosition(F, FOUR)
        );

        board.vacant(landingPositions.toArray(ChessboardPosition[]::new));

        Set<ChessboardPosition> moves = queen.getLegalDestinations(board, start);

        assertThat(moves).containsExactlyInAnyOrderElementsOf(landingPositions);
    }

    @Test
    @DisplayName("should stop sliding when encountering friendly piece")
    void shouldStopAtFriendlyPiece() {
        Rook rook = new Rook(ChessPieceColor.WHITE);
        board.placePiece(rook, start);

        ChessboardPosition beforeBlocker = new ChessboardPosition(D, FIVE);
        ChessboardPosition blocker = new ChessboardPosition(D, SIX);

        board.vacant(beforeBlocker);
        board.placeFriendly(blocker, ChessPieceColor.WHITE);

        Set<ChessboardPosition> moves = rook.getLegalDestinations(board, start);

        assertThat(moves).containsExactly(beforeBlocker);
    }

    @Test
    @DisplayName("should allow capturing opponent and stop")
    void shouldCaptureOpponentAndStop() {
        Bishop bishop = new Bishop(ChessPieceColor.WHITE);
        board.placePiece(bishop, start);

        ChessboardPosition mid = new ChessboardPosition(E, FIVE);
        ChessboardPosition enemy = new ChessboardPosition(F, SIX);

        board.vacant(mid);
        board.placeOpponent(enemy, ChessPieceColor.BLACK);

        Set<ChessboardPosition> moves = bishop.getLegalDestinations(board, start);

        assertThat(moves).containsExactlyInAnyOrder(mid, enemy);
    }
}