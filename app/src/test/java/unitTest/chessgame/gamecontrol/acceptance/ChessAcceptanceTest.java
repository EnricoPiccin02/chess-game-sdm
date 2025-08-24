package unittest.chessgame.gamecontrol.acceptance;

import static com.sdm.units.chessgame.gamelogic.domain.ChessboardFile.*;
import static com.sdm.units.chessgame.gamelogic.domain.ChessboardRank.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamecontrol.assembler.domain.BuiltChessGame;
import com.sdm.units.chessgame.gamecontrol.assembler.domain.ChessGameControllerBuilder;
import com.sdm.units.chessgame.gamecontrol.state.GameReason;
import com.sdm.units.chessgame.gamecontrol.state.GameStateController;
import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.DefaultPromotionPieceSelector;
import com.sdm.units.chessgame.gamelogic.pieces.Queen;
import com.sdm.units.chessgame.gui.controller.events.ChessGameEventPublisher;

import unittest.chessgame.gamecontrol.testdoubles.ChessGameViewSpy;

@DisplayName("Chess Acceptance Scenarios")
public class ChessAcceptanceTest {

    private GameStateController controller;
    private ChessGameViewSpy viewSpy;
    ChessboardFactoryHelper factory;
    ChessboardSimulatorHelper simulator;
    ChessboardComparerHelper comparer;

    @BeforeEach
    void setUp() {
        viewSpy = new ChessGameViewSpy();

        BuiltChessGame built = ChessGameControllerBuilder.create()
            .withOrientation(ChessboardOrientation.WHITE_BOTTOM)
            .withPromotionSelector(new DefaultPromotionPieceSelector())
            .build();

        ChessGameEventPublisher publisher = built.eventPublisher();
        publisher.addChessGameEventListener(viewSpy);

        controller = built.controller();
        factory = new ChessboardFactoryHelper();
        simulator = new ChessboardSimulatorHelper(factory.startingBoard());
        comparer = new ChessboardComparerHelper();
    }

    @Nested
    @DisplayName("GameFlow: Fool's Mate scenario")
    class FoolsMate {

        @Test
        @DisplayName("Black wins by delivering checkmate with Queen in H4")
        void testBlackWinsByFoolsMate() {
            startGame()
                .verifyShown()
                .expectGameRestarted(1)
                .expectBoard(factory.startingBoard())
                .expectMessage("Game started! White begins!")
                .expectClockStarted(ChessPieceColor.WHITE);

            move(at(F, TWO), at(F, THREE))
                .expectBoard(simulator.move(at(F, TWO), at(F, THREE)))
                .expectRecord("White, (F, 2) → (F, 3), Score: {White=0}")
                .expectClockStopped(ChessPieceColor.WHITE)
                .expectClockStarted(ChessPieceColor.BLACK);

            move(at(E, SEVEN), at(E, FIVE))
                .expectBoard(simulator.move(at(E, SEVEN), at(E, FIVE)))
                .expectRecord("Black, (E, 7) → (E, 5), Score: {White=0, Black=0}")
                .expectClockStopped(ChessPieceColor.BLACK)
                .expectClockStarted(ChessPieceColor.WHITE);

            move(at(G, TWO), at(G, FOUR))
                .expectBoard(simulator.move(at(G, TWO), at(G, FOUR)))
                .expectRecord("White, (G, 2) → (G, 4), Score: {White=0, Black=0}")
                .expectClockStopped(ChessPieceColor.WHITE)
                .expectClockStarted(ChessPieceColor.BLACK);

            move(at(D, EIGHT), at(H, FOUR))
                .expectBoard(factory.startingBoard())
                .expectMessage("Checkmate! Black wins the game!")
                .expectGameRestarted(2)
                .expectMessage("Game started! White begins!")
                .expectClockStopped(ChessPieceColor.BLACK)
                .expectClockStarted(ChessPieceColor.WHITE);

            closeGame()
                .verifyClosed()
                .expectMessage("Exiting...");
        }
    }

    @Nested
    @DisplayName("Capture scenario")
    class Capture {

        @Test
        @DisplayName("White pawn captures Black pawn on E5")
        void testPawnCapture() {
            startGame();

            move(at(E, TWO), at(E, FOUR)).expectBoard(simulator.move(at(E, TWO), at(E, FOUR)));
            move(at(D, SEVEN), at(D, FIVE)).expectBoard(simulator.move(at(D, SEVEN), at(D, FIVE)));

            move(at(E, FOUR), at(D, FIVE)).expectBoard(simulator.move(at(E, FOUR), at(D, FIVE)))
                .expectRecord("White, (E, 4) → (D, 5), Score: {White=1, Black=0}");
        }
    }

