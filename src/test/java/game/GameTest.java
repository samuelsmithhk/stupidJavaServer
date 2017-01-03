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

        isValidGame(game, 2);
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

    private void isValidGame(String game, int numberOfPlayers) {
        char[] gameArr = game.toCharArray();

        //first we need to check if a valid number of players in the game (2, 3, or 4)
        int players = Character.getNumericValue(gameArr[0]);
        if (players <  2 || players > 4)
            Assert.fail("Not a valid number of players in game string [" + players + "]");


        //is this number equal to the specified number?
        if (players != numberOfPlayers)
            Assert.fail("Number of players [" + players + "] not equal to specified [" + numberOfPlayers + "]");

        //now we need to pull out the players, deck, and pile from the game string
        int matchingNow = 0;
        boolean foundDeck = false;
        String[] playerStrings = new String[players];
        String deck = "", pile = "";
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < game.length(); i++) {
            char c = gameArr[i];

            if (matchingNow <= numberOfPlayers) {
                if (matchingNow == Character.getNumericValue(c)) {
                    playerStrings[matchingNow] = sb.toString();
                    sb = new StringBuilder();
                    matchingNow += 1;
                }

            } else {
                if (c == '?') {
                    if (!foundDeck) {
                        //looking at deck
                        foundDeck = true;
                        sb = new StringBuilder();
                    } else {
                        deck = sb.toString();
                        sb = new StringBuilder();
                    }
                }
            }

            sb.append(c);
        }

        pile = sb.toString();

        //now we need to check if the number of players pulled from the game match the specified number
        if (matchingNow + 1 != numberOfPlayers)
            Assert.fail("Game has [" + (matchingNow + 1) + "] players. Should have [" + numberOfPlayers + "]");

        //are each of the players valid?
        sb = new StringBuilder();

        for (int i = 0; i < playerStrings.length; i++) {
            String p = playerStrings[i];
            ValidationUtils.isValidPlayer(i, p);
            sb.append(p);
        }

        //is the deck valid?
        ValidationUtils.isValidDeck(deck);
        sb.append(deck);

        //is the pile valid?
        ValidationUtils.isValidPile(pile);
        sb.append(pile);

        //are there any duplicate cards?
        ValidationUtils.noDuplicateCards("Game", sb.toString());

    }

}
