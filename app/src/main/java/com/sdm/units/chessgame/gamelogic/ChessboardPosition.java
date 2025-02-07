package com.sdm.units.chessgame.gamelogic;

import java.util.stream.Stream;

public record ChessboardPosition(ChessboardFile file, ChessboardRank rank) {

    public ChessboardPosition(char fileCharacter, int rankNumber) {
        this(ChessboardFile.valueOf(fileCharacter), ChessboardRank.valueOf(rankNumber));
    }

    public boolean isWithinChessboard() {
        return Stream.of(ChessboardFile.values()).anyMatch(file::equals) &&
               Stream.of(ChessboardRank.values()).anyMatch(rank::equals);
    }

    public ChessboardPosition nextPosition(ChessboardDirection direction) {
        return switch (direction) {
            case UP -> new ChessboardPosition(file, rank.nextRank(ChessboardDirection.UP));
            case DOWN -> new ChessboardPosition(file, rank.nextRank(ChessboardDirection.DOWN));
            case LEFT -> new ChessboardPosition(file.nextFile(ChessboardDirection.LEFT), rank);
            case RIGHT -> new ChessboardPosition(file.nextFile(ChessboardDirection.RIGHT), rank);
            case UP_LEFT -> new ChessboardPosition(file.nextFile(ChessboardDirection.LEFT), rank.nextRank(ChessboardDirection.UP));
            case UP_RIGHT -> new ChessboardPosition(file.nextFile(ChessboardDirection.RIGHT), rank.nextRank(ChessboardDirection.UP));
            case DOWN_LEFT -> new ChessboardPosition(file.nextFile(ChessboardDirection.LEFT), rank.nextRank(ChessboardDirection.DOWN));
            case DOWN_RIGHT -> new ChessboardPosition(file.nextFile(ChessboardDirection.RIGHT), rank.nextRank(ChessboardDirection.DOWN));
        };
    }

    public ChessboardPosition nexPosition(ChessboardDirection ... directions) {
        ChessboardPosition nextPosition = this;
        for (ChessboardDirection direction : directions) {
            nextPosition = nextPosition.nextPosition(direction);
        }
        return nextPosition;
    }

    public int distance(ChessboardPosition otherPosition) {
        return this.file.distance(otherPosition.file) + this.rank.distance(otherPosition.rank);
    }
}