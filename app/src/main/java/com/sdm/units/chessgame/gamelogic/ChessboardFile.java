package com.sdm.units.chessgame.gamelogic;

import java.util.Arrays;
import java.util.Optional;

public enum ChessboardFile {
    
    A('a'),
    B('b'),
    C('c'),
    D('d'),
    E('e'),
    F('f'),
    G('g'),
    H('h');

    private final char fileCharacter;

    ChessboardFile(char fileCharacter) {
        this.fileCharacter = fileCharacter;
    }

    @Override
    public String toString(){
        return String.valueOf(this.fileCharacter);
    }

    public static ChessboardFile valueOf(int value) {
        Optional<ChessboardFile> derivedFile;

        derivedFile = Arrays.stream(values())
            .filter(chessboardFile -> chessboardFile.fileCharacter == value)
            .findFirst();
        
        return derivedFile.isPresent() ? derivedFile.get() : null;
    }
}