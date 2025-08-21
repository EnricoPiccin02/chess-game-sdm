package com.sdm.units.chessgame.gui.board.view;

import javax.swing.JButton;
import javax.swing.JToolBar;

import com.sdm.units.chessgame.gui.controller.interaction.ToolbarInteractionController;

public class ChessGameToolbar extends JToolBar {

    public ChessGameToolbar(ToolbarInteractionController interactionController) {
        this.setFloatable(false);

        JButton newGameBtn = new JButton("Restart");
        JButton undoBtn = new JButton("Undo");
        JButton exitBtn = new JButton("Exit");

        newGameBtn.addActionListener(e -> interactionController.restart());
        undoBtn.addActionListener(e -> interactionController.undoLastMove());
        exitBtn.addActionListener(e -> interactionController.close());

        this.add(newGameBtn);
        this.addSeparator();
        this.add(undoBtn);
        this.addSeparator();
        this.add(exitBtn);
    }
}