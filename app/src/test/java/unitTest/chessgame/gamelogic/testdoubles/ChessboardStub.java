package unitTest.chessgame.gamelogic.testdoubles;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public class ChessboardStub extends ChessboardFake {

    private final Map<ChessPieceColor, Set<ChessboardPosition>> occupiedSquares = new HashMap<>();

    public void setOccupiedSquares(ChessPieceColor color, Set<ChessboardPosition> positions) {
        occupiedSquares.put(color, positions);
    }

    @Override
    public Set<ChessboardPosition> getOccupiedSquaresOf(ChessPieceColor color) {
        return occupiedSquares.getOrDefault(color, Set.of());
    }
}