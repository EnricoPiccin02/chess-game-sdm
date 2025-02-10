package com.sdm.units.chessgame.gamelogic;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdm.units.chessgame.pieces.ChessPiece;
import com.sdm.units.chessgame.pieces.Pawn;
import com.sdm.units.chessgame.pieces.Rook;
import com.sdm.units.chessgame.pieces.Knight;
import com.sdm.units.chessgame.pieces.Bishop;
import com.sdm.units.chessgame.pieces.Queen;
import com.sdm.units.chessgame.pieces.King;

public class ChessboardInitialSetupBuilder {

    public static Map<ChessboardPosition, ChessPiece> getChessboardInitialSetup() {
        Map<ChessboardPosition, ChessPiece> startingPositions = new HashMap<>();

        Stream.of(ChessPieceColor.values()).forEach(color -> {
            startingPositions.putAll(getPawnStartingPositions(color));
            startingPositions.putAll(getRookStartingPositions(color));
            startingPositions.putAll(getKnightStartingPositions(color));
            startingPositions.putAll(getBishopStartingPositions(color));
            startingPositions.putAll(getQueenStartingPosition(color));
            startingPositions.putAll(getKingStartingPosition(color));
        });
        
        return startingPositions;
    }

    private static Map<ChessboardPosition, ChessPiece> getChessPieceStartingPositions(ChessPiece piece, Stream<ChessboardFile> files, ChessboardRank rank) {
        return files.map(file -> new ChessboardPosition(file, rank))
            .collect(Collectors.toMap(p -> p, p -> piece));
    }
    
    public static Map<ChessboardPosition, ChessPiece> getPawnStartingPositions(ChessPieceColor color) {
        return getChessPieceStartingPositions(
            new Pawn(color),
            Stream.of(ChessboardFile.values()), 
            color == ChessPieceColor.WHITE ? ChessboardRank.TWO : ChessboardRank.SEVEN
        );
    }

    public static Map<ChessboardPosition, ChessPiece> getRookStartingPositions(ChessPieceColor color) {
        return getChessPieceStartingPositions(
            new Rook(color),
            Stream.of(ChessboardFile.A, ChessboardFile.H),
            color == ChessPieceColor.WHITE ? ChessboardRank.ONE : ChessboardRank.EIGHT
        );
    }

    public static Map<ChessboardPosition, ChessPiece> getKnightStartingPositions(ChessPieceColor color) {
        return getChessPieceStartingPositions(
            new Knight(color),
            Stream.of(ChessboardFile.B, ChessboardFile.G),
            color == ChessPieceColor.WHITE ? ChessboardRank.ONE : ChessboardRank.EIGHT
        );
    }

    public static Map<ChessboardPosition, ChessPiece> getBishopStartingPositions(ChessPieceColor color) {
        return getChessPieceStartingPositions(
            new Bishop(color),
            Stream.of(ChessboardFile.C, ChessboardFile.F),
            color == ChessPieceColor.WHITE ? ChessboardRank.ONE : ChessboardRank.EIGHT
        );
    }

    public static Map<ChessboardPosition, ChessPiece> getQueenStartingPosition(ChessPieceColor color) {
        return getChessPieceStartingPositions(
            new Queen(color),
            Stream.of(ChessboardFile.D),
            color == ChessPieceColor.WHITE ? ChessboardRank.ONE : ChessboardRank.EIGHT
        );
    }

    public static Map<ChessboardPosition, ChessPiece> getKingStartingPosition(ChessPieceColor color) {
        return getChessPieceStartingPositions(
            new King(color),
            Stream.of(ChessboardFile.E),
            color == ChessPieceColor.WHITE ? ChessboardRank.ONE : ChessboardRank.EIGHT
        );
    }
}