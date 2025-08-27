package unittest.chessgame.gamelogic.board.state;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.board.state.KingLocator;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;

import unittest.chessgame.gamelogic.testdoubles.ChessboardStub;
import unittest.chessgame.gamelogic.testdoubles.PieceDummy;

@DisplayName("KingLocator")
class KingLocatorTest {

    private KingLocator locator;
    private ChessboardStub board;

    @BeforeEach
    void setUp() {
        locator = new KingLocator();
        board = new ChessboardStub();
    }

    @Nested
    @DisplayName("when board has no king of given color")
    class NoKingPresent {

        @Test
        @DisplayName("should not find king when no king of color present")
        void shouldNotFindKingWhenNoKingOfColorPresent() {
            ChessboardPosition e4 = new ChessboardPosition(ChessboardFile.E, ChessboardRank.FOUR);
            board.placePiece(new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.QUEEN), e4);

            Optional<ChessboardPosition> result = locator.locateFirstOf(board, ChessPieceColor.WHITE);

            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("when board has exactly one king of given color")
    class SingleKingPresent {

        @Test
        @DisplayName("should locate king correctly")
        void shouldLocateKingOfGivenColorWhenPresent() {
            ChessboardPosition e1 = new ChessboardPosition(ChessboardFile.E, ChessboardRank.ONE);
            board.placePiece(new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.KING), e1);

            Optional<ChessboardPosition> result = locator.locateFirstOf(board, ChessPieceColor.WHITE);

            assertThat(result).contains(e1);
        }
    }

    @Nested
    @DisplayName("when board has multiple kings of given color")
    class MultipleKingsPresent {

        @Test
        @DisplayName("should locate the first king found in iteration order")
        void shouldLocateFirstKingWhenMultipleKingsPresent() {
            ChessboardPosition e1 = new ChessboardPosition(ChessboardFile.E, ChessboardRank.ONE);
            ChessboardPosition d4 = new ChessboardPosition(ChessboardFile.D, ChessboardRank.FOUR);
            board.placePiece(new PieceDummy(ChessPieceColor.BLACK, ChessPieceInfo.KING), e1);
            board.placePiece(new PieceDummy(ChessPieceColor.BLACK, ChessPieceInfo.KING), d4);

            Optional<ChessboardPosition> result = locator.locateFirstOf(board, ChessPieceColor.BLACK);

            assertThat(result).isPresent();
            assertThat(Set.of(e1, d4)).contains(result.get());
        }
    }

    @Nested
    @DisplayName("when board has king of opponent color only")
    class OpponentKingOnly {

        @Test
        @DisplayName("should not find king for requested color")
        void shouldNotFindKingForRequestedColor() {
            ChessboardPosition e8 = new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT);
            board.placePiece(new PieceDummy(ChessPieceColor.BLACK, ChessPieceInfo.KING), e8);

            Optional<ChessboardPosition> result = locator.locateFirstOf(board, ChessPieceColor.WHITE);

            assertThat(result).isEmpty();
        }
    }
}