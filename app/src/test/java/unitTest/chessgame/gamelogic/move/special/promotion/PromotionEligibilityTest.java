package unitTest.chessgame.gamelogic.move.special.promotion;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionCandidate;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionEligibility;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import unitTest.chessgame.gamelogic.testdoubles.ChessboardFake;
import unitTest.chessgame.gamelogic.testdoubles.PieceDummy;
import unitTest.chessgame.gamelogic.testdoubles.PieceStub;

@DisplayName("PromotionEligibility")
class PromotionEligibilityTest {

    private PromotionEligibility eligibility;
    private ChessboardFake board;
    private ChessboardOrientation orientation;

    @BeforeEach
    void setUp() {
        eligibility = new PromotionEligibility();
        board = new ChessboardFake();
        orientation = ChessboardOrientation.WHITE_BOTTOM;
    }

    @Nested
    @DisplayName("canExecute")
    class CanExecute {

        @Test
        @DisplayName("should return true for pawn moving forward one step to promotion rank")
        void shouldReturnTrueForValidPromotionMove() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN);
            ChessboardPosition to = new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT);
            ChessPiece pawn = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.PAWN, Set.of(to));
            PromotionCandidate candidate = new PromotionCandidate(from, to, pawn, Optional.empty());

            boolean result = eligibility.canExecute(board, candidate, orientation);

            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("should return false when piece is not a pawn")
        void shouldReturnFalseWhenNotPawn() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN);
            ChessboardPosition to = new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT);
            ChessPiece rook = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.ROOK);
            PromotionCandidate candidate = new PromotionCandidate(from, to, rook, Optional.empty());

            boolean result = eligibility.canExecute(board, candidate, orientation);

            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("should return false not a legal pawn move")
        void shouldReturnFalseWhenNotLegalPawnMove() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SIX);
            ChessboardPosition legalTo = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN);
            ChessboardPosition illegalTo = new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT);
            ChessPiece pawn = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.PAWN, Set.of(legalTo));
            PromotionCandidate candidate = new PromotionCandidate(from, illegalTo, pawn, Optional.empty());

            boolean result = eligibility.canExecute(board, candidate, orientation);

            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("should return false when destination rank is not promotion rank")
        void shouldReturnFalseWhenNotPromotionRank() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SIX);
            ChessboardPosition to = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN);
            ChessPiece pawn = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.PAWN, Set.of(to));
            PromotionCandidate candidate = new PromotionCandidate(from, to, pawn, Optional.empty());

            boolean result = eligibility.canExecute(board, candidate, orientation);

            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("should handle black pawn moving in opposite direction")
        void shouldHandleBlackPawnDirection() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.TWO);
            ChessboardPosition to = new ChessboardPosition(ChessboardFile.E, ChessboardRank.ONE);
            ChessPiece pawn = new PieceStub(ChessPieceColor.BLACK, ChessPieceInfo.PAWN, Set.of(to));
            PromotionCandidate candidate = new PromotionCandidate(from, to, pawn, Optional.empty());

            boolean result = eligibility.canExecute(board, candidate, orientation);

            assertThat(result).isTrue();
        }
    }
}