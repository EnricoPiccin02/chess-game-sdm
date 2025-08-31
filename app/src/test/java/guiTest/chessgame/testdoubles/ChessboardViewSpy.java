package guitest.chessgame.testdoubles;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;

public class ChessboardViewSpy extends ChessboardViewFake {
    
    private boolean renderCalled = false;
    private Chessboard lastBoard;

    public boolean isRenderCalled() {
        return renderCalled;
    }

    public Chessboard getLastBoard() {
        return lastBoard;
    }

    @Override
    public void renderChessboardState(Chessboard board) {
        renderCalled = true;
        lastBoard = board;
    }
}