package guitest.chessgame.assembler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamecontrol.assembler.domain.BuiltChessGame;
import com.sdm.units.chessgame.gamecontrol.assembler.gui.ChessGameWindowBuilder;
import com.sdm.units.chessgame.gui.board.view.ChessGameView;

import guitest.chessgame.testdoubles.EventPublisherStub;

class ChessGameWindowBuilderTest {

    private BuiltChessGame dummyGame;
    private EventPublisherStub publisherStub;
    private ChessGameWindowBuilder builder;

    @BeforeEach
    void setUp() {
        publisherStub = new EventPublisherStub();
        dummyGame = new BuiltChessGame(publisherStub, null, null);
        builder = ChessGameWindowBuilder.create();
    }

    @Test
    @DisplayName("should allow fluent chaining when setting the game")
    void shouldAllowFluentChainingWhenSettingTheGame() {
        builder = builder.withGame(dummyGame);

        assertThat(builder).isNotNull();
    }

    @Test
    @DisplayName("should create valid and ready-to-use game view")
    void shouldCreateValidAndReadyToUseGameView() {
        ChessGameView view = builder
                .withGame(dummyGame)
                .build();

        assertThat(view).isNotNull();
    }

    @Test
    @DisplayName("should register frame as event listener")
    void shouldRegisterFrameAsEventListener() {
        ChessGameWindowBuilder.create()
                .withGame(dummyGame)
                .build();

        assertThat(publisherStub.listenerCount()).isEqualTo(1);
    }
}