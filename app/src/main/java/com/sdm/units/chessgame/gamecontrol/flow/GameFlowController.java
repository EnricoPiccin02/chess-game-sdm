package com.sdm.units.chessgame.gamecontrol.flow;

import com.sdm.units.chessgame.gamecontrol.state.GameReason;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.move.result.MoveResult;

public interface GameFlowController {

    void onGameStart();

    void onMoveApplied(MoveResult result);

    void onMoveUndone(MoveResult result);

    void onMoveRejected(GameReason reason);

    void onPlayerWon(ChessPieceColor winner, GameReason reason);

    void onGameEnd();
}