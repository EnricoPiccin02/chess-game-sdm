package com.sdm.units.chessgame.gamelogic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;

import com.sdm.units.chessgame.gamelogic.basics.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.basics.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.initialization.*;

import com.sdm.units.chessgame.pieces.ChessPiece;

public class Chessboard {
    
    protected Map<ChessboardPosition, Optional<ChessPiece>> board;
    protected ChessboardInitialSetupBuilder initialSetupBuilder;

    public Chessboard() {
        initialSetupBuilder = new ChessboardInitialSetupBuilder(List.of(
            new PawnPositionInitializer(),
            new RookPositionInitializer(),
            new KnightPositionInitializer(),
            new BishopPositionInitializer(),
            new QueenPositionInitializer(),
            new KingPositionInitializer()
        ));

        board = new HashMap<>(initialSetupBuilder.getChessboardInitialSetup());        
    }

    public void resetBoard() {
        board.clear();
        board.putAll(initialSetupBuilder.getChessboardInitialSetup());
    }

    public Map<ChessboardPosition, Optional<ChessPiece>> getBoard() {
        return board;
    }

    public List<ChessboardPosition> getNonVacantPositionsOfColor(ChessPieceColor color) {
        return board.keySet().stream()
            .filter(Predicate.not(this::isPositionVacant))
            .filter(key -> getPieceFromPosition(key).get().color.equals(color))
            .toList();
    }

    public Optional<ChessPiece> getPieceFromPosition(ChessboardPosition position) {
        Optional<ChessPiece> piece = board.get(position);
        return piece == null ? Optional.empty() : piece;
    }

    public OptionalInt getPieceValueFromPosition(ChessboardPosition position) {
        Optional<ChessPiece> piece = getPieceFromPosition(position);
        
        if (piece.isPresent())
            return OptionalInt.of(piece.map(ChessPiece::pieceInfo).map(ChessPieceInfo::getPieceValue).get());
        else
            return OptionalInt.empty();
    }

    public void setPositionVacant(ChessboardPosition position) {
        board.put(position, Optional.empty());
    }

    public boolean isPositionVacant(ChessboardPosition position) {
        return !getPieceFromPosition(position).isPresent();
    }

    public void movePiece(ChessboardPosition fromPosition, ChessboardPosition toPosition) {
        getPieceFromPosition(fromPosition).ifPresent(piece -> {
            piece.setHasMoved();
            board.put(toPosition, Optional.of(piece));
            setPositionVacant(fromPosition);
        });
    }
}