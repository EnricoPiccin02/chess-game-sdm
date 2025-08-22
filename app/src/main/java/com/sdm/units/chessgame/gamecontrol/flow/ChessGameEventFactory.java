package com.sdm.units.chessgame.gamecontrol.flow;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.move.result.MoveResult;
import com.sdm.units.chessgame.gui.controller.events.ChessGameEvent;
import com.sdm.units.chessgame.gui.controller.events.ClockStartEvent;
import com.sdm.units.chessgame.gui.controller.events.ClockStopEvent;
import com.sdm.units.chessgame.gui.controller.events.CompositeChessGameEvent;
import com.sdm.units.chessgame.gui.controller.events.GameEndedEvent;
import com.sdm.units.chessgame.gui.controller.events.GameMessageEvent;
import com.sdm.units.chessgame.gui.controller.events.GameRecordEvent;
import com.sdm.units.chessgame.gui.controller.events.GameStartedEvent;
import com.sdm.units.chessgame.gui.controller.events.UpdateChessboardEvent;

public class ChessGameEventFactory implements EventFactory {
    
    private final Chessboard board;

    public ChessGameEventFactory(Chessboard board) {
        this.board = board;
    }

    @Override
    public ChessGameEvent gameStarted(ChessPieceColor firstPlayer) {
        return new CompositeChessGameEvent(
            new GameStartedEvent("Game started! " + firstPlayer + " begins!"),
            new UpdateChessboardEvent(board),
            new ClockStartEvent(firstPlayer)
        );
    }

    @Override
    public ChessGameEvent moveApplied(MoveResult result, TurnManager turns, ScoreKeeper scores) {
        return new CompositeChessGameEvent(
            new GameRecordEvent(turns.current() + ", " + result.move() + ", " + scores),
            new UpdateChessboardEvent(board),
            new ClockStopEvent(turns.current()),
            new ClockStartEvent(turns.opponent())
        );
    }

    @Override
    public ChessGameEvent moveRejected(String reason) {
        return new GameMessageEvent("Move rejected: " + reason);
    }

    @Override
    public ChessGameEvent moveUndone(MoveResult result, TurnManager turns, ScoreKeeper scores) {
        return new CompositeChessGameEvent(
            new GameRecordEvent(turns.opponent() + ", Undone " + result.move() + ", " + scores),
            new UpdateChessboardEvent(board),
            new ClockStopEvent(turns.current()),
            new ClockStartEvent(turns.opponent())
        );
    }

    @Override
    public ChessGameEvent playerInCheck(ChessPieceColor player) {
        return new CompositeChessGameEvent(
            new UpdateChessboardEvent(board),
            new GameMessageEvent(player + " is in check!")
        );
    }

    @Override
    public ChessGameEvent playerWon(TurnManager turns) {
        return new CompositeChessGameEvent(
            new UpdateChessboardEvent(board),
            new ClockStopEvent(turns.current()),
            new ClockStartEvent(turns.opponent()),
            new GameMessageEvent(turns.current() + " wins the game!")
        );
    }

    @Override
    public ChessGameEvent gameEnded() {
        return new GameEndedEvent("Exiting...");
    }   
}