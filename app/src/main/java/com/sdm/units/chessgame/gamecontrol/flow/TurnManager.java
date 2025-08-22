package com.sdm.units.chessgame.gamecontrol.flow;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;

public class TurnManager {
    
    private ChessPieceColor current = ChessPieceColor.WHITE;

    public ChessPieceColor current() {
        return current;
    }

    public ChessPieceColor opponent() {
        return current.opponent();
    }

    public void start() {
        current = ChessPieceColor.WHITE;
    }

    public void swap() {
        current = opponent();
    }
}