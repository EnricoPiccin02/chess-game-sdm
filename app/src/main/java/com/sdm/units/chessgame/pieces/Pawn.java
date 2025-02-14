package com.sdm.units.chessgame.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.plaf.OptionPaneUI;

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
        
        directionsOfMovement.add(switch (color) {
            case ChessPieceColor.WHITE -> ChessboardDirection.UP;
            default -> ChessboardDirection.DOWN;
        });

        if(!hasMoved) directionsOfMovement.add(directionsOfMovement.getFirst());
        
        return loadPawnMoves(directionsOfMovement, fromPosition);
    }

    @Override
    public List<ChessPieceMove> getCaptureMoves(ChessboardPosition fromPosition) {
        List<ChessboardDirection> directionsOfMovement = color == ChessPieceColor.WHITE ?
            List.of(ChessboardDirection.UP_LEFT, ChessboardDirection.UP_RIGHT) :
            List.of(ChessboardDirection.DOWN_LEFT, ChessboardDirection.DOWN_RIGHT);
        
        return loadPawnMoves(directionsOfMovement, fromPosition);
    }

    private List<ChessPieceMove> loadPawnMoves(List<ChessboardDirection> directionsOfMovement, ChessboardPosition fromPosition) {
        List<ChessPieceMove> moves = new ArrayList<>();
        Optional<ChessboardPosition> currentPosition = Optional.of(fromPosition);

        for (ChessboardDirection direction : directionsOfMovement) {
            Optional<ChessboardPosition> nextPosition = currentPosition.get().nextPosition(direction);
            
            if (nextPosition.isPresent()) {
                moves.add(new ChessPieceMove(direction, nextPosition.get()));
                currentPosition = nextPosition;
            }
        }

        return moves;
    }
}