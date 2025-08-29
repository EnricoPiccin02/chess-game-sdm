package guitest.chessgame.testdoubles;

import javax.swing.JComponent;

import com.sdm.units.chessgame.gui.board.square.HighlightColor;
import com.sdm.units.chessgame.gui.board.square.HighlightRenderer;

public class HighlightRendererSpy implements HighlightRenderer {
    
    private HighlightColor lastApplied;

    @Override
    public void render(JComponent component, HighlightColor color) {
        this.lastApplied = color;
    }

    public HighlightColor getLastApplied() {
        return lastApplied;
    }
}