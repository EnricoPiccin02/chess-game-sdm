package com.sdm.units.chessgame.gamelogic.board.evaluation;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.move.core.MoveValidator;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;

public class MoveSimulationService implements CheckEscapeSimulator, PathSafetySimulator {

    private final MoveValidator moveValidator;
    private final AttackDetector attackDetector;
    private final ChessboardOrientation orientation;

    public MoveSimulationService(MoveValidator moveValidator, AttackDetector attackDetector, ChessboardOrientation orientation) {
        this.moveValidator = moveValidator;
        this.attackDetector = attackDetector;
        this.orientation = orientation;
    }

    @Override
    public boolean resolvesCheck(Chessboard board, ChessboardPosition from, ChessboardPosition to, ChessPieceColor defenderColor) {
        Optional<ReversibleMove> maybeMove = moveValidator.validateAndCreate(board, from, to, orientation);
        if (maybeMove.isEmpty()) return false;

        ReversibleMove move = maybeMove.get();
        move.executeOn(board);

        boolean escape = !attackDetector.isUnderAttack(board, defenderColor);
        move.undoOn(board);

        return escape;
    }

    @Override
    public boolean isPathSafe(Chessboard board, ChessboardPosition from, List<ChessboardPosition> path, ChessPieceColor color) {
        ChessboardPosition current = from;
        Deque<ReversibleMove> executedMoves = new ArrayDeque<>();

        try {
            for (ChessboardPosition next : path) {
                Optional<ReversibleMove> maybeMove = moveValidator.validateAndCreate(board, current, next, orientation);
                if (maybeMove.isEmpty()) {
                    return false;
                }
                ReversibleMove move = maybeMove.get();
                move.executeOn(board);
                executedMoves.push(move);

                if (attackDetector.isUnderAttack(board, color)) {
                    return false;
                }
                current = next;
            }
            return true;
        } finally {
            while (!executedMoves.isEmpty()) {
                executedMoves.pop().undoOn(board);
            }
        }
    }
}