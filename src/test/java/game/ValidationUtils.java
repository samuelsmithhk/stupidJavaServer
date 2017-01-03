package game;

import org.junit.Assert;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by samuelsmith on 1/3/17.
 */
public class ValidationUtils {

    static void isValidPlayer(int playerNumber, String player) {
        noDuplicateCards("Player " + playerNumber, player);
    }

    static void isValidDeck(String deck) {
        noDuplicateCards("Deck", deck);
    }

    static void isValidPile(String pile) {
        noDuplicateCards("Pile", pile);
    }

    static void noDuplicateCards(String x, String toCheck) {
        Set<Character> alreadyFound = new HashSet<>();
        char[] playerArr = toCheck.toCharArray();

        for (Character c : playerArr) {
            if (alreadyFound.contains(c))
                Assert.fail(x + " contains a duplicate card: [" + Card.fromCharacter(c).toString() + "]");
            alreadyFound.add(c);
        }
    }
}
