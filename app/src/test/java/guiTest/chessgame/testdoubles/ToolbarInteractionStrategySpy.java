package guitest.chessgame.testdoubles;

import com.sdm.units.chessgame.gui.controller.interaction.ToolbarInteractionStrategy;

public class ToolbarInteractionStrategySpy implements ToolbarInteractionStrategy {

    private boolean restartCalled = false;
    private boolean undoCalled = false;
    private boolean exitCalled = false;

    public boolean isRestartCalled() {
        return restartCalled;
    }

    public boolean isUndoCalled() {
        return undoCalled;
    }

    public boolean isExitCalled() {
        return exitCalled;
    }

    @Override
    public void restart() {
        restartCalled = true;
    }

    @Override
    public void undo() {
        undoCalled = true;
    }

    @Override
    public void exit() {
        exitCalled = true;
    }
}