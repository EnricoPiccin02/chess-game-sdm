package com.sdm.units.chessgame.gamelogic.move.special;

import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;

@FunctionalInterface
public interface SpecialMoveFactory<SpecialMoveCandidate> {

    ReversibleMove create(SpecialMoveCandidate candidate);
}