package test.chessgame.gamelogic.move.special;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.api.MoveComponent;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionMove;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gamelogic.pieces.Pawn;
import com.sdm.units.chessgame.gamelogic.pieces.Rook;

import test.chessgame.gamelogic.move.ChessboardSpy;

@DisplayName("PromotionMove")
class PromotionMoveTest {

    private ChessboardSpy boardSpy;
    private ChessboardPosition pawnPosition;
    private ChessboardPosition promotionPosition;
    private ChessPiece pawn;
    private ChessPiece promotedPiece;
    private PromotionMove move;

    @BeforeEach
    void setUp() {
        boardSpy = new ChessboardSpy();
        pawnPosition = new ChessboardPosition(ChessboardFile.A, ChessboardRank.SEVEN);
        promotionPosition = new ChessboardPosition(ChessboardFile.A, ChessboardRank.EIGHT);
        pawn = new Pawn(ChessPieceColor.WHITE, ChessboardOrientation.WHITE_BOTTOM);
        promotedPiece = new Rook(ChessPieceColor.WHITE);

        boardSpy.putPieceAt(pawnPosition, pawn);

        move = new PromotionMove(pawnPosition, promotionPosition, pawn, () -> { return promotedPiece; });
    }

    @Test
    @DisplayName("should promote pawn with chosen piece and mark it as moved")
    void shouldPromotePieceAndMarkAsMoved() {
        move.executeOn(boardSpy);

        assertTrue(promotedPiece.hasMoved());
        assertThat(boardSpy.getPieceAt(promotionPosition)).contains(promotedPiece);
        assertTrue(boardSpy.getPieceAt(pawnPosition).isEmpty());

        assertTrue(boardSpy.wasPutCalledWith(promotionPosition, promotedPiece));
        assertTrue(boardSpy.wasRemoveCalledWith(pawnPosition));
    }

    @Test
    @DisplayName("should restore pawn to original position")
    void shouldRestorePawnToOriginalPosition() {
        move.executeOn(boardSpy);
        move.undoOn(boardSpy);

        assertThat(boardSpy.getPieceAt(pawnPosition)).contains(pawn);
        assertThat(boardSpy.getPieceAt(promotionPosition)).isEmpty();
    }

    @Test
    @DisplayName("should return pawn and promotion positions")
    void shouldReturnPawnAndPromotionPositions() {
        List<MoveComponent> components = move.getMoveComponents();
        assertEquals(1, components.size());
        assertEquals(pawnPosition, components.get(0).from());
        assertEquals(promotionPosition, components.get(0).to());
    }
}