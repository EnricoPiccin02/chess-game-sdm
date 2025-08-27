package guitest.chessgame.pieces;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
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
import com.sdm.units.chessgame.gui.pieces.PieceViewFactory;
import com.sdm.units.chessgame.gui.pieces.SvgImagePanel;
import com.sdm.units.chessgame.gui.pieces.SvgRenderer;

import unittest.chessgame.gamelogic.testdoubles.PieceDummy;

@DisplayName("ChessPieceViewRegistry")
class ChessPieceViewRegistryTest {

    private static final String TEST_PATH = "test/path/pawn.svg";

    private SvgRenderer rendererMock;
    private PieceResourceResolver resolverStub;
    private PieceViewFactory registry;
    private ChessPiece pieceDummy;

    @BeforeEach
    void setUp() {
        rendererMock = Mockito.mock(SvgRenderer.class);
        resolverStub = Mockito.mock(PieceResourceResolver.class);
        registry = new ChessPieceViewRegistry(rendererMock, resolverStub);

        pieceDummy = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.PAWN);

        when(resolverStub.resolvePath(pieceDummy)).thenReturn(TEST_PATH);
    }

    @Test
    @DisplayName("should create a component that displays the correct piece image")
    void shouldCreateComponentThatDisplaysCorrectPieceImage() {
        JComponent component = registry.createComponentFor(pieceDummy);

        assertInstanceOf(SvgImagePanel.class, component);
    }

    @Test
    @DisplayName("should assign the resolved image path to the created component")
    void shouldAssignResolvedImagePathToComponent() {
        SvgImagePanel panel = (SvgImagePanel) registry.createComponentFor(pieceDummy);

        assertEquals(TEST_PATH, panel.getSvgPath());
    }

    @Test
    @DisplayName("should assign the renderer to the created component")
    void shouldAssignRendererToComponent() {
        SvgImagePanel panel = (SvgImagePanel) registry.createComponentFor(pieceDummy);

        assertSame(rendererMock, panel.getRenderer());
    }

    @Test
    @DisplayName("should resolve the image path for the given piece")
    void shouldResolveImagePathForPiece() {
        registry.createComponentFor(pieceDummy);

        verify(resolverStub).resolvePath(pieceDummy);
    }

    @Test
    @DisplayName("should not render immediately when creating the component")
    void shouldNotRenderImmediatelyWhenCreatingComponent() {
        registry.createComponentFor(pieceDummy);

        verifyNoInteractions(rendererMock);
    }
}