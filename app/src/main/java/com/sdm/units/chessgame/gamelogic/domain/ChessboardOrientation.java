package com.sdm.units.chessgame.gamelogic.domain;

import java.util.Set;

public enum ChessboardOrientation {
    
    WHITE_BOTTOM(ChessboardRank.ONE, ChessboardRank.TWO, ChessboardRank.SEVEN, ChessboardRank.EIGHT),
    BLACK_BOTTOM(ChessboardRank.EIGHT, ChessboardRank.SEVEN, ChessboardRank.TWO, ChessboardRank.ONE);

    private final ChessboardRank whiteBackRank;
    private final ChessboardRank whitePawnRank;
    private final ChessboardRank blackPawnRank;
    private final ChessboardRank blackBackRank;

    ChessboardOrientation(ChessboardRank whiteBackRank, ChessboardRank whitePawnRank, ChessboardRank blackPawnRank, ChessboardRank blackBackRank) {
        this.whiteBackRank = whiteBackRank;
        this.whitePawnRank = whitePawnRank;
        this.blackPawnRank = blackPawnRank;
        this.blackBackRank = blackBackRank;
    }

    public ChessboardRank getPawnRank(ChessPieceColor color) {
        return color == ChessPieceColor.WHITE ? whitePawnRank : blackPawnRank;
    }

    public ChessboardRank getBackRank(ChessPieceColor color) {
        return color == ChessPieceColor.WHITE ? whiteBackRank : blackBackRank;
    }

    public ChessboardDirection pawnForwardDirection(ChessPieceColor color) {
        return color == ChessPieceColor.WHITE && this == WHITE_BOTTOM || color == ChessPieceColor.BLACK && this == BLACK_BOTTOM
            ? ChessboardDirection.UP
            : ChessboardDirection.DOWN;
    }

    public Set<ChessboardDirection> pawnCaptureDirections(ChessPieceColor color) {
        return pawnForwardDirection(color) == ChessboardDirection.UP
            ? Set.of(ChessboardDirection.UP_LEFT, ChessboardDirection.UP_RIGHT)
            : Set.of(ChessboardDirection.DOWN_LEFT, ChessboardDirection.DOWN_RIGHT);
    }

    public ChessboardRank promotionRank(ChessPieceColor color) {
        return color == ChessPieceColor.WHITE ? blackBackRank : whiteBackRank;
    }
}