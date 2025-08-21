package guiTest.chessgame.testdoubles;

import javax.swing.JComponent;

import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gui.pieces.ChessPieceViewRegistry;

public class ChessPieceViewRegistryStub extends ChessPieceViewRegistry {
    
    private final JComponent fakeComponent;

    public ChessPieceViewRegistryStub(JComponent fakeComponent) {
        super(null, null);
        this.fakeComponent = fakeComponent;
    }

    @Override
    public JComponent createComponentFor(ChessPiece piece) {
        return fakeComponent;
    }
}