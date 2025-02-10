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

    private final Character fileCharacter;

    ChessboardFile(Character fileCharacter) {
        this.fileCharacter = fileCharacter;
    }

    @Override
    public String toString(){
        return String.valueOf(this.fileCharacter);
    }

    public Character value() {
        return this.fileCharacter;
    }

    public static ChessboardFile valueOf(Character value) {
        Optional<ChessboardFile> derivedFile;

        derivedFile = Arrays.stream(values())
            .filter(chessboardFile -> chessboardFile.fileCharacter == value)
            .findFirst();
        
        return derivedFile.isPresent() ? derivedFile.get() : null;
    }
    
    public ChessboardFile nextFile(ChessboardDirection direction) {
        return valueOf((char)(this.fileCharacter + direction.directionFileDescriptor()));
    }

    public Integer distance(ChessboardFile file) {
        return (this.fileCharacter - file.fileCharacter) * values().length;
    }
}