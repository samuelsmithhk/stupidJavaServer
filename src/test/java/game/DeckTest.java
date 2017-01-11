package game;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by samuelsmith on 1/2/17.
 */
public class DeckTest {

    @Test
    public void shouldReturnNewRandomlyShuffledDeckOnCreation() {
        Deck d = new Deck();

        do {
            d.dealNextCard();
        } while (d.size() >= 34);

        ValidationUtils.isValidDeck(d.toString());
    }

    @Test
    public void shouldReturnDeckInOrderOfString() {
        String expected = "abcdefghijklmnopqrstuvwxyzABC";
        Deck d = new Deck(expected);
        String actual = d.toString();
        ValidationUtils.isValidDeck(actual);
        Assert.assertEquals("Deck generated from String is not identical to the String", expected, actual);
    }
}
