package com.sdm.units.chessgame.gamelogic;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdm.units.chessgame.pieces.ChessPiece;

@FunctionalInterface
public interface ChessPiecePositionInitializer {
    
    public Map<ChessboardPosition, ChessPiece> initializeFor(ChessPieceColor color);

    default Map<ChessboardPosition, ChessPiece> chessPieceStartingPositions(ChessPiece piece, Stream<ChessboardFile> files, ChessboardRank rank) {
        return files.map(file -> new ChessboardPosition(file, rank))
            .collect(Collectors.toMap(p -> p, p -> piece));
    }
}