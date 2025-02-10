package com.sdm.units.chessgame.gamelogic.initialization;

import java.util.Map;
import java.util.stream.Stream;

import com.sdm.units.chessgame.gamelogic.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.ChessPiecePositionInitializer;
import com.sdm.units.chessgame.gamelogic.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.ChessboardRank;
import com.sdm.units.chessgame.pieces.Bishop;
import com.sdm.units.chessgame.pieces.ChessPiece;

public class BishopPositionInitializer implements ChessPiecePositionInitializer {

    @Override
    public Map<ChessboardPosition, ChessPiece> initializeFor(ChessPieceColor color) {
        return chessPieceStartingPositions(
            new Bishop(color),
            Stream.of(ChessboardFile.C, ChessboardFile.F),
            color == ChessPieceColor.WHITE ? ChessboardRank.ONE : ChessboardRank.EIGHT
        );
    }   
}