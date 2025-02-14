package com.sdm.units.chessgame.gamelogic;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdm.units.chessgame.gamelogic.basics.ChessPieceMove;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardPosition;

import java.util.OptionalInt;

public class ChessboardMovementHandler {

    public OptionalInt makeMove(Chessboard chessboard, ChessboardPosition fromPosition, ChessboardPosition toPosition) throws IllegalArgumentException {
        OptionalInt capturedPieceValue = chessboard.getPieceValueFromPosition(toPosition);
        
        if (getPieceValidMoves(chessboard, fromPosition).stream().anyMatch(position -> position.equals(toPosition)))
            throw new IllegalArgumentException("The move is not legit");
        
        chessboard.movePiece(fromPosition, toPosition);
        
        return capturedPieceValue;
    }

    public List<ChessboardPosition> getPieceValidMoves(Chessboard chessboard, ChessboardPosition fromPosition) {
        return Stream.concat(
            validMovements(chessboard, fromPosition).stream(),
            validCapturingMoves(chessboard, fromPosition).stream()
        ).collect(Collectors.toList());
    }

    private Map<ChessboardDirection, OptionalInt> maxAllowedDistanceForEachDirection(Chessboard chessboard, ChessboardPosition fromPosition) {
        return chessboard.getPieceFromPosition(fromPosition).map(piece -> piece
            .getPossibleMoves(fromPosition).stream()
            .sorted()
            .filter(move -> !chessboard.isPositionVacant(move.position()))
            .collect(Collectors.toMap(
                ChessPieceMove::direction,
                move -> move.position().distance(fromPosition),
                (d1, d2) -> d1 // In case of duplicates, keep the first distance
            ))
        ).orElse(Collections.emptyMap());
    }

    private List<ChessboardPosition> validMovements(Chessboard chessboard, ChessboardPosition fromPosition) {
        Map<ChessboardDirection, OptionalInt> invalidDirectionDistancesMap = maxAllowedDistanceForEachDirection(chessboard, fromPosition);
        
        return chessboard.getPieceFromPosition(fromPosition).map(piece -> piece
            .getPossibleMoves(fromPosition).stream()
            .filter(move -> {
                OptionalInt maxDistanceForDirection = invalidDirectionDistancesMap.get(move.direction());
                OptionalInt positionDistance = move.position().distance(fromPosition);

                if(maxDistanceForDirection != null && maxDistanceForDirection.isPresent() && positionDistance.isPresent())
                    return positionDistance.getAsInt() < maxDistanceForDirection.getAsInt();
                else
                    return true;
            })
            .map(ChessPieceMove::position)
            .collect(Collectors.toList())
        ).orElse(Collections.emptyList());
    }

    private List<ChessboardPosition> validCapturingMoves(Chessboard chessboard, ChessboardPosition fromPosition) {
        return chessboard.getPieceFromPosition(fromPosition).map(piece -> piece
            .getCaptureMoves(fromPosition).stream()
            .filter(move -> !chessboard.isPositionVacant(move.position()))
            .collect(Collectors.toMap(
                ChessPieceMove::direction,
                ChessPieceMove::position,
                (pos1, pos2) -> pos1 // In case of duplicates, keep the first position
            ))
            .values()
            .stream()
            .collect(Collectors.toList())
        ).orElse(Collections.emptyList());
    }
}