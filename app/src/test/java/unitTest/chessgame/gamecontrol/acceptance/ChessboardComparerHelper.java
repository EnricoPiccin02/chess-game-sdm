package unittest.chessgame.gamecontrol.acceptance;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;

public class ChessboardComparerHelper {

    public boolean equals(Chessboard a, Chessboard b) {
        for (ChessboardFile file : ChessboardFile.values()) {
            for (ChessboardRank rank : ChessboardRank.values()) {
                ChessboardPosition pos = new ChessboardPosition(file, rank);
                if (!a.getPieceAt(pos).equals(b.getPieceAt(pos))) {
                    return false;
                }
            }
        }
        return true;
    }
}