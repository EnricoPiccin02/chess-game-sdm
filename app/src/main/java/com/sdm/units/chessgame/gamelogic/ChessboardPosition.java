package com.sdm.units.chessgame.gamelogic;

public record ChessboardPosition(ChessboardFile file, ChessboardRank rank) {

    public ChessboardPosition(char fileCharacter, int rankNumber) {
        this(ChessboardFile.valueOf(rankNumber), ChessboardRank.valueOf(rankNumber));
    }
}