package com.sdm.units.chessgame.gamecontrol.flow;

import com.sdm.units.chessgame.gamecontrol.state.GameReason;
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

public final class ChessGameEventFactory implements EventFactory {
    
    private final Chessboard board;

    public ChessGameEventFactory(Chessboard board) {
        this.board = board;
    }

    @Override
    public ChessGameEvent gameStarted(ChessPieceColor firstPlayer) {
        return new CompositeChessGameEvent(
            new UpdateChessboardEvent(board),
            new GameStartedEvent("Game started! " + firstPlayer + " begins!"),
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
    public ChessGameEvent moveRejected(GameReason reason) {
        return new CompositeChessGameEvent(
            new UpdateChessboardEvent(board),
            new GameMessageEvent("Move rejected: " + reason.getDescription())
        );
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
    public ChessGameEvent playerWon(TurnManager turns, ChessPieceColor winner, GameReason reason) {
        return new CompositeChessGameEvent(
            new UpdateChessboardEvent(board),
            new ClockStopEvent(turns.current()),
            new ClockStopEvent(turns.opponent()),
            new GameMessageEvent(reason.getDescription() + " " + winner + " wins the game!")
        );
    }

    @Override
    public ChessGameEvent gameEnded(GameReason reason) {
        return new GameEndedEvent(reason.getDescription());
    }   
}