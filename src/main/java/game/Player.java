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
        return false;
    }

    public boolean dealShown(Card card) {
        return false;
    }

    public boolean dealHand(Card card) {
        return false;
    }

    public void pickUp(Card[] cards) {

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(playerNumber);

        return sb.toString();
    }
}
