package com.sdm.units.chessgame.gui.controller.interaction;

import java.util.Set;
import java.util.function.Consumer;

import com.sdm.units.chessgame.gamecontrol.model.domain.ChessGameController;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public class WaitingForSelectionState implements InteractionState {

    private final ChessGameController player;
    private final Consumer<InteractionState> stateTransition;
    private final ChessboardInteractionStrategy interactionStrategy;

    public WaitingForSelectionState(ChessGameController player, Consumer<InteractionState> stateTransition, ChessboardInteractionStrategy interactionStrategy) {
        this.player = player;
        this.stateTransition = stateTransition;
        this.interactionStrategy = interactionStrategy;
    }

    @Override
    public void onEnter() {
        interactionStrategy.clear();
        interactionStrategy.enableSelectablePieces(player.getSelectablePositions());
    }

    @Override
    public void onSquareClicked(ChessboardPosition clickedPosition) {
        Set<ChessboardPosition> legalDestinations = player.getLegalDestinationsFrom(clickedPosition);
        if (!legalDestinations.isEmpty()) {
            stateTransition.accept(new WaitingForDestinationState(player, clickedPosition, stateTransition, interactionStrategy));
        }
    }
}