package com.sdm.units.chessgame.gamelogic.board.state;

import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public class KingLocator implements PieceLocator {

    @Override
    public Optional<ChessboardPosition> locateFirstOf(Chessboard board, ChessPieceColor color) {
        return board.getOccupiedSquaresOf(color).stream()
            .filter(pos -> board.getPieceAt(pos)
                .map(piece -> piece.pieceInfo() == ChessPieceInfo.KING)
                .orElse(false))
            .findFirst();
    }
}