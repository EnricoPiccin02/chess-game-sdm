package test.chessgame.pieces;

import java.util.List;

import com.sdm.units.chessgame.gamelogic.basics.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.basics.ChessPieceMove;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardRank;
import com.sdm.units.chessgame.pieces.ChessPiece;
import com.sdm.units.chessgame.pieces.Knight;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

public class KnightTest {
    
    ChessPiece whiteKnight = new Knight(ChessPieceColor.WHITE);
    ChessPiece blackKnight = new Knight(ChessPieceColor.BLACK);

    @Test
    public void whiteKnightInitialMovementFromB1() {
        assertThat(
            whiteKnight.getPossibleMoves(new ChessboardPosition(ChessboardFile.B, ChessboardRank.ONE)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.UP_RIGHT, new ChessboardPosition(ChessboardFile.C, ChessboardRank.THREE)),
                new ChessPieceMove(ChessboardDirection.UP_LEFT, new ChessboardPosition(ChessboardFile.A, ChessboardRank.THREE)),
                new ChessPieceMove(ChessboardDirection.RIGHT, new ChessboardPosition(ChessboardFile.D, ChessboardRank.TWO))
            ).toArray())
        );
    }
    
    @Test
    public void blackKnightInitialMovementFromG8() {
        assertThat(
            blackKnight.getPossibleMoves(new ChessboardPosition(ChessboardFile.G, ChessboardRank.EIGHT)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.DOWN_LEFT, new ChessboardPosition(ChessboardFile.F, ChessboardRank.SIX)),
                new ChessPieceMove(ChessboardDirection.DOWN_RIGHT, new ChessboardPosition(ChessboardFile.H, ChessboardRank.SIX)),
                new ChessPieceMove(ChessboardDirection.LEFT, new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN))
            ).toArray())
        );
    }
}
