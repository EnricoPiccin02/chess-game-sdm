package guitest.chessgame.testdoubles;

import java.util.Optional;

import javax.swing.JComponent;

import com.sdm.units.chessgame.gui.board.square.HighlightRenderer;
import com.sdm.units.chessgame.gui.board.square.HighlightColor;

public class HighlightRendererSpy implements HighlightRenderer {
    
    private Optional<HighlightColor> lastApplied;

    @Override
    public void apply(JComponent component, Optional<HighlightColor> type) {
        this.lastApplied = type;
    }

    public Optional<HighlightColor> getLastApplied() {
        return lastApplied;
    }
}