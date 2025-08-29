package unittest.chessgame.gamecontrol.flow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamecontrol.flow.ScoreKeeper;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.result.CaptureResult;
import com.sdm.units.chessgame.gamelogic.move.result.MoveResult;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import unittest.chessgame.gamelogic.testdoubles.PieceDummy;

@DisplayName("ScoreKeeper")
class ScoreKeeperTest {

    private ScoreKeeper scoreKeeper;
    private ReversibleMove dummyMove;
    private ChessPiece capturedPawn;
    private MoveResult captureResult;

    @BeforeEach
    void setUp() {
        scoreKeeper = new ScoreKeeper();
        dummyMove = mock(ReversibleMove.class);
        capturedPawn = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.PAWN);
        captureResult = new MoveResult(dummyMove, new CaptureResult(Optional.of(capturedPawn)));
    }

    @Nested
    @DisplayName("Initialization")
    class Initialization {

        @Test
        @DisplayName("should start with zero scores for white player")
        void shouldStartWithZeroScoreForWhite() {
            assertEquals(0, scoreKeeper.scoreOf(ChessPieceColor.WHITE));
        }

        @Test
        @DisplayName("should start with zero scores for black player")
        void shouldStartWithZeroScoreForBlack() {
            assertEquals(0, scoreKeeper.scoreOf(ChessPieceColor.BLACK));
        }
    }

    @Nested
    @DisplayName("Applying a capture")
    class ApplyCapture {

        @Test
        @DisplayName("should increase the score of the capturing player by captured piece value")
        void shouldIncreaseScoreOnCapture() {
            scoreKeeper.apply(captureResult, ChessPieceColor.WHITE);

            assertEquals(1, scoreKeeper.scoreOf(ChessPieceColor.WHITE));
        }
    }

    @Nested
    @DisplayName("Reverting a capture")
    class RevertCapture {

        @BeforeEach
        void setUp() {
            scoreKeeper.apply(captureResult, ChessPieceColor.BLACK);
        }

        @Test
        @DisplayName("should restore score to previous value when reverted")
        void shouldRestoreScoreOnRevert() {
            scoreKeeper.revert(captureResult, ChessPieceColor.BLACK);

            assertEquals(0, scoreKeeper.scoreOf(ChessPieceColor.BLACK));
        }
    }

    @Nested
    @DisplayName("Resetting scores")
    class ResetScores {

        @BeforeEach
        void setUp() {
            scoreKeeper.apply(captureResult, ChessPieceColor.WHITE);
        }

        @Test
        @DisplayName("should reset white player's score to zero")
        void shouldResetWhiteScore() {
            scoreKeeper.reset();

            assertEquals(0, scoreKeeper.scoreOf(ChessPieceColor.WHITE));
        }

        @Test
        @DisplayName("should reset black player's score to zero")
        void shouldResetBlackScore() {
            scoreKeeper.reset();

            assertEquals(0, scoreKeeper.scoreOf(ChessPieceColor.BLACK));
        }
    }
}