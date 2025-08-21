package com.sdm.units.chessgame.gamelogic.move.special.castling;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMovePattern;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public class CastlingPattern implements SpecialMovePattern<CastlingCandidate> {

    @Override
    public List<CastlingCandidate> findCandidates(Chessboard board, ChessboardPosition kingFrom, ChessboardOrientation orientation) {
        return allCandidates(board, kingFrom)
                .toList();
    }

    @Override
    public Optional<CastlingCandidate> buildCandidate(Chessboard board, ChessboardPosition kingFrom, ChessboardPosition kingTo) {
        return allCandidates(board, kingFrom)
                .filter(candidate -> candidate.kingTo().equals(kingTo))
                .findFirst();
    }

    public List<ChessboardPosition> kingPathSquares(ChessboardPosition kingFrom, ChessboardPosition rookFrom) {
        return CastlingSide.fromRookFile(rookFrom.file())
                .map(side -> side.getKingPath(kingFrom))
                .orElse(List.of());
    }

    private Stream<CastlingCandidate> allCandidates(Chessboard board, ChessboardPosition kingFrom) {
        return Arrays.stream(CastlingSide.values())
                .map(side -> createCandidate(board, kingFrom, side))
                .flatMap(Optional::stream);
    }

    private Optional<CastlingCandidate> createCandidate(Chessboard board, ChessboardPosition kingFrom, CastlingSide side) {
        Optional<ChessPiece> maybeKing = board.getPieceAt(kingFrom);
        Optional<ChessPiece> maybeRook = board.getPieceAt(side.getRookFrom(kingFrom));

        if (maybeKing.isEmpty() || maybeRook.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new CastlingCandidate(
                kingFrom,
                side.getKingTo(kingFrom),
                side.getRookFrom(kingFrom),
                side.getRookTo(kingFrom),
                maybeKing.get(),
                maybeRook.get()
        ));
    }
}