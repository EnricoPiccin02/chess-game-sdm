package com.sdm.units.chessgame.gamelogic.initialization;

import java.util.HashMap;
import java.util.Map;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.pieces.Bishop;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gamelogic.pieces.King;
import com.sdm.units.chessgame.gamelogic.pieces.Knight;
import com.sdm.units.chessgame.gamelogic.pieces.Pawn;
import com.sdm.units.chessgame.gamelogic.pieces.Queen;
import com.sdm.units.chessgame.gamelogic.pieces.Rook;

public class StandardChessPiecePlacement implements ChessPiecePlacementStrategy {

    @Override
    public Map<ChessboardPosition, ChessPiece> initialize(ChessPieceColor color, ChessboardOrientation orientation) {
        Map<ChessboardPosition, ChessPiece> pieces = new HashMap<>();

        ChessboardRank backRank = orientation.getBackRank(color);
        ChessboardRank pawnRank = orientation.getPawnRank(color);

        pieces.put(new ChessboardPosition(ChessboardFile.A, backRank), new Rook(color));
        pieces.put(new ChessboardPosition(ChessboardFile.B, backRank), new Knight(color));
        pieces.put(new ChessboardPosition(ChessboardFile.C, backRank), new Bishop(color));
        pieces.put(new ChessboardPosition(ChessboardFile.D, backRank), new Queen(color));
        pieces.put(new ChessboardPosition(ChessboardFile.E, backRank), new King(color));
        pieces.put(new ChessboardPosition(ChessboardFile.F, backRank), new Bishop(color));
        pieces.put(new ChessboardPosition(ChessboardFile.G, backRank), new Knight(color));
        pieces.put(new ChessboardPosition(ChessboardFile.H, backRank), new Rook(color));

        for (ChessboardFile file : ChessboardFile.values()) {
            pieces.put(new ChessboardPosition(file, pawnRank), new Pawn(color, orientation));
        }

        return pieces;
    }
}