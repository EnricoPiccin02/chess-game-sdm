package com.sdm.units.chessgame.gamelogic.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum ChessboardRank {

    ONE(0, 1),
    TWO(1, 2),
    THREE(2, 3),
    FOUR(3, 4),
    FIVE(4, 5),
    SIX(5, 6),
    SEVEN(6, 7),
    EIGHT(7, 8);

    private final int index;
    private final int rankNumber;

    private static final Map<Integer, ChessboardRank> LOOKUP_BY_NUMBER = Arrays.stream(values()).collect(Collectors.toMap(ChessboardRank::rankNumber, r -> r));

    private static final Map<Integer, ChessboardRank> LOOKUP_BY_INDEX = Arrays.stream(values()).collect(Collectors.toMap(ChessboardRank::index, r -> r));

    ChessboardRank(int index, int rankNumber) {
        this.index = index;
        this.rankNumber = rankNumber;
    }

    public int index() {
        return index;
    }

    public int rankNumber() {
        return rankNumber;
    }

    public static Optional<ChessboardRank> ofNumber(int number) {
        return Optional.ofNullable(LOOKUP_BY_NUMBER.get(number));
    }

    public static Optional<ChessboardRank> ofIndex(int index) {
        return Optional.ofNullable(LOOKUP_BY_INDEX.get(index));
    }

    public Optional<ChessboardRank> nextRank(ChessboardDirection direction) {
        int newIndex = this.index + direction.directionRankDescriptor();
        return ofIndex(newIndex);
    }

    @Override
    public String toString() {
        return String.valueOf(rankNumber);
    }
}