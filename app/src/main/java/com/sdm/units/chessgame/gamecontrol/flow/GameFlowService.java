package com.sdm.units.chessgame.gamecontrol.flow;

import java.util.ArrayList;
import java.util.List;

import com.sdm.units.chessgame.gamecontrol.state.GameReason;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.move.result.MoveResult;
import com.sdm.units.chessgame.gui.controller.events.ChessGameEvent;
import com.sdm.units.chessgame.gui.controller.events.ChessGameEventListener;
import com.sdm.units.chessgame.gui.controller.events.ChessGameEventPublisher;

public final class GameFlowService implements GameFlowController, ChessGameEventPublisher {

    private final ScoreKeeper scores;
    private final TurnManager turns;
    private final EventFactory notifier;
    private final List<ChessGameEventListener> listeners = new ArrayList<>();

    public GameFlowService(ScoreKeeper scores, TurnManager turns, EventFactory notifier) {
        this.scores = scores;
        this.turns = turns;
        this.notifier = notifier;
    }

    @Override
    public void addChessGameEventListener(ChessGameEventListener l) {
        listeners.add(l);
    }

    @Override
    public void removeChessGameEventListener(ChessGameEventListener l) {
        listeners.remove(l);
    }

    @Override
    public void fireEvent(ChessGameEvent event) {
        for (ChessGameEventListener listener : listeners) {
            listener.onChessGameEvent(event);
        }
    }

    @Override
    public void onGameStart() {
        scores.reset();
        turns.start();
        fireEvent(notifier.gameStarted(turns.current()));
    }

    @Override
    public void onMoveApplied(MoveResult result) {
        scores.apply(result, turns.current());
        fireEvent(notifier.moveApplied(result, turns, scores));
        turns.swap();
    }

    @Override
    public void onMoveUndone(MoveResult result) {
        scores.revert(result, turns.opponent());
        fireEvent(notifier.moveUndone(result, turns, scores));
        turns.swap();
    }

    @Override
    public void onMoveRejected(GameReason reason) {
        fireEvent(notifier.moveRejected(reason));
    }
    
    @Override
    public void onPlayerWon(ChessPieceColor winner, GameReason reason) {
        fireEvent(notifier.playerWon(turns, winner, reason));
    }

    public void onGameEnd(GameReason reason) {
        fireEvent(notifier.gameEnded(reason));
    }
}