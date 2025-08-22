package unittest.chessgame.gamelogic.board.state;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.board.state.LegalMoveFinder;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.core.MoveComponent;
import com.sdm.units.chessgame.gamelogic.move.core.MoveGenerator;
import com.sdm.units.chessgame.gamelogic.move.core.MoveValidator;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;

import unittest.chessgame.gamelogic.testdoubles.ChessboardFake;
import unittest.chessgame.gamelogic.testdoubles.ReversibleMoveStub;

@DisplayName("LegalMoveFinder")
class LegalMoveFinderTest {

    private MoveValidator validatorMock;
    private MoveGenerator generatorMock;
    private Chessboard board;
    private ChessboardOrientation orientation;
    private LegalMoveFinder finder;

    private ChessboardPosition from;
    private ChessboardPosition to;
    MoveComponent component;
    private ReversibleMoveStub moveStub;

    @BeforeEach
    void setUp() {
        validatorMock = Mockito.mock(MoveValidator.class);
        generatorMock = Mockito.mock(MoveGenerator.class);
        orientation = ChessboardOrientation.WHITE_BOTTOM;
        board = new ChessboardFake();

        finder = new LegalMoveFinder(validatorMock, generatorMock, orientation);

        from = new ChessboardPosition(ChessboardFile.B, ChessboardRank.FOUR);
        to = new ChessboardPosition(ChessboardFile.C, ChessboardRank.FIVE);
        component = new MoveComponent(from, to);

        moveStub = new ReversibleMoveStub(null, component);
    }

    @Nested
    @DisplayName("createIfValid")
    class CreateIfValidTests {

        @Test
        @DisplayName("should return move when validator approves")
        void shouldReturnMoveWhenValidatorApproves() {
            Mockito.when(validatorMock.validateAndCreate(board, from, to, orientation))
                .thenReturn(Optional.of(moveStub));

            Optional<ReversibleMove> result = finder.createIfValid(board, from, to);

            assertThat(result).contains(moveStub);
        }

        @Test
        @DisplayName("should return empty when validator rejects")
        void shouldReturnEmptyWhenValidatorRejects() {
            Mockito.when(validatorMock.validateAndCreate(board, from, to, orientation))
                .thenReturn(Optional.empty());

            Optional<ReversibleMove> result = finder.createIfValid(board, from, to);

            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("findLegalDestinations")
    class FindLegalDestinationsTests {

        @Test
        @DisplayName("should return destination squares from generated moves")
        void shouldReturnDestinationSquaresFromGeneratedMoves() {
            List<ReversibleMove> moves = List.of(moveStub);
            Mockito.when(generatorMock.generateMovesFrom(board, from, orientation)).thenReturn(moves);

            Set<ChessboardPosition> result = finder.findLegalDestinations(board, from);

            assertThat(result).containsExactly(to);
        }

        @Test
        @DisplayName("should return empty set when no moves are generated")
        void shouldReturnEmptySetWhenNoMovesAreGenerated() {
            Mockito.when(generatorMock.generateMovesFrom(board, from, orientation)).thenReturn(List.of());

            Set<ChessboardPosition> result = finder.findLegalDestinations(board, from);

            assertThat(result).isEmpty();
        }
    }
}