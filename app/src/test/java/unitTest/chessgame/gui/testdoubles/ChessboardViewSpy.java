package unittest.chessgame.gui.testdoubles;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import javax.swing.JComponent;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gui.board.square.ChessboardSquareHandler;
import com.sdm.units.chessgame.gui.board.square.HighlightColor;
import com.sdm.units.chessgame.gui.board.view.ChessboardView;

public class ChessboardViewSpy implements ChessboardView {
    
    private HighlightColor lastHighlightType;
    private boolean listenerAttached;
    private boolean listenerRemoved;
    private boolean updateAllCalled;
    private Set<ChessboardPosition> updatedSquares = new HashSet<>();

    public void setLastHighlightType(HighlightColor lastHighlightType) {
        this.lastHighlightType = lastHighlightType;
    }

    public void setListenerAttached(boolean listenerAttached) {
        this.listenerAttached = listenerAttached;
    }

    public void setListenerRemoved(boolean listenerRemoved) {
        this.listenerRemoved = listenerRemoved;
    }

    public HighlightColor getLastHighlightType() {
        return lastHighlightType;
    }

    public boolean isListenerAttached() {
        return listenerAttached;
    }

    public boolean isListenerRemoved() {
        return listenerRemoved;
    }

    public boolean isUpdateAllCalled() {
        return updateAllCalled;
    }

    public Set<ChessboardPosition> getUpdatedSquares() {
        return updatedSquares;
    }

    @Override
    public void updateSquaresAt(Set<ChessboardPosition> positions, Consumer<? super ChessboardSquareHandler> operation) {
        updatedSquares.addAll(positions);
        operation.accept(new ChessboardSquareHandlerSpy(this));
    }
    
    @Override
    public void updateAllSquares(Consumer<? super ChessboardSquareHandler> operation) {
        updateAllCalled = true;
        operation.accept(new ChessboardSquareHandlerSpy(this));
    }

    @Override
    public JComponent asComponent() {
        return null;
    }

    @Override
    public void renderChessboardState(Chessboard board) {}
}