package unittest.chessgame.gamelogic.move.special.castling;

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
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingCandidate;
import com.sdm.units.chessgame.gamelogic.move.special.castling.CastlingRule;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import unittest.chessgame.gamelogic.move.special.SpecialMoveRuleTest;
import unittest.chessgame.gamelogic.testdoubles.PieceDummy;

@DisplayName("CastlingRule")
class CastlingRuleTest extends SpecialMoveRuleTest<CastlingCandidate> {

    private ChessboardPosition kingFrom;
    private ChessboardPosition kingTo;
    private ChessboardPosition rookFrom;
    private ChessboardPosition rookTo;

    private ChessPiece king;
    private ChessPiece rook;

    @BeforeEach
    void setupRule() {
        kingFrom = new ChessboardPosition(ChessboardFile.E, ChessboardRank.ONE);
        kingTo = new ChessboardPosition(ChessboardFile.G, ChessboardRank.ONE);
        rookFrom = new ChessboardPosition(ChessboardFile.H, ChessboardRank.ONE);
        rookTo = new ChessboardPosition(ChessboardFile.F, ChessboardRank.ONE);

        king = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.KING);
        rook = new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.ROOK);

        board.putPieceAt(kingFrom, king);
        board.putPieceAt(rookFrom, rook);

        rule = new CastlingRule(patternStub, eligibilityStub, factoryStub);
    }

    @Override
    protected CastlingCandidate createValidCandidate() {
        return new CastlingCandidate(kingFrom, kingTo, rookFrom, rookTo, king, rook);
    }

    @Override
    protected ChessboardPosition from() {
        return kingFrom;
    }

    @Override
    protected ChessboardPosition to() {
        return kingTo;
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