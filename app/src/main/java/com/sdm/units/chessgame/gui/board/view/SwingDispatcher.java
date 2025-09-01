package com.sdm.units.chessgame.gui.board.view;

@FunctionalInterface
public interface SwingDispatcher {
    
    void dispatch(Runnable task);
}