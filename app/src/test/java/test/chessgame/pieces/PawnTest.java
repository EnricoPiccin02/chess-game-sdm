package test.chessgame.pieces;

import java.util.List;

import com.sdm.units.chessgame.gamelogic.basics.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.basics.ChessPieceMove;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardRank;
import com.sdm.units.chessgame.pieces.ChessPiece;
import com.sdm.units.chessgame.pieces.Pawn;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

public class PawnTest {

    ChessPiece whitePawn = new Pawn(ChessPieceColor.WHITE);
    ChessPiece blackPawn = new Pawn(ChessPieceColor.BLACK);
    
    @Test
    public void whitePawnInitialMovementFromA2() {
        assertThat(
            whitePawn.getPossibleMoves(new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.A, ChessboardRank.THREE)),
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.A, ChessboardRank.FOUR))
            ).toArray())
        );
    }

    @Test
    public void whitePawnInitialMovementFromB2() {
        assertThat(
            whitePawn.getPossibleMoves(new ChessboardPosition(ChessboardFile.B, ChessboardRank.TWO)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.B, ChessboardRank.THREE)),
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.B, ChessboardRank.FOUR))
            ).toArray())
        );
    }

    @Test
    public void whitePawnInitialMovementFromC2() {
        assertThat(
            whitePawn.getPossibleMoves(new ChessboardPosition(ChessboardFile.C, ChessboardRank.TWO)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.C, ChessboardRank.THREE)),
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.C, ChessboardRank.FOUR))
            ).toArray())
        );
    }

    @Test
    public void whitePawnInitialMovementFromD2() {
        assertThat(
            whitePawn.getPossibleMoves(new ChessboardPosition(ChessboardFile.D, ChessboardRank.TWO)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.D, ChessboardRank.THREE)),
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.D, ChessboardRank.FOUR))
            ).toArray())
        );
    }

    @Test
    public void whitePawnInitialMovementFromE2() {
        assertThat(
            whitePawn.getPossibleMoves(new ChessboardPosition(ChessboardFile.E, ChessboardRank.TWO)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.E, ChessboardRank.THREE)),
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.E, ChessboardRank.FOUR))
            ).toArray())
        );
    }

    @Test
    public void whitePawnInitialMovementFromF2() {
        assertThat(
            whitePawn.getPossibleMoves(new ChessboardPosition(ChessboardFile.F, ChessboardRank.TWO)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.F, ChessboardRank.THREE)),
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.F, ChessboardRank.FOUR))
            ).toArray())
        );
    }

    @Test
    public void whitePawnInitialMovementFromG2() {
        assertThat(
            whitePawn.getPossibleMoves(new ChessboardPosition(ChessboardFile.G, ChessboardRank.TWO)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.G, ChessboardRank.THREE)),
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.G, ChessboardRank.FOUR))
            ).toArray())
        );
    }

    @Test
    public void whitePawnInitialMovementFromH2() {
        assertThat(
            whitePawn.getPossibleMoves(new ChessboardPosition(ChessboardFile.H, ChessboardRank.TWO)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.H, ChessboardRank.THREE)),
                new ChessPieceMove(ChessboardDirection.UP, new ChessboardPosition(ChessboardFile.H, ChessboardRank.FOUR))
            ).toArray())
        );
    }

    @Test
    public void blackPawnInitialMovementFromA7() {
        assertThat(
            blackPawn.getPossibleMoves(new ChessboardPosition(ChessboardFile.A, ChessboardRank.SEVEN)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.A, ChessboardRank.SIX)),
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.A, ChessboardRank.FIVE))
            ).toArray())
        );
    }

    @Test
    public void blackPawnInitialMovementFromB7() {
        assertThat(
            blackPawn.getPossibleMoves(new ChessboardPosition(ChessboardFile.B, ChessboardRank.SEVEN)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.B, ChessboardRank.SIX)),
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.B, ChessboardRank.FIVE))
            ).toArray())
        );
    }

    @Test
    public void blackPawnInitialMovementFromC7() {
        assertThat(
            blackPawn.getPossibleMoves(new ChessboardPosition(ChessboardFile.C, ChessboardRank.SEVEN)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.C, ChessboardRank.SIX)),
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.C, ChessboardRank.FIVE))
            ).toArray())
        );
    }

    @Test
    public void blackPawnInitialMovementFromD7() {
        assertThat(
            blackPawn.getPossibleMoves(new ChessboardPosition(ChessboardFile.D, ChessboardRank.SEVEN)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.D, ChessboardRank.SIX)),
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.D, ChessboardRank.FIVE))
            ).toArray())
        );
    }

    @Test
    public void blackPawnInitialMovementFromE7() {
        assertThat(
            blackPawn.getPossibleMoves(new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.E, ChessboardRank.SIX)),
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.E, ChessboardRank.FIVE))
            ).toArray())
        );
    }

    @Test
    public void blackPawnInitialMovementFromF7() {
        assertThat(
            blackPawn.getPossibleMoves(new ChessboardPosition(ChessboardFile.F, ChessboardRank.SEVEN)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.F, ChessboardRank.SIX)),
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.F, ChessboardRank.FIVE))
            ).toArray())
        );
    }

    @Test
    public void blackPawnInitialMovementFromG7() {
        assertThat(
            blackPawn.getPossibleMoves(new ChessboardPosition(ChessboardFile.G, ChessboardRank.SEVEN)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.G, ChessboardRank.SIX)),
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.G, ChessboardRank.FIVE))
            ).toArray())
        );
    }

    @Test
    public void blackPawnInitialMovementFromH7() {
        assertThat(
            blackPawn.getPossibleMoves(new ChessboardPosition(ChessboardFile.H, ChessboardRank.SEVEN)),
            containsInAnyOrder(List.of(
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.H, ChessboardRank.SIX)),
                new ChessPieceMove(ChessboardDirection.DOWN, new ChessboardPosition(ChessboardFile.H, ChessboardRank.FIVE))
            ).toArray())
        );
    }
}