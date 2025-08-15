package com.sdm.units.chessgame.gamelogic.move.standard;

import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.move.core.CaptureResult;
import com.sdm.units.chessgame.gamelogic.move.core.SingleMove;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPieceSnapshot;

public class StandardMove extends SingleMove {

    private final ChessPiece movingPiece;
    private final Optional<ChessPiece> capturedPiece;
    private final ChessPieceSnapshot movedSnapshot;

    public StandardMove(ChessboardPosition from, ChessboardPosition to, ChessPiece movingPiece, Optional<ChessPiece> capturedPiece) {
        super(from, to);
        this.movingPiece = movingPiece;
        this.capturedPiece = capturedPiece;
        this.movedSnapshot = movingPiece.createSnapshot();
    }

    @Override
    public CaptureResult executeOn(Chessboard board) {
        movingPiece.markAsMoved();
        board.removePieceAt(from());
        board.putPieceAt(to(), movingPiece);
        return new CaptureResult(capturedPiece);
    }

    @Override
    public CaptureResult undoOn(Chessboard board) {
        capturedPiece.ifPresentOrElse(
            captured -> board.putPieceAt(to(), captured),
            () -> board.removePieceAt(to())
        );
        board.putPieceAt(from(), movingPiece);
        movingPiece.restoreSnapshot(movedSnapshot);
        return new CaptureResult(capturedPiece);
    }
}