package unittest.chessgame.gamelogic.move.standard;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

import unittest.chessgame.gamelogic.testdoubles.ChessboardFake;
import unittest.chessgame.gamelogic.testdoubles.PieceStub;

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

    @Nested
    @DisplayName("when generating moves")
    class WhenGeneratingMoves {

        @Test
        @DisplayName("should supply legal destinations for the given piece")
        void shouldSupplyLegalDestinations() {
            ChessboardPosition pos2 = new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO);
            ChessboardPosition pos3 = new ChessboardPosition(ChessboardFile.A, ChessboardRank.THREE);
            pieceStub = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.ROOK, Set.of(pos2, pos3));
            board.putPieceAt(piecePosition, pieceStub);

            List<ReversibleMove> moves = rule.generateMovesFrom(board, piecePosition, orientation);

            assertThat(moves).hasSize(2);
        }

        @Test
        @DisplayName("should wrap each destination in a standard move")
        void shouldWrapDestinationsInStandardMove() {
            ChessboardPosition pos2 = new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO);
            pieceStub = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.ROOK, Set.of(pos2));
            board.putPieceAt(piecePosition, pieceStub);

            List<ReversibleMove> moves = rule.generateMovesFrom(board, piecePosition, orientation);

            assertThat(moves.get(0)).isInstanceOf(StandardMove.class);
        }

        @Test
        @DisplayName("should not generate any move when no piece at position")
        void shouldNotGenerateAnyMoveWhenNoPieceAtPosition() {
            ChessboardPosition emptyPos = new ChessboardPosition(ChessboardFile.B, ChessboardRank.TWO);

            List<ReversibleMove> moves = rule.generateMovesFrom(board, emptyPos, orientation);

            assertThat(moves).isEmpty();
        }
    }

    @Nested
    @DisplayName("when validating and creating a move")
    class WhenValidatingAndCreatingMove {

        @Test
        @DisplayName("should create a standard move for legal destination")
        void shouldCreateStandardMoveForLegalDestination() {
            ChessboardPosition legalDestination = new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO);
            pieceStub = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.ROOK, Set.of(legalDestination));
            board.putPieceAt(piecePosition, pieceStub);

            Optional<ReversibleMove> optMove = rule.validateAndCreate(board, piecePosition, legalDestination, orientation);

            assertThat(optMove).isPresent();
        }

        @Test
        @DisplayName("should set correct starting square on created move")
        void shouldSetCorrectStartingSquareOnMove() {
            ChessboardPosition legalDestination = new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO);
            pieceStub = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.ROOK, Set.of(legalDestination));
            board.putPieceAt(piecePosition, pieceStub);

            StandardMove move = (StandardMove) rule.validateAndCreate(board, piecePosition, legalDestination, orientation).orElseThrow();

            assertThat(move.getPrimaryMoveComponent().from()).isEqualTo(piecePosition);
        }

        @Test
        @DisplayName("should set correct destination square on created move")
        void shouldSetCorrectDestinationSquareOnMove() {
            ChessboardPosition legalDestination = new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO);
            pieceStub = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.ROOK, Set.of(legalDestination));
            board.putPieceAt(piecePosition, pieceStub);

            StandardMove move = (StandardMove) rule.validateAndCreate(board, piecePosition, legalDestination, orientation).orElseThrow();

            assertThat(move.getPrimaryMoveComponent().to()).isEqualTo(legalDestination);
        }

        @Test
        @DisplayName("should not create any move when destination is illegal")
        void shouldNotCreateAnyMoveWhenDestinationIllegal() {
            ChessboardPosition illegalDestination = new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO);
            pieceStub = new PieceStub(ChessPieceColor.WHITE, ChessPieceInfo.ROOK, Set.of());

            Optional<ReversibleMove> optMove = rule.validateAndCreate(board, piecePosition, illegalDestination, orientation);

            assertThat(optMove).isEmpty();
        }
    }

    @Nested
    @DisplayName("when asking for priority")
    class WhenAskingForPriority {

        @Test
        @DisplayName("should be low priority")
        void shouldBeLowPriority() {
            assertThat(rule.getPriority()).isEqualTo(MoveRulePriority.LOW_PRIORITY.getPriority());
        }
    }
}