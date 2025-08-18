package com.sdm.units.chessgame.gamelogic.board.evaluation;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.move.core.MoveComponent;
import com.sdm.units.chessgame.gamelogic.move.core.MoveGenerator;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;

public class CheckmateService implements CheckmateEvaluator {

    private final MoveGenerator moveGenerator;
    private final CheckEscapeSimulator simulator;
    private final AttackDetector attackDetector;
    private final ChessboardOrientation orientation;

    public CheckmateService(MoveGenerator moveGenerator, CheckEscapeSimulator simulator, AttackDetector attackDetector, ChessboardOrientation orientation) {
        this.moveGenerator = moveGenerator;
        this.simulator = simulator;
        this.attackDetector = attackDetector;
        this.orientation = orientation;
    }

    @Override
    public boolean isCheckmate(Chessboard board, ChessPieceColor defenderColor) {
        if (!attackDetector.isUnderAttack(board, defenderColor)) return false;

        return board.getOccupiedSquaresOf(defenderColor).stream()
            .noneMatch(from -> moveGenerator.generateMovesFrom(board, from, orientation).stream()
                .map(ReversibleMove::getPrimaryMoveComponent)
                .map(MoveComponent::to)
                .anyMatch(to -> simulator.resolvesCheck(board, from, to, defenderColor)));
    }
}