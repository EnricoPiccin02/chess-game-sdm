package com.sdm.units.chessgame;

import java.util.List;

import com.sdm.units.chessgame.gamelogic.Chessboard;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardPosition;

public interface ChessGameView {
    
    public void display();

    public void setChessboard(Chessboard board);

    public void setChoosablePieces(List<ChessboardPosition> choosablePieces);

    public void setChoosableMoves(List<ChessboardPosition> choosableLandingPositions);

    public void displayMessage(String messageToDisplay);

    public void close();
}