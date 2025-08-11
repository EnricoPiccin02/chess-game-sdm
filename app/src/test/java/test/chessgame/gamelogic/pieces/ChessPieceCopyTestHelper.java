package test.chessgame.gamelogic.pieces;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.function.Supplier;

import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public final class ChessPieceCopyTestHelper {

    private ChessPieceCopyTestHelper() { }

    public static void assertDeepCopyBehaviorForAllPieces(List<Supplier<ChessPiece>> pieceSuppliers) {
        for (Supplier<ChessPiece> supplier : pieceSuppliers) {
            ChessPiece original = supplier.get();
            ChessPiece copy = original.copy();

            String pieceName = original.getClass().getSimpleName() + " (" + original.pieceColor() + ")";

            // Must be a distinct instance
            assertNotSame(original, copy, pieceName + " copy should be a new instance");

            // Must preserve core identifying data
            assertEquals(original.pieceInfo(), copy.pieceInfo(), pieceName + " copy should have the same pieceInfo");
            assertEquals(original.pieceColor(), copy.pieceColor(), pieceName + " copy should have the same color");

            // Must not share mutation state
            mutatePiece(copy);
            assertNotEquals(original.hasMoved(), copy.hasMoved(), pieceName + " copy mutation should not affect original");

            // Original should be equal to a fresh instance from supplier
            assertEquals(supplier.get(), original, pieceName + " original should be equal to a fresh unmodified piece");

            // Copy should be equal to another fresh copy of original (if original not mutated)
            ChessPiece secondCopy = original.copy();
            assertEquals(copy.pieceInfo(), secondCopy.pieceInfo(), pieceName + " multiple copies should be equivalent in pieceInfo");
            assertEquals(copy.pieceColor(), secondCopy.pieceColor(), pieceName + " multiple copies should be equivalent in color");
        }
    }

    private static void mutatePiece(ChessPiece piece) {
        piece.markAsMoved();
    }
}