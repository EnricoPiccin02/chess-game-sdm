package com.sdm.units.chessgame.gui.board.clock;

import java.util.EnumMap;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;

public final class PlayerClocksFactory {

    private final long initialTimeMillis;

    public PlayerClocksFactory(long initialTimeMillis) {
        this.initialTimeMillis = initialTimeMillis;
    }

    public EnumMap<ChessPieceColor, ChessClock> createClocks() {
        EnumMap<ChessPieceColor, ChessClock> clocks = new EnumMap<>(ChessPieceColor.class);

        for (ChessPieceColor color : ChessPieceColor.values()) {
            clocks.put(color, new DefaultChessClock(initialTimeMillis));
        }

        return clocks;
    }
}