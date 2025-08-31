package guitest.chessgame.testdoubles;

import java.util.Set;
import java.util.function.Consumer;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gui.board.square.ChessboardSquareHandler;
import com.sdm.units.chessgame.gui.board.view.ChessboardView;

public class ChessboardViewFake implements ChessboardView {
    
    private JComponent component;

    public ChessboardViewFake() {
        component = new JPanel();
    }

    @Override
    public void renderChessboardState(Chessboard board) {}

    @Override
    public void updateSquaresAt(Set<ChessboardPosition> positions, Consumer<? super ChessboardSquareHandler> operation) {}
    
    @Override
    public void updateAllSquares(Consumer<? super ChessboardSquareHandler> operation) {}

    @Override
    public JComponent asComponent() {
        return component;
    }
}