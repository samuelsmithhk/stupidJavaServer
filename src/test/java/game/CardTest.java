package game;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by samuelsmith on 1/2/17.
 */
public class CardTest {

    @Test
    public void shouldReturnCorrectCardForCharacter() {
        Card c = Card.fromCharacter('N');
        Assert.assertNotNull("Card should not be null", c);
        Assert.assertTrue("Not returning correct card", c.getCardValue().equals("Ace of Spades"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowErrorForNonCardCharacter() {
        Card c = Card.fromCharacter('4');
    }

    @Test
    public void shouldReturnTrueForTwoEqualCards() {
        Card c1 = Card.fromCharacter('a');
        Card c2 = Card.fromCharacter('a');

        Assert.assertEquals("Equality function is not returning as expected", c1, c2);
    }

    @Test
    public void shouldReturnFalseForTwoNotEqualCards() {
        Card c1 = Card.fromCharacter('a');
        Card c2 = Card.fromCharacter('b');

        if (c1 == null || c2 == null) {
            Assert.fail("These cards should not be null");
        }
        Assert.assertFalse("Equality function is not returning as expected", c1.equals(c2));
    }

    @Test
    public void shouldReturnProperlyStructuredCard() {
        Card c = Card.fromCharacter('N');
        Assert.assertNotNull("Card should not be null", c);
        Assert.assertTrue("Incorrect card", c.getCardValue().equals("Ace of Spades"));
        Assert.assertTrue("Incorrect power", c.getPower() == 10);
        Assert.assertTrue("Incorrect suite", c.getSuite().equals(Card.Suite.SPADE));
    }
}
