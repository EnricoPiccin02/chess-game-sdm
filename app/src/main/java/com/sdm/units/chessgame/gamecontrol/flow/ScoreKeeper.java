package com.sdm.units.chessgame.gamecontrol.flow;

import java.util.EnumMap;
import java.util.Map;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.move.result.MoveResult;

public class ScoreKeeper {
    
    private final Map<ChessPieceColor, Integer> scores = new EnumMap<>(ChessPieceColor.class);

    public int scoreOf(ChessPieceColor color) {
        return scores.getOrDefault(color, 0);
    }

    public void apply(MoveResult result, ChessPieceColor mover) {
        scores.merge(mover, result.capturedPieceValue().orElse(0), Integer::sum);
    }

    public void revert(MoveResult result, ChessPieceColor moverWhoUndid) {
        scores.merge(moverWhoUndid, -result.capturedPieceValue().orElse(0), Integer::sum);
    }
    
    public void reset() {
        scores.clear();
    }

    @Override
    public String toString() {
        return "Score: " + scores;
    }
}