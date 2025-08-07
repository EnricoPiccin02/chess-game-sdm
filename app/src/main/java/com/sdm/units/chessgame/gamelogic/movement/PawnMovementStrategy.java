package com.sdm.units.chessgame.gamelogic.movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public class PawnMovementStrategy implements MovementStrategy {

    private final ChessboardOrientation orientation;

    public PawnMovementStrategy(ChessboardOrientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public List<ChessboardPosition> getLegalMoves(Chessboard board, ChessboardPosition fromPosition, ChessPieceColor playerColor) {
        List<ChessboardPosition> legalMoves = new ArrayList<>();

        legalMoves.addAll(getForwardMoves(board, fromPosition, orientation.pawnForwardDirection(playerColor)));
        legalMoves.addAll(getCaptureMoves(board, playerColor, fromPosition, orientation.pawnCaptureDirections(playerColor)));

        return legalMoves;
    }

    private List<ChessboardPosition> getForwardMoves(Chessboard board, ChessboardPosition fromPosition, ChessboardDirection forward) {
        List<ChessboardPosition> moves = new ArrayList<>();
        final boolean hasMoved = board.getPieceAt(fromPosition).map(ChessPiece::hasMoved).orElse(false);

        Optional<ChessboardPosition> oneStep = fromPosition.nextPosition(forward);
        if (oneStep.isPresent() && board.isPositionVacant(oneStep.get())) {
            moves.add(oneStep.get());

            if (!hasMoved) {
                Optional<ChessboardPosition> twoStep = oneStep.get().nextPosition(forward);
                if (twoStep.isPresent() && board.isPositionVacant(twoStep.get())) {
                    moves.add(twoStep.get());
                }
            }
        }

        return moves;
    }

    private List<ChessboardPosition> getCaptureMoves(Chessboard board, ChessPieceColor player, ChessboardPosition fromPosition, List<ChessboardDirection> captureDirs) {
        return captureDirs.stream()
            .map(fromPosition::nextPosition)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .filter(pos -> board.isOpponentAt(player, pos))
            .toList();
    }
}