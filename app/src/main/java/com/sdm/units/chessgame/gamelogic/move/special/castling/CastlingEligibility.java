package com.sdm.units.chessgame.gamelogic.move.special.castling;

import java.util.List;
import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.board.CheckEvaluator;
import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.move.core.MoveValidator;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMoveEligibility;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public class CastlingEligibility implements SpecialMoveEligibility<CastlingCandidate> {

    private final MoveValidator moveValidator;
    private final CheckEvaluator checkEvaluator;
    private final CastlingPattern pattern;

    public CastlingEligibility(MoveValidator moveValidator, CheckEvaluator checkEvaluator, CastlingPattern pattern) {
        this.moveValidator = moveValidator;
        this.checkEvaluator = checkEvaluator;
        this.pattern = pattern;
    }

    private boolean isKing(ChessPiece king) {
        return king.pieceInfo() == ChessPieceInfo.KING;
    }

    private boolean isRook(ChessPiece rook) {
        return rook.pieceInfo() == ChessPieceInfo.ROOK;
    }

    private boolean haveSameColor(ChessPiece king, ChessPiece rook) {
        return king.pieceColor().equals(rook.pieceColor());
    }
    
    private boolean haveNotMoved(ChessPiece king, ChessPiece rook) {
        return !king.hasMoved() && !rook.hasMoved();
    }

    private boolean isNotInCheck(Chessboard board, ChessPieceColor color) {
        return !checkEvaluator.isUnderAttack(board, color);
    }
    
    private boolean isPathClear(Chessboard board, CastlingCandidate candidate, List<ChessboardPosition> kingPath) {
        return kingPath.stream().allMatch(board::isUnoccupiedSquare);
    }

    private boolean isPathSafeForKing(Chessboard board, CastlingCandidate candidate, List<ChessboardPosition> kingPath, ChessboardOrientation orientation) {
        Chessboard copy = board.deepCopy();
        ChessboardPosition currentKingPosition = candidate.kingFrom();

        for (ChessboardPosition intermediate : kingPath) {
            Optional<ReversibleMove> maybeMove = moveValidator.validateAndCreate(copy, currentKingPosition, intermediate, orientation);
            if (maybeMove.isPresent()) {
                ReversibleMove move = maybeMove.get();
                move.executeOn(copy);
                currentKingPosition = intermediate;

                if (checkEvaluator.isUnderAttack(copy, candidate.king().pieceColor())) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean canExecute(Chessboard board, CastlingCandidate candidate, ChessboardOrientation orientation) {        
        ChessPiece king = candidate.king();
        ChessPiece rook = candidate.rook();
        List<ChessboardPosition> kingPath = pattern.kingPathSquares(candidate.kingFrom(), candidate.rookFrom());

        return isKing(king) && isRook(rook) &&
            haveSameColor(king, rook) &&
            haveNotMoved(king, rook) &&
            isNotInCheck(board, king.pieceColor()) &&
            isPathClear(board, candidate, kingPath) &&
            isPathSafeForKing(board, candidate, kingPath, orientation);
    }
}