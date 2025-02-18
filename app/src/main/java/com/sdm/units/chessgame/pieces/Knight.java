package com.sdm.units.chessgame.pieces;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdm.units.chessgame.gamelogic.basics.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.basics.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.basics.ChessPieceMove;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardPosition;
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
        return Stream.of(
            loadKnightMove(List.of(ChessboardDirection.LEFT, ChessboardDirection.LEFT, ChessboardDirection.DOWN), ChessboardDirection.LEFT, fromPosition),
            loadKnightMove(List.of(ChessboardDirection.LEFT, ChessboardDirection.LEFT, ChessboardDirection.UP), ChessboardDirection.UP, fromPosition),
            loadKnightMove(List.of(ChessboardDirection.UP, ChessboardDirection.UP, ChessboardDirection.LEFT), ChessboardDirection.UP_LEFT, fromPosition),
            loadKnightMove(List.of(ChessboardDirection.UP, ChessboardDirection.UP, ChessboardDirection.RIGHT), ChessboardDirection.UP_RIGHT, fromPosition),
            loadKnightMove(List.of(ChessboardDirection.RIGHT, ChessboardDirection.RIGHT, ChessboardDirection.UP), ChessboardDirection.RIGHT, fromPosition),
            loadKnightMove(List.of(ChessboardDirection.RIGHT, ChessboardDirection.RIGHT, ChessboardDirection.DOWN), ChessboardDirection.DOWN, fromPosition),
            loadKnightMove(List.of(ChessboardDirection.DOWN, ChessboardDirection.DOWN, ChessboardDirection.RIGHT), ChessboardDirection.DOWN_RIGHT, fromPosition),
            loadKnightMove(List.of(ChessboardDirection.DOWN, ChessboardDirection.DOWN, ChessboardDirection.LEFT), ChessboardDirection.DOWN_LEFT, fromPosition)
        )
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());
    }

    private Optional<ChessPieceMove> loadKnightMove(List<ChessboardDirection> directionsOfMovement, ChessboardDirection moveDirection, ChessboardPosition fromPosition) {
        return fromPosition.nextPosition(directionsOfMovement.toArray(new ChessboardDirection[0]))
            .map(pos -> new ChessPieceMove(moveDirection, pos));
    }

    @Override
    public List<ChessPieceMove> getCaptureMoves(ChessboardPosition fromPosition) {
        return getPossibleMoves(fromPosition);
    }  
}