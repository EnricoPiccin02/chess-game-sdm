package unittest.chessgame.gui.testdoubles;

import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gui.board.square.ChessboardSquareHandler;
import com.sdm.units.chessgame.gui.board.square.HighlightColor;
import com.sdm.units.chessgame.gui.board.square.SquareClickHandler;

public class ChessboardSquareHandlerSpy implements ChessboardSquareHandler {
    
    private final ChessboardViewSpy spy;

    private HighlightColor appliedHighlight;
    private boolean cleared = false;
    private boolean clickHandlerAttached = false;
    private boolean clickHandlerRemoved = false;

    public ChessboardSquareHandlerSpy() {
        this.spy = new ChessboardViewSpy();
    }

    public ChessboardSquareHandlerSpy(ChessboardViewSpy spy) {
        this.spy = spy;
    }

    public HighlightColor getAppliedHighlight() {
        return appliedHighlight;
    }

    public boolean isCleared() {
        return cleared;
    }

    public boolean isClickHandlerAttached() {
        return clickHandlerAttached;
    }

    public boolean isClickHandlerRemoved() {
        return clickHandlerRemoved;
    }

    @Override
    public void highlight(HighlightColor type) {
        appliedHighlight = type;
        spy.setLastHighlightType(type);
    }

    @Override
    public void attachClickHandler(SquareClickHandler clickHandler) {
        clickHandlerAttached = true;
        spy.setListenerAttached(true);
    }

    @Override
    public void removeClickHandler() {
        clickHandlerRemoved = true;
        spy.setListenerRemoved(true);
    }

    @Override
    public void clearHighlight() {
        cleared = true;
        spy.setLastHighlightType(null);
    }

    @Override
    public void refresh() {}

    @Override
    public ChessboardPosition getPosition() {
        return null;
    }

    @Override
    public void setPiece(Optional<ChessPiece> newPiece) {}
}