    @Nested
    @DisplayName("En Passant scenario")
    class EnPassant {

        @Test
        @DisplayName("White executes en passant capture on D5")
        void testEnPassantCapture() {
            startGame();

            move(at(E, TWO), at(E, FOUR)).expectBoard(simulator.move(at(E, TWO), at(E, FOUR)));
            move(at(H, SEVEN), at(H, SIX)).expectBoard(simulator.move(at(H, SEVEN), at(H, SIX)));
            move(at(E, FOUR), at(E, FIVE)).expectBoard(simulator.move(at(E, FOUR), at(E, FIVE)));
            move(at(D, SEVEN), at(D, FIVE)).expectBoard(simulator.move(at(D, SEVEN), at(D, FIVE)));

            move(at(E, FIVE), at(D, SIX))
                .expectBoard(simulator.enPassant(at(E, FIVE), at(D, SIX)))
                .expectRecord("White, (E, 5) → (D, 6) (EnPassant), Score: {White=1, Black=0}");
        }
    }

    @Nested
    @DisplayName("Castling scenario")
    class Castling {

        @Test
        @DisplayName("White castles kingside")
        void testWhiteCastlesKingside() {
            startGame();

            move(at(G, ONE), at(F, THREE)).expectBoard(simulator.move(at(G, ONE), at(F, THREE)));
            move(at(H, SEVEN), at(H, SIX)).expectBoard(simulator.move(at(H, SEVEN), at(H, SIX)));
            move(at(E, TWO), at(E, THREE)).expectBoard(simulator.move(at(E, TWO), at(E, THREE)));
            move(at(G, SEVEN), at(G, SIX)).expectBoard(simulator.move(at(G, SEVEN), at(G, SIX)));
            move(at(F, ONE), at(C, FOUR)).expectBoard(simulator.move(at(F, ONE), at(C, FOUR)));
            move(at(A, SEVEN), at(A, SIX)).expectBoard(simulator.move(at(A, SEVEN), at(A, SIX)));

            move(at(E, ONE), at(G, ONE))
                .expectBoard(simulator.castleKingside(ChessPieceColor.WHITE))
                .expectRecord("White, (E, 1) → (G, 1) + (H, 1) → (F, 1) (Castling), Score: {White=0, Black=0}");
        }
    }

    @Nested
    @DisplayName("Promotion scenario")
    class Promotion {

        @Test
        @DisplayName("White pawn promotes to Queen on H8")
        void testPawnPromotionToQueen() {
            startGame();

            move(at(B, TWO), at(B, FOUR)).expectBoard(simulator.move(at(B, TWO), at(B, FOUR)));
            move(at(A, SEVEN), at(A, FIVE)).expectBoard(simulator.move(at(A, SEVEN), at(A, FIVE)));
            move(at(B, FOUR), at(A, FIVE)).expectBoard(simulator.move(at(B, FOUR), at(A, FIVE)));
            move(at(G, EIGHT), at(H, SIX)).expectBoard(simulator.move(at(G, EIGHT), at(H, SIX)));
            move(at(A, FIVE), at(A, SIX)).expectBoard(simulator.move(at(A, FIVE), at(A, SIX)));
            move(at(B, EIGHT), at(C, SIX)).expectBoard(simulator.move(at(B, EIGHT), at(C, SIX)));
            move(at(A, SIX), at(B, SEVEN)).expectBoard(simulator.move(at(A, SIX), at(B, SEVEN)));
            move(at(C, SIX), at(A, SEVEN)).expectBoard(simulator.move(at(C, SIX), at(A, SEVEN)));

            move(at(B, SEVEN), at(A, EIGHT))
                .expectBoard(simulator.promote(at(B, SEVEN), at(A, EIGHT), new Queen(ChessPieceColor.WHITE)))
                .expectRecord("White, (B, 7) → (A, 8) + (A, 8) → Queen (Promotion), Score: {White=7, Black=0}");
        }
    }

    @Nested
    @DisplayName("Illegal move scenario")
    class IllegalMove {

        @Test
        @DisplayName("Rejects illegal Knight move")
        void testIllegalMoveRejected() {
            startGame()
                .verifyShown()
                .expectBoard(factory.startingBoard())
                .expectMessage("Game started! White begins!");

            move(at(B, ONE), at(B, THREE))
                .expectMessage("Move rejected: Illegal move!")
                .expectBoard(factory.startingBoard());
        }
    }

