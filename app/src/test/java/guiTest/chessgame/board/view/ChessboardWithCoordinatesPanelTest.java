package guitest.chessgame.board.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.awt.BorderLayout;
import java.awt.Component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gui.board.view.ChessboardView;
import com.sdm.units.chessgame.gui.board.view.ChessboardWithCoordinatesPanel;

import guitest.chessgame.testdoubles.ChessboardViewFake;

@DisplayName("ChessboardWithCoordinatesPanel")
public class ChessboardWithCoordinatesPanelTest {

    private ChessboardView fakeChessboardView;

    @BeforeEach
    void setUp() {
        fakeChessboardView = new ChessboardViewFake();
    }

    @Test
    @DisplayName("should contain five components")
    void shouldContainFiveComponents() {
        ChessboardWithCoordinatesPanel panel = new ChessboardWithCoordinatesPanel(fakeChessboardView);
        assertEquals(5, panel.getComponentCount());
    }

    @Test
    @DisplayName("should center the chessboard")
    void shouldCenterTheChessboard() {
        ChessboardWithCoordinatesPanel panel = new ChessboardWithCoordinatesPanel(fakeChessboardView);
        Component center = ((BorderLayout) panel.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        
        assertSame(fakeChessboardView.asComponent(), center);
    }
}