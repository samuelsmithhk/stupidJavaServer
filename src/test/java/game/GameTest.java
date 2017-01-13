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
        String game = "200abcdefghi1jklmnopqr?stuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?!";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.updateGame(game, 0, new char[]{});
        ValidationUtils.isValidGame(gameUpdated.game, 2);
        Assert.assertTrue("Game strings changed between deserialization and serialization", game.equals(gameUpdated.game));
        Assert.assertEquals("Should have success message", gameUpdated.message, "Success");
    }

    @Test
    public void shouldAllowLegalMoveNoCardsOnPile() {
        String game = "200abcdefghi1jklmnopqr?stuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?!";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.updateGame(game, 0, new char[]{'g'});
        ValidationUtils.isValidGame(gameUpdated.game, 2);
        ValidationUtils.assertCardsAreOnPile(gameUpdated.game, new char[]{'g'});
        ValidationUtils.assertPlayerDoesNotHaveCards(gameUpdated.game, 0, new char[]{'g'});
        Assert.assertEquals("Should have success message", gameUpdated.message, "Success");
    }

    @Test
    public void shouldAllowLegalMoveCardsOnPile() {
        String game = "200abcdefAhi1jklmnopqr?stuvwxyzBCDEFGHIJKLMNOPQRSTUVWXYZ?g";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.updateGame(game, 0, new char[]{'h'});
        ValidationUtils.isValidGame(gameUpdated.game, 2);
        ValidationUtils.assertCardsAreOnPile(gameUpdated.game, new char[]{'g','h'});
        ValidationUtils.assertPlayerDoesNotHaveCards(gameUpdated.game, 0, new char[]{'g','h'});
        Assert.assertEquals("Should have success message", gameUpdated.message, "Success");
    }

    @Test
    public void shouldAllowLegalMoveMultipleCards() {
        String game = "200abcdefgti1jklmnopqr?shuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?!";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.updateGame(game, 0, new char[]{'g','t'});
        ValidationUtils.isValidGame(gameUpdated.game, 2);
        ValidationUtils.assertCardsAreOnPile(gameUpdated.game, new char[]{'g'});
        ValidationUtils.assertPlayerDoesNotHaveCards(gameUpdated.game, 0, new char[]{'g','t'});
        Assert.assertEquals("Should have success message", gameUpdated.message, "Success");
    }

    @Test
    public void shouldBlockIllegalMoveWeakerCardThanPile() {
        String game = "200abcdefgAi1jklmnopqr?stuvwxyzBCDEFGHIJKLMNOPQRSTUVWXYZ?h";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.updateGame(game, 0, new char[]{'g'});
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
        Game.InstructionResult gameUpdated = Game.updateGame(game, 0, new char[]{'m'});
        ValidationUtils.isValidGame(gameUpdated.game, 2);
        Assert.assertTrue("Should have an error message regarding player's possession of card",
                gameUpdated.message.contains("hold"));
    }

    @Test
    public void shouldBlockIllegalMoveTwoDifferentCardsInOnePlay() {
        String game = "200abcdefghi1jklmnopqr?stuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?!";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.updateGame(game, 0, new char[]{'g', 'h'});
        ValidationUtils.isValidGame(gameUpdated.game, 2);
        Assert.assertTrue("Should have an error message regarding the different cards trying to be played",
                gameUpdated.message.contains("power"));
    }

    @Test
    public void shouldPickUpCardsIfHandLessThanThreeAfterMove() {
        String game = "200abcdefghi1jklmnopqr?stuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?!";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.updateGame(game, 0, new char[]{'g'});
        ValidationUtils.isValidGame(gameUpdated.game, 2);
        ValidationUtils.assertPlayerDoesHaveCards(gameUpdated.game, 0, new char[]{'s'});
    }

    @Test
    public void shouldNotPickUpCardsIfHandGreaterThanThreeAfterMove() {
        String game = "200abcdefghiABC1jklmnopqr?stuvwxyzDEFGHIJKLMNOPQRSTUVWXYZ?!";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.updateGame(game, 0, new char[]{'g'});
        ValidationUtils.isValidGame(gameUpdated.game, 2);
        ValidationUtils.assertPlayerDoesNotHaveCards(gameUpdated.game, 0, new char[]{'s'});
    }

    @Test
    public void shouldPickUpFromShownIfDeckIsEmpty() {
        String game = "200abcdefg1jklmnopqr?!?!";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.updateGame(game, 0, new char[]{'g'});
        ValidationUtils.isValidGame(gameUpdated.game, 2);
        ValidationUtils.assertPlayerDoesNotHaveCards(gameUpdated.game, 0, new char[]{'g'});
        ValidationUtils.assertInHand(gameUpdated.game, 0, new char[]{'d', 'e', 'f'});
    }

    @Test
    public void shouldNotPickUpFromShownIfAtLeastOneCardIsInDeck() {
        String game = "200abcdefgti1jklmnopqr?s?!";
        ValidationUtils.isValidGame(game, 2);
        Game.InstructionResult gameUpdated = Game.updateGame(game, 0, new char[]{'g','t'});
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
        Game.InstructionResult gameUpdated = Game.updateGame(game, 0, new char[]{'g'});
        ValidationUtils.isValidGame(gameUpdated.game, 2);
        ValidationUtils.assertCardsAreNotOnPile(gameUpdated.game, new char[]{'g'});
        ValidationUtils.assertPlayerDoesHaveCards(gameUpdated.game, 0, new char[]{'g'});
        Assert.assertTrue("Should have error message regarding incorrect player", gameUpdated.message.contains("turn"));
    }
}
