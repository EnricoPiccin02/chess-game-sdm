package unittest.chessgame.gamecontrol.flow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamecontrol.flow.ScoreKeeper;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.result.CaptureResult;
import com.sdm.units.chessgame.gamelogic.move.result.MoveResult;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

@DisplayName("ScoreKeeper")
class ScoreKeeperTest {

    private ScoreKeeper scores;
    private ReversibleMove dummyMove;
    private ChessPiece dummyPiece;
    private MoveResult result;

    @BeforeEach
    void setUp() {
        scores = new ScoreKeeper();
        dummyMove = mock(ReversibleMove.class);
        dummyPiece = mock(ChessPiece.class);

        when(dummyPiece.pieceInfo()).thenReturn(ChessPieceInfo.PAWN);
        result = new MoveResult(dummyMove, new CaptureResult(Optional.of(dummyPiece)));
    }

    @Test
    @DisplayName("should start with zero for all players")
    void shouldStartWithZero() {
        assertEquals(0, scores.scoreOf(ChessPieceColor.WHITE));
        assertEquals(0, scores.scoreOf(ChessPieceColor.BLACK));
    }

    @Test
    @DisplayName("should add captured piece value on apply")
    void shouldApplyCapturedScore() {
        scores.apply(result, ChessPieceColor.WHITE);

        assertEquals(1, scores.scoreOf(ChessPieceColor.WHITE));
    }

    @Test
    @DisplayName("should subtract captured piece value on revert")
    void shouldRevertCapturedScore() {
        scores.apply(result, ChessPieceColor.BLACK);
        scores.revert(result, ChessPieceColor.BLACK);

        assertEquals(0, scores.scoreOf(ChessPieceColor.BLACK));
    }

    @Test
    @DisplayName("should reset scores for both players")
    void shouldResetScores() {
        scores.apply(result, ChessPieceColor.WHITE);
        scores.reset();

        assertEquals(0, scores.scoreOf(ChessPieceColor.WHITE));
        assertEquals(0, scores.scoreOf(ChessPieceColor.BLACK));
    }
}