package unittest.chessgame.gui.pieces;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gui.pieces.DefaultPieceResourceResolver;
import com.sdm.units.chessgame.gui.pieces.PieceResourceResolver;

import unittest.chessgame.gamelogic.testdoubles.PieceDummy;

@DisplayName("DefaultPieceResourceResolver")
class DefaultPieceResourceResolverTest {

    private PieceResourceResolver resolver;

    @BeforeEach
    void setUp() {
        resolver = new DefaultPieceResourceResolver();
    }
    
    @Test
    @DisplayName("shouldResolveWhitePawnPath")
    void shouldResolveWhitePawnPath() {
        ChessPiece whitePawn = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.PAWN);
        String path = resolver.resolvePath(whitePawn);
        assertEquals("images/pieces/white/pawn.svg", path);
    }

    @Test
    @DisplayName("shouldResolveBlackQueenPath")
    void shouldResolveBlackQueenPath() {
        ChessPiece blackQueen = new PieceDummy(ChessPieceColor.BLACK, ChessPieceInfo.QUEEN);
        String path = resolver.resolvePath(blackQueen);
        assertEquals("images/pieces/black/queen.svg", path);
    }
}