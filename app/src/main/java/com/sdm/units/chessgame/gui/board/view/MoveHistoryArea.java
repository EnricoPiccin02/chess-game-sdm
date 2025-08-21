package com.sdm.units.chessgame.gui.board.view;

import javax.swing.JComponent;
import javax.swing.JTextArea;

public class MoveHistoryArea extends JTextArea implements MoveHistoryView {

    private static final int MOVE_HISTORY_ROWS = 4;
    private static final int MOVE_HISTORY_COLS = 30;

    public MoveHistoryArea() {
        super(MOVE_HISTORY_ROWS, MOVE_HISTORY_COLS);
        setEditable(false);
    }

    @Override
    public String getText() {
        return super.getText();
    }

    @Override
    public void append(String msg) {
        super.append(msg + "\n");
    }

    @Override
    public void clear() {
        super.setText("");
    }

    @Override
    public JComponent asComponent() {
        return this;
    }
}