package guitest.chessgame.testdoubles;

import java.util.Set;
import java.util.function.Consumer;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gui.board.square.ChessboardSquareHandler;
import com.sdm.units.chessgame.gui.board.view.ChessboardView;

public class ChessboardViewSpy implements ChessboardView {
    
    private boolean renderCalled = false;
    private Chessboard lastBoard;

    public boolean isRenderCalled() {
        return renderCalled;
    }

    public Chessboard getLastBoard() {
        return lastBoard;
    }

    @Override
    public void renderChessboardState(Chessboard board) {
        renderCalled = true;
        lastBoard = board;
    }

    @Override
    public void updateSquaresAt(Set<ChessboardPosition> positions, Consumer<? super ChessboardSquareHandler> operation) {}
    
    @Override
    public void updateAllSquares(Consumer<? super ChessboardSquareHandler> operation) {}

    @Override
    public JComponent asComponent() {
        return new JPanel();
    }
}