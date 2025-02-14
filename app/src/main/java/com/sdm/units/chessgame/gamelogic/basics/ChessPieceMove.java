package com.sdm.units.chessgame.gamelogic.basics;

import java.util.OptionalInt;

public record ChessPieceMove(ChessboardDirection direction, ChessboardPosition position) implements Comparable<ChessPieceMove> { 

    @Override
    public int compareTo(ChessPieceMove otherChessPieceMove) {
        Integer comparedDirection = direction.compareTo(otherChessPieceMove.direction);
        OptionalInt comparedPosition = position.distance(otherChessPieceMove.position());

        if(comparedDirection == 0 && comparedPosition.isPresent())
            return comparedPosition.getAsInt();
        else
            return comparedDirection;
    }
}