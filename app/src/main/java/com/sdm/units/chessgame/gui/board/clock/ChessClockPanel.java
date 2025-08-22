package com.sdm.units.chessgame.gui.board.clock;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;

public class ChessClockPanel extends JPanel {

    private final JLabel clockLabel = new JLabel();
    private final ChessClock clock;

    public ChessClockPanel(ChessPieceColor color, ChessClock clock) {
        this.clock = clock;
        
        setLayout(new GridBagLayout());
        add(new JLabel(color.toString()));
        add(clockLabel);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));        
    }

    public ChessClock getClock() {
        return clock;
    }

    public void reset() {
        clock.reset();
    }

    public void updateTime(String time) {
        clockLabel.setText(time);
    }

    public String getTime() {
        return clockLabel.getText();
    }
}