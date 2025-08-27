package com.sdm.units.chessgame.gui.board.view;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardFile;
import com.sdm.units.chessgame.gui.board.square.SquareSize;

public class FileNamesPanel extends ChessboardLabelPanel<ChessboardFile> {
    
    public FileNamesPanel() {
        super(ChessboardFile.values(), false, 
              SquareSize.SQUARE_WIDTH.getSize(), 
              SquareSize.SQUARE_HEIGHT.getSize(),
              String::valueOf
        );

        addPadding();
    }
}