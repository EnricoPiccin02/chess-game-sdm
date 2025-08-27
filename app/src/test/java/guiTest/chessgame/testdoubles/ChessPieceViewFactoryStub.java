package guitest.chessgame.testdoubles;

import javax.swing.JComponent;

import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gui.pieces.PieceViewFactory;

public class ChessPieceViewFactoryStub implements PieceViewFactory {
    
    private final JComponent fakeComponent;

    public ChessPieceViewFactoryStub(JComponent fakeComponent) {
        this.fakeComponent = fakeComponent;
    }

    @Override
    public JComponent createComponentFor(ChessPiece piece) {
        return fakeComponent;
    }
}