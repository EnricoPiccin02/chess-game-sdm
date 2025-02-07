package com.sdm.units.chessgame.gamelogic;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChessboardInitialSetup {
    
    public static List<ChessboardPosition> getPawnStartingPositions(ChessPieceColor color) {
        return Stream.of(ChessboardFile.values())
            .map(file -> new ChessboardPosition(file, 
                color == ChessPieceColor.WHITE ? ChessboardRank.TWO : ChessboardRank.SEVEN)
            )
            .collect(Collectors.toList());
    }

    public static List<ChessboardPosition> getRookStartingPositions(ChessPieceColor color) {
        return Stream.of(ChessboardFile.A, ChessboardFile.H)
            .map(file -> new ChessboardPosition(file, 
                color == ChessPieceColor.WHITE ? ChessboardRank.ONE : ChessboardRank.EIGHT)
            )
            .collect(Collectors.toList());
    }

    public static List<ChessboardPosition> getKnightStartingPositions(ChessPieceColor color) {
        return Stream.of(ChessboardFile.B, ChessboardFile.G)
            .map(file -> new ChessboardPosition(file, 
                color == ChessPieceColor.WHITE ? ChessboardRank.ONE : ChessboardRank.EIGHT)
            )
            .collect(Collectors.toList());
    }

    public static List<ChessboardPosition> getBishopStartingPositions(ChessPieceColor color) {
        return Stream.of(ChessboardFile.C, ChessboardFile.F)
            .map(file -> new ChessboardPosition(file, 
                color == ChessPieceColor.WHITE ? ChessboardRank.ONE : ChessboardRank.EIGHT)
            )
            .collect(Collectors.toList());
    }

    public static ChessboardPosition getQueenStartingPosition(ChessPieceColor color) {
        return new ChessboardPosition(ChessboardFile.D, 
            color == ChessPieceColor.WHITE ? ChessboardRank.ONE : ChessboardRank.EIGHT);
    }

    public static ChessboardPosition getKingStartingPosition(ChessPieceColor color) {
        return new ChessboardPosition(ChessboardFile.E, 
            color == ChessPieceColor.WHITE ? ChessboardRank.ONE : ChessboardRank.EIGHT);
    }
}