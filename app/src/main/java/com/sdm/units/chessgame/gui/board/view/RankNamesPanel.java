package com.sdm.units.chessgame.gui.board.view;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardRank;
import com.sdm.units.chessgame.gui.board.square.SquareSize;

public class RankNamesPanel extends ChessboardLabelPanel<ChessboardRank> {
    
    public RankNamesPanel() {
        super(ChessboardRank.values(), true, 
              SquareSize.SQUARE_WIDTH.getSize(), 
              SquareSize.SQUARE_HEIGHT.getSize(),
              String::valueOf
        );
    }
}