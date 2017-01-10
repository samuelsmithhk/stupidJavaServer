package game;

import java.util.*;

/**
 * Created by samuelsmith on 1/2/17.
 */
public class Deck {

    private final Queue<Card> cards;

    public Deck() {
        cards = new LinkedList<>();

        List<Character> allCards = new LinkedList<>(Card.getAllCardKeys());
        Random randy = new Random();

        while (cards.size() < 52) {
            int limit = 52 - cards.size();
            int nextPos = limit == 0 ? 0 : randy.nextInt(limit);
            char c = allCards.remove(nextPos);
            cards.add(Card.fromCharacter(c));
        }
    }

    public Deck(String deckString) {
        cards = new LinkedList<>();

        char[] cardsInDeck = deckString.toCharArray();

        for (char c : cardsInDeck) {
            cards.add(Card.fromCharacter(c));
        }
    }

    public Card dealNextCard() {
        return cards.poll();
    }

    public Card seeNextCard() {
        return cards.peek();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Card c :cards) {
            sb.append(c.toString());
        }

        return sb.toString();
    }

}
