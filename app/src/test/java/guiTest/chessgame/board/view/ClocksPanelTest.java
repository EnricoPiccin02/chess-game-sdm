package guitest.chessgame.board.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.clock.ChessClockView;
import com.sdm.units.chessgame.gui.board.view.ClocksPanel;

@DisplayName("ClocksPanel")
class ClocksPanelTest {
    
    private EnumMap<ChessPieceColor, ChessClockView> dummyClocks;

    @BeforeEach
    void setUp() {
        dummyClocks = new EnumMap<>(ChessPieceColor.class);
    }

    @Test
    @DisplayName("should contain one component per clock")
    void shouldContainOneComponentPerClock() {
        ClocksPanel panel = new ClocksPanel(dummyClocks);
        assertEquals(dummyClocks.size(), panel.getComponentCount());
    }
}