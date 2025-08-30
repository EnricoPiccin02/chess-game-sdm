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
import com.sdm.units.chessgame.gamelogic.board.state.DefaultLegalMoveFinder;
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
    private ReversibleMove dummyMove;

    @BeforeEach
    void setUp() {
        validatorMock = Mockito.mock(MoveValidator.class);
        generatorMock = Mockito.mock(MoveGenerator.class);
        orientation = ChessboardOrientation.WHITE_BOTTOM;
        board = new ChessboardFake();

        finder = new DefaultLegalMoveFinder(validatorMock, generatorMock, orientation);

        from = new ChessboardPosition(ChessboardFile.B, ChessboardRank.FOUR);
        to = new ChessboardPosition(ChessboardFile.C, ChessboardRank.FIVE);

        MoveComponent component = new MoveComponent(from, to);
        dummyMove = new ReversibleMoveStub(null, component);
    }

    @Nested
    @DisplayName("validate and create move")
    class CreateIfValid {

        @Test
        @DisplayName("should get correct move when validator approves")
        void shouldGetMoveWhenValidatorApproves() {
            Mockito.when(validatorMock.validateAndCreate(board, from, to, orientation))
                .thenReturn(Optional.of(dummyMove));

            Optional<ReversibleMove> result = finder.createIfValid(board, from, to);

            assertThat(result).contains(dummyMove);
        }

        @Test
        @DisplayName("should not get any move when validator rejects")
        void shouldNotGetAnyMoveWhenValidatorRejects() {
            Mockito.when(validatorMock.validateAndCreate(board, from, to, orientation))
                .thenReturn(Optional.empty());

            Optional<ReversibleMove> result = finder.createIfValid(board, from, to);

            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("find legal destinations")
    class FindLegalDestinations {

        @Test
        @DisplayName("should provide destination squares from generated moves")
        void shouldProvideDestinationSquaresFromGeneratedMoves() {
            List<ReversibleMove> moves = List.of(dummyMove);
            Mockito.when(generatorMock.generateMovesFrom(board, from, orientation)).thenReturn(moves);

            Set<ChessboardPosition> result = finder.findLegalDestinations(board, from);

            assertThat(result).containsExactly(to);
        }

        @Test
        @DisplayName("should not provide any destination when no moves are generated")
        void shouldNotProvideAnyDestinationWhenNoMovesAreGenerated() {
            Mockito.when(generatorMock.generateMovesFrom(board, from, orientation)).thenReturn(List.of());

            Set<ChessboardPosition> result = finder.findLegalDestinations(board, from);

            assertThat(result).isEmpty();
        }
    }
}