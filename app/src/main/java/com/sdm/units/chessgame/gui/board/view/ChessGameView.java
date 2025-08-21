package com.sdm.units.chessgame.gui.board.view;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;

public interface ChessGameView {
    
    void display();

    void setChessboard(Chessboard board);

    void displayMessage(String messageToDisplay);
    
    void recordMessage(String messageToRecord);

    void startClock(ChessPieceColor color);
    
    void stopClock(ChessPieceColor color);

    void close();

    void reset();
}