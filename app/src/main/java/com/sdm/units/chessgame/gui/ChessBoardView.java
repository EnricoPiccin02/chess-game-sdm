package com.sdm.units.chessgame.gui;

import com.sdm.units.chessgame.gamelogic.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.ChessboardRank;

import javax.swing.*;
import java.awt.*;

public class ChessBoardView extends JPanel {
    private final ChessController controller;
    private final JButton[][] squares;

    public ChessBoardView(ChessController controller) {
        this.controller = controller;
        this.squares = new JButton[8][8];
        setLayout(new GridLayout(8, 8));
        initializeBoard();
    }

    private void initializeBoard() {
        for (int rank = 7; rank >= 0; rank--) {  // Start from 7 to match chess ranks (1-8)
            for (int file = 0; file < 8; file++) {
                JButton square = new JButton();
                squares[rank][file] = square;
                final ChessboardPosition position = new ChessboardPosition(
                        ChessboardFile.values()[file],
                        ChessboardRank.values()[rank]
                );
                square.addActionListener(e -> controller.handleSquareClick(position));
                add(square);
            }
        }
    }

    
    public void highlightSquare(ChessboardPosition position) {
        // To be implemented
    }

    
    public void clearHighlights() {
        // To be implemented
    }

    
    public void updateBoard(ChessBoardViewModel viewModel) {
        // To be implemented
    }
}