package Deck;

import java.util.Objects;

/**
 * A standard card can be one of 52 cards. A card has a suit (hearts,clubs,
 * diamonds or spades) and a value from 2 to ace.
 *
 * @author Kory Bryson
 */
public class StandardCard implements Card {

    public enum Suit {
        HEARTS, SPADES, CLUBS, DIAMONDS
    };

    public enum Value {
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
    };

    private final int intValue;
    private final Suit suit;
    private final Value value;

    /**
     * Creates a card based of a given Suit and Value.
     *
     * @param s
     * @param v
     * @see Suit #see Value
     */
    public StandardCard(Suit s, Value v) {
        this.value = v;
        this.suit = s;
        this.intValue = v.ordinal() + 2;
    }

    @Override
    public int getIntValue() {
        return this.intValue;
    }

    @Override
    public Suit getSuit() {
        return suit;
    }

    @Override
    public Value getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {

        if (!(o instanceof StandardCard)) {
            return false;
        }

        StandardCard c = (StandardCard) o;

        return this.suit.equals(c.suit) && this.value.equals(c.value);

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.intValue;
        hash = 19 * hash + Objects.hashCode(this.suit);
        hash = 19 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public String toString() {

        String valueStr = this.value.toString();

        int len = 6 - valueStr.length();

        for (int i = 0; i < len; i++) {
            valueStr += " ";
        }

        return valueStr + "of " + this.suit.toString();
    }

    @Override
    public String getName() {
        return this.value.toString() + this.suit.toString();
    }

    public static void main(String[] args) {

        Card c = new StandardCard(Suit.HEARTS, Value.THREE);

        System.out.println("IS equal: " + c.equals(new StandardCard(Suit.HEARTS, Value.THREE)));

    }

}
