package com.sdm.units.chessgame.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.sdm.units.chessgame.gamelogic.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.ChessPieceMove;
import com.sdm.units.chessgame.gamelogic.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
import com.sdm.units.chessgame.gui.ChessPieceDrawFactory;
import com.sdm.units.chessgame.gui.DrawnChessPiece;

public class Queen extends ChessPiece {

    public Queen(ChessPieceColor color) {
        super(color);
        this.info = ChessPieceInfo.QUEEN;
    }

    @Override
    public DrawnChessPiece drawChessPiece() {
        return new DrawnChessPiece(ChessPieceDrawFactory::drawQueen, color);
    }

    @Override
    public List<ChessPieceMove> getPossibleMoves(ChessboardPosition fromPosition) {
        List<ChessPieceMove> possibleMoves = new ArrayList<>();

        Stream.of(ChessboardDirection.values()).forEach(direction -> {
            ChessboardPosition nextPosition = fromPosition.nextPosition(direction);
            while (nextPosition != null) {
                possibleMoves.add(new ChessPieceMove(direction, nextPosition));
                nextPosition = nextPosition.nextPosition(direction);
            }
        });
        
        return possibleMoves;
    }

    @Override
    public List<ChessPieceMove> getCaptureMoves(ChessboardPosition fromPosition) {
        return getPossibleMoves(fromPosition);
    }
    
}