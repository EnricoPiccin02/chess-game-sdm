package com.sdm.units.chessgame;

import javax.swing.SwingUtilities;

import com.sdm.units.chessgame.gamelogic.Chessboard;
import com.sdm.units.chessgame.gui.ChessboardFrame;

public class Game {
    public String getGreeting() {
        return "Hello World!";
    }
    public static void main(String[] args) {
        // System.out.println(new Game().getGreeting());
        // SwingUtilities.invokeLater(ChessboardFrame::createAndShowGUI);

        // Chessboard chessboard = new Chessboard();
        // System.out.println(chessboard.printBoard());
    }
}