package com.sdm.units.chessgame.gamelogic.domain;

import java.util.Optional;

public record ChessboardPosition(ChessboardFile file, ChessboardRank rank) {

    public int fileIndex() {
        return file.index();
    }

    public int rankIndex() {
        return rank.index();
    }
    
    public Optional<ChessboardPosition> nextPosition(ChessboardDirection direction) {
        return file.nextFile(direction)
            .flatMap(nextFile -> rank.nextRank(direction)
                .map(nextRank -> new ChessboardPosition(nextFile, nextRank)));
    }

    public Optional<ChessboardPosition> nextPosition(Iterable<ChessboardDirection> directions) {
        Optional<ChessboardPosition> current = Optional.of(this);
        for (ChessboardDirection direction : directions) {
            current = current.flatMap(pos -> pos.nextPosition(direction));
            if (current.isEmpty()) break;
        }
        return current;
    }

    public boolean isDiagonalTo(ChessboardPosition other) {
        if (other == null) return false;
        int fileDelta = Math.abs(this.fileIndex() - other.fileIndex());
        int rankDelta = Math.abs(this.rankIndex() - other.rankIndex());
        return fileDelta == rankDelta;
    }

    @Override
    public String toString() {
        return "(" + file + ", " + rank + ")";
    }
}