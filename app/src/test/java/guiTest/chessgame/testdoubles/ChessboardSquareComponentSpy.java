package guitest.chessgame.testdoubles;

import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gui.board.square.ChessboardSquareHandler;
import com.sdm.units.chessgame.gui.board.square.HighlightColor;
import com.sdm.units.chessgame.gui.board.square.SquareClickHandler;
import com.sdm.units.chessgame.gui.controller.interaction.SquareInteractionManager;

public class ChessboardSquareComponentSpy implements ChessboardSquareHandler {
    
    private Optional<ChessPiece> lastPiece = Optional.empty();
    private int timesCalledSetPiece = 0;

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
    public void highlight(HighlightColor type) {}

    @Override
    public void setClickHandler(SquareClickHandler clickHandler, SquareInteractionManager manager) {}

    @Override
    public void removeClickHandler() {}

    @Override
    public void clearHighlight() {}

    @Override
    public void refresh() {}

    @Override
    public ChessboardPosition getPosition() {
        return null;
    }
}