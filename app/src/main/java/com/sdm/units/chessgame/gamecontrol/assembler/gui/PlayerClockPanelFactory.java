package com.sdm.units.chessgame.gamecontrol.assembler.gui;

import java.util.EnumMap;

import com.sdm.units.chessgame.gamecontrol.state.GameStateController;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.clock.ChessClock;
import com.sdm.units.chessgame.gui.board.clock.ChessClockPanel;
import com.sdm.units.chessgame.gui.board.clock.ClockFormatter;
import com.sdm.units.chessgame.gui.board.clock.DefaultClockFormatter;

public final class PlayerClockPanelFactory {

    private final GameStateController controller;

    public PlayerClockPanelFactory(GameStateController controller) {
        this.controller = controller;
    }

    public EnumMap<ChessPieceColor, ChessClockPanel> createPanels(EnumMap<ChessPieceColor, ChessClock> clocks) {
        EnumMap<ChessPieceColor, ChessClockPanel> panels = new EnumMap<>(ChessPieceColor.class);

        for (ChessPieceColor color : ChessPieceColor.values()) {
            ChessClock clock = clocks.get(color);

            ChessClockPanel panel = new ChessClockPanel(color, clock);
            ClockFormatter formatter = new DefaultClockFormatter();

            clock.setListener(new ClockDisplayAdapter(panel, formatter, controller, color));

            panels.put(color, panel);
        }

        return panels;
    }
}