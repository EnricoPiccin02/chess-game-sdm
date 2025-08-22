package com.sdm.units.chessgame.gui.controller.interaction;

import java.util.function.Consumer;

import com.sdm.units.chessgame.gamecontrol.state.GameStateController;
import com.sdm.units.chessgame.gamecontrol.state.MoveQuery;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public class WaitingForDestinationState implements InteractionState {

    private final MoveQuery moveQuery;
    private final GameStateController controller;
    private final ChessboardPosition fromPosition;
    private final Consumer<InteractionState> stateTransition;
    private final ChessboardInteractionStrategy interactionStrategy;

    public WaitingForDestinationState(MoveQuery moveQuery, GameStateController controller, ChessboardPosition fromPosition, Consumer<InteractionState> stateTransition, ChessboardInteractionStrategy interactionStrategy) {
        this.moveQuery = moveQuery;
        this.controller = controller;
        this.fromPosition = fromPosition;
        this.stateTransition = stateTransition;
        this.interactionStrategy = interactionStrategy;
    }

    @Override
    public void onEnter() {
        interactionStrategy.clear();
        interactionStrategy.enableLegalDestinations(moveQuery.legalDestinations(fromPosition));
    }

    @Override
    public void onSquareClicked(ChessboardPosition toPosition) {
        if (toPosition.equals(fromPosition)) {
            stateTransition.accept(new WaitingForSelectionState(moveQuery, controller, stateTransition, interactionStrategy));
            return;
        }

        controller.makeMove(fromPosition, toPosition);
        stateTransition.accept(new WaitingForSelectionState(moveQuery, controller, stateTransition, interactionStrategy));
    }
}