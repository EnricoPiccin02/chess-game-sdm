package unittest.chessgame.gui.testdoubles;

import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gui.board.square.ChessboardSquareHandler;
import com.sdm.units.chessgame.gui.board.square.HighlightColor;
import com.sdm.units.chessgame.gui.board.square.SquareClickHandler;
import com.sdm.units.chessgame.gui.controller.interaction.SquareInteractionManager;

public class ChessboardSquareHandlerSpy implements ChessboardSquareHandler {

    private HighlightColor appliedHighlight;
    private boolean cleared = false;
    private boolean clickHandlerAttached = false;
    private boolean clickHandlerRemoved = false;

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
    }

    @Override
    public void setClickHandler(SquareClickHandler clickHandler, SquareInteractionManager manager) {
        clickHandlerAttached = true;
    }

    @Override
    public void removeClickHandler() {
        clickHandlerRemoved = true;
    }

    @Override
    public void clearHighlight() {
        cleared = true;
    }

    @Override
    public void refresh() {}

    @Override
    public ChessboardPosition getPosition() {
        return new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
    }

    @Override
    public void setPiece(Optional<ChessPiece> newPiece) {}
}