package game;

import org.junit.Assert;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by samuelsmith on 1/3/17.
 */
class ValidationUtils {

    static void isValidGame(String game, int numberOfPlayers) {
        char[] gameArr = game.toCharArray();

        //first we need to check if a valid number of players in the game (2, 3, or 4)
        int players = Character.getNumericValue(gameArr[0]);
        if (players <  2 || players > 4)
            Assert.fail("Not a valid number of players in game string [" + players + "]");


        //is this number equal to the specified number?
        if (players != numberOfPlayers)
            Assert.fail("Number of players [" + players + "] not equal to specified [" + numberOfPlayers + "]");

        //now we need to pull out the players, deck, and pile from the game string
        int matchingNow = 0;
        boolean onPlayers = false;
        boolean foundDeck = false;
        String[] playerStrings = new String[players];
        String deck = "", pile = "";
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < game.length(); i++) {
            char c = gameArr[i];

            if (matchingNow <= numberOfPlayers) {
                if (matchingNow == Character.getNumericValue(c) || c == '?') {
                    if (onPlayers) {
                        playerStrings[matchingNow - 1] = sb.deleteCharAt(0).toString();
                        sb = new StringBuilder();
                    } else onPlayers = true;

                    matchingNow += 1;
                }

            } else {
                if (c == '?' || gameArr[i - 1] == '?') {
                    if (!foundDeck) {
                        //looking at deck
                        foundDeck = true;
                        sb = new StringBuilder();
                    } else {
                        deck = sb.toString();
                        sb = new StringBuilder();
                        foundDeck = false;
                    }
                }
            }

            sb.append(c);
        }

        pile = sb.toString();

        //now we need to check if the number of players pulled from the game match the specified number
        if (matchingNow - 1 != numberOfPlayers)
            Assert.fail("Game has [" + (matchingNow - 1) + "] players. Should have [" + numberOfPlayers + "]");

        //are each of the players valid?
        sb = new StringBuilder();

        for (int i = 0; i < playerStrings.length; i++) {
            String p = playerStrings[i];
            ValidationUtils.isValidPlayer(i, p);
            sb.append(p);
        }

        //is the deck valid?
        isValidDeck(deck);
        sb.append(deck);

        //is the pile valid?
        isValidPile(pile);
        sb.append(pile);

        //are there any duplicate cards?
        noDuplicateCards("Game", sb.toString());

    }

    static void isValidPlayer(int playerNumber, String player) {
        Assert.assertTrue("A valid player string contains at least 7 characters [" + player + "]",player.length() >= 7);
        Assert.assertTrue("A valid player string may contain no more than 59 characters [" + player + "]", player.length() <= 59);

        char[] playerArr = player.toCharArray();

        //first six values should be cards or !
        for (int i = 0; i < 6; i++) {
            if (playerArr[i] != '!') {
                try {
                    Card.fromCharacter(playerArr[i]);
                } catch (IllegalArgumentException e) {
                    Assert.fail("playerArr["+ i + "] should be ! or a valid card, currently [" + playerArr[i] + "]");
                }
            }
        }

        //remaining values should be 0 or more cards. If zero cards, should be one !
        if (playerArr.length == 7) {
            if (playerArr[6] != '!') {
                try {
                    Card.fromCharacter(playerArr[6]);
                } catch (IllegalArgumentException e) {
                    Assert.fail("playerArr[6] should be ! or a valid card, currently [" + playerArr[6] + "]");
                }
            }
        } else {
            for (int i = 6; i < playerArr.length; i++) {
                try {
                    Card.fromCharacter(playerArr[i]);
                } catch (IllegalArgumentException e) {
                    Assert.fail("playerArr["+ i + "] should be a valid card, currently [" + playerArr[i] + "]");
                }
            }
        }

        noDuplicateCards("Player " + playerNumber, player);
    }

    static void isValidDeck(String deck) {
        Assert.assertTrue("Deck should have at least 1 character" + deck + "]", deck.length() > 0);
        Assert.assertTrue("Deck can not have more than 34 characters [" + deck + "]", deck.length() <= 34);

        //a valid deck either has 1 or more cards, or one !
        char[] deckArr = deck.toCharArray();

        if (deckArr.length == 0) {
            if (deckArr[0] != '!') {
                try {
                    Card.fromCharacter(deckArr[0]);
                } catch (IllegalArgumentException e) {
                    Assert.fail("deckArr[0] should be ! or a valid card, currently [" + deckArr[0] + "]");
                }
            }
        } else {
            for (int i = 0; i < deckArr.length; i++) {
                try {
                    Card.fromCharacter(deckArr[i]);
                } catch (IllegalArgumentException e) {
                    Assert.fail("deckArr[" + i + "] should be a valid card, currently [" + deckArr[i] + "]");
                }
            }
        }

        noDuplicateCards("Deck", deck);
    }

    static void isValidPile(String pile) {
        Assert.assertTrue("Pile must be atleast one character [" + pile + "]", pile.length() > 0);
        Assert.assertTrue("Pile must not contain more than 52 characters [" + pile + "]", pile.length() <= 52);

        //All characters must be valid cards, or just a single !
        char[] pileArr = pile.toCharArray();

        if (pileArr.length == 1) {
            if (pileArr[0] != '!') {
                try {
                    Card.fromCharacter(pileArr[0]);
                } catch (IllegalArgumentException e) {
                    Assert.fail("pileArr[0] may only contain ! or a valid card character, currently [" + pileArr[0] + "]");
                }
            }
        } else {
            for (int i = 0; i < pileArr.length; i++) {
                try {
                    Card.fromCharacter(pileArr[i]);
                } catch (IllegalArgumentException e) {
                    Assert.fail("pileArr[" + i + "] must contain a valid card character, currently [" + pileArr[i] + "]");
                }
            }
        }

        noDuplicateCards("Pile", pile);
    }

    static void noDuplicateCards(String x, String toCheck) {
        Set<Character> alreadyFound = new HashSet<>();
        char[] playerArr = toCheck.toCharArray();

        for (Character c : playerArr) {
            if (alreadyFound.contains(c))
                Assert.fail(x + " contains a duplicate card: [" + Card.fromCharacter(c).getCardValue() + "]");
            alreadyFound.add(c);
        }
    }
}