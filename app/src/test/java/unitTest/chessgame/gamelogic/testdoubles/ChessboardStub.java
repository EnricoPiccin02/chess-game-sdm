package unittest.chessgame.gamelogic.testdoubles;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public class ChessboardStub extends ChessboardFake {
    
    private final Map<ChessboardPosition, ChessPieceColor> occupiedByColor = new HashMap<>();
    private final Set<ChessboardPosition> vacantSquares = new HashSet<>();

    public ChessboardStub placePiece(ChessPiece piece, ChessboardPosition pos) {
        putPieceAt(pos, piece);
        occupiedByColor.put(pos, piece.pieceColor());
        vacantSquares.remove(pos);
        return this;
    }

    public ChessboardStub vacant(ChessboardPosition... positions) {
        for (ChessboardPosition pos : positions) {
            removePieceAt(pos);
            occupiedByColor.remove(pos);
            vacantSquares.add(pos);
        }
        return this;
    }

    public ChessboardStub placeFriendly(ChessboardPosition pos, ChessPieceColor color) {
        putPieceAt(pos, new PieceDummy(color, ChessPieceInfo.PAWN));
        occupiedByColor.put(pos, color);
        vacantSquares.remove(pos);
        return this;
    }

    public ChessboardStub placeOpponent(ChessboardPosition pos, ChessPieceColor opponentColor) {
        putPieceAt(pos, new PieceDummy(opponentColor, ChessPieceInfo.PAWN));
        occupiedByColor.put(pos, opponentColor);
        vacantSquares.remove(pos);
        return this;
    }

    @Override
    public boolean isUnoccupiedSquare(ChessboardPosition pos) {
        return vacantSquares.contains(pos) && !occupiedByColor.containsKey(pos);
    }

    @Override
    public boolean isOpponentAt(ChessPieceColor player, ChessboardPosition pos) {
        ChessPieceColor occ = occupiedByColor.get(pos);
        return occ != null && occ != player;
    }

    @Override
    public Set<ChessboardPosition> getOccupiedSquaresOf(ChessPieceColor color) {
        return occupiedByColor.entrySet()
            .stream()
            .filter(e -> e.getValue() == color)
            .map(Map.Entry::getKey)
            .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public void removePieceAt(ChessboardPosition position) {
        super.removePieceAt(position);
        occupiedByColor.remove(position);
    }

    @Override
    public void resetBoard() {
        super.resetBoard();
        occupiedByColor.clear();
        vacantSquares.clear();
    }
}