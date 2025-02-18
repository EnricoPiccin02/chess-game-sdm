package com.sdm.units.chessgame.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.sdm.units.chessgame.gamelogic.basics.ChessPieceColor;
import com.sdm.units.chessgame.gamelogic.basics.ChessPieceInfo;
import com.sdm.units.chessgame.gamelogic.basics.ChessPieceMove;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardDirection;
import com.sdm.units.chessgame.gamelogic.basics.ChessboardPosition;
import com.sdm.units.chessgame.gui.ChessPieceDrawFactory;
import com.sdm.units.chessgame.gui.DrawnChessPiece;

public class Pawn extends ChessPiece {

    public Pawn(ChessPieceColor color) {
        super(color);
        this.info = ChessPieceInfo.PAWN;
    }

    @Override
    public DrawnChessPiece drawChessPiece() {
        return new DrawnChessPiece(ChessPieceDrawFactory::drawPawn, color);
    }

    @Override
    public List<ChessPieceMove> getPossibleMoves(ChessboardPosition fromPosition) {
        List<ChessboardDirection> directionsOfMovement = new ArrayList<>();
        List<ChessPieceMove> possibleMoves = new ArrayList<>();
        Optional<ChessboardPosition> currentPosition = Optional.of(fromPosition);
        
        directionsOfMovement.add(switch (color) {
            case ChessPieceColor.WHITE -> ChessboardDirection.UP;
            default -> ChessboardDirection.DOWN;
        });

        if(!hasMoved) directionsOfMovement.add(directionsOfMovement.getFirst());
        
        for (ChessboardDirection direction : directionsOfMovement) {
            Optional<ChessboardPosition> nextPosition = currentPosition.get().nextPosition(direction);
            if (nextPosition.isPresent()) {
                possibleMoves.add(new ChessPieceMove(direction, nextPosition.get()));
                currentPosition = nextPosition;
            }
        }
        
        return possibleMoves;
    }

    @Override
    public List<ChessPieceMove> getCaptureMoves(ChessboardPosition fromPosition) {
        List<ChessboardDirection> directionsOfMovement = color == ChessPieceColor.WHITE ?
            List.of(ChessboardDirection.UP_LEFT, ChessboardDirection.UP_RIGHT) :
            List.of(ChessboardDirection.DOWN_LEFT, ChessboardDirection.DOWN_RIGHT);
        
        return directionsOfMovement.stream()
            .map(direction -> fromPosition.nextPosition(direction).map(position -> new ChessPieceMove(direction, position)))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }
}