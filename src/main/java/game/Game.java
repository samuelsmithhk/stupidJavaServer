package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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

    public static InstructionResult makeMove(String gameString, int player, char[] cardsToPlay) {
        Game g = new Game(gameString);
        String message = g.move(player, cardsToPlay);
        return new InstructionResult(g.toString(), message);
    }

    public static InstructionResult makeSwitch(String gameString, int player, char[] shownToSwitch, char[] handToSwitch) {
        Game g = new Game(gameString);
        String message =  g.switchCards(player, shownToSwitch, handToSwitch);
        return new InstructionResult(g.toString(), message);
    }

    public static InstructionResult startGame(String gameString) {
        Game g = new Game(gameString);
        String message = g.start();
        return new InstructionResult(g.toString(), message);
    }

    private final Deck deck;
    private final Player[] players;
    private final Queue<Card> pile;
    private int currentPlayer = -1;

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

    private Game(String gameString) {
        if (gameString.charAt(1) == '%') currentPlayer = -1;
        else currentPlayer = Character.getNumericValue(gameString.charAt(1));

        int endOfPlayers = gameString.indexOf('?');
        int endOfDeck = gameString.lastIndexOf('?');
        String[] playerStrings = gameString.substring(3, endOfPlayers).split("[0-9]");
        players = new Player[playerStrings.length];

        for (int i = 0; i < playerStrings.length; i++) {
            players[i] = new Player(i, playerStrings[i]);
        }

        String deckString = gameString.substring(endOfPlayers + 1, endOfDeck);
        deck = new Deck(deckString);

        String pileString = gameString.substring(endOfDeck + 1);
        char[] pileStringArr = pileString.toCharArray();

        pile = new LinkedList<>();

        if (pileStringArr[0] != '!') {
            for (char c : pileStringArr) {
                pile.add(Card.fromCharacter(c));
            }
        }
    }

    private String move(int player, char[] cardsToPlay) {
        if (currentPlayer == -1) return "Move cannot be made: game has not started yet";
        if (player != currentPlayer) return "Move cannot be made: not the player's turn";

        Player p = players[player];
        List<Card> cardsConverted = new ArrayList<>();

        if (cardsToPlay.length == 0) return "Success";

        //are all cards playable in single move (ie, all same power)
        int power = -1;

        for (char c : cardsToPlay) {
            Card card = Card.fromCharacter(c);

            if (card != null) {
                cardsConverted.add(card);
                int cPower = card.getPower();

                if (power != -1)
                    if (cPower != power) return "Move cannot be made: not all cards are of equal power";

                power = cPower;
            }
            else return "Move cannot be made: trying to play invalid card";
        }

        if (p.hasPlayableCards(cardsConverted)) { //to prevent cheating

            if (pile.size() != 0 && pile.peek().getPower() > power) {
                return "Move cannot be made: card is not powerful enough to add to pile";
            }

            p.removePlayableCards(cardsConverted);
            pile.addAll(cardsConverted);

            if (deck.isEmpty()) {
                if (p.getNumberOfPlayableCards() == 0) {
                    p.pickUpShownCards();
                }
            } else {
                while (p.getNumberOfPlayableCards() < 3) {
                    Card c = deck.dealNextCard();
                    if (c != null) p.dealHand(c);
                    else break;
                }
            }

            if (currentPlayer < players.length) currentPlayer += 1;
            else currentPlayer = 0;

            return "Success";
        } else return "Move cannot be made: player does not hold the cards trying to be played";
    }

    private String switchCards(int player, char[] shownToSwitch, char[] handToSwitch) {
        if (currentPlayer != -1) return "Switch cannot be completed: game has already been started";

        Player p = players[player];
        List<Card> shownConverted = new ArrayList<>();
        List<Card> handConverted = new ArrayList<>();

        for (char s : shownToSwitch) {
            try {
                Card sh = Card.fromCharacter(s);

                if (sh != null) shownConverted.add(sh);
                else return "Switch cannot be completed: invalid card [" + s + "]";
            } catch (IllegalArgumentException e) {
                return "Switch cannot be completed: invalid card [" + s + "]";
            }
        }

        for (char h : handToSwitch) {
            try {
                Card ha = Card.fromCharacter(h);

                if (ha != null) handConverted.add(ha);
                else return "Switch cannot be completed: invalid card [" + h + "]";
            } catch (IllegalArgumentException e) {
                return "Switch cannot be completed: invalid card [" + h + "]";
            }
        }

        if (p.hasPlayableCards(handConverted) && p.hasShownCards(shownConverted)) {
            p.removePlayableCards(handConverted);
            p.pickUpShownCards(shownConverted);
            for (Card c : handConverted) p.dealShown(c);

            return "Success";
        } else return "Switch cannot be completed: player does not have the cards being switched";
    }

    private String start() {
        if (currentPlayer != -1) return "Unable to start game: already started";
        currentPlayer = 0;
        return "Success";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(55 + players.length);

        sb.append(players.length);

        if (currentPlayer != -1) sb.append(currentPlayer);
        else sb.append('%');

        for (Player p : players) {
            sb.append(p.toString());
        }

        sb.append("?").append(deck.toString()).append("?");

        if (pile.size() == 0) sb.append("!");
        else for (Card c : pile) sb.append(c.toString());


        return sb.toString();
    }

    public static class InstructionResult {
        public final String game, message;

        public InstructionResult(String game, String message) {
            this.game = game;
            this.message = message;
        }
    }
}
