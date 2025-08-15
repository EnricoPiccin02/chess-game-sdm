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
import com.sdm.units.chessgame.gamelogic.move.core.MoveRulePriority;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.standard.StandardMove;
import com.sdm.units.chessgame.gamelogic.move.standard.StandardMoveRule;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

import test.chessgame.gamelogic.testdoubles.PieceStub;
import test.chessgame.gamelogic.testdoubles.ChessboardFake;

@DisplayName("StandardMoveRule")
class StandardMoveRuleTest {

    private ChessboardFake board;
    private ChessboardOrientation orientation;
    private ChessboardPosition piecePosition;
    private ChessPiece pieceStub;
    private StandardMoveRule rule;

    @BeforeEach
    void setUp() {
        board = new ChessboardFake();
        orientation = ChessboardOrientation.WHITE_BOTTOM;
        piecePosition = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);
        rule = new StandardMoveRule();
    }

    @Test
    @DisplayName("should generate moves based on piece's legal moves")
    void shouldGenerateMovesFromPieceStrategy() {
        List<ChessboardPosition> legalMoves = List.of(
            new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO),
            new ChessboardPosition(ChessboardFile.A, ChessboardRank.THREE)
        );
        pieceStub = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.ROOK, legalMoves);
        board.putPieceAt(piecePosition, pieceStub);
        
        List<ReversibleMove> moves = rule.generateMovesFrom(board, piecePosition, orientation);

        assertThat(moves).hasSize(2);
        assertThat(moves).allSatisfy(move -> assertThat(move).isInstanceOf(StandardMove.class));
        assertThat(moves.stream().map(m -> ((StandardMove) m).getPrimaryMoveComponent().to()))
                .containsExactlyInAnyOrderElementsOf(legalMoves);
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
        pieceStub = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.ROOK, List.of(legalMove));
        board.putPieceAt(piecePosition, pieceStub);

        Optional<ReversibleMove> optMove = rule.validateAndCreate(board, piecePosition, legalMove, orientation);

        assertThat(optMove).isPresent();
        ReversibleMove move = optMove.get();
        assertThat(move).isInstanceOf(StandardMove.class);

        StandardMove standardMove = (StandardMove) move;
        assertThat(standardMove.getPrimaryMoveComponent().from()).isEqualTo(piecePosition);
        assertThat(standardMove.getPrimaryMoveComponent().to()).isEqualTo(legalMove);
    }

    @Test
    @DisplayName("should return empty when move is illegal")
    void shouldReturnEmptyWhenIllegal() {
        ChessboardPosition illegalMove = new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO);
        pieceStub = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.ROOK, List.of());

        Optional<ReversibleMove> optMove = rule.validateAndCreate(board, piecePosition, illegalMove, orientation);

        assertThat(optMove).isEmpty();
    }

    @Test
    @DisplayName("should have low priority")
    void shouldHaveLowPriority() {
        assertThat(rule.getPriority()).isEqualTo(MoveRulePriority.LOW_PRIORITY.getPriority());
    }
}