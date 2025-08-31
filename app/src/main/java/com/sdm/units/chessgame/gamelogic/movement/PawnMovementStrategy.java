package com.sdm.units.chessgame.gamelogic.movement;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
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

    public ChessboardOrientation getOrientation() {
        return orientation;
    }

    @Override
    public Set<ChessboardPosition> getLegalDestinations(Chessboard board, ChessboardPosition fromPosition, ChessPieceColor playerColor) {
        Set<ChessboardPosition> legalDestinations = new HashSet<>();

        legalDestinations.addAll(getForwardMoves(board, fromPosition, orientation.pawnForwardDirection(playerColor)));
        legalDestinations.addAll(getCaptureMoves(board, playerColor, fromPosition, orientation.pawnCaptureDirections(playerColor)));

        return legalDestinations;
    }

    private Set<ChessboardPosition> getForwardMoves(Chessboard board, ChessboardPosition fromPosition, ChessboardDirection forward) {
        Set<ChessboardPosition> moves = new HashSet<>();
        final boolean hasMoved = board.getPieceAt(fromPosition).map(ChessPiece::hasMoved).orElse(false);

        Optional<ChessboardPosition> oneStep = fromPosition.nextPosition(forward);
        if (oneStep.isPresent() && board.isUnoccupiedSquare(oneStep.get())) {
            moves.add(oneStep.get());

            if (!hasMoved) {
                Optional<ChessboardPosition> twoStep = oneStep.get().nextPosition(forward);
                if (twoStep.isPresent() && board.isUnoccupiedSquare(twoStep.get())) {
                    moves.add(twoStep.get());
                }
            }
        }

        return moves;
    }

    private Set<ChessboardPosition> getCaptureMoves(Chessboard board, ChessPieceColor player, ChessboardPosition fromPosition, Set<ChessboardDirection> captureDirs) {
        return captureDirs.stream()
            .map(fromPosition::nextPosition)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .filter(pos -> board.isOpponentAt(player, pos))
            .collect(Collectors.toSet());
    }
}