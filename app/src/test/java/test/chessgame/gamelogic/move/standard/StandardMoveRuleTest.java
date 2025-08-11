package test.chessgame.gamelogic.move.standard;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
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
import com.sdm.units.chessgame.gamelogic.move.api.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.standard.StandardMove;
import com.sdm.units.chessgame.gamelogic.move.standard.StandardMoveRule;

import test.chessgame.gamelogic.ChessPieceStub;
import test.chessgame.gamelogic.move.ChessboardFake;

@DisplayName("StandardMoveRule")
class StandardMoveRuleTest {

    private ChessboardFake board;
    private ChessboardOrientation orientation;
    private ChessboardPosition piecePosition;
    private ChessPieceStub piece;
    private StandardMoveRule rule;

    @BeforeEach
    void setUp() {
        board = new ChessboardFake();
        orientation = ChessboardOrientation.WHITE_BOTTOM;
        piecePosition = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
        piece = new ChessPieceStub(ChessPieceColor.WHITE, ChessPieceInfo.ROOK);
        board.putPieceAt(piecePosition, piece);
        rule = new StandardMoveRule();
    }

    @Test
    @DisplayName("should generate moves based on piece's legal moves")
    void shouldGenerateMovesFromPieceStrategy() {
        ChessboardPosition legalMove1 = new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO);
        ChessboardPosition legalMove2 = new ChessboardPosition(ChessboardFile.A, ChessboardRank.THREE);
        piece.setLegalMoves(List.of(legalMove1, legalMove2));

        List<ReversibleMove> moves = rule.generateMovesFrom(board, piecePosition, orientation);

        assertThat(moves).hasSize(2);
        assertThat(moves).allSatisfy(move -> assertThat(move).isInstanceOf(StandardMove.class));
        assertThat(moves.stream().map(m -> ((StandardMove) m).getMoveComponents().get(0).to()))
                .containsExactlyInAnyOrder(legalMove1, legalMove2);
    }

    @Test
    @DisplayName("should return empty move list if no piece at position")
    void shouldReturnEmptyIfNoPiece() {
        ChessboardPosition emptyPos = new ChessboardPosition(ChessboardFile.B, ChessboardRank.TWO);

        List<ReversibleMove> moves = rule.generateMovesFrom(board, emptyPos, orientation);

        assertThat(moves).isEmpty();
    }

    @Test
    @DisplayName("should create valid move when target is legal")
    void shouldValidateAndCreateWhenLegal() {
        ChessboardPosition legalMove = new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO);
        piece.setLegalMoves(List.of(legalMove));

        Optional<ReversibleMove> optMove = rule.validateAndCreate(board, piecePosition, legalMove, orientation);

        assertThat(optMove).isPresent();
        ReversibleMove move = optMove.get();
        assertThat(move).isInstanceOf(StandardMove.class);

        StandardMove standardMove = (StandardMove) move;
        assertThat(standardMove.getMoveComponents()).hasSize(1);
        assertThat(standardMove.getMoveComponents().get(0).from()).isEqualTo(piecePosition);
        assertThat(standardMove.getMoveComponents().get(0).to()).isEqualTo(legalMove);
    }

    @Test
    @DisplayName("should return empty when move is illegal")
    void shouldReturnEmptyWhenIllegal() {
        ChessboardPosition illegalMove = new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO);
        piece.setLegalMoves(List.of()); // no legal moves

        Optional<ReversibleMove> optMove = rule.validateAndCreate(board, piecePosition, illegalMove, orientation);

        assertThat(optMove).isEmpty();
    }

    @Test
    @DisplayName("should return priority 2")
    void shouldReturnCorrectPriority() {
        assertThat(rule.getPriority()).isEqualTo(2);
    }
}