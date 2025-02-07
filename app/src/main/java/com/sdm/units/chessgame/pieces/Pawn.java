package com.sdm.units.chessgame.pieces;

import java.util.List;

import com.sdm.units.chessgame.gamelogic.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.ChessPieceMove;
import com.sdm.units.chessgame.gamelogic.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
import com.sdm.units.chessgame.gui.ChessPieceDrawFactory;
import com.sdm.units.chessgame.gui.DrawnChessPiece;

public class Pawn extends ChessPiece {

    public Pawn(ChessPieceColor color) {
        super(color);
        this.info = ChessPieceInfo.PAWN;
    }

    @Override
    public DrawnChessPiece drawChessPiece() {
        return new DrawnChessPiece(ChessPieceDrawFactory::drawPawn, color);
    }

    @Override
    public List<ChessPieceMove> getPossibleMoves(ChessboardPosition fromPosition) {
        ChessboardDirection directionOfMovement = color == ChessPieceColor.WHITE ? ChessboardDirection.UP : ChessboardDirection.DOWN;
        ChessboardPosition forwardPosition = fromPosition.nextPosition(directionOfMovement);
        return List.of(
            new ChessPieceMove(directionOfMovement, forwardPosition),
            hasMoved ? null : new ChessPieceMove(directionOfMovement, forwardPosition.nextPosition(directionOfMovement))
        );
    }

    @Override
    public List<ChessPieceMove> getCaptureMoves(ChessboardPosition fromPosition) {
        return List.of(
            color == ChessPieceColor.WHITE ? new ChessPieceMove(ChessboardDirection.UP_LEFT, fromPosition) : new ChessPieceMove(ChessboardDirection.DOWN_LEFT, fromPosition),
            color == ChessPieceColor.WHITE ? new ChessPieceMove(ChessboardDirection.UP_RIGHT, fromPosition) : new ChessPieceMove(ChessboardDirection.DOWN_RIGHT, fromPosition)
        );
    }
}