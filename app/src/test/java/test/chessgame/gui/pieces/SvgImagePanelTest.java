package test.chessgame.gui.pieces;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gui.pieces.SvgImagePanel;

import test.chessgame.gui.testdoubles.SvgRendererSpy;

@DisplayName("SvgImagePanel")
class SvgImagePanelTest {

    private SvgRendererSpy rendererSpy;
    private SvgImagePanel panel;

    @BeforeEach
    void setUp() {
        rendererSpy = new SvgRendererSpy();
        panel = new SvgImagePanel("piece.svg", rendererSpy);
        panel.setSize(50, 50);
    }

    @Test
    @DisplayName("should call renderer with current size on first paint")
    void shouldCallRendererWithCurrentSizeOnFirstPaint() {
        panel.paint(new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB).getGraphics());

        assertTrue(rendererSpy.wasCalledWith("piece.svg", 50f, 50f));
    }

    @Test
    @DisplayName("should not call renderer again if size did not change")
    void shouldNotCallRendererAgainIfSizeDidNotChange() {
        Graphics g = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB).getGraphics();

        panel.paint(g);
        int callsAfterFirst = rendererSpy.callsSize();

        panel.paint(g);
        assertEquals(callsAfterFirst, rendererSpy.callsSize());
    }

    @Test
    @DisplayName("should call renderer again if size changed")
    void shouldCallRendererAgainIfSizeChanged() {
        Graphics g = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB).getGraphics();
        panel.paint(g);

        panel.setSize(100, 100);
        panel.paint(g);
        assertTrue(rendererSpy.wasCalledWith("piece.svg", 100f, 100f));
    }
}