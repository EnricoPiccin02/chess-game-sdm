package com.sdm.units.chessgame.gui.board.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class ChessboardWithCoordinatesPanel extends JPanel {
    
    public ChessboardWithCoordinatesPanel(ChessboardView chessboardView) {
        super(new BorderLayout());
        add(chessboardView.asComponent(), BorderLayout.CENTER);
        add(new FileNamesPanel(), BorderLayout.NORTH);
        add(new FileNamesPanel(), BorderLayout.SOUTH);
        add(new RankNamesPanel(), BorderLayout.WEST);
        add(new RankNamesPanel(), BorderLayout.EAST);
    }
}