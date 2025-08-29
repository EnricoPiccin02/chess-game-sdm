package unittest.chessgame.gamecontrol.testdoubles;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumMap;

import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.view.ChessGameView;
import com.sdm.units.chessgame.gui.controller.events.ChessGameEvent;
import com.sdm.units.chessgame.gui.controller.events.ChessGameEventListener;

public class ChessGameViewSpy implements ChessGameView, ChessGameEventListener {
    
    private boolean initialized = false;
    private boolean closed = false;
    private int resetCount = 0;
    private Deque<String> displayedMessages = new ArrayDeque<>();
    private String lastRecordedMessage;
    private Chessboard lastBoard;
    private EnumMap<ChessPieceColor, Boolean> lastClockStart = new EnumMap<>(ChessPieceColor.class);
    private EnumMap<ChessPieceColor, Boolean> lastClockStop = new EnumMap<>(ChessPieceColor.class);
    private ChessGameEvent lastEvent;

    public boolean isInitialized() {
        return initialized;
    }

    public boolean isClosed() {
        return closed;
    }

    public int getResetCount() {
        return resetCount;
    }

    public String popLastDisplayedMessage() {
        return displayedMessages.isEmpty() ? null : displayedMessages.pop();
    }

    public String getLastRecordedMessage() {
        return lastRecordedMessage;
    }

    public Chessboard getLastBoard() {
        return lastBoard;
    }

    public boolean getLastClockStartFor(ChessPieceColor c) {
        return lastClockStart.getOrDefault(c, false);
    }

    public boolean getLastClockStopFor(ChessPieceColor c) {
        return lastClockStop.getOrDefault(c, false);
    }

    public ChessGameEvent getLastEvent() {
        return lastEvent;
    }

    @Override
    public void initialize() {
        initialized = true;
    }

    @Override
    public void close() {
        closed = true;
    }

    @Override
    public void displayMessage(String msg) {
        displayedMessages.add(msg);
    }

    @Override
    public void recordMessage(String msg) {
        lastRecordedMessage = msg;
    }

    @Override
    public void setChessboard(Chessboard board) {
        lastBoard = board;
    }

    @Override
    public void startClock(ChessPieceColor c) {
        lastClockStart.put(c, true);
        lastClockStop.put(c, false);
    }

    @Override
    public void stopClock(ChessPieceColor c) {
        lastClockStop.put(c, true);
        lastClockStart.put(c, false);
    }

    @Override
    public void reset() {
        resetCount++;
    }

    @Override
    public void onChessGameEvent(ChessGameEvent event) {
        lastEvent = event;
        event.handleOn(this);
    }
}