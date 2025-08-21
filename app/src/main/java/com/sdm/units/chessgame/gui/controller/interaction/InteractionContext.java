package com.sdm.units.chessgame.gui.controller.interaction;

import com.sdm.units.chessgame.gamecontrol.model.domain.ChessGameController;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gui.controller.command.EndGameCommand;
import com.sdm.units.chessgame.gui.controller.command.GameCommand;
import com.sdm.units.chessgame.gui.controller.command.RestartCommand;
import com.sdm.units.chessgame.gui.controller.command.UndoCommand;

public class InteractionContext {

    private InteractionState currentState;
    private final ChessboardInteractionStrategy boardUI;
    private final ChessGameController player;

    public InteractionContext(ChessGameController player, ChessboardInteractionStrategy boardUI) {
        this.player = player;
        this.boardUI = boardUI;
    }

    public void handleSquareClick(ChessboardPosition position) {
        currentState.onSquareClicked(position);
    }

    public void setState(InteractionState newState) {
        this.currentState = newState;
        newState.onEnter();
    }

    public void resetToStartState() {
        setState(new WaitingForSelectionState(player, this::setState, boardUI));
    }

    public void executeCommand(GameCommand command) {
        command.execute();
    }

    public GameCommand createUndoCommand() {
        return new UndoCommand(this, player);
    }

    public GameCommand createRestartCommand() {
        return new RestartCommand(this, player);
    }

    public GameCommand createEndGameCommand() {
        return new EndGameCommand(player);
    }
}