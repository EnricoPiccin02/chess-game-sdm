package unittest.chessgame.gamelogic.testdoubles;

import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMoveFactory;

public class SpecialMoveFactoryStub<CandidateT> implements SpecialMoveFactory<CandidateT> {

    private ReversibleMove moveToReturn;

    public SpecialMoveFactoryStub(ReversibleMove moveToReturn) {
        this.moveToReturn = moveToReturn;
    }

    @Override
    public ReversibleMove create(CandidateT from) {
        return moveToReturn;
    } 
}