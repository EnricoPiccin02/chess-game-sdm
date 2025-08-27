package unittest.chessgame.gamecontrol.state;

import static com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamecontrol.flow.TurnManager;
import com.sdm.units.chessgame.gamecontrol.state.MoveQueryService;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;

import unittest.chessgame.gamecontrol.testdoubles.LegalMoveFinderStub;
import unittest.chessgame.gamelogic.testdoubles.ChessboardStub;
import unittest.chessgame.gamelogic.testdoubles.PieceDummy;

@DisplayName("MoveQueryService")
class MoveQueryServiceTest {

    private ChessboardStub board;
    private LegalMoveFinderStub moveFinder;
    private TurnManager stubTurns;
    private MoveQueryService service;

    private final ChessboardPosition pos = new ChessboardPosition(ChessboardFile.A, ChessboardRank.ONE);

    @BeforeEach
    void setUp() {
        board = new ChessboardStub();
        moveFinder = new LegalMoveFinderStub();
        stubTurns = mock(TurnManager.class);
        when(stubTurns.current()).thenReturn(ChessPieceColor.WHITE);
        when(stubTurns.opponent()).thenReturn(ChessPieceColor.BLACK);

        service = new MoveQueryService(board, moveFinder, stubTurns);
    }

    @Nested
    @DisplayName("when selecting occupied squares")
    class SelectingOccupiedSquares {

        @Test
        @DisplayName("should provide only occupied squares of the current player")
        void shouldProvideOccupiedSquaresOfCurrentPlayer() {
            board.placeFriendly(pos, WHITE);

            Set<ChessboardPosition> result = service.selectable();

            assertEquals(Set.of(pos), result);
        }
    }

    @Nested
    @DisplayName("when computing legal destinations")
    class LegalDestinations {

        private final ChessboardPosition from = pos;
        private final Set<ChessboardPosition> destinations = Set.of(
            new ChessboardPosition(ChessboardFile.A, ChessboardRank.TWO)
        );

        @Test
        @DisplayName("should provide legal moves if piece belongs to current player")
        void shouldProvideLegalMovesOfCurrentPlayer() {
            board.putPieceAt(from, new PieceDummy(ChessPieceColor.WHITE, ChessPieceInfo.PAWN));
            moveFinder.setDestinations(destinations);

            Set<ChessboardPosition> result = service.legalDestinations(from);

            assertEquals(destinations, result);
        }

        @Test
        @DisplayName("should not provide any legal destination when no piece at position")
        void shouldNotProvideAnyLegalDestinationWhenNoPieceAtPosition() {
            Set<ChessboardPosition> result = service.legalDestinations(from);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("should not provide any legal destination when piece belongs to opponent")
        void shouldNotProvideAnyLegalDestinationWhenPieceOfOpponent() {
            board.putPieceAt(from, new PieceDummy(ChessPieceColor.BLACK, ChessPieceInfo.PAWN));

            Set<ChessboardPosition> result = service.legalDestinations(from);

            assertTrue(result.isEmpty());
        }
    }
}