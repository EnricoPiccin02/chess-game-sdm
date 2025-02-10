package com.sdm.units.chessgame.gamelogic;

public enum ChessboardDirection {

    LEFT(new Integer[]{-1, 0}),
    RIGHT(new Integer[]{1, 0}),
    UP(new Integer[]{0, 1}),
    DOWN(new Integer[]{0, -1}),
    UP_LEFT(new Integer[]{-1, 1}),
    UP_RIGHT(new Integer[]{1, 1}),
    DOWN_LEFT(new Integer[]{-1, -1}),
    DOWN_RIGHT(new Integer[]{1, -1});

    private final Integer[] direction;

    ChessboardDirection(Integer[] direction) {
        this.direction = direction;
    }

    public Integer[] directionDescriptor() {
        return direction;
    }

    public Integer directionFileDescriptor() {
        return direction[0];
    }

    public Integer directionRankDescriptor() {
        return direction[1];
    }
}