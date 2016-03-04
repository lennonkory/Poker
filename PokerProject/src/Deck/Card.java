package Deck;

import Deck.StandardCard.*;

/**
 *Interface for Card. In general a card should have some kind of suit and value.
 * @author Kory Bryson
 */
public interface Card {

    /**
     *Returns a integer value of the card.
     * @return int
     */
    public int getIntValue();

    /**
     *
     * @return
     */
    public Value getValue(); //THIS needs to be fixed

    /**
     *Returns the Name of the card. Example would be ACEOFSPADES
     * @return String
     */
    public String getName();

    /**
     *Returns the suit of the card.
     * @return Suit
     * @see Suit
     */
    public Suit getSuit();
    
}
