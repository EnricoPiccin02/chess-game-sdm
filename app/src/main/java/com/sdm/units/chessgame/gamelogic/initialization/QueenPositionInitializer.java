package com.sdm.units.chessgame.gamelogic.initialization;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.sdm.units.chessgame.gamelogic.ChessPiecePositionInitializer;
import com.sdm.units.chessgame.gamelogic.basics.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardRank;
import com.sdm.units.chessgame.pieces.ChessPiece;
import com.sdm.units.chessgame.pieces.Queen;

public class QueenPositionInitializer implements ChessPiecePositionInitializer {

    @Override
    public Map<ChessboardPosition, Optional<ChessPiece>> initializeFor(ChessPieceColor color) {
        return chessPieceStartingPositions(
            () -> new Queen(color),
            Stream.of(ChessboardFile.D),
            color == ChessPieceColor.WHITE ? ChessboardRank.ONE : ChessboardRank.EIGHT
        );
    }
}