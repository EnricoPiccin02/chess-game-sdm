package com.sdm.units.chessgame.gui.board.view;

import java.awt.GridLayout;
import java.util.EnumMap;

import javax.swing.JPanel;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.clock.ChessClockView;

public class ClocksPanel extends JPanel {
    
    public ClocksPanel(EnumMap<ChessPieceColor, ChessClockView> clocks) {
        super(new GridLayout(1, clocks.size()));
        clocks.values().forEach(clock -> add(clock.asComponent()));
    }
}