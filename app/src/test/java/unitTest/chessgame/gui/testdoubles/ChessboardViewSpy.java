package unittest.chessgame.gui.testdoubles;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import javax.swing.JComponent;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gui.board.square.ChessboardSquareHandler;
import com.sdm.units.chessgame.gui.board.view.ChessboardView;

public class ChessboardViewSpy implements ChessboardView {
    
    private boolean updateAllCalled;
    private Set<ChessboardPosition> updatedSquares = new HashSet<>();

    public boolean isUpdateAllCalled() {
        return updateAllCalled;
    }

    public Set<ChessboardPosition> getUpdatedSquares() {
        return updatedSquares;
    }

    @Override
    public void updateSquaresAt(Set<ChessboardPosition> positions, Consumer<? super ChessboardSquareHandler> operation) {
        updatedSquares.addAll(positions);
        operation.accept(null);
    }
    
    @Override
    public void updateAllSquares(Consumer<? super ChessboardSquareHandler> operation) {
        updateAllCalled = true;
        operation.accept(null);
    }

    @Override
    public JComponent asComponent() {
        return null;
    }

    @Override
    public void renderChessboardState(Chessboard board) {}
}