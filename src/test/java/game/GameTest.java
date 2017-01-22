package game;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by samuelsmith on 1/2/17.
 */
public class GameTest {

    @Test
    public void shouldCreateNewGameState2Player() {
        String game = Game.createNewGame(2);
        ValidationUtils.isValidGame(game, 2);
    }

    @Test
    public void shouldCreateNewGameState3Player() {
        String game = Game.createNewGame(3);
        ValidationUtils.isValidGame(game, 3);
    }

    @Test
    public void shouldCreateNewGameState4Player() {
        String game = Game.createNewGame(4);
        ValidationUtils.isValidGame(game, 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectLessThan2Players() {
        Game.createNewGame(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectMoreThan4Players() {
        Game.createNewGame(5);
    }

    @Test
    public void shouldCreateCorrectGameFromString() {
        String game = "2%0abcdefghi1jklmnopqr?stuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?!";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.makeMove(game, -1, new char[]{});
        ValidationUtils.isValidGame(gameUpdated.game, 2);
        Assert.assertTrue("Game strings changed between deserialization and serialization", game.equals(gameUpdated.game));
        Assert.assertTrue("Should have a message regarding that the game has not started yet",
                gameUpdated.message.contains("started"));
    }

    @Test
    public void shouldAllowLegalMoveNoCardsOnPile() {
        String game = "200abcdefghi1jklmnopqr?stuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?!";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.makeMove(game, 0, new char[]{'g'});
        ValidationUtils.isValidGame(gameUpdated.game, 2);
        ValidationUtils.assertCardsAreOnPile(gameUpdated.game, new char[]{'g'});
        ValidationUtils.assertPlayerDoesNotHaveCards(gameUpdated.game, 0, new char[]{'g'});
        Assert.assertEquals("Should have success message", gameUpdated.message, "Success");
    }

    @Test
    public void shouldAllowLegalMoveCardsOnPile() {
        String game = "200abcdefAhi1jklmnopqr?stuvwxyzBCDEFGHIJKLMNOPQRSTUVWXYZ?g";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.makeMove(game, 0, new char[]{'h'});
        ValidationUtils.isValidGame(gameUpdated.game, 2);
        ValidationUtils.assertCardsAreOnPile(gameUpdated.game, new char[]{'g','h'});
        ValidationUtils.assertPlayerDoesNotHaveCards(gameUpdated.game, 0, new char[]{'g','h'});
        Assert.assertEquals("Should have success message", gameUpdated.message, "Success");
    }

    @Test
    public void shouldAllowLegalMoveMultipleCards() {
        String game = "200abcdefgti1jklmnopqr?shuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?!";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.makeMove(game, 0, new char[]{'g','t'});
        ValidationUtils.isValidGame(gameUpdated.game, 2);
        ValidationUtils.assertCardsAreOnPile(gameUpdated.game, new char[]{'g'});
        ValidationUtils.assertPlayerDoesNotHaveCards(gameUpdated.game, 0, new char[]{'g','t'});
        Assert.assertEquals("Should have success message", gameUpdated.message, "Success");
    }

    @Test
    public void shouldBlockMoveIfGameHasNotYetStarted() {
        String game = "2%0abcdefghi1jklmnopqr?stuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?!";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.makeMove(game, 0, new char[]{'g'});
        Assert.assertTrue("Should not allow move as the game has not yet started",
                gameUpdated.message.contains("game has not started yet"));
    }

    @Test
    public void shouldBlockIllegalMoveWeakerCardThanPile() {
        String game = "200abcdefgAi1jklmnopqr?stuvwxyzBCDEFGHIJKLMNOPQRSTUVWXYZ?h";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.makeMove(game, 0, new char[]{'g'});
        ValidationUtils.isValidGame(gameUpdated.game, 2);
        ValidationUtils.assertCardsAreOnPile(gameUpdated.game, new char[]{'h'});
        ValidationUtils.assertPlayerDoesNotHaveCards(gameUpdated.game, 0, new char[]{'h'});
        Assert.assertTrue("Should have an error message regarding the card's power",
                gameUpdated.message.contains("powerful"));
    }

    @Test
    public void shouldBlockIllegalMovePlayerDoesNotHaveCard() {
        String game = "200abcdefghi1jklmnopqr?stuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?!";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.makeMove(game, 0, new char[]{'m'});
        ValidationUtils.isValidGame(gameUpdated.game, 2);
        Assert.assertTrue("Should have an error message regarding player's possession of card",
                gameUpdated.message.contains("hold"));
    }

    @Test
    public void shouldBlockIllegalMoveTwoDifferentCardsInOnePlay() {
        String game = "200abcdefghi1jklmnopqr?stuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?!";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.makeMove(game, 0, new char[]{'g', 'h'});
        ValidationUtils.isValidGame(gameUpdated.game, 2);
        Assert.assertTrue("Should have an error message regarding the different cards trying to be played",
                gameUpdated.message.contains("power"));
    }

    @Test
    public void shouldPickUpCardsIfHandLessThanThreeAfterMove() {
        String game = "200abcdefghi1jklmnopqr?stuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?!";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.makeMove(game, 0, new char[]{'g'});
        ValidationUtils.isValidGame(gameUpdated.game, 2);
        ValidationUtils.assertPlayerDoesHaveCards(gameUpdated.game, 0, new char[]{'s'});
    }

    @Test
    public void shouldNotPickUpCardsIfHandGreaterThanThreeAfterMove() {
        String game = "200abcdefghiABC1jklmnopqr?stuvwxyzDEFGHIJKLMNOPQRSTUVWXYZ?!";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.makeMove(game, 0, new char[]{'g'});
        ValidationUtils.isValidGame(gameUpdated.game, 2);
        ValidationUtils.assertPlayerDoesNotHaveCards(gameUpdated.game, 0, new char[]{'s'});
    }

    @Test
    public void shouldPickUpFromShownIfDeckIsEmpty() {
        String game = "200abcdefg1jklmnopqr?!?!";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.makeMove(game, 0, new char[]{'g'});
        ValidationUtils.isValidGame(gameUpdated.game, 2);
        ValidationUtils.assertPlayerDoesNotHaveCards(gameUpdated.game, 0, new char[]{'g'});
        ValidationUtils.assertInHand(gameUpdated.game, 0, new char[]{'d', 'e', 'f'});
    }

    @Test
    public void shouldNotPickUpFromShownIfAtLeastOneCardIsInDeck() {
        String game = "200abcdefgti1jklmnopqr?s?!";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.makeMove(game, 0, new char[]{'g','t'});
        ValidationUtils.isValidGame(gameUpdated.game, 2);
        ValidationUtils.assertCardsAreOnPile(gameUpdated.game, new char[]{'g'});
        ValidationUtils.assertPlayerDoesNotHaveCards(gameUpdated.game, 0, new char[]{'g','t'});
        ValidationUtils.assertInHand(gameUpdated.game, 0, new char[]{'s'});
        ValidationUtils.assertNotInHand(gameUpdated.game, 0, new char[]{'d', 'e', 'f'});
    }

    @Test
    public void shouldNotAllowAPlayerToMoveWhenItIsNotTheirTurn() {
        String game = "210abcdefghi1jklmnopqr?stuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?!";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.makeMove(game, 0, new char[]{'g'});
        ValidationUtils.isValidGame(gameUpdated.game, 2);
        ValidationUtils.assertCardsAreNotOnPile(gameUpdated.game, new char[]{'g'});
        ValidationUtils.assertPlayerDoesHaveCards(gameUpdated.game, 0, new char[]{'g'});
        Assert.assertTrue("Should have error message regarding incorrect player", gameUpdated.message.contains("turn"));
    }

    @Test
    public void shouldCreateGameThatHasNotStarted() {
        String g = Game.createNewGame(3);
        Assert.assertEquals("Game should not have started", g.charAt(1), '%');

        Game.InstructionResult attemptMove = Game.makeMove(g, 2, new char[0]);
        Assert.assertTrue("Should contain error message regarding move not allowed because the game has not started",
                attemptMove.message.contains("game has not started yet"));
    }

    @Test
    public void shouldStartGame() {
        String game = Game.createNewGame(4);

        Game.InstructionResult started = Game.startGame(game);
        Assert.assertTrue("", started.message.equals("Success"));
        ValidationUtils.isValidGame(started.game, 4);

        char playerChar = started.game.charAt(1);
        Assert.assertFalse("Game has not been started", playerChar == '%');

        int playerNum = Character.getNumericValue(playerChar);
        Game.InstructionResult actual = Game.makeMove(started.game, playerNum, new char[0]);

        Assert.assertFalse("Game should have started", actual.message.contains("started"));
    }

    @Test
    public void shouldAllowPlayerToSwitchCards() {
        String game = "2%0abcdefghi1jklmnopqr?stuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?!";
        Game.InstructionResult switchAttempt = Game.makeSwitch(game, 0, new char[]{'d'}, new char[]{'g'});
        Assert.assertTrue("Should have success message", switchAttempt.message.equals("Success"));

        ValidationUtils.assertInHand(switchAttempt.game, 0, new char[]{'d', 'h', 'i'});
        ValidationUtils.assertNotInHand(switchAttempt.game, 0, new char[]{'g'});
        ValidationUtils.assertOnShown(switchAttempt.game, 0, new char[]{'g', 'e', 'f'});
        ValidationUtils.assertNotOnShown(switchAttempt.game, 0, new char[]{'d'});
    }

    @Test
    public void shouldNotAllowPlayerToSwitchCardsOnceGameHasStarted() {
        String game = "2%0abcdefghi1jklmnopqr?stuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?!";
        Game.InstructionResult started = Game.startGame(game);
        Assert.assertTrue("Game should have started", started.message.equals("Success"));
        Assert.assertTrue("Game should have started", started.game.charAt(1) != '%');

        Game.InstructionResult actual = Game.makeSwitch(started.game, 0, new char[0], new char[0]);
        Assert.assertTrue("Should have an error message regarding the game already being started",
                actual.message.contains("game has already been started"));
    }

    @Test
    public void shouldNotAllowPlayerToSwitchCardsIfInvalidCards() {
        String game = "2%0abcdefghi1jklmnopqr?stuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?!";
        Game.InstructionResult switchAttempt = Game.makeSwitch(game, 0, new char[]{'3'}, new char[]{'a'});
        Assert.assertTrue("Should have error message regarding invalid card",
                switchAttempt.message.contains("invalid card"));
    }

    @Test
    public void shouldNotAllowPlayerToSwitchCardIfPlayerDoesNotHaveCardInHand() {
        String game = "2%0abcdefghi1jklmnopqr?stuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?!";
        Game.InstructionResult switchAttempt = Game.makeSwitch(game, 0, new char[]{'d'}, new char[]{'m'});
        Assert.assertTrue("Should have error message regarding not having card in hand",
                switchAttempt.message.contains("player does not have the cards being switched"));
    }

    @Test
    public void shouldNotAllowPlayerToSwitchCardIfPlayerDoesNotHaveCardInShown() {
        String game = "2%0abcdefghi1jklmnopqr?stuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?!";
        Game.InstructionResult switchAttempt = Game.makeSwitch(game, 0, new char[]{'a'}, new char[]{'g'});
        Assert.assertTrue("Should have error message regarding not having card in shown",
                switchAttempt.message.contains("player does not have the cards being switched"));
    }


    @Test
    public void shouldStartGameWithPlayerWithLowestCardSelected() {
        //player 0 has 7 of diamonds as lowest card
        //player 1 has 3 of hearts as lowest card
        //should pick player 1
        String game = "2%0abcdefghi1jklmnopqr?stuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?!";
        Game.InstructionResult started = Game.startGame(game);
        Assert.assertTrue("Did not correctly select player with weakest hand", started.game.charAt(1) == '1');
    }

    @Test
    public void shouldStartGameWithPlayerWithLowestSecondCardWhenDrawOnLowestCard() {
        //player 0 has 3 of diamonds as lowest card
        //player 0 has 4 of hearts as second weakest card
        //player 1 has 3 of hearts as lowest card
        //player 1 has 8 of diamonds as second weakest card
        //should pick player 0
        String game = "2%0abgdefcqi1jklmnophr?stuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?!";
        Game.InstructionResult started = Game.startGame(game);
        Assert.assertTrue("Did not correctly select player with weakest hand", started.game.charAt(1) == '0');
    }

    @Test
    public void shouldStartGameWithPlayerWithLowestThirdCardWithDrawOnFirstTwoCards() {
        //player 0 has 3 of diamonds as lowest card
        //player 0 has 4 of hearts as second weakest card
        //player 0 has 8 of spades as strongest card
        //player 1 has 3 of hearts as lowest card
        //player 1 has 4 of diamonds as second weakest card
        //player 1 has 7 of spades as strongest card
        //should pick player 1
        String game = "2%0abghefpqU1jklmnocdT?stuvwxyzABCDEFGHIJKLMNOPQRSriVWXYZ?!";
        Game.InstructionResult started = Game.startGame(game);
        Assert.assertTrue("Did not correctly select player with weakest hand", started.game.charAt(1) == '1');
    }

    @Test
    public void shouldStartGameWithFirstPlayerIfDrawOnAllThreeCards() {
        //player 0 has 3 of diamonds as lowest card
        //player 0 has 4 of hearts as second weakest card
        //player 0 has 8 of spades as strongest card
        //player 1 has 3 of hearts as lowest card
        //player 1 has 4 of diamonds as second weakest card
        //player 1 has 8 of clubs as strongest card
        //should pick player 0
        String game = "2%0abghefcqU1jklmnopdH?stuvwxyzABCDEFGTIJKLMNOPQRSriVWXYZ?!";
        Game.InstructionResult started = Game.startGame(game);
        Assert.assertTrue("Did not correctly select player with weakest hand", started.game.charAt(1) == '0');
    }
}
