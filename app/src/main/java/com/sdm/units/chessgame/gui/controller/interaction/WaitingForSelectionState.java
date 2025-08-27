package com.sdm.units.chessgame.gui.controller.interaction;

import java.util.Set;
import java.util.function.Consumer;

import com.sdm.units.chessgame.gamecontrol.state.GameStateController;
import com.sdm.units.chessgame.gamecontrol.state.MoveQuery;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public class WaitingForSelectionState implements InteractionState {

    private final MoveQuery moveQuery;
    private final GameStateController controller;
    private final Consumer<InteractionState> stateTransition;
    private final ChessboardInteractionStrategy interactionStrategy;

    public WaitingForSelectionState(MoveQuery moveQuery, GameStateController controller, Consumer<InteractionState> stateTransition, ChessboardInteractionStrategy interactionStrategy) {
        this.moveQuery = moveQuery;
        this.controller = controller;
        this.stateTransition = stateTransition;
        this.interactionStrategy = interactionStrategy;
    }

    @Override
    public void onEnter() {
        interactionStrategy.clear();
        interactionStrategy.enableSelectableSquares(moveQuery.selectable());
    }

    @Override
    public void onSquareClicked(ChessboardPosition clickedPosition) {
        Set<ChessboardPosition> legalDestinations = moveQuery.legalDestinations(clickedPosition);
        if (!legalDestinations.isEmpty()) {
            stateTransition.accept(new WaitingForDestinationState(moveQuery, controller, clickedPosition, stateTransition, interactionStrategy));
        }
    }
}