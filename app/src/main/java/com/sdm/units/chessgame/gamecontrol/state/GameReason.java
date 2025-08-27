package com.sdm.units.chessgame.gamecontrol.state;

public enum GameReason {

    CHECKMATE {
        @Override
        public String getDescription() {
            return "Checkmate!";
        }
    },
    TIMEOUT {
        @Override
        public String getDescription() {
            return "Time's up!";
        }
    },
    ILLEGAL_MOVE {
        @Override
        public String getDescription() {
            return "Illegal move!";
        }
    },
    UNDER_ATTACK {
        @Override
        public String getDescription() {
            return "King's under attack!";
        }
    },
    NO_UNDO {
        @Override
        public String getDescription() {
            return "No undo available!";
        }
    },
    GAME_ENDED {
        @Override
        public String getDescription() {
            return "Game has ended! Exiting...";
        }
    };

    public abstract String getDescription();
}