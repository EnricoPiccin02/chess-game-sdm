package com.sdm.units.chessgame.gamelogic.pieces;

import java.util.List;
import java.util.Objects;

import com.sdm.units.chessgame.gamelogic.board.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.movement.MovementStrategy;

public abstract class ChessPiece {
    
    private boolean hasMoved;
    private final ChessPieceColor color;
    private final ChessPieceInfo info;
    private final MovementStrategy movementStrategy;
    
    protected ChessPiece(ChessPieceColor color, ChessPieceInfo info, MovementStrategy movementStrategy) {
        this.hasMoved = false;
        this.movementStrategy = movementStrategy;
        this.info = info;
        this.color = color;
    }

    public void markAsMoved() {
        this.hasMoved = true;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public ChessPieceInfo pieceInfo() {
        return info;
    }

    public ChessPieceColor pieceColor() {
        return color;
    }

    public boolean isOpponentOf(ChessPieceColor color) {
        return this.color.opponent() == color;
    }


    public List<ChessboardPosition> getLegalMoves(Chessboard board, ChessboardPosition from) {
        return movementStrategy.getLegalMoves(board, from, color);
    }

    public ChessPieceSnapshot createSnapshot() {
        return new ChessPieceSnapshot(this, hasMoved);
    }

    public void restoreSnapshot(ChessPieceSnapshot snapshot) {
        if (snapshot.getPiece() == this) {
            this.hasMoved = snapshot.wasMoved();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        ChessPiece other = (ChessPiece) obj;
        return color == other.color && info.equals(other.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, info);
    }
}