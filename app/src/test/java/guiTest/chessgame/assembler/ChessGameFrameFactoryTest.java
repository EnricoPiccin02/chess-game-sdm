package guitest.chessgame.assembler;

import org.junit.jupiter.api.*;

import com.sdm.units.chessgame.gamecontrol.assembler.gui.ChessGameFrameFactory;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.clock.ChessClockView;
import com.sdm.units.chessgame.gui.board.view.ChessGameFrame;
import com.sdm.units.chessgame.gui.board.view.ChessboardView;
import com.sdm.units.chessgame.gui.controller.interaction.ToolbarInteractionStrategy;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.EnumMap;

class ChessGameFrameFactoryTest {

    private ChessboardView dummyChessboardView;
    private EnumMap<ChessPieceColor, ChessClockView> dummyClocks;
    private ToolbarInteractionStrategy dummyToolbar;

    @BeforeEach
    void setUp() {
        dummyChessboardView = mock(ChessboardView.class);
        dummyClocks = new EnumMap<>(ChessPieceColor.class);
        dummyToolbar = mock(ToolbarInteractionStrategy.class);
    }

    @Test
    @DisplayName("should create valid and ready-to-use game frame")
    void shouldCreateValidAndReadyToUseGameFrame() {
        ChessGameFrame frame = ChessGameFrameFactory.create(dummyChessboardView, dummyClocks, dummyToolbar);
        assertThat(frame).isNotNull();
    }
}