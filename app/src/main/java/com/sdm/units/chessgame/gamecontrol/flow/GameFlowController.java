package com.sdm.units.chessgame.gamecontrol.flow;

import com.sdm.units.chessgame.gamelogic.move.result.MoveResult;

public interface GameFlowController {

    void onGameStart();

    void onMoveApplied(MoveResult result);

    void onMoveUndone(MoveResult result);

    void onMoveRejected(String reason);

    void onPlayerWon();

    void onGameEnd();
}