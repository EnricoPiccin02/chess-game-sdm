package com.sdm.units.chessgame.gamecontrol.state;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;

public interface GameStateController {

    void start();

    void makeMove(ChessboardPosition from, ChessboardPosition to);

    void undoMove();

    void proclaimWinner(ChessPieceColor winner, GameReason reason);

    void end();
}