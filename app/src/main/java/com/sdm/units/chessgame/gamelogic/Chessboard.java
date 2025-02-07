package com.sdm.units.chessgame.gamelogic;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.sdm.units.chessgame.pieces.Bishop;
import com.sdm.units.chessgame.pieces.ChessPiece;
import com.sdm.units.chessgame.pieces.King;
import com.sdm.units.chessgame.pieces.Knight;
import com.sdm.units.chessgame.pieces.Pawn;
import com.sdm.units.chessgame.pieces.Queen;
import com.sdm.units.chessgame.pieces.Rook;

public class Chessboard {
    
    protected Map<ChessboardPosition, ChessPiece> board;

    public Chessboard() {
        board = new HashMap<>(ChessboardFile.values().length * ChessboardRank.values().length);

        for (ChessboardFile file : ChessboardFile.values()) {
            for (ChessboardRank rank : ChessboardRank.values()) {
                board.put(new ChessboardPosition(file, rank), null);
            }
        }

        for (ChessPieceColor color : ChessPieceColor.values()) {
            board.putAll(ChessboardInitialSetup.getPawnStartingPositions(color).stream()
                .collect(HashMap::new, (m, p) -> m.put(p, new Pawn(color)), HashMap::putAll)
            );
            board.putAll(ChessboardInitialSetup.getRookStartingPositions(color).stream()
                .collect(HashMap::new, (m, p) -> m.put(p, new Rook(color)), HashMap::putAll)
            );
            board.putAll(ChessboardInitialSetup.getKnightStartingPositions(color).stream()
                .collect(HashMap::new, (m, p) -> m.put(p, new Knight(color)), HashMap::putAll)
            );
            board.putAll(ChessboardInitialSetup.getBishopStartingPositions(color).stream()
                .collect(HashMap::new, (m, p) -> m.put(p, new Bishop(color)), HashMap::putAll)
            );
            board.put(ChessboardInitialSetup.getQueenStartingPosition(color), new Queen(color));
            board.put(ChessboardInitialSetup.getKingStartingPosition(color), new King(color));
        }
    }

    public Map<ChessboardPosition, ChessPiece> getBoard() {
        return board;
    }

    public ChessPiece getPiece(ChessboardPosition position) {
        return board.get(position);
    }

    public boolean isPositionVacant(ChessboardPosition position) {
        return getPiece(position) == null;
    }

    public int movePiece(ChessboardPosition fromPosition, ChessboardPosition toPosition) {
        ChessPiece startingPiece = getPiece(fromPosition);
        ChessPiece endingPiece = getPiece(toPosition);
        board.put(fromPosition, null);
        board.put(toPosition, startingPiece);
        startingPiece.setHasMoved(true);
        return endingPiece == null ? 0 : endingPiece.pieceInfo().getPieceValue();
    }

    public List<ChessboardPosition> getPieceValidMoves(ChessboardPosition fromPosition) {
        ChessPiece piece = getPiece(fromPosition);
        List<ChessboardPosition> validMoves = Collections.emptyList();

        if (piece == null) {
            return Collections.emptyList();
        }

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