package com.sdm.units.chessgame.gui.board.clock;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;

public class ChessClockPanel extends JPanel implements ChessClockView {

    private final JLabel clockLabel = new JLabel();
    private final ChessClock clock;

    public ChessClockPanel(ChessPieceColor color, ChessClock clock) {
        this.clock = clock;
        
        setLayout(new GridBagLayout());
        add(new JLabel(color.toString()));
        add(clockLabel);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));        
    }

    @Override
    public ChessClock getClock() {
        return clock;
    }

    @Override
    public void reset() {
        clock.reset();
    }

    @Override
    public void updateTime(String time) {
        clockLabel.setText(time);
    }

    @Override
    public String getTime() {
        return clockLabel.getText();
    }

    @Override
    public JComponent asComponent() {
        return this;
    }
}