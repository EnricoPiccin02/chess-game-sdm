package com.sdm.units.chessgame.gamelogic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.sdm.units.chessgame.pieces.ChessPiece;

public class ChessboardInitialSetupBuilder {

    List<ChessPiecePositionInitializer> initializers;

    public ChessboardInitialSetupBuilder(List<ChessPiecePositionInitializer> initializers) {
        this.initializers = initializers;
    }

    public Map<ChessboardPosition, ChessPiece> getChessboardInitialSetup() {
        Map<ChessboardPosition, ChessPiece> startingBoard = new HashMap<>();
        
        initializers.stream().forEach(
            initializer -> Stream.of(ChessPieceColor.values()).forEach( color ->
                startingBoard.putAll(initializer.initializeFor(color)) 
            )
        );

        Stream.of(ChessboardFile.values()).forEach(file -> {
            Stream.of(ChessboardRank.values()).forEach(rank -> {
                startingBoard.putIfAbsent(new ChessboardPosition(file, rank), null);
            });
        });

        return startingBoard;
    }
}