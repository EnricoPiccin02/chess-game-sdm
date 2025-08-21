package com.sdm.units.chessgame.gamelogic.move.special.castling;

import java.util.List;

import com.sdm.units.chessgame.gamelogic.board.evaluation.AttackDetector;
import com.sdm.units.chessgame.gamelogic.board.evaluation.PathSafetySimulator;
import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMoveEligibility;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public class CastlingEligibility implements SpecialMoveEligibility<CastlingCandidate> {

    private final PathSafetySimulator simulator;
    private final AttackDetector attackDetector;
    private final CastlingPattern pattern;

    public CastlingEligibility(PathSafetySimulator simulator, AttackDetector attackDetector, CastlingPattern pattern) {
        this.simulator = simulator;
        this.attackDetector = attackDetector;
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
        return !attackDetector.isUnderAttack(board, color);
    }
    
    private boolean isPathClear(Chessboard board, CastlingCandidate candidate, List<ChessboardPosition> kingPath) {
        return kingPath.stream().allMatch(board::isUnoccupiedSquare);
    }

    private boolean isPathSafeForKing(Chessboard board, CastlingCandidate candidate, List<ChessboardPosition> kingPath) {
        return simulator.isPathSafe(board, candidate.kingFrom(), kingPath, candidate.king().pieceColor());
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
            isPathSafeForKing(board, candidate, kingPath);
    }
}