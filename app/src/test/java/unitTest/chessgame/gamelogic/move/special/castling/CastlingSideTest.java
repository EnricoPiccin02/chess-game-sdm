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
        @DisplayName("should return correct king destination for KING_SIDE")
        void shouldReturnCorrectKingDestinationForKingSide() {
            ChessboardPosition result = CastlingSide.KING_SIDE.getKingTo(kingFromE1);
            assertThat(result).isEqualTo(new ChessboardPosition(ChessboardFile.G, ChessboardRank.ONE));
        }

        @Test
        @DisplayName("should return correct king destination for QUEEN_SIDE")
        void shouldReturnCorrectKingDestinationForQueenSide() {
            ChessboardPosition result = CastlingSide.QUEEN_SIDE.getKingTo(kingFromE8);
            assertThat(result).isEqualTo(new ChessboardPosition(ChessboardFile.C, ChessboardRank.EIGHT));
        }

        @Test
        @DisplayName("should return correct rook origin for KING_SIDE")
        void shouldReturnCorrectRookOriginForKingSide() {
            ChessboardPosition result = CastlingSide.KING_SIDE.getRookFrom(kingFromE1);
            assertThat(result).isEqualTo(new ChessboardPosition(ChessboardFile.H, ChessboardRank.ONE));
        }

        @Test
        @DisplayName("should return correct rook destination for QUEEN_SIDE")
        void shouldReturnCorrectRookDestinationForQueenSide() {
            ChessboardPosition result = CastlingSide.QUEEN_SIDE.getRookTo(kingFromE8);
            assertThat(result).isEqualTo(new ChessboardPosition(ChessboardFile.D, ChessboardRank.EIGHT));
        }
    }

    @Nested
    @DisplayName("King path")
    class KingPath {

        @Test
        @DisplayName("should return correct path squares for KING_SIDE")
        void shouldReturnCorrectPathSquaresForKingSide() {
            List<ChessboardPosition> result = CastlingSide.KING_SIDE.getKingPath(kingFromE1);
            assertThat(result)
                .containsExactly(
                    new ChessboardPosition(ChessboardFile.F, ChessboardRank.ONE),
                    new ChessboardPosition(ChessboardFile.G, ChessboardRank.ONE)
                );
        }

        @Test
        @DisplayName("should return correct path squares for QUEEN_SIDE")
        void shouldReturnCorrectPathSquaresForQueenSide() {
            List<ChessboardPosition> result = CastlingSide.QUEEN_SIDE.getKingPath(kingFromE8);
            assertThat(result)
                .containsExactly(
                    new ChessboardPosition(ChessboardFile.D, ChessboardRank.EIGHT),
                    new ChessboardPosition(ChessboardFile.C, ChessboardRank.EIGHT)
                );
        }
    }

    @Nested
    @DisplayName("fromRookFile")
    class FromRookFile {

        @Test
        @DisplayName("should return KING_SIDE for rook file H")
        void shouldReturnKingSideForFileH() {
            Optional<CastlingSide> result = CastlingSide.fromRookFile(ChessboardFile.H);
            assertThat(result).contains(CastlingSide.KING_SIDE);
        }

        @Test
        @DisplayName("should return QUEEN_SIDE for rook file A")
        void shouldReturnQueenSideForFileA() {
            Optional<CastlingSide> result = CastlingSide.fromRookFile(ChessboardFile.A);
            assertThat(result).contains(CastlingSide.QUEEN_SIDE);
        }

        @Test
        @DisplayName("should return empty when file does not match a rook position")
        void shouldReturnEmptyForNonRookFile() {
            Optional<CastlingSide> result = CastlingSide.fromRookFile(ChessboardFile.B);
            assertThat(result).isEmpty();
        }
    }
}