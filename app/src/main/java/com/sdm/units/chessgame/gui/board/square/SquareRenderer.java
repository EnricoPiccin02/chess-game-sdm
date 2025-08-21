package com.sdm.units.chessgame.gui.board.square;

import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;

public interface SquareRenderer {
 
    void setPiece(Optional<ChessPiece> newPiece);
    
    void highlight(HighlightColor type);

    void clearHighlight();

    void attachClickHandler(SquareClickHandler clickHandler);
    
    void removeClickHandler();

    void refresh();
}