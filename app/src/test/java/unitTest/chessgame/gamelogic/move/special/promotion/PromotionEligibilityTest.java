package unittest.chessgame.gamelogic.move.special.promotion;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionCandidate;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionEligibility;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import unittest.chessgame.gamelogic.testdoubles.ChessboardFake;
import unittest.chessgame.gamelogic.testdoubles.PieceDummy;
import unittest.chessgame.gamelogic.testdoubles.PieceStub;

@DisplayName("PromotionEligibility")
class PromotionEligibilityTest {

    private PromotionEligibility eligibility;
    private Chessboard board;
    private ChessboardOrientation orientation;

    @BeforeEach
    void setUp() {
        eligibility = new PromotionEligibility();
        board = new ChessboardFake();
        orientation = ChessboardOrientation.WHITE_BOTTOM;
    }

    @Nested
    @DisplayName("when all conditions are met")
    class WhenAllConditionsMet {

        @Test
        @DisplayName("should recognize valid and eligible promotion")
        void shouldRecognizeValidPromotion() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN);
            ChessboardPosition to = new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT);
            ChessPiece pawn = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.PAWN, Set.of(to));
            PromotionCandidate candidate = new PromotionCandidate(from, to, pawn, Optional.empty());

            boolean result = eligibility.canExecute(board, candidate, orientation);

            assertThat(result).isTrue();
        }
    }

    @Nested
    @DisplayName("when any condition fails")
    class WhenAnyConditionFails {

        @Test
        @DisplayName("should deny when piece is not a pawn")
        void shouldDenyWhenNotPawn() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN);
            ChessboardPosition to = new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT);
            ChessPiece rook = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.ROOK);
            PromotionCandidate candidate = new PromotionCandidate(from, to, rook, Optional.empty());

            boolean result = eligibility.canExecute(board, candidate, orientation);

            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("should deny when not a legal pawn move")
        void shouldDenyWhenNotLegalPawnMove() {
            ChessboardPosition from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SIX);
            ChessboardPosition legalTo = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN);
            ChessboardPosition illegalTo = new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT);
            ChessPiece pawn = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.PAWN, Set.of(legalTo));
            PromotionCandidate candidate = new PromotionCandidate(from, illegalTo, pawn, Optional.empty());

            boolean result = eligibility.canExecute(board, candidate, orientation);

            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("should deny when destination rank is not promotion rank")
        void shouldDenyWhenNotPromotionRank() {
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