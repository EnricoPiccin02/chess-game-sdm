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
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.Knight;
import com.sdm.units.chessgame.gamelogic.pieces.Pawn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.*;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.*;

@DisplayName("Pawn Movement")
class PawnMovementStrategyTest {

    private Chessboard board;
    private Pawn pawn;
    private ChessboardPosition pawnPosition;

    @BeforeEach
    void setUp() {
        board = Mockito.mock(Chessboard.class);
        pawn = new Pawn(ChessPieceColor.WHITE, ChessboardOrientation.WHITE_BOTTOM);
        pawnPosition = new ChessboardPosition(D, TWO);
        
        when(board.getPieceAt(pawnPosition)).thenReturn(Optional.of(pawn));
    }

    @Nested
    @DisplayName("getLegalMoves(board, position)")
    class GetLegalMoves {

        @Test
        @DisplayName("should move one step forward if square is empty")
        void shouldMoveOneStepForwardIfVacant() {
            ChessboardPosition forwardPosition = new ChessboardPosition(D, THREE);
            when(board.isPositionVacant(forwardPosition)).thenReturn(true);
            when(board.isOpponentAt(any(), any())).thenReturn(false);
            
            List<ChessboardPosition> legalMoves = pawn.getLegalMoves(board, pawnPosition);

            assertThat(legalMoves).contains(forwardPosition);
        }

        @Test
        @DisplayName("should move two steps forward if first move and both squares are empty")
        void shouldMoveTwoStepsForwardIfFirstMove() {
            ChessboardPosition forwardPosition = new ChessboardPosition(D, THREE);
            ChessboardPosition forwardForwardPosition = new ChessboardPosition(D, FOUR);
            when(board.isPositionVacant(forwardPosition)).thenReturn(true);
            when(board.isPositionVacant(forwardForwardPosition)).thenReturn(true);
            when(board.isOpponentAt(any(), any())).thenReturn(false);

            List<ChessboardPosition> legalMoves = pawn.getLegalMoves(board, pawnPosition);

            assertThat(legalMoves).contains(forwardForwardPosition);
        }

        @Test
        @DisplayName("should capture diagonally if opponent present")
        void shouldCaptureDiagonallyIfOpponentPresent() {
            ChessboardPosition target = new ChessboardPosition(E, THREE);
            when(board.getPieceAt(target)).thenReturn(Optional.of(new Knight(ChessPieceColor.BLACK)));
            when(board.isPositionVacant(target)).thenReturn(false);
            when(board.isOpponentAt(ChessPieceColor.WHITE, target)).thenReturn(true);

            List<ChessboardPosition> legalMoves = pawn.getLegalMoves(board, pawnPosition);

            assertThat(legalMoves).contains(target);
        }

        @Test
        @DisplayName("should not capture diagonally if no opponent present")
        void shouldNotCaptureDiagonallyIfEmpty() {
            ChessboardPosition target = new ChessboardPosition(E, THREE);
            when(board.getPieceAt(target)).thenReturn(Optional.empty());
            when(board.isPositionVacant(target)).thenReturn(true);

            List<ChessboardPosition> legalMoves = pawn.getLegalMoves(board, pawnPosition);

            assertThat(legalMoves).doesNotContain(target);
        }
    }
}