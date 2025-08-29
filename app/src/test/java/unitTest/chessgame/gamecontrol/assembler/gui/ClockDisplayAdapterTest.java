package unittest.chessgame.gamecontrol.assembler.gui;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamecontrol.assembler.gui.ClockDisplayAdapter;
import com.sdm.units.chessgame.gamecontrol.state.GameReason;
import com.sdm.units.chessgame.gamecontrol.state.GameStateController;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.clock.ChessClockView;
import com.sdm.units.chessgame.gui.board.clock.ClockFormatter;

@DisplayName("ClockDisplayAdapter")
class ClockDisplayAdapterTest {

    private ChessClockView view;
    private ClockFormatter formatterStub;
    private GameStateController controller;
    private ClockDisplayAdapter adapter;

    @BeforeEach
    void setUp() {
        view = mock(ChessClockView.class);
        formatterStub = millis -> "formatted:" + millis;
        controller = mock(GameStateController.class);
        adapter = new ClockDisplayAdapter(view, formatterStub, controller, ChessPieceColor.WHITE);
    }

    @Test
    @DisplayName("should update clock view with formatted time")
    void shouldUpdateClockViewWithFormattedTime() {
        adapter.onTimeUpdated(1234L);

        verify(view).updateTime("formatted:1234");
    }

    @Test
    @DisplayName("should proclaim opponent as winner on timeout")
    void shouldProclaimOpponentAsWinnerOnTimeout() {
        adapter.onTimeExpired();

        verify(controller).proclaimWinner(ChessPieceColor.BLACK, GameReason.TIMEOUT);
    }
}