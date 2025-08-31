package unittest.chessgame.gamelogic.pieces;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.movement.KingMovementStrategy;
import com.sdm.units.chessgame.gamelogic.movement.KnightMovementStrategy;
import com.sdm.units.chessgame.gamelogic.movement.PawnMovementStrategy;
import com.sdm.units.chessgame.gamelogic.movement.SlidingMovementStrategy;
import com.sdm.units.chessgame.gamelogic.pieces.Bishop;
import com.sdm.units.chessgame.gamelogic.pieces.King;
import com.sdm.units.chessgame.gamelogic.pieces.Knight;
import com.sdm.units.chessgame.gamelogic.pieces.Pawn;
import com.sdm.units.chessgame.gamelogic.pieces.Queen;
import com.sdm.units.chessgame.gamelogic.pieces.Rook;

@DisplayName("ChessPieces")
class ChessPiecesTest {

    @Nested
    @DisplayName("Bishop")
    class BishopTest {
        private Bishop bishop;

        @BeforeEach
        void setUp() {
            bishop = new Bishop(ChessPieceColor.WHITE);
        }

        @Test
        @DisplayName("should have the correct info associated")
        void shouldHaveCorrectInfo() {
            assertThat(bishop.pieceInfo()).isEqualTo(ChessPieceInfo.BISHOP);
        }

        @Test
        @DisplayName("should use the correct piece movement strategy")
        void shouldUseCorrectPieceMovementStrategy() {
            assertThat(bishop.getMovementStrategy()).isInstanceOf(SlidingMovementStrategy.class);
        }
    }

    @Nested
    @DisplayName("King")
    class KingTest {
        private King king;

        @BeforeEach
        void setUp() {
            king = new King(ChessPieceColor.BLACK);
        }

        @Test
        @DisplayName("should have the correct info associated")
        void shouldHaveCorrectInfo() {
            assertThat(king.pieceInfo()).isEqualTo(ChessPieceInfo.KING);
        }

        @Test
        @DisplayName("should use the correct piece movement strategy")
        void shouldUseCorrectPieceMovementStrategy() {
            assertThat(king.getMovementStrategy()).isInstanceOf(KingMovementStrategy.class);
        }
    }

    @Nested
    @DisplayName("Knight")
    class KnightTest {
        private Knight knight;

        @BeforeEach
        void setUp() {
            knight = new Knight(ChessPieceColor.WHITE);
        }

        @Test
        @DisplayName("should have the correct info associated")
        void shouldHaveCorrectInfo() {
            assertThat(knight.pieceInfo()).isEqualTo(ChessPieceInfo.KNIGHT);
        }

        @Test
        @DisplayName("should use the correct piece movement strategy")
        void shouldUseCorrectPieceMovementStrategy() {
            assertThat(knight.getMovementStrategy()).isInstanceOf(KnightMovementStrategy.class);
        }
    }

    @Nested
    @DisplayName("Queen")
    class QueenTest {
        private Queen queen;

        @BeforeEach
        void setUp() {
            queen = new Queen(ChessPieceColor.BLACK);
        }

        @Test
        @DisplayName("should have the correct info associated")
        void shouldHaveCorrectInfo() {
            assertThat(queen.pieceInfo()).isEqualTo(ChessPieceInfo.QUEEN);
        }

        @Test
        @DisplayName("should use the correct piece movement strategy")
        void shouldUseCorrectPieceMovementStrategy() {
            assertThat(queen.getMovementStrategy()).isInstanceOf(SlidingMovementStrategy.class);
        }
    }

    @Nested
    @DisplayName("Rook")
    class RookTest {
        private Rook rook;

        @BeforeEach
        void setUp() {
            rook = new Rook(ChessPieceColor.WHITE);
        }

        @Test
        @DisplayName("should have the correct info associated")
        void shouldHaveCorrectInfo() {
            assertThat(rook.pieceInfo()).isEqualTo(ChessPieceInfo.ROOK);
        }

        @Test
        @DisplayName("should use the correct piece movement strategy")
        void shouldUseCorrectPieceMovementStrategy() {
            assertThat(rook.getMovementStrategy()).isInstanceOf(SlidingMovementStrategy.class);
        }
    }

    @Nested
    @DisplayName("Pawn")
    class PawnTest {
        private Pawn pawn;

        @BeforeEach
        void setUp() {
            pawn = new Pawn(ChessPieceColor.WHITE, ChessboardOrientation.WHITE_BOTTOM);
        }

        @Test
        @DisplayName("should have the correct info associated")
        void shouldHaveCorrectInfo() {
            assertThat(pawn.pieceInfo()).isEqualTo(ChessPieceInfo.PAWN);
        }

        @Test
        @DisplayName("should use the correct piece movement strategy")
        void shouldUseCorrectPieceMovementStrategy() {
            assertThat(pawn.getMovementStrategy()).isInstanceOf(PawnMovementStrategy.class);
        }

        @Test
        @DisplayName("should preserve given orientation")
        void shouldPreserveGivenOrientation() {
            PawnMovementStrategy strategy = (PawnMovementStrategy) pawn.getMovementStrategy();
            assertThat(strategy.getOrientation()).isEqualTo(ChessboardOrientation.WHITE_BOTTOM);
        }
    }
}