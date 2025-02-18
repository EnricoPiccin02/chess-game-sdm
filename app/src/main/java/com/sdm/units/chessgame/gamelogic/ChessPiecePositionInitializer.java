package com.sdm.units.chessgame.gamelogic;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdm.units.chessgame.gamelogic.basics.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardRank;
import com.sdm.units.chessgame.pieces.ChessPiece;

@FunctionalInterface
public interface ChessPiecePositionInitializer {
    
    public Map<ChessboardPosition, Optional<ChessPiece>> initializeFor(ChessPieceColor color);

    default Map<ChessboardPosition, Optional<ChessPiece>> chessPieceStartingPositions(Supplier<? extends ChessPiece> chessPieceSupplier, Stream<ChessboardFile> files, ChessboardRank rank) {
        return files.map(file -> new ChessboardPosition(file, rank))
            .collect(Collectors.toMap(p -> p, p -> Optional.of(chessPieceSupplier.get())));
    }
}