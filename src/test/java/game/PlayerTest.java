package game;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samuelsmith on 1/2/17.
 */
public class PlayerTest {

    @Test
    public void shouldCreateValidPlayer() {
        Deck d = new Deck();
        Player p = new Player(1);

        //should correctly have hidden cards loaded
        Card h1 = d.seeNextCard();
        p.dealHidden(d.dealNextCard());

        Card h2 = d.seeNextCard();
        p.dealHidden(d.dealNextCard());

        Card h3 = d.seeNextCard();
        p.dealHidden(d.dealNextCard());

        //should correctly have shown cards loaded
        Card s1 = d.seeNextCard();
        p.dealShown(d.dealNextCard());

        Card s2 = d.seeNextCard();
        p.dealShown(d.dealNextCard());

        Card s3 = d.seeNextCard();
        p.dealShown(d.dealNextCard());

        //should correctly have hand loaded
        Card ha1 = d.seeNextCard();
        p.dealHand(d.dealNextCard());

        Card ha2 = d.seeNextCard();
        p.dealHand(d.dealNextCard());

        Card ha3 = d.seeNextCard();
        p.dealHand(d.dealNextCard());

        String pString = p.toString();
        ValidationUtils.isValidPlayer(1, pString.substring(1));

        String[] pStringArr = pString.substring(1).split("");

        Assert.assertEquals("First hidden card not expected", pStringArr[0], h1.toString());
        Assert.assertEquals("Second hidden card not expected", pStringArr[1], h2.toString());
        Assert.assertEquals("Third hidden card not expected", pStringArr[2], h3.toString());

        Assert.assertEquals("First shown card not expected", pStringArr[3], s1.toString());
        Assert.assertEquals("Second shown card not expected", pStringArr[4], s2.toString());
        Assert.assertEquals("Third shown card not expected", pStringArr[5], s3.toString());

        Assert.assertEquals("First hand card not expected", pStringArr[6], ha1.toString());
        Assert.assertEquals("Second hand card not expected", pStringArr[7], ha2.toString());
        Assert.assertEquals("Third hand card not expected", pStringArr[8], ha3.toString());
    }

    @Test
    public void shouldDealThreeHiddenCards() {
        Deck d = new Deck();
        Player p = new Player(1);

        int c = 0;

        while (p.dealHidden(d.dealNextCard())) c++;

        Assert.assertTrue("Player did not accept three hidden cards dealt", c == 3);
    }

    @Test
    public void shouldDealThreeShownCards() {
        Deck d = new Deck();
        Player p = new Player(1);

        int c = 0;

        while (p.dealShown(d.dealNextCard())) c++;

        Assert.assertTrue("Player did not accept three shown cards dealt", c == 3);
    }

    @Test
    public void shouldReturnTrueIfCardInHand() {
        Player p = new Player(0);

        List<Card> cards = new ArrayList<>();
        cards.add(Card.fromCharacter('a'));
        cards.add(Card.fromCharacter('b'));
        cards.add(Card.fromCharacter('c'));

        p.dealHand(cards.get(0));
        p.dealHand(cards.get(1));
        p.dealHand(cards.get(2));

        Assert.assertTrue("Player not holding the correct playable cards", p.hasPlayableCards(cards));
    }

    @Test
    public void shouldReturnFalseIfCardNotInHand() {
        Player p = new Player(0);

        List<Card> cards = new ArrayList<>();
        cards.add(Card.fromCharacter('a'));
        cards.add(Card.fromCharacter('b'));
        cards.add(Card.fromCharacter('c'));

        p.dealHand(Card.fromCharacter('d'));
        p.dealHand(Card.fromCharacter('e'));
        p.dealHand(Card.fromCharacter('f'));

        Assert.assertFalse("Player not holding the correct playable cards", p.hasPlayableCards(cards));
    }

    @Test
    public void shouldRemoveCardsFromHand() {
        Player p = new Player(0);

        List<Card> cards = new ArrayList<>();
        cards.add(Card.fromCharacter('a'));
        cards.add(Card.fromCharacter('b'));
        cards.add(Card.fromCharacter('c'));

        p.dealHand(cards.get(0));
        p.dealHand(cards.get(1));
        p.dealHand(cards.get(2));

        List<Card> toRemove = new ArrayList<>();
        toRemove.add(cards.get(1));
        p.removePlayableCards(toRemove);
        cards.remove(1);

        Assert.assertTrue("Player not holding the correct playable cards", p.hasPlayableCards(cards));
        Assert.assertFalse("Player not holding the correct playable cards", p.hasPlayableCards(toRemove));
    }
}
