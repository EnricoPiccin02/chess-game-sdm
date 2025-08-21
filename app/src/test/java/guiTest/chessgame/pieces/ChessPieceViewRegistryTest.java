package guiTest.chessgame.pieces;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import javax.swing.JComponent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gui.pieces.ChessPieceViewRegistry;
import com.sdm.units.chessgame.gui.pieces.PieceResourceResolver;
import com.sdm.units.chessgame.gui.pieces.SvgImagePanel;
import com.sdm.units.chessgame.gui.pieces.SvgRenderer;

import unitTest.chessgame.gamelogic.testdoubles.PieceDummy;

@DisplayName("ChessPieceViewRegistry")
class ChessPieceViewRegistryTest {

    private final String testPath = "test/path/pawn.svg";

    private SvgRenderer rendererMock;
    private PieceResourceResolver resolverMock;
    private ChessPieceViewRegistry registry;
    private ChessPiece pieceDummy;

    @BeforeEach
    void setUp() {
        rendererMock = Mockito.mock(SvgRenderer.class);
        resolverMock = Mockito.mock(PieceResourceResolver.class);
        registry = new ChessPieceViewRegistry(rendererMock, resolverMock);

        pieceDummy = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.PAWN);

        when(resolverMock.resolvePath(pieceDummy)).thenReturn(testPath);
    }

    @Test
    @DisplayName("should build SvgImagePanel with resolved path and given renderer")
    void shouldBuildSvgImagePanelWithResolvedPathAndRenderer() {
        JComponent component = registry.createComponentFor(pieceDummy);

        assertTrue(component instanceof SvgImagePanel);

        SvgImagePanel panel = (SvgImagePanel) component;
        assertEquals(testPath, panel.getSvgPath());
        assertSame(rendererMock, panel.getRenderer());

        verify(resolverMock).resolvePath(pieceDummy);
        verifyNoInteractions(rendererMock);
    }
}