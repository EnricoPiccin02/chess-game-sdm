package test.chessgame.pieces;

import java.util.List;

import com.sdm.units.chessgame.gamelogic.basics.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.basics.ChessPieceMove;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardRank;
import com.sdm.units.chessgame.pieces.ChessPiece;
import com.sdm.units.chessgame.pieces.Queen;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

public class QueenTest {
    
    ChessPiece whiteQueen = new Queen(ChessPieceColor.WHITE);
    ChessPiece blackQueen = new Queen(ChessPieceColor.BLACK);

    @Test
    public void whiteQueenInitialMovementFromD1() {
        assertThat(
            whiteQueen.getPossibleMoves(new ChessboardPosition(ChessboardFile.D, ChessboardRank.ONE)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.UP_RIGHT, new ChessboardPosition(ChessboardFile.E, ChessboardRank.TWO)),
                new ChessPieceMove(ChessboardDirection.UP_RIGHT, new ChessboardPosition(ChessboardFile.F, ChessboardRank.THREE)),
                new ChessPieceMove(ChessboardDirection.UP_RIGHT, new ChessboardPosition(ChessboardFile.G, ChessboardRank.FOUR)),
                new ChessPieceMove(ChessboardDirection.UP_RIGHT, new ChessboardPosition(ChessboardFile.H, ChessboardRank.FIVE)),
                new ChessPieceMove(ChessboardDirection.UP_LEFT, new ChessboardPosition(ChessboardFile.C, ChessboardRank.TWO)),
                new ChessPieceMove(ChessboardDirection.UP_LEFT, new ChessboardPosition(ChessboardFile.B, ChessboardRank.THREE)),
                new ChessPieceMove(ChessboardDirection.UP_LEFT, new ChessboardPosition(ChessboardFile.A, ChessboardRank.FOUR)),
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.D, ChessboardRank.TWO)),
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.D, ChessboardRank.THREE)),
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.D, ChessboardRank.FOUR)),
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.D, ChessboardRank.FIVE)),
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.D, ChessboardRank.SIX)),
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.D, ChessboardRank.SEVEN)),
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.D, ChessboardRank.EIGHT)),
                new ChessPieceMove(ChessboardDirection.RIGHT, new ChessboardPosition(ChessboardFile.E, ChessboardRank.ONE)),
                new ChessPieceMove(ChessboardDirection.RIGHT, new ChessboardPosition(ChessboardFile.F, ChessboardRank.ONE)),
                new ChessPieceMove(ChessboardDirection.RIGHT, new ChessboardPosition(ChessboardFile.G, ChessboardRank.ONE)),
                new ChessPieceMove(ChessboardDirection.RIGHT, new ChessboardPosition(ChessboardFile.H, ChessboardRank.ONE)),
                new ChessPieceMove(ChessboardDirection.LEFT, new ChessboardPosition(ChessboardFile.C, ChessboardRank.ONE)),
                new ChessPieceMove(ChessboardDirection.LEFT, new ChessboardPosition(ChessboardFile.B, ChessboardRank.ONE)),
                new ChessPieceMove(ChessboardDirection.LEFT, new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE))
            ).toArray())
        );
    }

    @Test
    public void blackQueenInitialMovementFromD8() {
        assertThat(
            blackQueen.getPossibleMoves(new ChessboardPosition(ChessboardFile.D, ChessboardRank.EIGHT)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.DOWN_RIGHT, new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN)),
                new ChessPieceMove(ChessboardDirection.DOWN_RIGHT, new ChessboardPosition(ChessboardFile.F, ChessboardRank.SIX)),
                new ChessPieceMove(ChessboardDirection.DOWN_RIGHT, new ChessboardPosition(ChessboardFile.G, ChessboardRank.FIVE)),
                new ChessPieceMove(ChessboardDirection.DOWN_RIGHT, new ChessboardPosition(ChessboardFile.H, ChessboardRank.FOUR)),
                new ChessPieceMove(ChessboardDirection.DOWN_LEFT, new ChessboardPosition(ChessboardFile.C, ChessboardRank.SEVEN)),
                new ChessPieceMove(ChessboardDirection.DOWN_LEFT, new ChessboardPosition(ChessboardFile.B, ChessboardRank.SIX)),
                new ChessPieceMove(ChessboardDirection.DOWN_LEFT, new ChessboardPosition(ChessboardFile.A, ChessboardRank.FIVE)),
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.D, ChessboardRank.SEVEN)),
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.D, ChessboardRank.SIX)),
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.D, ChessboardRank.FIVE)),
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.D, ChessboardRank.FOUR)),
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.D, ChessboardRank.THREE)),
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.D, ChessboardRank.TWO)),
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.D, ChessboardRank.ONE)),
                new ChessPieceMove(ChessboardDirection.RIGHT, new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT)),
                new ChessPieceMove(ChessboardDirection.RIGHT, new ChessboardPosition(ChessboardFile.F, ChessboardRank.EIGHT)),
                new ChessPieceMove(ChessboardDirection.RIGHT, new ChessboardPosition(ChessboardFile.G, ChessboardRank.EIGHT)),
                new ChessPieceMove(ChessboardDirection.RIGHT, new ChessboardPosition(ChessboardFile.H, ChessboardRank.EIGHT)),
                new ChessPieceMove(ChessboardDirection.LEFT, new ChessboardPosition(ChessboardFile.C, ChessboardRank.EIGHT)),
                new ChessPieceMove(ChessboardDirection.LEFT, new ChessboardPosition(ChessboardFile.B, ChessboardRank.EIGHT)),
                new ChessPieceMove(ChessboardDirection.LEFT, new ChessboardPosition(ChessboardFile.A, ChessboardRank.EIGHT))
            ).toArray())
        );
    }
}
