package test.chessgame.gamelogic.move.special;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.api.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionMove;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionPieceSelector;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionRule;

import test.chessgame.gamelogic.ChessPieceStub;
import test.chessgame.gamelogic.move.ChessboardFake;

@DisplayName("PromotionRule")
class PromotionRuleTest {

    private ChessboardFake board;
    private ChessboardOrientation orientation;
    private ChessboardPosition pawnPosition;
    private ChessPieceStub pawn;
    private PromotionPieceSelector pieceSelector;
    private PromotionRule rule;

    @BeforeEach
    void setUp() {
        board = new ChessboardFake();
        orientation = ChessboardOrientation.WHITE_BOTTOM;
        pawnPosition = new ChessboardPosition(ChessboardFile.A, ChessboardRank.SEVEN);
        pawn = new ChessPieceStub(ChessPieceColor.WHITE, ChessPieceInfo.PAWN);
        board.putPieceAt(pawnPosition, pawn);

        pieceSelector = mock(PromotionPieceSelector.class);
        rule = new PromotionRule(pieceSelector);
    }

    @Test
    @DisplayName("should generate promotion moves only for pawns at promotion rank")
    void shouldGeneratePromotionMovesForPawnAtPromotionRank() {
        ChessboardPosition promotionPos = new ChessboardPosition(ChessboardFile.A, ChessboardRank.EIGHT);
        ChessboardPosition nonPromotionPos = new ChessboardPosition(ChessboardFile.B, ChessboardRank.SEVEN);
        pawn.setLegalMoves(List.of(promotionPos, nonPromotionPos));

        List<ReversibleMove> moves = rule.generateMovesFrom(board, pawnPosition, orientation);

        assertThat(moves).hasSize(1);
        assertThat(moves.get(0)).isInstanceOf(PromotionMove.class);
    }

    @Test
    @DisplayName("should return empty moves list if piece is not a pawn")
    void shouldReturnEmptyIfNotPawn() {
        ChessPieceStub rook = new ChessPieceStub(ChessPieceColor.WHITE, ChessPieceInfo.ROOK);
        board.putPieceAt(pawnPosition, rook);

        List<ReversibleMove> moves = rule.generateMovesFrom(board, pawnPosition, orientation);

        assertThat(moves).isEmpty();
    }

    @Test
    @DisplayName("should return empty moves if no piece at position")
    void shouldReturnEmptyIfNoPiece() {
        ChessboardPosition emptyPos = new ChessboardPosition(ChessboardFile.C, ChessboardRank.TWO);

        List<ReversibleMove> moves = rule.generateMovesFrom(board, emptyPos, orientation);

        assertThat(moves).isEmpty();
    }

    @Test
    @DisplayName("should validate and create promotion move for single step pawn to promotion rank")
    void shouldValidateAndCreatePromotionMove() {
        ChessboardPosition promotionPos = new ChessboardPosition(ChessboardFile.A, ChessboardRank.EIGHT);
        pawn.setLegalMoves(List.of(promotionPos));

        Optional<ReversibleMove> optMove = rule.validateAndCreate(board, pawnPosition, promotionPos, orientation);

        assertThat(optMove).isPresent();
        assertThat(optMove.get()).isInstanceOf(PromotionMove.class);
    }

    @Test
    @DisplayName("should return empty if validating move from non-pawn piece")
    void shouldReturnEmptyWhenValidateFromNonPawn() {
        ChessPieceStub rook = new ChessPieceStub(ChessPieceColor.WHITE, ChessPieceInfo.ROOK);
        board.putPieceAt(pawnPosition, rook);

        ChessboardPosition to = new ChessboardPosition(ChessboardFile.A, ChessboardRank.EIGHT);

        Optional<ReversibleMove> optMove = rule.validateAndCreate(board, pawnPosition, to, orientation);

        assertThat(optMove).isEmpty();
    }

    @Test
    @DisplayName("should return empty if move is not a single step forward")
    void shouldReturnEmptyIfNotSingleStepForward() {
        ChessboardPosition to = new ChessboardPosition(ChessboardFile.A, ChessboardRank.SIX); // two steps forward
        pawn.setLegalMoves(List.of(to));

        Optional<ReversibleMove> optMove = rule.validateAndCreate(board, pawnPosition, to, orientation);

        assertThat(optMove).isEmpty();
    }

    @Test
    @DisplayName("should return empty if destination is not promotion rank")
    void shouldReturnEmptyIfNotPromotionRank() {
        ChessboardPosition to = new ChessboardPosition(ChessboardFile.A, ChessboardRank.SEVEN); // not promotion rank
        pawn.setLegalMoves(List.of(to));

        Optional<ReversibleMove> optMove = rule.validateAndCreate(board, pawnPosition, to, orientation);

        assertThat(optMove).isEmpty();
    }

    @Test
    @DisplayName("should return priority 0")
    void shouldReturnCorrectPriority() {
        assertThat(rule.getPriority()).isEqualTo(0);
    }
}