package com.sdm.units.chessgame.gamelogic.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.initialization.ChessboardSetup;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public class BoardStateManager implements Chessboard {

    private final ChessboardOrientation orientation;
    private final ChessboardSetup setup;
    private final Map<ChessboardPosition, ChessPiece> board;

    public BoardStateManager(ChessboardOrientation orientation, ChessboardSetup setup) {
        this.orientation = orientation;
        this.setup = setup;
        this.board = new HashMap<>(setup.generate(orientation));
    }

    private BoardStateManager(BoardStateManager other) {
        this.orientation = other.orientation;
        this.setup = other.setup;
        this.board = other.board.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> e.getValue().copy()
            ));
    }

    @Override
    public Chessboard deepCopy() {
        return new BoardStateManager(this);
    }
    
    @Override
    public void resetBoard() {
        board.clear();
        board.putAll(setup.generate(orientation));
    }

    @Override
    public ChessboardOrientation getOrientation() {
        return orientation;
    }

    @Override
    public List<ChessboardPosition> getOccupiedSquaresOf(ChessPieceColor color) {
        return board.entrySet().stream()
            .filter(e -> e.getValue() != null && e.getValue().pieceColor().equals(color))
            .map(Map.Entry::getKey)
            .toList();
    }

    @Override
    public boolean isUnoccupiedSquare(ChessboardPosition pos) {
        return !board.containsKey(pos);
    }

    @Override
    public Optional<ChessPiece> getPieceAt(ChessboardPosition position) {
        return Optional.ofNullable(board.get(position));
    }

    @Override
    public boolean isOpponentAt(ChessPieceColor player, ChessboardPosition pos) {
        return getPieceAt(pos)
            .map(ChessPiece::pieceColor)
            .map(color -> player.opponent().equals(color))
            .orElse(false);
    }

    @Override
    public void putPieceAt(ChessboardPosition position, ChessPiece piece) {
        board.put(position, piece);
    }

    @Override
    public void removePieceAt(ChessboardPosition position) {
        board.remove(position);
    }
}