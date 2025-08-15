package com.sdm.units.chessgame.gamelogic.move.special.castling;

import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.special.SpecialMoveFactory;
import com.sdm.units.chessgame.gamelogic.move.standard.StandardMove;

public class CastlingMoveFactory implements SpecialMoveFactory<CastlingCandidate> {

    public ReversibleMove create(CastlingCandidate candidate) {
        ReversibleMove kingMove = new StandardMove(
            candidate.kingFrom(),
            candidate.kingTo(),
            candidate.king(),
            Optional.empty());

        ReversibleMove rookMove = new StandardMove(
            candidate.rookFrom(),
            candidate.rookTo(),
            candidate.rook(),
            Optional.empty());

        return new CastlingMove(kingMove, rookMove);
    }
}