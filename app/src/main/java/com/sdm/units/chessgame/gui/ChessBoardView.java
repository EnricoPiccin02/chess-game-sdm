package com.sdm.units.chessgame.gui;

import com.sdm.units.chessgame.gamelogic.ChessboardFile;
import com.sdm.units.chessgame.gamelogic.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.ChessboardRank;

import javax.swing.*;
import java.awt.*;

public class ChessBoardView extends JPanel {
    private final ChessController controller;
    private final JButton[][] squares;

    private static final Color HIGHLIGHT_COLOR = new Color(255, 255, 0, 128);
    private static final Color LIGHT_SQUARE_COLOR = new Color(240, 217, 181);
    private static final Color DARK_SQUARE_COLOR = new Color(181, 136, 99);

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
                square.setBackground(getSquareColor(rank, file));
                square.setOpaque(true);
                square.setBorderPainted(false);
                add(square);
            }
        }
    }

    private Color getSquareColor(int rank, int file) {
        return (rank + file) % 2 == 0 ? LIGHT_SQUARE_COLOR : DARK_SQUARE_COLOR;
    }

    public void highlightSquare(ChessboardPosition position) {
        int file = position.file().ordinal();
        int rank = position.rank().ordinal();
        squares[rank][file].setBackground(HIGHLIGHT_COLOR);
    }

    public void clearHighlights() {
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                squares[rank][file].setBackground(getSquareColor(rank, file));
            }
        }
    }
    
    public void updateBoard(ChessBoardViewModel viewModel) {
        // To be implemented
    }

    // Helper method to get button at position (for testing)
    public JButton getSquareButton(ChessboardPosition position) {
        return squares[position.rank().ordinal()][position.file().ordinal()];
    }
}