package com.sdm.units.chessgame.gamelogic.move.special.enpassant;

import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMoveFactory;
import com.sdm.units.chessgame.gamelogic.move.standard.StandardMove;

public class EnPassantMoveFactory implements SpecialMoveFactory<EnPassantCandidate> {

    @Override
    public ReversibleMove create(EnPassantCandidate candidate) {
        ReversibleMove captureAdjacentMove = new StandardMove(
            candidate.from(),
            candidate.capturingPosition(),
            candidate.movingPawn(),
            Optional.of(candidate.capturedPawn()));
            
        ReversibleMove diagonalMove = new StandardMove(
            candidate.capturingPosition(),
            candidate.to(),
            candidate.movingPawn(),
            Optional.empty());

        return new EnPassantMove(captureAdjacentMove, diagonalMove);
    }
}