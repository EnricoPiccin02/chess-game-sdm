package com.sdm.units.chessgame.gamecontrol.state;

import com.sdm.units.chessgame.gamelogic.board.evaluation.GameOutcomeEvaluator;
import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.board.state.LegalMoveFinder;
import com.sdm.units.chessgame.gamelogic.board.state.MoveExecutor;

public record BoardAssembly(
    Chessboard board,
    MoveExecutor executor,
    LegalMoveFinder moveFinder,
    GameOutcomeEvaluator outcome
) { }