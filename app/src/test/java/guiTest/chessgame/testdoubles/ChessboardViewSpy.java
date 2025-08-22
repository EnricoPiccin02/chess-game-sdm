package guitest.chessgame.testdoubles;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gui.board.square.ChessboardSquareComponent;
import com.sdm.units.chessgame.gui.board.square.HighlightColor;
import com.sdm.units.chessgame.gui.board.view.ChessboardView;

public class ChessboardViewSpy implements ChessboardView {
    
    private final ChessboardPosition dummyPosition = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
    private boolean renderCalled = false;
    private Chessboard lastBoard;
    private Set<ChessboardPosition> updatedSquares = new HashSet<>();
    private HighlightColor lastHighlightType;
    private boolean listenerAttached;
    private boolean listenerRemoved;
    private boolean updateAllCalled;

    public boolean isRenderCalled() {
        return renderCalled;
    }

    public Chessboard getLastBoard() {
        return lastBoard;
    }

    public Set<ChessboardPosition> getUpdatedSquares() {
        return updatedSquares;
    }

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

    @Override
    public void renderChessboardState(Chessboard board) {
        renderCalled = true;
        lastBoard = board;
    }

    @Override
    public void updateSquaresAt(Set<ChessboardPosition> positions, Consumer<? super ChessboardSquareComponent> operation) {
        updatedSquares.addAll(positions);
        operation.accept(new ChessboardSquareComponentSpy(dummyPosition, this));
    }
    
    @Override
    public void updateAllSquares(Consumer<? super ChessboardSquareComponent> operation) {
        updateAllCalled = true;
        operation.accept(new ChessboardSquareComponentSpy(dummyPosition, this));
    }

    @Override
    public JComponent asComponent() {
        return new JPanel();
    }
}