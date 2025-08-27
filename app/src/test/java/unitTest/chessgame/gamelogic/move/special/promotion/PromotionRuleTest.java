package unittest.chessgame.gamelogic.move.special.promotion;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

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
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionCandidate;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionRule;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import unittest.chessgame.gamelogic.move.special.SpecialMoveRuleTest;
import unittest.chessgame.gamelogic.testdoubles.PieceDummy;

@DisplayName("PromotionRule")
class PromotionRuleTest extends SpecialMoveRuleTest<PromotionCandidate> {

    private ChessboardPosition from;
    private ChessboardPosition to;
    private ChessPiece pawn;

    @BeforeEach
    void setupRule() {
        pawn = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.PAWN);
        from = new ChessboardPosition(ChessboardFile.E, ChessboardRank.SEVEN);
        to = new ChessboardPosition(ChessboardFile.E, ChessboardRank.EIGHT);
        board.putPieceAt(from, pawn);

        rule = new PromotionRule(patternStub, eligibilityStub, factoryStub);
    }

    @Override
    protected PromotionCandidate createValidCandidate() {
        return new PromotionCandidate(from, to, pawn, Optional.empty());
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
    @DisplayName("should have high priority")
    void shouldHaveHighPriority() {
        assertThat(rule.getPriority()).isEqualTo(MoveRulePriority.HIGH_PRIORITY.getPriority());
    }
}