package com.sdm.units.chessgame.gui.board.square;

public enum HighlightStyle {

    NONE {
        @Override
        public void apply(ChessboardSquareHandler squareHandler, SquareClickHandler handler) {
            squareHandler.clearHighlight();
            squareHandler.removeClickHandler();
        }
    },
    SELECTABLE {
        @Override
        public void apply(ChessboardSquareHandler squareHandler, SquareClickHandler handler) {
            squareHandler.highlight(HighlightColor.SELECTABLE);
            squareHandler.attachClickHandler(handler);
        }
    },
    HOVER {
        @Override
        public void apply(ChessboardSquareHandler squareHandler, SquareClickHandler handler) {
            squareHandler.highlight(HighlightColor.HOVER);
        }
    },
    CLICKED {
        @Override
        public void apply(ChessboardSquareHandler squareHandler, SquareClickHandler handler) {
            squareHandler.highlight(HighlightColor.CLICKED);
        }
    };

    public abstract void apply(ChessboardSquareHandler squareHandler, SquareClickHandler handler);
}