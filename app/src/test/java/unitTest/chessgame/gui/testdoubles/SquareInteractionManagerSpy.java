package unittest.chessgame.gui.testdoubles;

import com.sdm.units.chessgame.gui.board.square.ChessboardSquareHandler;
import com.sdm.units.chessgame.gui.board.square.SquareClickHandler;
import com.sdm.units.chessgame.gui.controller.interaction.SquareInteractionManager;

public class SquareInteractionManagerSpy implements SquareInteractionManager {

    private boolean noneCalled = false;
    private boolean selectableCalled = false;
    private boolean hoverCalled = false;
    private boolean clickedCalled = false;

    public boolean isNoneCalled() {
        return noneCalled;
    }

    public boolean isSelectableCalled() {
        return selectableCalled;
    }

    public boolean isHoverCalled() {
        return hoverCalled;
    }

    public boolean isClickedCalled() {
        return clickedCalled;
    }

    @Override
    public void setNone(ChessboardSquareHandler square) {
        noneCalled = true;
    }

    @Override
    public void setSelectable(ChessboardSquareHandler square, SquareClickHandler handler) {
        selectableCalled = true;
    }

    @Override
    public void setHover(ChessboardSquareHandler square) {
        hoverCalled = true;
    }

    @Override
    public void setClicked(ChessboardSquareHandler square) {
        clickedCalled = true;
    }
}