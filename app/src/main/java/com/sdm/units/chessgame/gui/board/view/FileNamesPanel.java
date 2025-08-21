package com.sdm.units.chessgame.gui.board.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gui.board.square.SquareSize;

public class FileNamesPanel extends JPanel {
    
    public FileNamesPanel() {
        super();

        int preferredWidth = ChessboardFile.values().length * SquareSize.SQUARE_WIDTH.getSize();
        int preferredHeight = SquareSize.SQUARE_HEIGHT.getSize() / 4;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        
        GridLayout fileNamesGridLayout = new GridLayout(0, ChessboardFile.values().length);
        fileNamesGridLayout.layoutContainer(this);
        setLayout(fileNamesGridLayout);

        setBorder(BorderFactory.createEmptyBorder(0, SquareSize.SQUARE_WIDTH.getSize() / 4, 0, SquareSize.SQUARE_HEIGHT.getSize() / 4));

        addFileNames();
    }

    public void addFileNames() {
        removeAll();

        Stream.of(ChessboardFile.values())
            .forEach(file ->
                add(new JLabel(file.toString(), SwingConstants.CENTER))
        );
    }
}