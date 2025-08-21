package test.chessgame.gui.testdoubles;

import javax.swing.JComponent;
import javax.swing.JTextArea;

import com.sdm.units.chessgame.gui.board.view.MoveHistoryView;

public class MoveHistoryAreaFake implements MoveHistoryView {

    private final StringBuilder stringBuilder = new StringBuilder();

    @Override
    public void append(String msg) {
        stringBuilder.append(msg);
    }

    public String getText() {
        return stringBuilder.toString();
    }

    @Override
    public void clear() {
        stringBuilder.setLength(0);
    }

    @Override
    public JComponent asComponent() {
        return new JTextArea();
    }
}