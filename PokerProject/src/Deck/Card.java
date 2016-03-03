package Deck;

import Deck.StandardCard.*;

/**
 *
 * @author kory
 */
public interface Card {

    /**
     *
     * @return int
     */
    public int getIntValue();
    public Value getValue(); //THIS needs to be fixed
    public String getName();
    public Suit getSuit();
    
}
