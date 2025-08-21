package unitTest.chessgame.gamelogic.movement;

import java.util.Set;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.Knight;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.*;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.*;

@DisplayName("Knight Movement")
public class KnightMovementStrategyTest {

    private Chessboard board;
    private Knight knight;
    private ChessboardPosition knightPosition;

    @BeforeEach
    void setUp() {
        board = mock(Chessboard.class);
        knight = new Knight(ChessPieceColor.WHITE);
        knightPosition = new ChessboardPosition(D, FOUR);

        when(board.getPieceAt(knightPosition)).thenReturn(Optional.of(knight));
    }

    @Nested
    @DisplayName("getLegalMoves")
    class GetLegalMoves {

        @Test
        @DisplayName("should return all 8 L-shaped moves when board is empty")
        void shouldReturnAllLShapedMoves() {
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

            ChessboardMockUtils.mockVacantPositions(board, landingPositions);

            Set<ChessboardPosition> legalDestinations = knight.getLegalDestinations(board, knightPosition);

            assertThat(legalDestinations).containsExactlyInAnyOrderElementsOf(landingPositions);
        }

        @Test
        @DisplayName("should exclude squares with friendly pieces")
        void shouldExcludeFriendlyPieces() {
            ChessboardPosition friendPos = new ChessboardPosition(B, THREE);
            ChessboardMockUtils.mockFriendlyPieceAt(board, friendPos, ChessPieceColor.WHITE);

            Set<ChessboardPosition> legalDestinations = knight.getLegalDestinations(board, knightPosition);

            assertThat(legalDestinations).doesNotContain(friendPos);
        }

        @Test
        @DisplayName("should include squares with opponent pieces")
        void shouldIncludeOpponentPieces() {
            ChessboardPosition enemyPos = new ChessboardPosition(F, FIVE);
            ChessboardMockUtils.mockOpponentPieceAt(board, enemyPos, ChessPieceColor.WHITE, ChessPieceColor.BLACK);

            Set<ChessboardPosition> legalDestinations = knight.getLegalDestinations(board, knightPosition);

            assertThat(legalDestinations).contains(enemyPos);
        }
    }
}