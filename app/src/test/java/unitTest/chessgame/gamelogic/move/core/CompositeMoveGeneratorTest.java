package unittest.chessgame.gamelogic.move.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.core.CompositeMoveGenerator;
import com.sdm.units.chessgame.gamelogic.move.core.MoveGenerator;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;

@DisplayName("CompositeMoveGenerator")
class CompositeMoveGeneratorTest {

    private MoveGenerator firstRule;
    private MoveGenerator secondRule;
    private CompositeMoveGenerator generator;
    private Chessboard board;
    private ChessboardPosition from;
    private ChessboardOrientation orientation;

    @BeforeEach
    void setUp() {
        firstRule = mock(MoveGenerator.class);
        secondRule = mock(MoveGenerator.class);
        generator = new CompositeMoveGenerator(List.of(firstRule, secondRule));
        
        board = mock(Chessboard.class);
        from = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
        orientation = ChessboardOrientation.WHITE_BOTTOM;
    }

    @Test
    @DisplayName("should combine moves from all generators")
    void shouldCombineMovesFromAllGenerators() {
        ReversibleMove move1 = mock(ReversibleMove.class);
        ReversibleMove move2 = mock(ReversibleMove.class);

        when(firstRule.generateMovesFrom(board, from, orientation)).thenReturn(List.of(move1));
        when(secondRule.generateMovesFrom(board, from, orientation)).thenReturn(List.of(move2));

        List<ReversibleMove> result = generator.generateMovesFrom(board, from, orientation);

        assertEquals(List.of(move1, move2), result);
    }

    @Test
    @DisplayName("should generate no moves when no rules generate moves")
    void shouldGenerateNoMovesWhenNoRulesGenerateMoves() {
        when(firstRule.generateMovesFrom(board, from, orientation)).thenReturn(List.of());
        when(secondRule.generateMovesFrom(board, from, orientation)).thenReturn(List.of());

        List<ReversibleMove> result = generator.generateMovesFrom(board, from, orientation);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("should expose the same rules it was created with")
    void shouldExposeSameRulesItWasCreatedWith() {
        assertEquals(List.of(firstRule, secondRule), generator.rules());
    }
}