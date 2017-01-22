package game;


import java.util.*;

public class Player {

    private final int playerNumber;
    private final Set<Card> hand;
    private final Card[] hidden, shown;

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        hand = new TreeSet<>();

        hidden = new Card[3];
        shown = new Card[3];
    }

    public Player(int playerNumber, String playerString) {
        this(playerNumber);

        char[] playerArr = playerString.toCharArray();

        dealHidden(Card.fromCharacter(playerArr[0]));
        dealHidden(Card.fromCharacter(playerArr[1]));
        dealHidden(Card.fromCharacter(playerArr[2]));

        dealShown(Card.fromCharacter(playerArr[3]));
        dealShown(Card.fromCharacter(playerArr[4]));
        dealShown(Card.fromCharacter(playerArr[5]));

        if (playerArr[6] != '!') {
            for (int i = 6; i < playerArr.length; i++) {
                dealHand(Card.fromCharacter(playerArr[i]));
            }
        }
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public boolean dealHidden(Card card) {
        for (int i = 0; i <= 2; i++) {
            if (hidden[i] == null) {
                hidden[i] = card;
                return true;
            }
        }

        return false;
    }

    public boolean dealShown(Card card) {
        for (int i = 0; i <= 2; i++) {
            if (shown[i] == null) {
                shown[i] = card;
                return true;
            }
        }

        return false;
    }

    public boolean dealHand(Card card) {
        hand.add(card);
        return true;
    }

    public void pickUpShownCards() {
        for (int i = 0; i <= 2; i++) {
            if (shown[i] != null) dealHand(shown[i]);
            shown[i] = null;
        }
    }

    public void pickUpShownCards(List<Card> shownToPickUp) {
        for (Card c : shownToPickUp) pickUpShownCard(c);
    }

    public void pickUpShownCard(Card c) {
        if (hasShownCard(c)) {
            for (int i = 0; i < 3; i++) {
                if (shown[i].equals(c)) {
                    dealHand(c);
                    shown[i] = null;
                }
            }
        }
    }

    public boolean removePlayableCards(Collection<Card> cards) {
        return hand.removeAll(cards);
    }

    public boolean hasPlayableCards(Collection<Card> cards) {
        return hand.containsAll(cards);
    }

    public boolean hasShownCard(Card card) {
        for (int i = 0; i < 3; i++) {
            if (shown[i].equals(card)) return true;
        }

        return false;
    }

    public boolean hasShownCards(List<Card> shownToFind) {
        for (Card c : shownToFind) {
            if (!hasShownCard(c)) return false;
        }

        return true;
    }

    public int getNumberOfPlayableCards() {
        return hand.size();
    }

    public Card getNextWeakestCard(Card previous) {

        Iterator<Card> handIt = hand.iterator();

        if (previous == null) return handIt.next();

        Card c = handIt.next();

        while (c != null) {
            if (c.equals(previous)) {
                if (handIt.hasNext()) return handIt.next();
                else return null;
            }

            if (handIt.hasNext()) c = handIt.next();
            else return null;
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(playerNumber);

        for (int i = 0; i <= 2; i++) {
            if (hidden[i] == null) sb.append("!");
            else sb.append(hidden[i]);
        }

        for (int i = 0; i <= 2; i++) {
            if (shown[i] == null) sb.append("!");
            else sb.append(shown[i]);
        }

        if (hand.size() == 0) sb.append("!");
        else for (Card c : hand) sb.append(c);

        return sb.toString();
    }
}
