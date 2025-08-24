package com.sdm.units.chessgame.gamecontrol.state;

import java.util.Set;

import com.sdm.units.chessgame.gamecontrol.flow.TurnManager;
import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.board.state.LegalMoveFinder;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public final class MoveQueryService implements MoveQuery {

    private final Chessboard board;
    private final LegalMoveFinder moveFinder;
    private final TurnManager turns;
    
    public MoveQueryService(Chessboard board, LegalMoveFinder moveFinder, TurnManager turns) {
        this.board = board;
        this.moveFinder = moveFinder;
        this.turns = turns;
    }

    @Override
    public Set<ChessboardPosition> selectable() {
        return board.getOccupiedSquaresOf(turns.current());
    }

    @Override
    public Set<ChessboardPosition> legalDestinations(ChessboardPosition from) {
        return board.getPieceAt(from)
            .filter(p -> p.pieceColor() == turns.current())
            .map(p -> moveFinder.findLegalDestinations(board, from))
            .orElseGet(Set::of);
    }
}