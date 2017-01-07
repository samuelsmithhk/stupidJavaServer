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
        String game = Game.createNewGame(3);
        ValidationUtils.isValidGame(game, 3);
    }

    @Test
    public void shouldCreateNewGameState4Player() {
        String game = Game.createNewGame(4);
        ValidationUtils.isValidGame(game, 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectLessThan2Players() {
        Game.createNewGame(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectMoreThan4Players() {
        Game.createNewGame(5);
    }

}
