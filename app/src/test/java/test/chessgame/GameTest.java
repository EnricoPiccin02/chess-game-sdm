package test.chessgame;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.sdm.units.chessgame.Game;


class GameTest {
    @Test void appHasAGreeting() {
        Game classUnderTest = new Game();
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
    }
}