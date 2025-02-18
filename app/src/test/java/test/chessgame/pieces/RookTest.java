package test.chessgame.pieces;

import java.util.List;

import com.sdm.units.chessgame.gamelogic.basics.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.basics.ChessPieceMove;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardRank;
import com.sdm.units.chessgame.pieces.ChessPiece;
import com.sdm.units.chessgame.pieces.Rook;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

public class RookTest {
    
    ChessPiece whiteRook = new Rook(ChessPieceColor.WHITE);
    ChessPiece blackRook = new Rook(ChessPieceColor.BLACK);

    @Test
    public void whiteRookInitialMovementFromA1() {
        assertThat(
            whiteRook.getPossibleMoves(new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO)),
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.A, ChessboardRank.THREE)),
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.A, ChessboardRank.FOUR)),
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.A, ChessboardRank.FIVE)),
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.A, ChessboardRank.SIX)),
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.A, ChessboardRank.SEVEN)),
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.A, ChessboardRank.EIGHT)),
                new ChessPieceMove(ChessboardDirection.RIGHT, new ChessboardPosition(ChessboardFile.B, ChessboardRank.ONE)),
                new ChessPieceMove(ChessboardDirection.RIGHT, new ChessboardPosition(ChessboardFile.C, ChessboardRank.ONE)),
                new ChessPieceMove(ChessboardDirection.RIGHT, new ChessboardPosition(ChessboardFile.D, ChessboardRank.ONE)),
                new ChessPieceMove(ChessboardDirection.RIGHT, new ChessboardPosition(ChessboardFile.E, ChessboardRank.ONE)),
                new ChessPieceMove(ChessboardDirection.RIGHT, new ChessboardPosition(ChessboardFile.F, ChessboardRank.ONE)),
                new ChessPieceMove(ChessboardDirection.RIGHT, new ChessboardPosition(ChessboardFile.G, ChessboardRank.ONE)),
                new ChessPieceMove(ChessboardDirection.RIGHT, new ChessboardPosition(ChessboardFile.H, ChessboardRank.ONE))
            ).toArray())
        );
    }
    
    @Test
    public void blackRookInitialMovementFromH7() {
        assertThat(
            blackRook.getPossibleMoves(new ChessboardPosition(ChessboardFile.H, ChessboardRank.EIGHT)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.H, ChessboardRank.SEVEN)),
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.H, ChessboardRank.SIX)),
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.H, ChessboardRank.FIVE)),
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.H, ChessboardRank.FOUR)),
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.H, ChessboardRank.THREE)),
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.H, ChessboardRank.TWO)),
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.H, ChessboardRank.ONE)),
                new ChessPieceMove(ChessboardDirection.LEFT, new ChessboardPosition(ChessboardFile.G, ChessboardRank.EIGHT)),
                new ChessPieceMove(ChessboardDirection.LEFT, new ChessboardPosition(ChessboardFile.F, ChessboardRank.EIGHT)),
                new ChessPieceMove(ChessboardDirection.LEFT, new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT)),
                new ChessPieceMove(ChessboardDirection.LEFT, new ChessboardPosition(ChessboardFile.D, ChessboardRank.EIGHT)),
                new ChessPieceMove(ChessboardDirection.LEFT, new ChessboardPosition(ChessboardFile.C, ChessboardRank.EIGHT)),
                new ChessPieceMove(ChessboardDirection.LEFT, new ChessboardPosition(ChessboardFile.B, ChessboardRank.EIGHT)),
                new ChessPieceMove(ChessboardDirection.LEFT, new ChessboardPosition(ChessboardFile.A, ChessboardRank.EIGHT))
            ).toArray())
        );
    }
}