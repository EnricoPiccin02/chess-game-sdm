package com.sdm.units.chessgame.gamelogic.basics;

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

    public Optional<ChessboardPosition> nextPosition(ChessboardDirection ... directions) {
        Optional<ChessboardPosition> currentPosition = Optional.of(this);
        
        for (ChessboardDirection direction : directions) {
            if (currentPosition.isPresent())
                currentPosition = currentPosition.get().nextPosition(direction);
        }

        return currentPosition;
    }

    public OptionalInt distance(ChessboardPosition otherPosition) {
        if(otherPosition == null || file == null || rank == null) return OptionalInt.empty();

        return Stream.of(this.file.distance(otherPosition.file),this.rank.distance(otherPosition.rank))
            .filter(OptionalInt::isPresent)
            .mapToInt(OptionalInt::getAsInt)
            .reduce(Integer::sum);
    }

    @Override
    public int compareTo(ChessboardPosition otherChessboardPosition) {
        OptionalInt distanceCompare = this.distance(otherChessboardPosition);

        if (distanceCompare.isPresent()) return distanceCompare.getAsInt();

        return 0;
    }
}