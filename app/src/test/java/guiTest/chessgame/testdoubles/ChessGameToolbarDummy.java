package guiTest.chessgame.testdoubles;

import static org.mockito.Mockito.mock;

import com.sdm.units.chessgame.gui.board.view.ChessGameToolbar;
import com.sdm.units.chessgame.gui.controller.interaction.ToolbarInteractionController;

public class ChessGameToolbarDummy extends ChessGameToolbar {
    
    public ChessGameToolbarDummy() {
        super(mock(ToolbarInteractionController.class));
    }
}