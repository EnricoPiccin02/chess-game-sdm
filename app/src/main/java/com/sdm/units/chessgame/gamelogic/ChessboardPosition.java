package com.sdm.units.chessgame.gamelogic;

import java.util.stream.Stream;

public record ChessboardPosition(ChessboardFile file, ChessboardRank rank) implements Comparable<ChessboardPosition> {

    public boolean isWithinChessboard() {
        return Stream.of(ChessboardFile.values()).anyMatch(file::equals) &&
               Stream.of(ChessboardRank.values()).anyMatch(rank::equals);
    }

    public ChessboardPosition nextPosition(ChessboardDirection direction) {
        return new ChessboardPosition(file.nextFile(direction), rank.nextRank(direction));
    }

    public ChessboardPosition nexPosition(ChessboardDirection ... directions) {
        ChessboardPosition nextPosition = this;
        for (ChessboardDirection direction : directions) {
            nextPosition = nextPosition.nextPosition(direction);
        }
        return nextPosition;
    }

    public Integer distance(ChessboardPosition otherPosition) {
        return this.file.distance(otherPosition.file) + this.rank.distance(otherPosition.rank);
    }

    @Override
    public int compareTo(ChessboardPosition otheChessboardPosition) {
        return this.distance(otheChessboardPosition);
    }
}