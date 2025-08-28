package com.sdm.units.chessgame.gui.board.view;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToolBar;

import com.sdm.units.chessgame.gui.controller.interaction.ToolbarInteractionStrategy;

public class ChessGameToolbar extends JToolBar implements GameToolbarView {

    private final ToolbarInteractionStrategy interactionController;

    public ChessGameToolbar(ToolbarInteractionStrategy interactionController) {
        this.interactionController = interactionController;

        this.setFloatable(false);
        
        JButton restartBtn = new JButton("Restart");
        JButton undoBtn = new JButton("Undo");
        JButton exitBtn = new JButton("Exit");

        restartBtn.addActionListener(e -> restart());
        undoBtn.addActionListener(e -> undo());
        exitBtn.addActionListener(e -> exit());

        this.add(restartBtn);
        this.addSeparator();
        this.add(undoBtn);
        this.addSeparator();
        this.add(exitBtn);
    }

    public void restart() {
        interactionController.restart();
    }

    public void undo() {
        interactionController.undo();
    }

    public void exit() {
        interactionController.exit();
    }

    @Override
    public JComponent asComponent() {
        return this;
    }    
}