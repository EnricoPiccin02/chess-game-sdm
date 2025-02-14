package test.chessgame.pieces;

import java.util.List;

import com.sdm.units.chessgame.gamelogic.basics.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.basics.ChessPieceMove;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardRank;
import com.sdm.units.chessgame.pieces.Bishop;
import com.sdm.units.chessgame.pieces.ChessPiece;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

public class BishopTest {

    ChessPiece whiteBishop = new Bishop(ChessPieceColor.WHITE);
    ChessPiece blackBishop = new Bishop(ChessPieceColor.BLACK);

    @Test
    public void whiteBishopInitialMovementFromC1() {
        assertThat(
            whiteBishop.getPossibleMoves(new ChessboardPosition(ChessboardFile.C, ChessboardRank.ONE)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.UP_RIGHT, new ChessboardPosition(ChessboardFile.D, ChessboardRank.TWO)),
                new ChessPieceMove(ChessboardDirection.UP_RIGHT, new ChessboardPosition(ChessboardFile.E, ChessboardRank.THREE)),
                new ChessPieceMove(ChessboardDirection.UP_RIGHT, new ChessboardPosition(ChessboardFile.F, ChessboardRank.FOUR)),
                new ChessPieceMove(ChessboardDirection.UP_RIGHT, new ChessboardPosition(ChessboardFile.G, ChessboardRank.FIVE)),
                new ChessPieceMove(ChessboardDirection.UP_RIGHT, new ChessboardPosition(ChessboardFile.H, ChessboardRank.SIX)),
                new ChessPieceMove(ChessboardDirection.UP_LEFT, new ChessboardPosition(ChessboardFile.B, ChessboardRank.TWO)),
                new ChessPieceMove(ChessboardDirection.UP_LEFT, new ChessboardPosition(ChessboardFile.A, ChessboardRank.THREE))
            ).toArray())
        );
    }

    @Test
    public void blackBishopInitialMovementFromF8() {
        assertThat(
            blackBishop.getPossibleMoves(new ChessboardPosition(ChessboardFile.F, ChessboardRank.EIGHT)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.DOWN_LEFT, new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN)),
                new ChessPieceMove(ChessboardDirection.DOWN_LEFT, new ChessboardPosition(ChessboardFile.D, ChessboardRank.SIX)),
                new ChessPieceMove(ChessboardDirection.DOWN_LEFT, new ChessboardPosition(ChessboardFile.C, ChessboardRank.FIVE)),
                new ChessPieceMove(ChessboardDirection.DOWN_LEFT, new ChessboardPosition(ChessboardFile.B, ChessboardRank.FOUR)),
                new ChessPieceMove(ChessboardDirection.DOWN_LEFT, new ChessboardPosition(ChessboardFile.A, ChessboardRank.THREE)),
                new ChessPieceMove(ChessboardDirection.DOWN_RIGHT, new ChessboardPosition(ChessboardFile.G, ChessboardRank.SEVEN)),
                new ChessPieceMove(ChessboardDirection.DOWN_RIGHT, new ChessboardPosition(ChessboardFile.H, ChessboardRank.SIX))
            ).toArray())
        );
    }
}
