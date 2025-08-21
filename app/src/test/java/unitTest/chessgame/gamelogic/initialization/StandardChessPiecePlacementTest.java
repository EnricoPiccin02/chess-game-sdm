package unitTest.chessgame.gamelogic.initialization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.initialization.StandardChessPiecePlacement;
import com.sdm.units.chessgame.gamelogic.pieces.Bishop;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gamelogic.pieces.King;
import com.sdm.units.chessgame.gamelogic.pieces.Knight;
import com.sdm.units.chessgame.gamelogic.pieces.Pawn;
import com.sdm.units.chessgame.gamelogic.pieces.Queen;
import com.sdm.units.chessgame.gamelogic.pieces.Rook;

@DisplayName("StandardChessPiecePlacement")
class StandardChessPiecePlacementTest {

    private final StandardChessPiecePlacement placement = new StandardChessPiecePlacement();

    @ParameterizedTest
    @EnumSource(ChessPieceColor.class)
    @DisplayName("should place exactly 16 pieces for each color")
    void shouldPlaceSixteenPiecesPerColor(ChessPieceColor color) {
        Map<ChessboardPosition, ChessPiece> pieces = placement.initialize(color, ChessboardOrientation.WHITE_BOTTOM);

        assertEquals(16, pieces.size());
    }

    @ParameterizedTest
    @EnumSource(ChessPieceColor.class)
    @DisplayName("should place correcct number of all major and minor pieces")
    void shouldPlaceCorrectNumberOfAllMajorAndMinorPieces(ChessPieceColor color) {
        Map<ChessboardPosition, ChessPiece> pieces = placement.initialize(color, ChessboardOrientation.WHITE_BOTTOM);

        assertEquals(1, countOf(pieces, King.class));
        assertEquals(1, countOf(pieces, Queen.class));
        assertEquals(2, countOf(pieces, Bishop.class));
        assertEquals(2, countOf(pieces, Knight.class));
        assertEquals(2, countOf(pieces, Rook.class));
        assertEquals(8, countOf(pieces, Pawn.class));
    }

    @ParameterizedTest
    @EnumSource(ChessPieceColor.class)
    @DisplayName("should place pieces on correct orientation-aware ranks")
    void shouldPlacePiecesOnCorrectRanks(ChessPieceColor color) {
        Map<ChessboardPosition, ChessPiece> pieces = placement.initialize(color, ChessboardOrientation.WHITE_BOTTOM);

        ChessboardRank backRank = ChessboardOrientation.WHITE_BOTTOM.getBackRank(color);
        ChessboardRank pawnRank = ChessboardOrientation.WHITE_BOTTOM.getPawnRank(color);

        for (Map.Entry<ChessboardPosition, ChessPiece> entry : pieces.entrySet()) {
            if (entry.getValue() instanceof Pawn) {
                assertEquals(pawnRank, entry.getKey().rank());
            } else {
                assertEquals(backRank, entry.getKey().rank());
            }
        }
    }

    @ParameterizedTest
    @EnumSource(ChessPieceColor.class)
    @DisplayName("should not place pieces on duplicate positions")
    void shouldNotPlacePiecesOnDuplicatePositions(ChessPieceColor color) {
        Map<ChessboardPosition, ChessPiece> pieces = placement.initialize(color, ChessboardOrientation.WHITE_BOTTOM);

        Set<ChessboardPosition> uniquePositions = new HashSet<>(pieces.keySet());

        assertEquals(pieces.size(), uniquePositions.size(), "There are duplicate positions");
    }

    @ParameterizedTest
    @EnumSource(ChessPieceColor.class)
    @DisplayName("should assign correct color to all pieces")
    void shouldAssignCorrectColorToAllPieces(ChessPieceColor color) {
        Map<ChessboardPosition, ChessPiece> pieces = placement.initialize(color, ChessboardOrientation.WHITE_BOTTOM);

        for (ChessPiece piece : pieces.values()) {
            assertEquals(color, piece.pieceColor());
        }
    }

    @ParameterizedTest
    @EnumSource(ChessPieceColor.class)
    @DisplayName("no piece should be placed outside the board")
    void shouldNotPlacePiecesOutsideBoard(ChessPieceColor color) {
        Map<ChessboardPosition, ChessPiece> pieces = placement.initialize(color, ChessboardOrientation.WHITE_BOTTOM);

        for (ChessboardPosition pos : pieces.keySet()) {
            assertNotNull(pos.file());
            assertNotNull(pos.rank());

            assertTrue(pos.file().ordinal() >= 0 && pos.file().ordinal() < 8, () -> "Invalid file: " + pos.file());
            assertTrue(pos.rank().ordinal() >= 0 && pos.rank().ordinal() < 8, () -> "Invalid rank: " + pos.rank());
        }
    }

    private long countOf(Map<ChessboardPosition, ChessPiece> map, Class<? extends ChessPiece> type) {
        return map.values().stream().filter(p -> type.isAssignableFrom(p.getClass())).count();
    }
}