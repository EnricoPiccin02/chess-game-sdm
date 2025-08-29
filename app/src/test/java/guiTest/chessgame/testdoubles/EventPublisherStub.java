package guitest.chessgame.testdoubles;

import com.sdm.units.chessgame.gui.controller.events.ChessGameEvent;
import com.sdm.units.chessgame.gui.controller.events.ChessGameEventListener;
import com.sdm.units.chessgame.gui.controller.events.ChessGameEventPublisher;

public class EventPublisherStub implements ChessGameEventPublisher {
    
    private int listeners = 0;

    public int listenerCount() {
        return listeners;
    }

    @Override
    public void addChessGameEventListener(ChessGameEventListener listener) {
        listeners++;
    }

    @Override
    public void removeChessGameEventListener(ChessGameEventListener listener) {
        listeners--;
    }

    @Override
    public void fireEvent(ChessGameEvent event) {}
}