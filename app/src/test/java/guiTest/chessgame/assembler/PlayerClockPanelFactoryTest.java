package guitest.chessgame.assembler;

import org.junit.jupiter.api.*;

import com.sdm.units.chessgame.gamecontrol.assembler.gui.PlayerClockPanelFactory;
import com.sdm.units.chessgame.gamecontrol.state.GameStateController;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.clock.ChessClock;
import com.sdm.units.chessgame.gui.board.clock.ChessClockView;

import unittest.chessgame.gamecontrol.testdoubles.ChessClockSpy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.EnumMap;

@DisplayName("PlayerClockPanelFactory")
class PlayerClockPanelFactoryTest {

    private GameStateController controller;
    private PlayerClockPanelFactory factory;
    private EnumMap<ChessPieceColor, ChessClock> clocks;

    private ChessClockSpy whiteClockSpy;
    private ChessClockSpy blackClockSpy;

    @BeforeEach
    void setUp() {
        controller = mock(GameStateController.class);
        factory = new PlayerClockPanelFactory(controller);

        whiteClockSpy = new ChessClockSpy();
        blackClockSpy = new ChessClockSpy();
    
        clocks = new EnumMap<>(ChessPieceColor.class);
        clocks.put(ChessPieceColor.WHITE, whiteClockSpy);
        clocks.put(ChessPieceColor.BLACK, blackClockSpy);
    }

    @Test
    @DisplayName("should create clocks for both players")
    void shouldCreateClocksForBothPlayers() {
        EnumMap<ChessPieceColor, ChessClockView> panels = factory.createPanels(clocks);

        assertThat(panels).containsKeys(ChessPieceColor.WHITE, ChessPieceColor.BLACK);
    }

    @Test
    @DisplayName("should register listeners to all clocks")
    void shouldRegisterListenersToAllClocks() {
        factory.createPanels(clocks);

        assertThat(((ChessClockSpy) clocks.get(ChessPieceColor.WHITE)).isListenerSet()).isTrue();
        assertThat(((ChessClockSpy) clocks.get(ChessPieceColor.BLACK)).isListenerSet()).isTrue();
    }
}