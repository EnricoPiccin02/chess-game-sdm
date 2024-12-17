package com.sdm.units.chessgame;

import javax.swing.SwingUtilities;

import com.sdm.units.chessgame.gui.Chessboard;

public class Game {
    public String getGreeting() {
        return "Hello World!";
    }
    public static void main(String[] args) {
        // System.out.println(new Game().getGreeting());
        SwingUtilities.invokeLater(Chessboard::createAndShowGUI);
    }
}
