package unittest.chessgame.gamelogic.move.result;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.result.CaptureResult;
import com.sdm.units.chessgame.gamelogic.move.result.MoveResult;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import unittest.chessgame.gamelogic.testdoubles.PieceDummy;

import java.util.Optional;
import java.util.OptionalInt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("MoveAndCaptureResult")
class MoveAndCaptureResultTest {

    private ChessPieceInfo info;
    private ChessPiece capturedPiece;
    private ReversibleMove move;

    @BeforeEach
    void setUp() {
        info = ChessPieceInfo.ROOK;
        capturedPiece = new PieceDummy(ChessPieceColor.BLACK, info);
        move = mock(ReversibleMove.class);
    }

    @Nested
    @DisplayName("CaptureResult")
    class CaptureResultTests {

        @Test
        @DisplayName("should indicate no capture if no piece is present")
        void shouldIndicateNoCaptureWhenNoPiecePresent() {
            CaptureResult result = CaptureResult.none();

            assertThat(result.isCapture()).isFalse();
        }

        @Test
        @DisplayName("should indicate capture when piece is present")
        void shouldIndicateCaptureWhenPiecePresent() {
            CaptureResult result = new CaptureResult(Optional.of(capturedPiece));

            assertThat(result.isCapture()).isTrue();
        }

        @Test
        @DisplayName("should provide no value when no capture")
        void shouldProvideNoValueWhenNoCapture() {
            CaptureResult result = CaptureResult.none();

            assertThat(result.pieceValue()).isEqualTo(OptionalInt.empty());
        }

        @Test
        @DisplayName("should provide the captured piece value when capture occurs")
        void shouldProvideCapturedPieceValueWhenCaptureOccurs() {
            CaptureResult result = new CaptureResult(Optional.of(capturedPiece));

            assertThat(result.pieceValue()).isEqualTo(OptionalInt.of(info.getPieceValue()));
        }
    }

    @Nested
    @DisplayName("MoveResult")
    class MoveResultTests {

        @Test
        @DisplayName("should create move result with no capture")
        void shouldCreateMoveResultWithNoCapture() {
            MoveResult result = MoveResult.noCapture(move);

            assertThat(result.isCapture()).isFalse();
        }

        @Test
        @DisplayName("should provide capture result when move captures piece")
        void shouldProvideCaptureResultWhenMoveCapturesPiece() {
            CaptureResult captureResult = new CaptureResult(Optional.of(capturedPiece));
            MoveResult result = MoveResult.of(move, captureResult);

            assertThat(result.isCapture()).isTrue();
        }

        @Test
        @DisplayName("should expose captured piece when present")
        void shouldExposeCapturedPieceWhenPresent() {
            CaptureResult captureResult = new CaptureResult(Optional.of(capturedPiece));
            MoveResult result = MoveResult.of(move, captureResult);

            assertThat(result.capturedPiece()).contains(capturedPiece);
        }

        @Test
        @DisplayName("should expose captured piece value when present")
        void shouldExposeCapturedPieceValueWhenPresent() {
            CaptureResult captureResult = new CaptureResult(Optional.of(capturedPiece));
            MoveResult result = MoveResult.of(move, captureResult);

            assertThat(result.capturedPieceValue()).isEqualTo(OptionalInt.of(info.getPieceValue()));
        }

        @Test
        @DisplayName("should return no captured piece when no capture")
        void shouldReturnNoCapturedPieceWhenNoCapture() {
            MoveResult result = MoveResult.noCapture(move);

            assertThat(result.capturedPiece()).isEmpty();
        }
    }
}