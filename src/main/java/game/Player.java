package game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Player {

    private final int playerNumber;
    private final List<Card> hand;
    private final Card[] hidden, shown;

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        hand = new ArrayList<>();

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
