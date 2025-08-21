package com.sdm.units.chessgame.gamelogic.domain;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Stream;

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

    public String id(){
        return name();
    }

    public Character value() {
        return this.fileCharacter;
    }

    public static Optional<ChessboardFile> valueOf(Character value) {
        return Stream.of(values())
            .filter(chessboardFile -> chessboardFile.fileCharacter == value)
            .findFirst();
    }
    
    public Optional<ChessboardFile> nextFile(ChessboardDirection direction) {
        if (direction == null) return Optional.empty();
        
        return valueOf((char)(this.fileCharacter + direction.directionFileDescriptor()));
    }

    public OptionalInt distance(ChessboardFile file) {
        if(file == null) return OptionalInt.empty();

        return OptionalInt.of(this.fileCharacter - file.fileCharacter);
    }
}