package unittest.chessgame.gamelogic.initialization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.initialization.StandardChessPiecePlacement;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gamelogic.pieces.Pawn;

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
    @DisplayName("should place correct number of pawns")
    void shouldPlaceCorrectNumberOfPawns(ChessPieceColor color) {
        Map<ChessboardPosition, ChessPiece> pieces = placement.initialize(color, ChessboardOrientation.WHITE_BOTTOM);
        assertEquals(8, countOf(pieces, ChessPieceInfo.PAWN));
    }

    @ParameterizedTest
    @EnumSource(ChessPieceColor.class)
    @DisplayName("should place correct number of rooks")
    void shouldPlaceCorrectNumberOfRooks(ChessPieceColor color) {
        Map<ChessboardPosition, ChessPiece> pieces = placement.initialize(color, ChessboardOrientation.WHITE_BOTTOM);
        assertEquals(2, countOf(pieces, ChessPieceInfo.ROOK));
    }

    @ParameterizedTest
    @EnumSource(ChessPieceColor.class)
    @DisplayName("should place correct number of bishops")
    void shouldPlaceCorrectNumberOfBishops(ChessPieceColor color) {
        Map<ChessboardPosition, ChessPiece> pieces = placement.initialize(color, ChessboardOrientation.WHITE_BOTTOM);
        assertEquals(2, countOf(pieces, ChessPieceInfo.BISHOP));
    }

    @ParameterizedTest
    @EnumSource(ChessPieceColor.class)
    @DisplayName("should place correct number of knights")
    void shouldPlaceCorrectNumberOfKnights(ChessPieceColor color) {
        Map<ChessboardPosition, ChessPiece> pieces = placement.initialize(color, ChessboardOrientation.WHITE_BOTTOM);
        assertEquals(2, countOf(pieces, ChessPieceInfo.KNIGHT));
    }

    @ParameterizedTest
    @EnumSource(ChessPieceColor.class)
    @DisplayName("should place correct number of queens")
    void shouldPlaceCorrectNumberOfQueens(ChessPieceColor color) {
        Map<ChessboardPosition, ChessPiece> pieces = placement.initialize(color, ChessboardOrientation.WHITE_BOTTOM);
        assertEquals(1, countOf(pieces, ChessPieceInfo.QUEEN));
    }

    @ParameterizedTest
    @EnumSource(ChessPieceColor.class)
    @DisplayName("should place correct number of kings")
    void shouldPlaceCorrectNumberOfKings(ChessPieceColor color) {
        Map<ChessboardPosition, ChessPiece> pieces = placement.initialize(color, ChessboardOrientation.WHITE_BOTTOM);
        assertEquals(1, countOf(pieces, ChessPieceInfo.KING));
    }

    @ParameterizedTest
    @EnumSource(ChessPieceColor.class)
    @DisplayName("should place pawns on the correct pawn rank")
    void shouldPlacePawnsOnPawnRank(ChessPieceColor color) {
        Map<ChessboardPosition, ChessPiece> pieces = placement.initialize(color, ChessboardOrientation.WHITE_BOTTOM);

        ChessboardRank pawnRank = ChessboardOrientation.WHITE_BOTTOM.getPawnRank(color);

        assertTrue(
            pieces.entrySet().stream()
                .filter(e -> e.getValue() instanceof Pawn)
                .allMatch(e -> e.getKey().rank() == pawnRank)
        );
    }

    @ParameterizedTest
    @EnumSource(ChessPieceColor.class)
    @DisplayName("should place back rank pieces on the correct back rank")
    void shouldPlaceBackRankPiecesOnBackRank(ChessPieceColor color) {
        Map<ChessboardPosition, ChessPiece> pieces = placement.initialize(color, ChessboardOrientation.WHITE_BOTTOM);

        ChessboardRank backRank = ChessboardOrientation.WHITE_BOTTOM.getBackRank(color);

        assertTrue(
            pieces.entrySet().stream()
                .filter(e -> !(e.getValue() instanceof Pawn))
                .allMatch(e -> e.getKey().rank() == backRank)
        );
    }

    @ParameterizedTest
    @EnumSource(ChessPieceColor.class)
    @DisplayName("should not place pieces on duplicate positions")
    void shouldNotPlacePiecesOnDuplicatePositions(ChessPieceColor color) {
        Map<ChessboardPosition, ChessPiece> pieces = placement.initialize(color, ChessboardOrientation.WHITE_BOTTOM);

        Set<ChessboardPosition> uniquePositions = new HashSet<>(pieces.keySet());

        assertEquals(pieces.size(), uniquePositions.size());
    }

    @ParameterizedTest
    @EnumSource(ChessPieceColor.class)
    @DisplayName("should assign correct color to all pieces")
    void shouldAssignCorrectColorToAllPieces(ChessPieceColor color) {
        Map<ChessboardPosition, ChessPiece> pieces = placement.initialize(color, ChessboardOrientation.WHITE_BOTTOM);

        assertTrue(pieces.values().stream().allMatch(p -> p.pieceColor() == color));
    }

    @ParameterizedTest
    @EnumSource(ChessPieceColor.class)
    @DisplayName("no piece should be placed outside the board")
    void shouldNotPlacePiecesOutsideBoard(ChessPieceColor color) {
        Map<ChessboardPosition, ChessPiece> pieces = placement.initialize(color, ChessboardOrientation.WHITE_BOTTOM);

        assertTrue(
            pieces.keySet().stream().allMatch(pos -> pos.file() != null && pos.rank() != null),
            () -> "Some pieces are outside the board for " + color
        );
    }

    private long countOf(Map<ChessboardPosition, ChessPiece> map, ChessPieceInfo type) {
        return map.values().stream().filter(p -> p.pieceInfo() == type).count();
    }
}