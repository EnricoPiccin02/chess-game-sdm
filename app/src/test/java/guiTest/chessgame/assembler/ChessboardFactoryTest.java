package guitest.chessgame.assembler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamecontrol.assembler.gui.ChessboardAssembly;
import com.sdm.units.chessgame.gamecontrol.assembler.gui.ChessboardFactory;
import com.sdm.units.chessgame.gamecontrol.state.GameStateController;
import com.sdm.units.chessgame.gamecontrol.state.MoveQuery;

class ChessboardFactoryTest {

    private MoveQuery dummyMoveQuery;
    private GameStateController dummyController;

    @BeforeEach
    void setUp() {
        dummyMoveQuery = mock(MoveQuery.class);
        dummyController = mock(GameStateController.class);
    }

    @Test
    @DisplayName("should create valid and ready-to-use chessboard components")
    void shouldCreateValidChessboardComponents() {
        ChessboardAssembly assembly = ChessboardFactory.create(dummyMoveQuery, dummyController);
        assertThat(assembly)
            .extracting(ChessboardAssembly::view,
                        ChessboardAssembly::controller,
                        ChessboardAssembly::context)
            .doesNotContainNull();
    }
}