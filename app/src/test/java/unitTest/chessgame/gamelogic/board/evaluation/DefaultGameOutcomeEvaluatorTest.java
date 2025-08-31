package unittest.chessgame.gamelogic.board.evaluation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.board.evaluation.AttackDetector;
import com.sdm.units.chessgame.gamelogic.board.evaluation.CheckmateEvaluator;
import com.sdm.units.chessgame.gamelogic.board.evaluation.DefaultGameOutcomeEvaluator;
import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;

@DisplayName("DefaultGameOutcomeEvaluator")
class DefaultGameOutcomeEvaluatorTest {

    private AttackDetector attackDetector;
    private CheckmateEvaluator checkmate;
    private DefaultGameOutcomeEvaluator evaluator;
    private Chessboard board;
    private ChessPieceColor color;

    @BeforeEach
    void setUp() {
        attackDetector = mock(AttackDetector.class);
        checkmate = mock(CheckmateEvaluator.class);
        evaluator = new DefaultGameOutcomeEvaluator(attackDetector, checkmate);

        board = mock(Chessboard.class);
        color = ChessPieceColor.WHITE;
    }

    @Test
    @DisplayName("should delegate attack detection")
    void shouldDelegateAttackDetection() {
        when(attackDetector.isUnderAttack(board, color)).thenReturn(true);

        boolean result = evaluator.isIllegalBecauseOfCheck(board, color);

        assertThat(result).isTrue();
        verify(attackDetector).isUnderAttack(board, color);
    }

    @Test
    @DisplayName("should delegate checkmate evaluation")
    void shouldDelegateCheckmateEvaluation() {
        when(checkmate.isCheckmate(board, color)).thenReturn(true);

        boolean result = evaluator.isCheckmate(board, color);

        assertThat(result).isTrue();
        verify(checkmate).isCheckmate(board, color);
    }
}