package com.sdm.units.chessgame.gamelogic.domain;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum ChessboardFile {

    A(0, 'a'),
    B(1, 'b'),
    C(2, 'c'),
    D(3, 'd'),
    E(4, 'e'),
    F(5, 'f'),
    G(6, 'g'),
    H(7, 'h');

    private static final Map<Character, ChessboardFile> LOOKUP_BY_CHAR = Arrays.stream(values()).collect(Collectors.toMap(ChessboardFile::fileCharacter, f -> f));
    private static final Map<Integer, ChessboardFile> LOOKUP_BY_INDEX = Arrays.stream(values()).collect(Collectors.toMap(ChessboardFile::index, f -> f));
    
    private final int index;
    private final char fileCharacter;

    ChessboardFile(int index, char fileCharacter) {
        this.index = index;
        this.fileCharacter = fileCharacter;
    }

    public int index() {
        return index;
    }

    public char fileCharacter() {
        return fileCharacter;
    }

    public static Optional<ChessboardFile> of(char c) {
        return Optional.ofNullable(LOOKUP_BY_CHAR.get(Character.toLowerCase(c)));
    }

    public static Optional<ChessboardFile> of(int index) {
        return Optional.ofNullable(LOOKUP_BY_INDEX.get(index));
    }

    public Optional<ChessboardFile> nextFile(ChessboardDirection direction) {
        int newIndex = this.index + direction.directionFileDescriptor();
        return of(newIndex);
    }

    @Override
    public String toString() {
        return String.valueOf(fileCharacter).toUpperCase();
    }
}