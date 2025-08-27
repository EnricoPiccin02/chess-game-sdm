package unittest.chessgame.gamelogic.move.special.enpassant;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.core.MoveRulePriority;
import com.sdm.units.chessgame.gamelogic.move.special.enpassant.EnPassantCandidate;
import com.sdm.units.chessgame.gamelogic.move.special.enpassant.EnPassantRule;

import unittest.chessgame.gamelogic.move.special.SpecialMoveRuleTest;
import unittest.chessgame.gamelogic.testdoubles.PieceDummy;

@DisplayName("EnPassantRule")
class EnPassantRuleTest extends SpecialMoveRuleTest<EnPassantCandidate> {

    private ChessboardPosition from;
    private ChessboardPosition to;
    private ChessboardPosition capturedPawnPos;
    private PieceDummy movingPawn;
    private PieceDummy capturedPawn;

    @BeforeEach
    void setupRule() {
        from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.FIVE);
        to = new ChessboardPosition(ChessboardFile.F, ChessboardRank.SIX);
        capturedPawnPos = new ChessboardPosition(ChessboardFile.F, ChessboardRank.FIVE);

        movingPawn = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.PAWN);
        capturedPawn = new PieceDummy(ChessPieceColor.BLACK, ChessPieceInfo.PAWN);

        board.putPieceAt(from, movingPawn);
        board.putPieceAt(capturedPawnPos, capturedPawn);

        rule = new EnPassantRule(patternStub, eligibilityStub, factoryStub);
    }

    @Override
    protected EnPassantCandidate createValidCandidate() {
        return new EnPassantCandidate(from, to, capturedPawnPos, movingPawn, capturedPawn);
    }

    @Override
    protected ChessboardPosition from() {
        return from;
    }

    @Override
    protected ChessboardPosition to() {
        return to;
    }

    @Override
    protected ChessboardOrientation orientation() {
        return ChessboardOrientation.WHITE_BOTTOM;
    }

    @Test
    @DisplayName("should have medium priority")
    void shouldHaveMediumPriority() {
        assertThat(rule.getPriority()).isEqualTo(MoveRulePriority.MEDIUM_PRIORITY.getPriority());
    }
}