package com.sdm.units.chessgame.gui.board.view;

import javax.swing.SwingUtilities;

public class SwingUtilitiesDispatcher implements SwingDispatcher {
    
    @Override
    public void dispatch(Runnable task) {
        if (SwingUtilities.isEventDispatchThread()) {
            task.run();
        } else {
            SwingUtilities.invokeLater(task);
        }
    }
}