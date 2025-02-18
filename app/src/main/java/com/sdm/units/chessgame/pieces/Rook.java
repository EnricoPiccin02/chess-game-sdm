package com.sdm.units.chessgame.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.sdm.units.chessgame.gamelogic.basics.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.basics.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.basics.ChessPieceMove;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardPosition;
import com.sdm.units.chessgame.gui.ChessPieceDrawFactory;
import com.sdm.units.chessgame.gui.DrawnChessPiece;

public class Rook extends ChessPiece {

    public Rook(ChessPieceColor color) {
        super(color);
        this.info = ChessPieceInfo.ROOK;
    }

    @Override
    public DrawnChessPiece drawChessPiece() {
        return new DrawnChessPiece(ChessPieceDrawFactory::drawRook, color);
    }

    @Override
    public List<ChessPieceMove> getPossibleMoves(ChessboardPosition fromPosition) {
        List<ChessPieceMove> possibleMoves = new ArrayList<>();
        
        Stream.of(ChessboardDirection.UP, ChessboardDirection.DOWN, ChessboardDirection.LEFT, ChessboardDirection.RIGHT).forEach(direction -> {
            Optional<ChessboardPosition> currentPosition = fromPosition.nextPosition(direction);
            
            while (currentPosition.isPresent()) {
                possibleMoves.add(new ChessPieceMove(direction, currentPosition.get()));
                currentPosition = currentPosition.get().nextPosition(direction);
            }
        });
        
        return possibleMoves;
    }

    @Override
    public List<ChessPieceMove> getCaptureMoves(ChessboardPosition fromPosition) {
        return getPossibleMoves(fromPosition);
    }
}