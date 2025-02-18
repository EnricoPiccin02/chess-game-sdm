package test.chessgame.pieces;

import java.util.List;

import com.sdm.units.chessgame.gamelogic.basics.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.basics.ChessPieceMove;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardRank;
import com.sdm.units.chessgame.pieces.ChessPiece;
import com.sdm.units.chessgame.pieces.King;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

public class KingTest {
    
    ChessPiece whiteKing = new King(ChessPieceColor.WHITE);
    ChessPiece blackKing = new King(ChessPieceColor.BLACK);

    @Test
    public void whiteKingInitialMovementFromE1() {
        assertThat(
            whiteKing.getPossibleMoves(new ChessboardPosition(ChessboardFile.E, ChessboardRank.ONE)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.E, ChessboardRank.TWO)),
                new ChessPieceMove(ChessboardDirection.UP_RIGHT, new ChessboardPosition(ChessboardFile.F, ChessboardRank.TWO)),
                new ChessPieceMove(ChessboardDirection.RIGHT, new ChessboardPosition(ChessboardFile.F, ChessboardRank.ONE)),
                new ChessPieceMove(ChessboardDirection.LEFT, new ChessboardPosition(ChessboardFile.D, ChessboardRank.ONE)),
                new ChessPieceMove(ChessboardDirection.UP_LEFT, new ChessboardPosition(ChessboardFile.D, ChessboardRank.TWO))
            ).toArray())
        );
    }

    @Test
    public void blackKingInitialMovementFromE8() {
        assertThat(
            blackKing.getPossibleMoves(new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN)),
                new ChessPieceMove(ChessboardDirection.DOWN_RIGHT, new ChessboardPosition(ChessboardFile.F, ChessboardRank.SEVEN)),
                new ChessPieceMove(ChessboardDirection.RIGHT, new ChessboardPosition(ChessboardFile.F, ChessboardRank.EIGHT)),
                new ChessPieceMove(ChessboardDirection.LEFT, new ChessboardPosition(ChessboardFile.D, ChessboardRank.EIGHT)),
                new ChessPieceMove(ChessboardDirection.DOWN_LEFT, new ChessboardPosition(ChessboardFile.D, ChessboardRank.SEVEN))
            ).toArray())
        );
    }
}
