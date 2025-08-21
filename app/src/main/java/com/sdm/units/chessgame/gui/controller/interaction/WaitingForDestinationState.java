package com.sdm.units.chessgame.gui.controller.interaction;

import java.util.function.Consumer;

import com.sdm.units.chessgame.gamecontrol.model.domain.ChessGameController;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public class WaitingForDestinationState implements InteractionState {

    private final ChessGameController player;
    private final ChessboardPosition fromPosition;
    private final Consumer<InteractionState> stateTransition;
    private final ChessboardInteractionStrategy interactionStrategy;

    public WaitingForDestinationState(ChessGameController player, ChessboardPosition fromPosition, Consumer<InteractionState> stateTransition, ChessboardInteractionStrategy interactionStrategy) {
        this.player = player;
        this.fromPosition = fromPosition;
        this.stateTransition = stateTransition;
        this.interactionStrategy = interactionStrategy;
    }

    @Override
    public void onEnter() {
        interactionStrategy.clear();
        interactionStrategy.enableLegalDestinations(player.getLegalDestinationsFrom(fromPosition));
    }

    @Override
    public void onSquareClicked(ChessboardPosition toPosition) {
        if (toPosition.equals(fromPosition)) {
            stateTransition.accept(new WaitingForSelectionState(player, stateTransition, interactionStrategy));
            return;
        }

        player.makeMove(fromPosition, toPosition);
        stateTransition.accept(new WaitingForSelectionState(player, stateTransition, interactionStrategy));
    }
}