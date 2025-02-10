package com.sdm.units.chessgame.gamelogic;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdm.units.chessgame.pieces.ChessPiece;

public class Chessboard {
    
    protected Map<ChessboardPosition, ChessPiece> board;

    public Chessboard() {
        board = new HashMap<>(ChessboardInitialSetupBuilder.getChessboardInitialSetup());

        Stream.of(ChessboardFile.values()).forEach(file -> {
            Stream.of(ChessboardRank.values()).forEach(rank -> {
                board.putIfAbsent(new ChessboardPosition(file, rank), null);
            });
        });
    }

    public void resetBoard() {
        board.clear();
    }

    public Map<ChessboardPosition, ChessPiece> getBoard() {
        return board;
    }

    public ChessPiece getPieceFromPosition(ChessboardPosition position) {
        return board.get(position);
    }

    public boolean isPositionVacant(ChessboardPosition position) {
        return getPieceFromPosition(position) == null;
    }

    public int movePiece(ChessboardPosition fromPosition, ChessboardPosition toPosition) {
        ChessPiece startingPiece = getPieceFromPosition(fromPosition);
        ChessPiece endingPiece = getPieceFromPosition(toPosition);
        board.put(fromPosition, null);
        board.put(toPosition, startingPiece);
        startingPiece.setHasMoved(true);
        return endingPiece == null ? 0 : endingPiece.pieceInfo().getPieceValue();
    }

    public List<ChessboardPosition> getPieceValidMoves(ChessboardPosition fromPosition) {
        ChessPiece piece = getPieceFromPosition(fromPosition);
        List<ChessboardPosition> validMoves = Collections.emptyList();
        
        if (isPositionVacant(fromPosition)) return Collections.emptyList();

        Map<ChessboardDirection, Integer> invalidDirectionDistancesMap = piece.getPossibleMoves(fromPosition).stream()
            .sorted()
            .filter(move -> !isPositionVacant(move.position()))
            .collect(Collectors.toMap(
                ChessPieceMove::direction,
                move -> move.position().distance(fromPosition),
                (d1, d2) -> d1 // In case of duplicates, keep the first one
        ));

        validMoves.addAll(piece.getPossibleMoves(fromPosition).stream()
            .filter(move -> {
                Integer limitDistanceForDirection = invalidDirectionDistancesMap.get(move.direction());
                return limitDistanceForDirection == null || move.position().distance(fromPosition) >= limitDistanceForDirection;
            })
            .map(ChessPieceMove::position)
            .collect(Collectors.toList())
        );

        validMoves.addAll(piece.getCaptureMoves(fromPosition).stream()
            .filter(move -> !isPositionVacant(move.position()))
            .collect(Collectors.toMap(
                ChessPieceMove::direction,
                ChessPieceMove::position,
                (pos1, pos2) -> pos1 // In case of duplicates, keep the first one
            ))
            .values()
            .stream()
            .collect(Collectors.toList())
        );

        return validMoves;
    }
}