package com.sdm.units.chessgame.pieces;

import java.util.List;

import com.sdm.units.chessgame.gamelogic.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.ChessPieceMove;
import com.sdm.units.chessgame.gamelogic.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
import com.sdm.units.chessgame.gui.ChessPieceDrawFactory;
import com.sdm.units.chessgame.gui.DrawnChessPiece;

public class Knight extends ChessPiece {
    
    public Knight(ChessPieceColor color) {
        super(color);
        this.info = ChessPieceInfo.KNIGHT;
    }

    @Override
    public DrawnChessPiece drawChessPiece() {
        return new DrawnChessPiece(ChessPieceDrawFactory::drawKnight, color);
    }

    @Override
    public List<ChessPieceMove> getPossibleMoves(ChessboardPosition fromPosition) {
        return List.of(
            new ChessPieceMove(ChessboardDirection.UP, fromPosition.nexPosition(ChessboardDirection.UP, ChessboardDirection.UP, ChessboardDirection.LEFT)),
            new ChessPieceMove(ChessboardDirection.UP, fromPosition.nexPosition(ChessboardDirection.UP, ChessboardDirection.UP, ChessboardDirection.RIGHT)),
            new ChessPieceMove(ChessboardDirection.DOWN, fromPosition.nexPosition(ChessboardDirection.DOWN, ChessboardDirection.DOWN, ChessboardDirection.LEFT)),
            new ChessPieceMove(ChessboardDirection.DOWN, fromPosition.nexPosition(ChessboardDirection.DOWN, ChessboardDirection.DOWN, ChessboardDirection.RIGHT)),
            new ChessPieceMove(ChessboardDirection.LEFT, fromPosition.nexPosition(ChessboardDirection.LEFT, ChessboardDirection.LEFT, ChessboardDirection.UP)),
            new ChessPieceMove(ChessboardDirection.LEFT, fromPosition.nexPosition(ChessboardDirection.LEFT, ChessboardDirection.LEFT, ChessboardDirection.DOWN)),
            new ChessPieceMove(ChessboardDirection.RIGHT, fromPosition.nexPosition(ChessboardDirection.RIGHT, ChessboardDirection.RIGHT, ChessboardDirection.UP)),
            new ChessPieceMove(ChessboardDirection.RIGHT, fromPosition.nexPosition(ChessboardDirection.RIGHT, ChessboardDirection.RIGHT, ChessboardDirection.DOWN))
        );
    }

    @Override
    public List<ChessPieceMove> getCaptureMoves(ChessboardPosition fromPosition) {
        return getPossibleMoves(fromPosition);
    }   
}