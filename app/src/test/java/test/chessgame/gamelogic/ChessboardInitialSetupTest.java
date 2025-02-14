package test.chessgame.gamelogic;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sdm.units.chessgame.gamelogic.basics.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.initialization.BishopPositionInitializer;
import com.sdm.units.chessgame.gamelogic.initialization.KingPositionInitializer;
import com.sdm.units.chessgame.gamelogic.initialization.KnightPositionInitializer;
import com.sdm.units.chessgame.gamelogic.initialization.PawnPositionInitializer;
import com.sdm.units.chessgame.gamelogic.initialization.QueenPositionInitializer;
import com.sdm.units.chessgame.gamelogic.initialization.RookPositionInitializer;
import com.sdm.units.chessgame.pieces.Bishop;
import com.sdm.units.chessgame.pieces.King;
import com.sdm.units.chessgame.pieces.Knight;
import com.sdm.units.chessgame.pieces.Pawn;
import com.sdm.units.chessgame.pieces.Queen;
import com.sdm.units.chessgame.pieces.Rook;

public class ChessboardInitialSetupTest {

    @Test
    public void isExpectedWhitePawnStartingPosition() {
        assertEquals(
            new PawnPositionInitializer().initializeFor(ChessPieceColor.WHITE),
            Map.ofEntries(
                Map.entry(new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO), Optional.of(new Pawn(ChessPieceColor.WHITE))),
                Map.entry(new ChessboardPosition(ChessboardFile.B, ChessboardRank.TWO), Optional.of(new Pawn(ChessPieceColor.WHITE))),
                Map.entry(new ChessboardPosition(ChessboardFile.C, ChessboardRank.TWO), Optional.of(new Pawn(ChessPieceColor.WHITE))),
                Map.entry(new ChessboardPosition(ChessboardFile.D, ChessboardRank.TWO), Optional.of(new Pawn(ChessPieceColor.WHITE))),
                Map.entry(new ChessboardPosition(ChessboardFile.E, ChessboardRank.TWO), Optional.of(new Pawn(ChessPieceColor.WHITE))),
                Map.entry(new ChessboardPosition(ChessboardFile.F, ChessboardRank.TWO), Optional.of(new Pawn(ChessPieceColor.WHITE))),
                Map.entry(new ChessboardPosition(ChessboardFile.G, ChessboardRank.TWO), Optional.of(new Pawn(ChessPieceColor.WHITE))),
                Map.entry(new ChessboardPosition(ChessboardFile.H, ChessboardRank.TWO), Optional.of(new Pawn(ChessPieceColor.WHITE)))
            )
        );
    }

    @Test
    public void isExpectedWhiteRookStartingPosition() {
        assertEquals(
            new RookPositionInitializer().initializeFor(ChessPieceColor.WHITE),
            Map.ofEntries(
                Map.entry(new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE), Optional.of(new Rook(ChessPieceColor.WHITE))),
                Map.entry(new ChessboardPosition(ChessboardFile.H, ChessboardRank.ONE), Optional.of(new Rook(ChessPieceColor.WHITE)))
            )
        );
    }

    @Test
    public void isExpectedWhiteKnightStartingPosition() {
        assertEquals(
            new KnightPositionInitializer().initializeFor(ChessPieceColor.WHITE),
            Map.ofEntries(
                Map.entry(new ChessboardPosition(ChessboardFile.B, ChessboardRank.ONE), Optional.of(new Knight(ChessPieceColor.WHITE))),
                Map.entry(new ChessboardPosition(ChessboardFile.G, ChessboardRank.ONE), Optional.of(new Knight(ChessPieceColor.WHITE)))
            )
        );
    }

    @Test
    public void isExpectedWhiteBishopStartingPosition() {
        assertEquals(
            new BishopPositionInitializer().initializeFor(ChessPieceColor.WHITE),
            Map.ofEntries(
                Map.entry(new ChessboardPosition(ChessboardFile.C, ChessboardRank.ONE), Optional.of(new Bishop(ChessPieceColor.WHITE))),
                Map.entry(new ChessboardPosition(ChessboardFile.F, ChessboardRank.ONE), Optional.of(new Bishop(ChessPieceColor.WHITE)))
            )
        );
    }

    @Test
    public void isExpectedWhiteQueenStartingPosition() {
        assertEquals(
            new QueenPositionInitializer().initializeFor(ChessPieceColor.WHITE),
            Map.ofEntries(
                Map.entry(new ChessboardPosition(ChessboardFile.D, ChessboardRank.ONE), Optional.of(new Queen(ChessPieceColor.WHITE)))
            )
        );
    }

    @Test
    public void isExpectedWhiteKingStartingPosition() {
        assertEquals(
            new KingPositionInitializer().initializeFor(ChessPieceColor.WHITE),
            Map.ofEntries(
                Map.entry(new ChessboardPosition(ChessboardFile.E, ChessboardRank.ONE), Optional.of(new King(ChessPieceColor.WHITE)))
            )
        );
    }

    @Test
    public void isExpectedBlackPawnStartingPosition() {
        assertEquals(
            new PawnPositionInitializer().initializeFor(ChessPieceColor.BLACK),
            Map.ofEntries(
                Map.entry(new ChessboardPosition(ChessboardFile.A, ChessboardRank.SEVEN), Optional.of(new Pawn(ChessPieceColor.BLACK))),
                Map.entry(new ChessboardPosition(ChessboardFile.B, ChessboardRank.SEVEN), Optional.of(new Pawn(ChessPieceColor.BLACK))),
                Map.entry(new ChessboardPosition(ChessboardFile.C, ChessboardRank.SEVEN), Optional.of(new Pawn(ChessPieceColor.BLACK))),
                Map.entry(new ChessboardPosition(ChessboardFile.D, ChessboardRank.SEVEN), Optional.of(new Pawn(ChessPieceColor.BLACK))),
                Map.entry(new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN), Optional.of(new Pawn(ChessPieceColor.BLACK))),
                Map.entry(new ChessboardPosition(ChessboardFile.F, ChessboardRank.SEVEN), Optional.of(new Pawn(ChessPieceColor.BLACK))),
                Map.entry(new ChessboardPosition(ChessboardFile.G, ChessboardRank.SEVEN), Optional.of(new Pawn(ChessPieceColor.BLACK))),
                Map.entry(new ChessboardPosition(ChessboardFile.H, ChessboardRank.SEVEN), Optional.of(new Pawn(ChessPieceColor.BLACK)))
            )
        );
    }

    @Test
    public void isExpectedBlackRookStartingPosition() {
        assertEquals(
            new RookPositionInitializer().initializeFor(ChessPieceColor.BLACK),
            Map.ofEntries(
                Map.entry(new ChessboardPosition(ChessboardFile.A, ChessboardRank.EIGHT), Optional.of(new Rook(ChessPieceColor.BLACK))),
                Map.entry(new ChessboardPosition(ChessboardFile.H, ChessboardRank.EIGHT), Optional.of(new Rook(ChessPieceColor.BLACK)))
            )
        );
    }

    @Test
    public void isExpectedBlackKnightStartingPosition() {
        assertEquals(
            new KnightPositionInitializer().initializeFor(ChessPieceColor.BLACK),
            Map.ofEntries(
                Map.entry(new ChessboardPosition(ChessboardFile.B, ChessboardRank.EIGHT), Optional.of(new Knight(ChessPieceColor.BLACK))),
                Map.entry(new ChessboardPosition(ChessboardFile.G, ChessboardRank.EIGHT), Optional.of(new Knight(ChessPieceColor.BLACK)))
            )
        );
    }

    @Test
    public void isExpectedBlackBishopStartingPosition() {
        assertEquals(
            new BishopPositionInitializer().initializeFor(ChessPieceColor.BLACK),
            Map.ofEntries(
                Map.entry(new ChessboardPosition(ChessboardFile.C, ChessboardRank.EIGHT), Optional.of(new Bishop(ChessPieceColor.BLACK))),
                Map.entry(new ChessboardPosition(ChessboardFile.F, ChessboardRank.EIGHT), Optional.of(new Bishop(ChessPieceColor.BLACK)))
            )
        );
    }

    @Test
    public void isExpectedBlackQueenStartingPosition() {
        assertEquals(
            new QueenPositionInitializer().initializeFor(ChessPieceColor.BLACK),
            Map.ofEntries(
                Map.entry(new ChessboardPosition(ChessboardFile.D, ChessboardRank.EIGHT), Optional.of(new Queen(ChessPieceColor.BLACK)))
            )
        );
    }

    @Test
    public void isExpectedBlackKingStartingPosition() {
        assertEquals(
            new KingPositionInitializer().initializeFor(ChessPieceColor.BLACK),
            Map.ofEntries(
                Map.entry(new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT), Optional.of(new King(ChessPieceColor.BLACK)))
            )
        );
    }
}