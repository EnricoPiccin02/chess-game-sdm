package unitTest.chessgame.gamelogic.pieces;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPieceSnapshot;
import com.sdm.units.chessgame.gamelogic.pieces.Pawn;

@DisplayName("ChessPieceSnapshot")
class ChessPieceSnapshotTest {

    @Test
    @DisplayName("should store piece and moved state")
    void shouldStorePieceAndMovedState() {
        ChessPiece piece = new Pawn(ChessPieceColor.WHITE, ChessboardOrientation.WHITE_BOTTOM);
        piece.markAsMoved();

        ChessPieceSnapshot snapshot = new ChessPieceSnapshot(piece, piece.hasMoved());

        assertThat(snapshot.getPiece()).isSameAs(piece);
        assertThat(snapshot.wasMoved()).isTrue();
    }
}