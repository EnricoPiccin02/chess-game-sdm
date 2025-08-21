package test.chessgame.gui.testdoubles;

import java.util.Optional;

import org.mockito.Mockito;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gui.board.square.ChessboardSquareComponent;
import com.sdm.units.chessgame.gui.board.square.HighlightColor;
import com.sdm.units.chessgame.gui.board.square.HighlightRenderer;
import com.sdm.units.chessgame.gui.board.square.SquareClickHandler;
import com.sdm.units.chessgame.gui.pieces.ChessPieceViewRegistry;

public class ChessboardSquareComponentSpy extends ChessboardSquareComponent {
    
    private final ChessboardViewSpy spy;
    private Optional<ChessPiece> lastPiece = Optional.empty();
    private int timesCalledSetPiece = 0;

    public ChessboardSquareComponentSpy(ChessboardPosition position, ChessboardViewSpy spy) {
        super(position, Mockito.mock(ChessPieceViewRegistry.class), Mockito.mock(HighlightRenderer.class));
        this.spy = spy;
    }

    @Override
    public void setPiece(Optional<ChessPiece> newPiece) {
        this.lastPiece = newPiece;
        this.timesCalledSetPiece++;
    }

    public Optional<ChessPiece> lastPiece() {
        return lastPiece;
    }

    public int timesCalledSetPiece() {
        return timesCalledSetPiece;
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
}