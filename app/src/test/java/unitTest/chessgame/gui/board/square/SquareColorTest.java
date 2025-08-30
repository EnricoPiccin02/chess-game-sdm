package unittest.chessgame.gui.board.square;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gui.board.square.SquareColor;

@DisplayName("SquareColor")
class SquareColorTest {

    @Test
    @DisplayName("should be light on alternate squares")
    void shouldBeLightOnAlternateSquares() {
        ChessboardPosition B1 = new ChessboardPosition(ChessboardFile.B, ChessboardRank.ONE);
        assertEquals(SquareColor.LIGHT, SquareColor.fromPosition(B1));
    }

    @Test
    @DisplayName("should be dark on alternate squares")
    void shouldBeDarkOnAlternateSquares() {
        ChessboardPosition B2 = new ChessboardPosition(ChessboardFile.B, ChessboardRank.TWO);
        assertEquals(SquareColor.DARK, SquareColor.fromPosition(B2));
    }
}