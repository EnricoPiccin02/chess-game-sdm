package unittest.chessgame.gamecontrol.assembler.gui;

import org.junit.jupiter.api.*;

import com.sdm.units.chessgame.gamecontrol.assembler.gui.ToolbarFactory;
import com.sdm.units.chessgame.gamecontrol.state.GameStateController;
import com.sdm.units.chessgame.gui.controller.interaction.InteractionContext;
import com.sdm.units.chessgame.gui.controller.interaction.ToolbarInteractionStrategy;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ToolbarFactoryTest {

    private InteractionContext dummyContext;
    private GameStateController dummyController;

    @BeforeEach
    void setUp() {
        dummyController = mock(GameStateController.class);
        dummyContext = new InteractionContext(null, dummyController, null);
    }

    @Test
    @DisplayName("should create valid and ready-to-use controller")
    void shouldCreateValidAndReadyToUseController() {
        ToolbarInteractionStrategy toolbar = ToolbarFactory.create(dummyContext, dummyController);
        assertThat(toolbar).isNotNull();
    }
}