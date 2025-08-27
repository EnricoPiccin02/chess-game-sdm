package unittest.chessgame.gamelogic.initialization;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.initialization.ChessPiecePlacementStrategy;
import com.sdm.units.chessgame.gamelogic.initialization.ChessboardSetup;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gamelogic.pieces.Rook;

@DisplayName("ChessboardSetup")
class ChessboardSetupTest {

    private ChessPiecePlacementStrategy strategy;
    private ChessboardSetup setup;

    @BeforeEach
    void setUp() {
        strategy = Mockito.mock(ChessPiecePlacementStrategy.class);
        setup = new ChessboardSetup(strategy);
    }

    @ParameterizedTest
    @EnumSource(ChessPieceColor.class)
    @DisplayName("should call placement strategy for both colors")
    void shouldCallStrategyForBothColors(ChessPieceColor color) {
        when(strategy.initialize(any(), any())).thenReturn(Map.of());

        setup.generate(ChessboardOrientation.WHITE_BOTTOM);

        verify(strategy).initialize(eq(color), eq(ChessboardOrientation.WHITE_BOTTOM));
    }

    @Test
    @DisplayName("should merge placements from both colors into the board")
    void shouldMergePlacementsFromBothColors() {
        Map<ChessboardPosition, ChessPiece> whitePieces = Map.of(
            new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE),
            new Rook(ChessPieceColor.WHITE)
        );
        Map<ChessboardPosition, ChessPiece> blackPieces = Map.of(
            new ChessboardPosition(ChessboardFile.A, ChessboardRank.EIGHT),
            new Rook(ChessPieceColor.BLACK)
        );

        when(strategy.initialize(ChessPieceColor.WHITE, ChessboardOrientation.WHITE_BOTTOM)).thenReturn(whitePieces);
        when(strategy.initialize(ChessPieceColor.BLACK, ChessboardOrientation.WHITE_BOTTOM)).thenReturn(blackPieces);

        Map<ChessboardPosition, ChessPiece> result = setup.generate(ChessboardOrientation.WHITE_BOTTOM);

        assertThat(result)
            .containsAllEntriesOf(whitePieces)
            .containsAllEntriesOf(blackPieces);
    }
}