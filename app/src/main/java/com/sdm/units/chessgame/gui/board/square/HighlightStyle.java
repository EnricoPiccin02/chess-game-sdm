package com.sdm.units.chessgame.gui.board.square;

public enum HighlightStyle {
    NONE {
        @Override
        public void apply(SquareRenderer square, SquareClickHandler handler) {
            square.clearHighlight();
            square.removeClickHandler();
        }
    },
    SELECTABLE {
        @Override
        public void apply(SquareRenderer square, SquareClickHandler handler) {
            square.highlight(HighlightColor.SELECTED);
            square.attachClickHandler(handler);
        }
    },
    LEGAL_DESTINATION {
        @Override
        public void apply(SquareRenderer square, SquareClickHandler handler) {
            square.highlight(HighlightColor.LEGAL_DESTINATION);
            square.attachClickHandler(handler);
        }
    },
    HOVER {
        @Override
        public void apply(SquareRenderer square, SquareClickHandler handler) {
            square.highlight(HighlightColor.HOVER);
        }
    },
    CLICKED {
        @Override
        public void apply(SquareRenderer square, SquareClickHandler handler) {
            square.highlight(HighlightColor.CLICKED);
        }
    };

    public abstract void apply(SquareRenderer square, SquareClickHandler handler);
}