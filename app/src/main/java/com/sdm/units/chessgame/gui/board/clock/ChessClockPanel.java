package com.sdm.units.chessgame.gui.board.clock;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;

public class ChessClockPanel extends JPanel {

    private final long CLOCK_TIME = 600000; // 10 minutes

    private final JLabel clockLabel = new JLabel();
    private final ChessClock clock;

    public ChessClockPanel(ChessPieceColor color) {
        setLayout(new GridBagLayout());
        
        add(new JLabel(color.toString()));
        add(clockLabel);

        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        ClockFormatter clockFormatter = new DefaultClockFormatter();
        clockFormatter.setDisplayListener(clockLabel::setText);
        this.clock = new DefaultChessClock(clockFormatter, CLOCK_TIME);
    }

    public ChessClock getClock() {
        return clock;
    }

    public void reset() {
        clock.reset();
    }
}