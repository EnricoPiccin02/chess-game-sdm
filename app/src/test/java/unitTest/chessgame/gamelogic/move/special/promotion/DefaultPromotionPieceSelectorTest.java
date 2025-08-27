package unittest.chessgame.gamelogic.move.special.promotion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.DefaultPromotionPieceSelector;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionPieceSelector;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gamelogic.pieces.Queen;

import unittest.chessgame.gamelogic.testdoubles.PieceDummy;

@DisplayName("DefaultPromotionPieceSelector")
class DefaultPromotionPieceSelectorTest {

    PromotionPieceSelector selector;

    @BeforeEach
    void setUp() {
        selector = new DefaultPromotionPieceSelector();
    }

    @Test
    @DisplayName("should promote a pawn to queen by default")
    void shouldPromoteToQueenByDefault() {
        ChessPiece pawn = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.PAWN);
        ChessPiece promoted = selector.selectPromotionPiece(pawn.pieceColor());
        assertThat(promoted).isInstanceOf(Queen.class);
    }

    @Test
    @DisplayName("should assign to the promoted piece the same color as the promoter")
    void shouldAssignPromotedSameColourOfThePromoter() {
        ChessPiece pawn = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.PAWN);
        ChessPiece promoted = selector.selectPromotionPiece(pawn.pieceColor());
        assertEquals(pawn.pieceColor(), promoted.pieceColor());
    }
}