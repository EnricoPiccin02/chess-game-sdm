package unittest.chessgame.gamelogic.move.special.castling;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingSide;

@DisplayName("CastlingSide")
class CastlingSideTest {

    private ChessboardPosition kingFromE1;
    private ChessboardPosition kingFromE8;

    @BeforeEach
    void setUp() {
        kingFromE1 = new ChessboardPosition(ChessboardFile.E, ChessboardRank.ONE);
        kingFromE8 = new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT);
    }

    @Nested
    @DisplayName("King and rook positions")
    class KingAndRookPositions {

        @Test
        @DisplayName("should provide correct king destination for Kingside castling")
        void shouldProvideCorrectKingDestinationForKingSide() {
            ChessboardPosition result = CastlingSide.KING_SIDE.getKingTo(kingFromE1);
            assertThat(result).isEqualTo(new ChessboardPosition(ChessboardFile.G, ChessboardRank.ONE));
        }

        @Test
        @DisplayName("should provide correct king destination for Queenside castling")
        void shouldProvideCorrectKingDestinationForQueenSide() {
            ChessboardPosition result = CastlingSide.QUEEN_SIDE.getKingTo(kingFromE8);
            assertThat(result).isEqualTo(new ChessboardPosition(ChessboardFile.C, ChessboardRank.EIGHT));
        }

        @Test
        @DisplayName("should provide correct rook origin for Kingside castling")
        void shouldProvideCorrectRookOriginForKingSide() {
            ChessboardPosition result = CastlingSide.KING_SIDE.getRookFrom(kingFromE1);
            assertThat(result).isEqualTo(new ChessboardPosition(ChessboardFile.H, ChessboardRank.ONE));
        }

        @Test
        @DisplayName("should provide correct rook destination for Queenside castling")
        void shouldProvideCorrectRookDestinationForQueenSide() {
            ChessboardPosition result = CastlingSide.QUEEN_SIDE.getRookTo(kingFromE8);
            assertThat(result).isEqualTo(new ChessboardPosition(ChessboardFile.D, ChessboardRank.EIGHT));
        }
    }

    @Nested
    @DisplayName("King path")
    class KingPath {

        @Test
        @DisplayName("should supply correct path squares for Kingside castling")
        void shouldSupplyCorrectPathSquaresForKingSide() {
            List<ChessboardPosition> result = CastlingSide.KING_SIDE.getKingPath(kingFromE1);
            assertThat(result)
                .containsExactly(
                    new ChessboardPosition(ChessboardFile.F, ChessboardRank.ONE),
                    new ChessboardPosition(ChessboardFile.G, ChessboardRank.ONE)
                );
        }

        @Test
        @DisplayName("should supply correct path squares for Queenside castling")
        void shouldSupplyCorrectPathSquaresForQueenSide() {
            List<ChessboardPosition> result = CastlingSide.QUEEN_SIDE.getKingPath(kingFromE8);
            assertThat(result)
                .containsExactly(
                    new ChessboardPosition(ChessboardFile.D, ChessboardRank.EIGHT),
                    new ChessboardPosition(ChessboardFile.C, ChessboardRank.EIGHT)
                );
        }
    }

    @Nested
    @DisplayName("castling side from rook file")
    class CastlingSideFromRookFile {

        @Test
        @DisplayName("should recognize Kingside castling when rook is in correct file")
        void shouldRecognizeKingSideForFileH() {
            Optional<CastlingSide> result = CastlingSide.fromRookFile(ChessboardFile.H);
            assertThat(result).contains(CastlingSide.KING_SIDE);
        }

        @Test
        @DisplayName("should recognize Queenside castling when rook is in correct file")
        void shouldRecognizeQueenSideForFileA() {
            Optional<CastlingSide> result = CastlingSide.fromRookFile(ChessboardFile.A);
            assertThat(result).contains(CastlingSide.QUEEN_SIDE);
        }

        @Test
        @DisplayName("should not recognize any valid castling side when file does not match a rook position")
        void shouldNotRecognizeAnyValidCastlingSideWhenNoMatch() {
            Optional<CastlingSide> result = CastlingSide.fromRookFile(ChessboardFile.B);
            assertThat(result).isEmpty();
        }
    }
}