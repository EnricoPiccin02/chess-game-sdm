package unitTest.chessgame.gamelogic.testdoubles;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.move.core.MoveComponent;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.result.CaptureResult;

public class ReversibleMoveStub implements ReversibleMove {

    private final CaptureResult result;
    private final MoveComponent component;
    private boolean executed;
    private boolean undone;

    public ReversibleMoveStub(CaptureResult result, MoveComponent component) {
        this.result = result;
        this.component = component;
    }

    @Override
    public CaptureResult executeOn(Chessboard board) {
        executed = true;
        return result;
    }

    @Override
    public CaptureResult undoOn(Chessboard board) {
        undone = true;
        return result;
    }

    public boolean wasExecuted() {
        return executed;
    }

    public boolean wasUndone() {
        return undone;
    }

    @Override
    public MoveComponent getPrimaryMoveComponent() {
        return component;
    }
}