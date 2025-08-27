package guitest.chessgame.pieces;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gui.pieces.BatikSvgRenderer;
import com.sdm.units.chessgame.gui.pieces.SvgRenderer;

@DisplayName("BatikSvgRenderer")
class BatikSvgRendererTest {

    private SvgRenderer renderer;

    @BeforeEach
    void setUp() {
        renderer = new BatikSvgRenderer();
    }

    @Test
    @DisplayName("shouldRenderImageFromExistingSvg")
    void shouldRenderImageFromExistingSvg() {
        Optional<BufferedImage> image = renderer.render("images/test.svg", 50, 50);
        assertTrue(image.isPresent());
    }

    @Test
    @DisplayName("shouldHandleMissingSvgFileGracefully")
    void shouldHandleMissingSvgFileGracefully() {
        Optional<BufferedImage> image = renderer.render("images/missing.svg", 50, 50);
        assertTrue(image.isEmpty());
    }
}