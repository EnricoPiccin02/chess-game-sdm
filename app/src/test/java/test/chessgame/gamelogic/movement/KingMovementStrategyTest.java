package test.chessgame.gamelogic.movement;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.King;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.*;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.*;

@DisplayName("King Movement")
class KingMovementStrategyTest {

    private Chessboard board;
    private King king;
    private ChessboardPosition kingPosition;

    @BeforeEach
    void setUp() {
        board = Mockito.mock(Chessboard.class);
        king = new King(ChessPieceColor.WHITE);
        kingPosition = new ChessboardPosition(D, FOUR);
        
        when(board.getPieceAt(kingPosition)).thenReturn(Optional.of(king));
    }

    @Nested
    @DisplayName("getLegalMoves(board, position)")
    class GetLegalMoves {

        @Test
        @DisplayName("should move one square in all directions if square is empty")
        void shouldMoveOneSquareInAllDirectionsIfVacant() {
            List<ChessboardPosition> landingPositions = List.of(
                new ChessboardPosition(C, THREE),
                new ChessboardPosition(C, FOUR),
                new ChessboardPosition(C, FIVE),
                new ChessboardPosition(D, THREE),
                new ChessboardPosition(D, FIVE),
                new ChessboardPosition(E, THREE),
                new ChessboardPosition(E, FOUR),
                new ChessboardPosition(E, FIVE)
            );

            ChessboardMockUtils.mockVacantPositions(board, landingPositions);

            List<ChessboardPosition> legalMoves = king.getLegalMoves(board, kingPosition);

            assertThat(legalMoves).containsExactlyInAnyOrderElementsOf(landingPositions);
        }

        @Test
        @DisplayName("should not move to square occupied by friendly piece")
        void shouldNotMoveToSquareWithFriendlyPiece() {
            ChessboardPosition friendPos = new ChessboardPosition(C, THREE);
            ChessboardMockUtils.mockFriendlyPieceAt(board, friendPos, ChessPieceColor.WHITE);

            List<ChessboardPosition> legalMoves = king.getLegalMoves(board, kingPosition);

            assertThat(legalMoves).doesNotContain(friendPos);
        }

        @Test
        @DisplayName("should allow capturing opponent in adjacent square")
        void shouldCaptureAdjacentOpponent() {
            ChessboardPosition enemyPos = new ChessboardPosition(E, FOUR);
            ChessboardMockUtils.mockOpponentPieceAt(board, enemyPos, ChessPieceColor.WHITE, ChessPieceColor.BLACK);
            
            List<ChessboardPosition> legalMoves = king.getLegalMoves(board, kingPosition);

            assertThat(legalMoves).contains(enemyPos);
        }
    }
}