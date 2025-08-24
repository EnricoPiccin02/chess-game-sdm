package com.sdm.units.chessgame.gamecontrol.assembler.gui;

import com.sdm.units.chessgame.gamecontrol.state.GameReason;
import com.sdm.units.chessgame.gamecontrol.state.GameStateController;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.clock.ChessClockListener;
import com.sdm.units.chessgame.gui.board.clock.ChessClockPanel;
import com.sdm.units.chessgame.gui.board.clock.ClockFormatter;

public final class ClockDisplayAdapter implements ChessClockListener {

    private final ChessClockPanel panel;
    private final ClockFormatter formatter;
    private final GameStateController controller;
    private final ChessPieceColor color;

    public ClockDisplayAdapter(ChessClockPanel panel, ClockFormatter formatter, GameStateController controller, ChessPieceColor color) {
        this.panel = panel;
        this.formatter = formatter;
        this.controller = controller;
        this.color = color;
    }

    @Override
    public void onTimeUpdated(long remainingMillis) {
        panel.updateTime(formatter.formatTime(remainingMillis));
    }

    @Override
    public void onTimeExpired() {
        controller.proclaimWinner(color.opponent(), GameReason.TIMEOUT);
    }
}