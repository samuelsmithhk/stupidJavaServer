package game;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by samuelsmith on 1/2/17.
 */
public class GameTest {

    @Test
    public void shouldCreateNewGameState2Player() {
        String game = Game.createNewGame(2);

        ValidationUtils.isValidGame(game, 2);
    }

    @Test
    public void shouldCreateNewGameState3Player() {

    }

    @Test
    public void shouldCreateNewGameState4Player() {

    }

    @Test
    public void shouldRejectLessThan2Players() {

    }

    @Test
    public void shouldRejectMoreThan4Players() {

    }

}
