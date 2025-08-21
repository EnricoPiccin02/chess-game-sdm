package com.sdm.units.chessgame.gui.board.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.stream.Stream;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gui.board.square.SquareSize;

public class RankNamesPanel extends JPanel {
    
    public RankNamesPanel() {
        super();

        int preferredWidth = SquareSize.SQUARE_HEIGHT.getSize() / 4; 
        int preferredHeight = ChessboardRank.values().length * SquareSize.SQUARE_HEIGHT.getSize();
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        
        GridLayout rankNamesGridLayout = new GridLayout(ChessboardRank.values().length, 0);
        rankNamesGridLayout.layoutContainer(this);
        setLayout(rankNamesGridLayout);

        addRankNames();
    }

    public void addRankNames() {
        removeAll();

        Stream.of(ChessboardRank.values())
            .sorted((r1, r2) -> r2.compareTo(r1))
            .forEach(rank ->
                add(new JLabel(rank.toString(), SwingConstants.CENTER))
        );
    }
}