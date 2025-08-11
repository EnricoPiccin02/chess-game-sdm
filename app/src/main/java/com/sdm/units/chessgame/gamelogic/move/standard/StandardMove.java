package com.sdm.units.chessgame.gamelogic.move.standard;

import java.util.List;
import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.move.api.MoveComponent;
import com.sdm.units.chessgame.gamelogic.move.api.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPieceSnapshot;

public class StandardMove implements ReversibleMove {

    private final ChessboardPosition from;
    private final ChessboardPosition to;
    private final ChessPiece movedPiece;
    private final Optional<ChessPiece> capturedPiece;
    private final ChessPieceSnapshot movedSnapshot;

    public StandardMove(ChessboardPosition from, ChessboardPosition to, ChessPiece movedPiece, Optional<ChessPiece> capturedPiece) {
        this.from = from;
        this.to = to;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
        this.movedSnapshot = movedPiece.createSnapshot();
    }

    @Override
    public void executeOn(Chessboard board) {
        movedPiece.markAsMoved();
        board.putPieceAt(to, movedPiece);
        board.removePieceAt(from);
    }

    @Override
    public void undoOn(Chessboard board) {
        board.putPieceAt(from, movedPiece);
        capturedPiece.ifPresentOrElse(
            captured -> board.putPieceAt(to, captured),
            () -> board.removePieceAt(to)
        );
        movedPiece.restoreSnapshot(movedSnapshot);
    }

    @Override
    public List<MoveComponent> getMoveComponents() {
        return List.of(new MoveComponent(from, to));
    }

    @Override
    public String toString() {
        return from + " → " + to;
    }
}