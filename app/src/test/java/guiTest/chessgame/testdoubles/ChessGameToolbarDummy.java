package guitest.chessgame.testdoubles;

import javax.swing.JComponent;
import javax.swing.JToolBar;

import com.sdm.units.chessgame.gui.board.view.GameToolbarView;

public class ChessGameToolbarDummy implements GameToolbarView {

    @Override
    public void restart() {
        throw new UnsupportedOperationException("Dummy should not be asked for 'restart'");
    }

    @Override
    public void undo() {
        throw new UnsupportedOperationException("Dummy should not be asked for 'undo'");
    }

    @Override
    public void exit() {
        throw new UnsupportedOperationException("Dummy should not be asked for 'exit'");
    }

    @Override
    public JComponent asComponent() {
        return new JToolBar();
    }
}