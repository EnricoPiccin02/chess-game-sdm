package com.sdm.units.chessgame.gamelogic.pieces;

import java.util.Objects;
import java.util.Set;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.movement.MovementStrategy;

public abstract class BoardPiece implements ChessPiece {
    
    private boolean hasMoved;
    private final ChessPieceColor color;
    private final ChessPieceInfo info;
    private final MovementStrategy movementStrategy;
    
    protected BoardPiece(ChessPieceColor color, ChessPieceInfo info, MovementStrategy movementStrategy) {
        this.hasMoved = false;
        this.movementStrategy = movementStrategy;
        this.info = info;
        this.color = color;
    }

    public void markAsMoved() {
        hasMoved = true;
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

    public MovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    public boolean isOpponentOf(ChessPieceColor color) {
        return this.color.opponent() == color;
    }

    public Set<ChessboardPosition> getLegalDestinations(Chessboard board, ChessboardPosition from) {
        return movementStrategy.getLegalDestinations(board, from, color);
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

        BoardPiece other = (BoardPiece) obj;
        return color == other.color && info.equals(other.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, info);
    }
}