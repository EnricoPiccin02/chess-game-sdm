package com.sdm.units.chessgame.gui.board.square;

import java.util.Optional;

import com.sdm.units.chessgame.gamelogic.domain.ChessboardPosition;
import com.sdm.units.chessgame.gamelogic.pieces.ChessPiece;
import com.sdm.units.chessgame.gui.controller.interaction.SquareInteractionManager;

public interface ChessboardSquareHandler extends SquareRenderer {

    ChessboardPosition getPosition();

    void setPiece(Optional<ChessPiece> newPiece);

    void setClickHandler(SquareClickHandler handler, SquareInteractionManager manager);

    void removeClickHandler();
}