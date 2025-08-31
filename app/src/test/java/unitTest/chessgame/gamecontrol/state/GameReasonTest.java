package unittest.chessgame.gamecontrol.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.sdm.units.chessgame.gamecontrol.state.GameReason;

@DisplayName("GameReason")
class GameReasonTest {

    @ParameterizedTest(name = "{0} should provide description \''{1}\''")
    @MethodSource("reasonDescriptionProvider")
    @DisplayName("should provide correct description for each reason")
    void shouldProvideDescription(GameReason reason, String description) {
        assertEquals(description, reason.getDescription());
    }

    static Stream<Arguments> reasonDescriptionProvider() {
        return Stream.of(
            arguments(GameReason.CHECKMATE, "Checkmate!"),
            arguments(GameReason.TIMEOUT, "Time's up!"),
            arguments(GameReason.ILLEGAL_MOVE, "Illegal move!"),
            arguments(GameReason.UNDER_ATTACK, "King's under attack!"),
            arguments(GameReason.NO_UNDO, "No undo available!"),
            arguments(GameReason.GAME_ENDED, "Game has ended! Exiting...")
        );
    }
}