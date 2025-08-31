package com.sdm.units.chessgame.gui.controller.interaction;

import com.sdm.units.chessgame.gamecontrol.state.GameStateController;
import com.sdm.units.chessgame.gamecontrol.state.MoveQuery;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public class InteractionContext {

    private final MoveQuery moveQuery;
    private final GameStateController controller;
    private final ChessboardInteractionStrategy boardUI;
    private InteractionState currentState;

    public InteractionContext(MoveQuery moveQuery, GameStateController controller, ChessboardInteractionStrategy boardUI) {
        this.moveQuery = moveQuery;
        this.controller = controller;
        this.boardUI = boardUI;
    }

    public void handleSquareClick(ChessboardPosition position) {
        currentState.onSquareClicked(position);
    }

    public void setState(InteractionState newState) {
        this.currentState = newState;
        newState.onEnter();
    }

    public InteractionState getState() {
        return currentState;
    }

    public void initialize() {
        setState(new WaitingForSelectionState(moveQuery, controller, this::setState, boardUI));
    }
}