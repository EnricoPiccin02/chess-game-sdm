package unittest.chessgame.gamelogic.move.special.promotion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.DefaultPromotionPieceSelector;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionPieceSelector;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gamelogic.pieces.Queen;

@DisplayName("DefaultPromotionPieceSelector")
class DefaultPromotionPieceSelectorTest {

    PromotionPieceSelector selector;

    @BeforeEach
    void setUp() {
        selector = new DefaultPromotionPieceSelector();
    }

    @Test
    @DisplayName("should return queen by default")
    void shouldReturnQueenByDefault() {
        ChessPiece queen = new Queen(ChessPieceColor.WHITE);
        ChessPiece promoted = selector.selectPromotionPiece(queen.pieceColor());
        assertThat(promoted).isInstanceOf(Queen.class);
        assertEquals(queen.pieceColor(), promoted.pieceColor());
    }
}