package test.chessgame.gamelogic.movement;

import java.util.Set;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.Bishop;
import com.sdm.units.chessgame.gamelogic.pieces.Queen;
import com.sdm.units.chessgame.gamelogic.pieces.Rook;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.*;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.*;

@DisplayName("Sliding Movement")
class SlidingMovementStrategyTest {

    private Chessboard board;
    private ChessboardPosition start;

    @BeforeEach
    void setUp() {
        board = Mockito.mock(Chessboard.class);
        start = new ChessboardPosition(D, FOUR);

        // By default, all positions are vacant (no piece)
        when(board.getPieceAt(any())).thenReturn(Optional.empty());
        when(board.isUnoccupiedSquare(any())).thenReturn(true);
        when(board.isOpponentAt(any(), any())).thenReturn(false);
    }

    @Nested
    @DisplayName("getLegalMoves(board, position)")
    class GetLegalMoves {

        @Test
        @DisplayName("should slide in direction until blocked")
        void shouldSlideUntilBlocked() {
            Set<ChessboardPosition> subsetOfLandingPositions = Set.of(
                new ChessboardPosition(D, FIVE),
                new ChessboardPosition(D, SIX),
                new ChessboardPosition(D, SEVEN),
                new ChessboardPosition(D, EIGHT),
                new ChessboardPosition(E, FOUR),
                new ChessboardPosition(F, FOUR)
            );

            ChessboardMockUtils.mockVacantPositions(board, subsetOfLandingPositions);

            Queen queen = new Queen(ChessPieceColor.WHITE);
            when(board.getPieceAt(start)).thenReturn(Optional.of(queen));

            Set<ChessboardPosition> legalDestinations = queen.getLegalDestinations(board, start);

            assertThat(legalDestinations).containsAll(subsetOfLandingPositions);
        }

        @Test
        @DisplayName("should stop sliding when encountering friendly piece")
        void shouldStopAtFriendlyPiece() {            
            Rook rook = new Rook(ChessPieceColor.WHITE);
            when(board.getPieceAt(start)).thenReturn(Optional.of(rook));
            
            ChessboardPosition beforeBlocker = new ChessboardPosition(D, FIVE);
            ChessboardPosition blocker = new ChessboardPosition(D, SIX);
            ChessboardMockUtils.mockFriendlyPieceAt(board, blocker, ChessPieceColor.WHITE);

            Set<ChessboardPosition> legalDestinations = rook.getLegalDestinations(board, start);

            assertThat(legalDestinations).doesNotContain(blocker);
            assertThat(legalDestinations).contains(beforeBlocker);
        }

        @Test
        @DisplayName("should allow capturing opponent and stop")
        void shouldCaptureOpponentAndStop() {
            Bishop bishop = new Bishop(ChessPieceColor.WHITE);
            when(board.getPieceAt(start)).thenReturn(Optional.of(bishop));

            ChessboardPosition enemy = new ChessboardPosition(F, SIX);
            ChessboardPosition afterEnemy = new ChessboardPosition(G, SEVEN);
            ChessboardMockUtils.mockOpponentPieceAt(board, enemy, ChessPieceColor.WHITE, ChessPieceColor.BLACK);
            
            Set<ChessboardPosition> legalDestinations = bishop.getLegalDestinations(board, start);

            assertThat(legalDestinations).contains(enemy);
            assertThat(legalDestinations).doesNotContain(afterEnemy);
        }
    }
}