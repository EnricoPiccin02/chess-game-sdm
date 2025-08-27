package com.sdm.units.chessgame.gamecontrol.assembler.gui;

import java.util.EnumMap;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.clock.ChessClockView;
import com.sdm.units.chessgame.gui.board.view.ChessGameFrame;
import com.sdm.units.chessgame.gui.board.view.ChessGameToolbar;
import com.sdm.units.chessgame.gui.board.view.ChessboardView;
import com.sdm.units.chessgame.gui.board.view.GameToolbarView;
import com.sdm.units.chessgame.gui.board.view.MoveHistoryArea;
import com.sdm.units.chessgame.gui.board.view.MoveHistoryView;
import com.sdm.units.chessgame.gui.controller.interaction.ToolbarInteractionController;

public final class ChessGameFrameFactory {
    
    public static ChessGameFrame create(ChessboardView chessboardView, EnumMap<ChessPieceColor, ChessClockView> clocks, ToolbarInteractionController toolbarController) {        
        MoveHistoryView moveHistoryArea = new MoveHistoryArea();
        GameToolbarView toolBar = new ChessGameToolbar(toolbarController);

        return new ChessGameFrame("Chess", chessboardView, clocks, moveHistoryArea, toolBar);
    }
}