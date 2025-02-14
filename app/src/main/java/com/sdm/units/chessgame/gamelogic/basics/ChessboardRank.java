package com.sdm.units.chessgame.gamelogic.basics;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Stream;

public enum ChessboardRank {
    
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8);

    public final Integer rankNumber;

    ChessboardRank(int rankNumber) {
        this.rankNumber = rankNumber;
    }

    @Override
    public String toString(){
        return String.valueOf(this.rankNumber);
    }

    public Integer value() {
        return this.rankNumber;
    }

    public static Optional<ChessboardRank> valueOf(Integer value) {
        return Stream.of(values())
            .filter(chessboardRank -> chessboardRank.rankNumber == value)
            .findFirst();
    }

    public Optional<ChessboardRank> nextRank(ChessboardDirection direction) {
        if (direction == null) return Optional.empty();

        return valueOf(this.rankNumber + direction.directionRankDescriptor());
    }

    public OptionalInt distance(ChessboardRank rank) {
        if(rank == null) return OptionalInt.empty();

        return OptionalInt.of(this.rankNumber - rank.rankNumber);
    }
}