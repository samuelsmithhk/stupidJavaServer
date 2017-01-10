package game;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by samuelsmith on 1/2/17.
 */
public class Card {

    enum Suite {
        DIAMOND, HEART, CLUB, SPADE
    }

    private static final Map<Character, String> valueMap;

    public static Card fromCharacter(char c) {
        if (c == '!') return null;

        if (!valueMap.containsKey(c))
            throw new IllegalArgumentException("No card exists for char [" + c + "]");

        String v = valueMap.get(c);
        return new Card(c, v);
    }

    private final char value;
    private final String cardValue;
    private final int power;
    private final Suite suite;


    private Card(char value, String cardValue) {
        this.value = value;
        this.cardValue = cardValue;

        if (cardValue.contains("Diamonds")) suite = Suite.DIAMOND;
        else if (cardValue.contains("Hearts")) suite = Suite.HEART;
        else if (cardValue.contains("Clubs")) suite = Suite.CLUB;
        else suite = Suite.SPADE;

        switch (cardValue.substring(0, 1)) {
            case "3" : power = 0; break;
            case "4" : power = 1; break;
            case "5" : power = 2; break;
            case "6" : power = 3; break;
            case "7" : power = 4; break;
            case "8" : power = 5; break;
            case "9" : power = 6; break;
            case "J" : power = 7; break;
            case "Q" : power = 8; break;
            case "K" : power = 9; break;
            default: power = 10;
        }
    }

    public String getCardValue() {
        return cardValue;
    }

    public int getPower() {
        return power;
    }

    public Suite getSuite() {
        return suite;
    }

    @Override
    public String toString() {
        return new String(new char[]{value});
    }

    public static Set<Character> getAllCardKeys() {
        return valueMap.keySet();
    }

    static {
        valueMap = new HashMap<>();

        valueMap.put('a', "Ace of Diamonds");
        valueMap.put('b', "2 of Diamonds");
        valueMap.put('c', "3 of Diamonds");
        valueMap.put('d', "4 of Diamonds");
        valueMap.put('e', "5 of Diamonds");
        valueMap.put('f', "6 of Diamonds");
        valueMap.put('g', "7 of Diamonds");
        valueMap.put('h', "8 of Diamonds");
        valueMap.put('i', "9 of Diamonds");
        valueMap.put('j', "10 of Diamonds");
        valueMap.put('k', "Jack of Diamonds");
        valueMap.put('l', "Queen of Diamonds");
        valueMap.put('m', "King of Diamonds");

        valueMap.put('n', "Ace of Hearts");
        valueMap.put('o', "2 of Hearts");
        valueMap.put('p', "3 of Hearts");
        valueMap.put('q', "4 of Hearts");
        valueMap.put('r', "5 of Hearts");
        valueMap.put('s', "6 of Hearts");
        valueMap.put('t', "7 of Hearts");
        valueMap.put('u', "8 of Hearts");
        valueMap.put('v', "9 of Hearts");
        valueMap.put('w', "10 of Hearts");
        valueMap.put('x', "Jack of Hearts");
        valueMap.put('y', "Queen of Hearts");
        valueMap.put('z', "King of Hearts");

        valueMap.put('A', "Ace of Clubs");
        valueMap.put('B', "2 of Clubs");
        valueMap.put('C', "3 of Clubs");
        valueMap.put('D', "4 of Clubs");
        valueMap.put('E', "5 of Clubs");
        valueMap.put('F', "6 of Clubs");
        valueMap.put('G', "7 of Clubs");
        valueMap.put('H', "8 of Clubs");
        valueMap.put('I', "9 of Clubs");
        valueMap.put('J', "10 of Clubs");
        valueMap.put('K', "Jack of Clubs");
        valueMap.put('L', "Queen of Clubs");
        valueMap.put('M', "King of Clubs");

        valueMap.put('N', "Ace of Spades");
        valueMap.put('O', "2 of Spades");
        valueMap.put('P', "3 of Spades");
        valueMap.put('Q', "4 of Spades");
        valueMap.put('R', "5 of Spades");
        valueMap.put('S', "6 of Spades");
        valueMap.put('T', "7 of Spades");
        valueMap.put('U', "8 of Spades");
        valueMap.put('V', "9 of Spades");
        valueMap.put('W', "10 of Spades");
        valueMap.put('X', "Jack of Spades");
        valueMap.put('Y', "Queen of Spades");
        valueMap.put('Z', "King of Spades");
    }
}
