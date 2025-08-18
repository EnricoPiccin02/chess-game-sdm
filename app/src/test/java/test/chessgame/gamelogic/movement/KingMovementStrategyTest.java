package test.chessgame.gamelogic.movement;

import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.C;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.D;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.E;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.FIVE;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.FOUR;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.THREE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.King;

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

            ChessboardMockUtils.mockVacantPositions(board, landingPositions);

            Set<ChessboardPosition> legalDestinations = king.getLegalDestinations(board, kingPosition);

            assertThat(legalDestinations).containsExactlyInAnyOrderElementsOf(landingPositions);
        }

        @Test
        @DisplayName("should not move to square occupied by friendly piece")
        void shouldNotMoveToSquareWithFriendlyPiece() {
            ChessboardPosition friendPos = new ChessboardPosition(C, THREE);
            ChessboardMockUtils.mockFriendlyPieceAt(board, friendPos, ChessPieceColor.WHITE);

            Set<ChessboardPosition> legalDestinations = king.getLegalDestinations(board, kingPosition);

            assertThat(legalDestinations).doesNotContain(friendPos);
        }

        @Test
        @DisplayName("should allow capturing opponent in adjacent square")
        void shouldCaptureAdjacentOpponent() {
            ChessboardPosition enemyPos = new ChessboardPosition(E, FOUR);
            ChessboardMockUtils.mockOpponentPieceAt(board, enemyPos, ChessPieceColor.WHITE, ChessPieceColor.BLACK);
            
            Set<ChessboardPosition> legalDestinations = king.getLegalDestinations(board, kingPosition);

            assertThat(legalDestinations).contains(enemyPos);
        }
    }
}