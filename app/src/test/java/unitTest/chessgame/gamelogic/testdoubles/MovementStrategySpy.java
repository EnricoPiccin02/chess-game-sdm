package unittest.chessgame.gamelogic.testdoubles;

import java.util.Set;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.movement.MovementStrategy;

public class MovementStrategySpy implements MovementStrategy {
    
    private boolean called = false;

    public boolean isCalled() {
        return called;
    }

    @Override
    public Set<ChessboardPosition> getLegalDestinations(Chessboard board, ChessboardPosition from, ChessPieceColor color) {
        called = true;
        return Set.of();
    }
}