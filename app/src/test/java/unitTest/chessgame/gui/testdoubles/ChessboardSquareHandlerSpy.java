package unittest.chessgame.gui.testdoubles;

import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gui.board.square.ChessboardSquareHandler;
import com.sdm.units.chessgame.gui.board.square.HighlightColor;
import com.sdm.units.chessgame.gui.board.square.SquareClickHandler;

public class ChessboardSquareHandlerSpy implements ChessboardSquareHandler {
    
    private final ChessboardViewSpy spy;

    public ChessboardSquareHandlerSpy(ChessboardViewSpy spy) {
        this.spy = spy;
    }

    @Override
    public void highlight(HighlightColor type) {
        spy.setLastHighlightType(type);
    }

    @Override
    public void attachClickHandler(SquareClickHandler clickHandler) {
        spy.setListenerAttached(true);
    }

    @Override
    public void removeClickHandler() {
        spy.setListenerRemoved(true);
    }

    @Override
    public void clearHighlight() {
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