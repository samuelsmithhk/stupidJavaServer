package game;

import java.util.ArrayList;
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

    public void pickUp(Card[] cards) {

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