    @Nested
    @DisplayName("Undo scenario")
    class UndoMove {

        @Test
        @DisplayName("Undo last move successfully")
        void testUndoLastMove() {
            startGame();

            move(at(E, TWO), at(E, FOUR))
                .expectBoard(simulator.move(at(E, TWO), at(E, FOUR)))
                .expectRecord("White, (E, 2) → (E, 4), Score: {White=0}");

            undo()
                .expectBoard(factory.startingBoard())
                .expectRecord("White, Undone (E, 2) → (E, 4), Score: {White=0}");
        }

        @Test
        @DisplayName("Undo rejected when no moves exist")
        void testUndoWithoutAnyMoves() {
            startGame()
                .expectMessage("Game started! White begins!");

            undo()
                .expectMessage("Move rejected: No undo available!")
                .expectBoard(factory.startingBoard());
        }
    }

    @Nested
    @DisplayName("Time-out scenario")
    class TimeOut {

        @Test
        @DisplayName("White runs out of time, Black wins")
        void testTimeRunsOutForWhite() {
            startGame()
                .verifyShown()
                .expectGameRestarted(1)
                .expectBoard(factory.startingBoard())
                .expectMessage("Game started! White begins!")
                .expectClockStarted(ChessPieceColor.WHITE);

            timeRunsOut(ChessPieceColor.WHITE)
                .expectMessage("Time's up! Black wins the game!")
                .expectGameRestarted(2)
                .expectMessage("Game started! White begins!")
                .expectClockStopped(ChessPieceColor.BLACK)
                .expectClockStarted(ChessPieceColor.WHITE);
        }

        @Test
        @DisplayName("Black runs out of time, White wins")
        void testTimeRunsOutForBlack() {
            startGame()
                .verifyShown()
                .expectGameRestarted(1)
                .expectBoard(factory.startingBoard())
                .expectMessage("Game started! White begins!")
                .expectClockStarted(ChessPieceColor.WHITE);

            move(at(F, TWO), at(F, THREE))
                .expectBoard(simulator.move(at(F, TWO), at(F, THREE)))
                .expectRecord("White, (F, 2) → (F, 3), Score: {White=0}")
                .expectClockStopped(ChessPieceColor.WHITE)
                .expectClockStarted(ChessPieceColor.BLACK);
            
            timeRunsOut(ChessPieceColor.BLACK)
                .expectMessage("Time's up! White wins the game!")
                .expectGameRestarted(2)
                .expectMessage("Game started! White begins!")
                .expectClockStopped(ChessPieceColor.BLACK)
                .expectClockStarted(ChessPieceColor.WHITE);
        }
    }

    private ChessAcceptanceTest startGame() {
        controller.start();
        return this;
    }

    private ChessAcceptanceTest closeGame() {
        controller.end();
        return this;
    }

    private ChessboardPosition at(ChessboardFile file, ChessboardRank rank) {
        return new ChessboardPosition(file, rank);
    }

    private ChessAcceptanceTest move(ChessboardPosition from, ChessboardPosition to) {
        controller.makeMove(from, to);
        return this;
    }

    private ChessAcceptanceTest undo() {
        controller.undoMove();
        return this;
    }

    private ChessAcceptanceTest timeRunsOut(ChessPieceColor color) {
        controller.proclaimWinner(color.opponent(), GameReason.TIMEOUT);
        return this;
    }

    private ChessAcceptanceTest verifyShown() {
        assertTrue(viewSpy.isShown());
        return this;
    }

    private ChessAcceptanceTest expectGameRestarted(int resetCount) {
        assertEquals(resetCount, viewSpy.getResetCount());
        return this;
    }

    private ChessAcceptanceTest verifyClosed() {
        assertTrue(viewSpy.isClosed());
        return this;
    }

    private ChessAcceptanceTest expectMessage(String expected) {
        assertEquals(expected, viewSpy.popLastDisplayedMessage());
        return this;
    }

    private ChessAcceptanceTest expectRecord(String expected) {
        assertEquals(expected, viewSpy.getLastRecordedMessage());
        return this;
    }

    private ChessAcceptanceTest expectBoard(Chessboard expected) {
        assertTrue(comparer.equals(expected, viewSpy.getLastBoard()));
        return this;
    }

    private ChessAcceptanceTest expectClockStopped(ChessPieceColor color) {
        assertTrue(viewSpy.getLastClockStopFor(color));
        return this;
    }

    private ChessAcceptanceTest expectClockStarted(ChessPieceColor color) {
        assertTrue(viewSpy.getLastClockStartFor(color));
        return this;
    }
}