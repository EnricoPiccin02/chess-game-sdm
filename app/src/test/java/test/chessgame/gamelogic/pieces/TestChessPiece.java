package test.chessgame.gamelogic.pieces;

import java.util.List;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.movement.MovementStrategy;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public class TestChessPiece extends ChessPiece {
    
    public TestChessPiece(ChessPieceColor color) {
        super(color, ChessPieceInfo.ROOK, new StubStrategy()); 
    }

    private static class StubStrategy implements MovementStrategy {

        @Override
        public List<ChessboardPosition> getLegalMoves(Chessboard board, ChessboardPosition fromPosition, ChessPieceColor playerColor) {
            return List.of();
        }
    }
}