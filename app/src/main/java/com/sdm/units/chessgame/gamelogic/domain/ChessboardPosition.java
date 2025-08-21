package com.sdm.units.chessgame.gamelogic.domain;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Stream;

public record ChessboardPosition(ChessboardFile file, ChessboardRank rank) implements Comparable<ChessboardPosition> {

    public Optional<ChessboardPosition> nextPosition(ChessboardDirection direction) {
        Optional<ChessboardFile> nextFile = file != null ? file.nextFile(direction) : Optional.empty();
        Optional<ChessboardRank> nextRank = rank != null ? rank.nextRank(direction) : Optional.empty();
        
        if(nextFile.isPresent() && nextRank.isPresent())
            return Optional.of(new ChessboardPosition(nextFile.get(), nextRank.get()));
        else
            return Optional.empty();
    }

    public Optional<ChessboardPosition> nextPosition(Iterable<ChessboardDirection> directions) {
        Optional<ChessboardPosition> currentPosition = Optional.of(this);

        for (ChessboardDirection direction : directions) {
            if (currentPosition.isPresent())
                currentPosition = currentPosition.get().nextPosition(direction);
            else
                break;
        }

        return currentPosition;
    }

    public OptionalInt distance(ChessboardPosition otherChessboardPosition) {
        if (otherChessboardPosition == null || file == null || rank == null) return OptionalInt.empty();

        return Stream.of(this.file.distance(otherChessboardPosition.file), this.rank.distance(otherChessboardPosition.rank))
            .filter(OptionalInt::isPresent)
            .mapToInt(OptionalInt::getAsInt)
            .map(Math::abs)
            .reduce(Integer::sum);
    }

    @Override
    public int compareTo(ChessboardPosition otherChessboardPosition) {
        final int[] comparedPosition = {0};
        
        if (otherChessboardPosition != null && file != null && rank != null) {
            this.file.distance(otherChessboardPosition.file).ifPresent(
                fileDistance -> comparedPosition[0] += fileDistance
            );
            this.rank.distance(otherChessboardPosition.rank).ifPresent(
                rankDistance -> comparedPosition[0] += rankDistance * ChessboardRank.values().length
            );
        }
        
        return comparedPosition[0];
    }

    @Override
    public String toString() {
        return "(" + file + ", " + rank + ")";
    }
}