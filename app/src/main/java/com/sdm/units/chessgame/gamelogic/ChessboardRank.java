package com.sdm.units.chessgame.gamelogic;

import java.util.Arrays;
import java.util.Optional;

public enum ChessboardRank {
    
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8);

    public final int rankNumber;

    ChessboardRank(int rankNumber) {
        this.rankNumber = rankNumber;
    }

    @Override
    public String toString(){
        return String.valueOf(this.rankNumber);
    }

    public int value() {
        return this.rankNumber;
    }

    public static ChessboardRank valueOf(int value) {
        Optional<ChessboardRank> derivedRank;

        derivedRank = Arrays.stream(values())
            .filter(chessboardRank -> chessboardRank.rankNumber == value)
            .findFirst();
        
        return derivedRank.isPresent() ? derivedRank.get() : null;
    }

    public ChessboardRank nextRank(ChessboardDirection direction) {
        return valueOf(this.rankNumber + direction.directionRankDescriptor());
    }

    public int distance(ChessboardRank rank) {
        return Math.abs(this.rankNumber - rank.rankNumber);
    }
}