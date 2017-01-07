package game;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by samuelsmith on 1/2/17.
 */
public class Game {

    public static String createNewGame(int numberOfPlayers) {
        if (numberOfPlayers < 2) throw new IllegalArgumentException("A game must have at least 2 players.");
        if (numberOfPlayers > 4) throw new IllegalArgumentException("A game may have no more than 4 players");

        return new Game(numberOfPlayers).toString();
    }

    private final Deck deck;
    private final Player[] players;
    private final Queue<Card> pile;

    private Game(int numberOfPlayers) {
        deck = new Deck();
        players = new Player[numberOfPlayers];
        pile = new LinkedList<>();

        for (int i = 1; i <= 3; i++) {
            for (int x = 0; x < players.length; x++) {
                if (players[x] == null) players[x] = new Player(x);
                players[x].dealHidden(deck.dealNextCard());
            }
        }

        for (int i = 1; i <= 3; i++) {
            for (int x = 0; x < players.length; x++) {
                players[x].dealShown(deck.dealNextCard());
            }
        }

        for (int i = 1; i <= 3; i++) {
            for (int x = 0; x < players.length; x++) {
                players[x].dealHand(deck.dealNextCard());
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(55 + players.length);

        sb.append(players.length);

        for (Player p : players) {
            sb.append(p.toString());
        }

        sb.append("?").append(deck.toString()).append("?");

        if (pile.size() == 0) sb.append("!");
        else for (Card c : pile) sb.append(c.toString());


        return sb.toString();
    }

}