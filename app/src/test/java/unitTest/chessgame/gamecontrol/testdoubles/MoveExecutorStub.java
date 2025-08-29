package unittest.chessgame.gamecontrol.testdoubles;

import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.board.state.MoveExecutor;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.result.MoveResult;

public class MoveExecutorStub implements MoveExecutor {

    private boolean reset = false;
    private boolean undone = false;
    private MoveResult nextResult;
    private Optional<MoveResult> undoResult = Optional.empty();

    @Override
    public MoveResult executeMove(Chessboard board, ReversibleMove move) {
        return nextResult;
    }

    @Override
    public Optional<MoveResult> undoLastMove(Chessboard board) {
        undone = true;
        return undoResult;
    }

    @Override
    public void reset() {
        reset = true;
    }

    public void setNextResult(MoveResult result) {
        this.nextResult = result;
    }
    
    public void setUndoResult(Optional<MoveResult> result) {
        this.undoResult = result;
    }

    public boolean wasReset() {
        return reset;
    }

    public boolean wasUndone() {
        return undone;
    }
}