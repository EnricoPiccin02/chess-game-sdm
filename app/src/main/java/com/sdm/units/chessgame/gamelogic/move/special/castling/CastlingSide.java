package com.sdm.units.chessgame.gamelogic.move.special.castling;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public enum CastlingSide {
    
    KING_SIDE(
            ChessboardFile.H,
            ChessboardFile.G,
            ChessboardFile.F,
            ChessboardDirection.RIGHT
    ),
    QUEEN_SIDE(
            ChessboardFile.A,
            ChessboardFile.C,
            ChessboardFile.D,
            ChessboardDirection.LEFT
    );

    private final ChessboardFile rookFromFile;
    private final ChessboardFile kingToFile;
    private final ChessboardFile rookToFile;
    private final ChessboardDirection moveDirection;

    CastlingSide(ChessboardFile rookFromFile, ChessboardFile kingToFile, ChessboardFile rookToFile, ChessboardDirection moveDirection) {
        this.rookFromFile = rookFromFile;
        this.kingToFile = kingToFile;
        this.rookToFile = rookToFile;
        this.moveDirection = moveDirection;
    }

    public ChessboardPosition getKingTo(ChessboardPosition kingFrom) {
        return new ChessboardPosition(kingToFile, kingFrom.rank());
    }

    public ChessboardPosition getRookFrom(ChessboardPosition kingFrom) {
        return new ChessboardPosition(rookFromFile, kingFrom.rank());
    }

    public ChessboardPosition getRookTo(ChessboardPosition kingFrom) {
        return new ChessboardPosition(rookToFile, kingFrom.rank());
    }

    public List<ChessboardPosition> getKingPath(ChessboardPosition kingFrom) {
        return List.of(
                kingFrom.nextPosition(moveDirection),
                kingFrom.nextPosition(List.of(moveDirection, moveDirection))
        ).stream()
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();
    }

    public static Optional<CastlingSide> fromRookFile(ChessboardFile file) {
        return Arrays.stream(values())
            .filter(side -> side.rookFromFile == file)
            .findFirst();
    }
}