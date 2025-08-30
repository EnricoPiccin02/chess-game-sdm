package unittest.chessgame.gamecontrol.assembler.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamecontrol.assembler.domain.BuiltChessGame;
import com.sdm.units.chessgame.gamecontrol.assembler.domain.ChessGameControllerBuilder;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionPieceSelector;

@DisplayName("ChessGameControllerBuilder")
class ChessGameControllerBuilderTest {

    private ChessboardOrientation orientation;
    private PromotionPieceSelector selectorDummy;
    private ChessGameControllerBuilder builder;

    @BeforeEach
    void setUp() {
        orientation = ChessboardOrientation.WHITE_BOTTOM;
        selectorDummy = mock(PromotionPieceSelector.class);
        builder = ChessGameControllerBuilder.create();
    }

    @Test
    @DisplayName("should allow fluent chaining when setting orientation")
    void shouldAllowFluentChainingWhenSettingOrientation() {
        builder = builder.withOrientation(orientation);

        assertThat(builder).isNotNull();
    }

    @Test
    @DisplayName("should allow fluent chaining when setting promotion selector")
    void shouldAllowFluentChainingWhenSettingPromotionSelector() {
        builder = builder.withPromotionSelector(selectorDummy);

        assertThat(builder).isNotNull();
    }

    @Test
    @DisplayName("should create valid and ready-to-use game")
    void shouldCreateValidAndReadyToUseGame() {
        BuiltChessGame game = builder
            .withOrientation(orientation)
            .withPromotionSelector(selectorDummy)
            .build();

        assertThat(game).isNotNull();
    }

    @Test
    @DisplayName("should build a complete chess game when orientation and selector are set")
    void shouldBuildCompleteChessGameWhenOrientationAndSelectorAreSet() {
        BuiltChessGame game = builder
            .withOrientation(orientation)
            .withPromotionSelector(selectorDummy)
            .build();

        assertThat(game)
            .extracting(BuiltChessGame::eventPublisher,
                        BuiltChessGame::moveQuery,
                        BuiltChessGame::controller)
            .doesNotContainNull();
    }
}