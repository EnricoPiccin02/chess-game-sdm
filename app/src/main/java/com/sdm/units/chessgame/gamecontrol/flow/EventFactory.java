package com.sdm.units.chessgame.gamecontrol.flow;

import com.sdm.units.chessgame.gamecontrol.state.GameReason;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.move.result.MoveResult;
import com.sdm.units.chessgame.gui.controller.events.ChessGameEvent;

public interface EventFactory {

    ChessGameEvent gameStarted(ChessPieceColor firstPlayer);

    ChessGameEvent moveApplied(MoveResult result, TurnManager turns, ScoreKeeper scores);

    ChessGameEvent moveRejected(GameReason reason);

    ChessGameEvent moveUndone(MoveResult result, TurnManager turns, ScoreKeeper scores);

    ChessGameEvent playerWon(TurnManager turns, ChessPieceColor winner, GameReason reason);

    ChessGameEvent gameEnded(GameReason reason);
}