package com.sdm.units.chessgame.gamelogic.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.initialization.ChessboardSetup;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public class Chessboard {
    
    private final ChessboardOrientation orientation;
    private final ChessboardSetup setup;
    private final Map<ChessboardPosition, ChessPiece> board;

    public Chessboard(ChessboardOrientation orientation, ChessboardSetup setup) {
        this.orientation = orientation;
        this.setup = setup;
        this.board = new HashMap<>(setup.generate(orientation));
    }

    private Chessboard(Chessboard other) {
        this.orientation = other.orientation;
        this.setup = other.setup;
        this.board = other.board.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> e.getValue().copy()
            ));
    }

    public Chessboard deepCopy() {
        return new Chessboard(this);
    }

    public void resetBoard() {
        board.clear();
        board.putAll(setup.generate(orientation));
    }

    public ChessboardOrientation getOrientation() {
        return orientation;
    }

    public List<ChessboardPosition> getOccupiedSquaresOf(ChessPieceColor color) {
        return board.entrySet().stream()
            .filter(e -> e.getValue() != null && e.getValue().pieceColor().equals(color))
            .map(Map.Entry::getKey)
            .toList();
    }

    public boolean isUnoccupiedSquare(ChessboardPosition pos) {
        return !board.containsKey(pos);
    }

    public Optional<ChessPiece> getPieceAt(ChessboardPosition position) {
        return Optional.ofNullable(board.get(position));
    }

    public boolean isOpponentAt(ChessPieceColor player, ChessboardPosition pos) {
        return getPieceAt(pos)
            .map(ChessPiece::pieceColor)
            .map(color -> player.opponent().equals(color))
            .orElse(false);
    }

    public OptionalInt getPieceValueAt(ChessboardPosition position) {
        return getPieceAt(position)
            .map(ChessPiece::pieceInfo)
            .map(ChessPieceInfo::getPieceValue)
            .map(OptionalInt::of)
            .orElse(OptionalInt.empty());
    }

    public void putPieceAt(ChessboardPosition position, ChessPiece piece) {
        board.put(position, piece);
    }

    public void removePieceAt(ChessboardPosition position) {
        board.remove(position);
    }
}