package unittest.chessgame.gamecontrol.testdoubles;

import com.sdm.units.chessgame.gamelogic.board.evaluation.GameOutcomeEvaluator;
import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;

public class OutcomeEvaluatorStub implements GameOutcomeEvaluator {
        
    private boolean illegalBecauseOfCheck = false;
    private boolean checkmate = false;

    public void setIllegalBecauseOfCheck(boolean flag) {
        this.illegalBecauseOfCheck = flag;
    }

    public void setCheckmate(boolean flag) {
        this.checkmate = flag;
    }

    @Override
    public boolean isIllegalBecauseOfCheck(Chessboard b, ChessPieceColor c) {
        return illegalBecauseOfCheck;
    }

    @Override
    public boolean isCheckmate(Chessboard b, ChessPieceColor c) {
        return checkmate;
    }
}