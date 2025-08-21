package com.sdm.units.chessgame.gamelogic.move.special.promotion;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.move.core.MoveComponent;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.result.CaptureResult;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public class PieceReplacementMove implements ReversibleMove {

    private final ChessboardPosition replacementPosition;
    private final ChessPiece replacementPiece;

    public PieceReplacementMove(ChessboardPosition replacementPosition, ChessPiece replacementPiece) {
        this.replacementPosition = replacementPosition;
        this.replacementPiece = replacementPiece;
    }
    
    @Override
    public CaptureResult executeOn(Chessboard board) {
        board.putPieceAt(replacementPosition, replacementPiece);
        return CaptureResult.none();
    }

    @Override
    public CaptureResult undoOn(Chessboard board) {
        board.removePieceAt(replacementPosition);
        return CaptureResult.none();
    }
    
    @Override
    public MoveComponent getPrimaryMoveComponent() {
        return new MoveComponent(replacementPosition, replacementPosition);
    }

    @Override
    public String toString() {
        return replacementPosition + " → " + replacementPiece.pieceInfo();
    }
}