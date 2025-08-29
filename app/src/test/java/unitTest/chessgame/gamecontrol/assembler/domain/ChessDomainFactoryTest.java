package unittest.chessgame.gamecontrol.assembler.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamecontrol.assembler.domain.ChessDomainFactory;
import com.sdm.units.chessgame.gamecontrol.state.BoardAssembly;
import com.sdm.units.chessgame.gamecontrol.state.GameStateAssembly;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionPieceSelector;

@DisplayName("ChessDomainFactory")
class ChessDomainFactoryTest {

    private ChessboardOrientation orientation;
    private PromotionPieceSelector selectorDummy;
    private ChessDomainFactory factory;

    @BeforeEach
    void setUp() {
        orientation = ChessboardOrientation.WHITE_BOTTOM;
        selectorDummy = mock(PromotionPieceSelector.class);
        factory = new ChessDomainFactory(orientation, selectorDummy);
    }

    @Test
    @DisplayName("should create valid and ready-to-use board components")
    void shouldCreateValidAndReadyToUseBoardComponents() {
        BoardAssembly assembly = factory.createChessboardComponents();
        assertThat(assembly)
            .extracting(BoardAssembly::board,
                        BoardAssembly::executor,
                        BoardAssembly::moveFinder,
                        BoardAssembly::outcome)
            .doesNotContainNull();
    }

    @Test
    @DisplayName("should create valid and ready-to-use game state components")
    void shouldCreateValidAndReadyToUseGameStateComponents() {
        GameStateAssembly assembly = factory.createGameStateComponents();
        assertThat(assembly)
            .extracting(GameStateAssembly::turns,
                        GameStateAssembly::scores,
                        GameStateAssembly::notifier)
            .doesNotContainNull();
    }
}