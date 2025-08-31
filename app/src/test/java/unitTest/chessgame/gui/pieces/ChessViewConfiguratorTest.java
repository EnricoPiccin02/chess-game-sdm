package unittest.chessgame.gui.pieces;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gui.pieces.ChessViewConfigurator;
import com.sdm.units.chessgame.gui.pieces.PieceViewFactory;

@DisplayName("ChessViewConfigurator")
class ChessViewConfiguratorTest {

    @Test
    @DisplayName("should create valid and ready-to-use piece factory")
    void shouldCreateValidAndReadyToUsePieceFactory() {
        PieceViewFactory factory = ChessViewConfigurator.createPieceViewFactory();
        assertThat(factory).isNotNull();
    }   
}