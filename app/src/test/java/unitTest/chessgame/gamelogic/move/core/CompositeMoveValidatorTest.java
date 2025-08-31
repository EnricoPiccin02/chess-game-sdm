package unittest.chessgame.gamelogic.move.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.core.CompositeMoveValidator;
import com.sdm.units.chessgame.gamelogic.move.core.PrioritizedMoveValidator;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;

@DisplayName("CompositeMoveValidator")
class CompositeMoveValidatorTest {

    private PrioritizedMoveValidator firstRule;
    private PrioritizedMoveValidator secondRule;
    private CompositeMoveValidator validator;
    private Chessboard board;
    private ChessboardPosition from;
    private ChessboardPosition to;
    private ChessboardOrientation orientation;

    @BeforeEach
    void setUp() {
        firstRule = mock(PrioritizedMoveValidator.class);
        secondRule = mock(PrioritizedMoveValidator.class);
        validator = new CompositeMoveValidator(List.of(firstRule, secondRule));
        
        board = mock(Chessboard.class);
        from = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
        to = new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO);
        orientation = ChessboardOrientation.WHITE_BOTTOM;

        when(firstRule.getPriority()).thenReturn(1);
        when(secondRule.getPriority()).thenReturn(2);
    }

    @Test
    @DisplayName("should provide first successful validation result")
    void shouldProvideFirstSuccessfulValidationResult() {
        ReversibleMove expectedMove = mock(ReversibleMove.class);

        when(firstRule.validateAndCreate(board, from, to, orientation)).thenReturn(Optional.of(expectedMove));
        when(secondRule.validateAndCreate(board, from, to, orientation)).thenReturn(Optional.empty());

        Optional<ReversibleMove> result = validator.validateAndCreate(board, from, to, orientation);

        assertEquals(Optional.of(expectedMove), result);
    }

    @Test
    @DisplayName("should skip lower priority rules when higher succeeds")
    void shouldSkipLowerPriorityRulesWhenHigherSucceeds() {
        ReversibleMove expectedMove = mock(ReversibleMove.class);

        when(firstRule.validateAndCreate(board, from, to, orientation)).thenReturn(Optional.of(expectedMove));

        validator.validateAndCreate(board, from, to, orientation);

        verify(secondRule, never()).validateAndCreate(any(), any(), any(), any());
    }

    @Test
    @DisplayName("should create no move when no validator accepts it")
    void shouldCreateNoMoveWhenNoValidatorAcceptsIt() {
        when(firstRule.validateAndCreate(board, from, to, orientation)).thenReturn(Optional.empty());
        when(secondRule.validateAndCreate(board, from, to, orientation)).thenReturn(Optional.empty());

        Optional<ReversibleMove> result = validator.validateAndCreate(board, from, to, orientation);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("should expose the same rules it was created with")
    void shouldExposeSameRulesItWasCreatedWith() {
        assertEquals(List.of(firstRule, secondRule), validator.rules());
    }
}