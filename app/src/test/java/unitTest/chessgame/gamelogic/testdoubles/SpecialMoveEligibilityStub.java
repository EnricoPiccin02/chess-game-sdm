package unittest.chessgame.gamelogic.testdoubles;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMoveEligibility;

public class SpecialMoveEligibilityStub<CandidateT> implements SpecialMoveEligibility<CandidateT> {

    private boolean canExecute = false;

    @Override
    public boolean canExecute(Chessboard board, CandidateT candidate, ChessboardOrientation orientation) {
        return canExecute;
    }

    public void setCanExecute(boolean canExecute) {
        this.canExecute = canExecute;
    }
}