package com.sdm.units.chessgame.gamelogic.initialization;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.sdm.units.chessgame.gamelogic.ChessPiecePositionInitializer;
import com.sdm.units.chessgame.gamelogic.basics.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardRank;
import com.sdm.units.chessgame.pieces.Bishop;
import com.sdm.units.chessgame.pieces.ChessPiece;

public class BishopPositionInitializer implements ChessPiecePositionInitializer {

    @Override
    public Map<ChessboardPosition, Optional<ChessPiece>> initializeFor(ChessPieceColor color) {
        return chessPieceStartingPositions(
            () -> new Bishop(color),
            Stream.of(ChessboardFile.C, ChessboardFile.F),
            color == ChessPieceColor.WHITE ? ChessboardRank.ONE : ChessboardRank.EIGHT
        );
    }   
}