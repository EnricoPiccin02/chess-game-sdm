package com.sdm.units.chessgame.gamelogic.domain;

public enum ChessboardDirection {

    LEFT(new int[]{-1, 0}),
    RIGHT(new int[]{1, 0}),
    UP(new int[]{0, 1}),
    DOWN(new int[]{0, -1}),
    UP_LEFT(new int[]{-1, 1}),
    UP_RIGHT(new int[]{1, 1}),
    DOWN_LEFT(new int[]{-1, -1}),
    DOWN_RIGHT(new int[]{1, -1});

    private final int[] direction;

    ChessboardDirection(int[] direction) {
        this.direction = direction;
    }

    public int[] directionDescriptor() {
        return direction;
    }

    public int directionFileDescriptor() {
        return direction[0];
    }

    public int directionRankDescriptor() {
        return direction[1];
    }
}