package Deck;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Abstract class for a Deck of Cards. 
 * @author Kory Bryson
 */
public abstract class Deck {

    /**
     *Holds a list of cards.
     */
    public final List<Card> deck;
    
    /**
     *Creates a new Deck of cards. The deck is empty.
     */
    public Deck(){
        deck = new ArrayList<>();
    }
    
    /**
     *Creates a copy of a deck from another deck.
     * @param deck A list of cards.
     */
    public Deck(List<Card> deck){
        this.deck = new ArrayList(deck);
    }
    
    /**
     *Deals a single random card from the deck. That card is removed from the deck.
     * @return a random Card
     * @throws Deck.NotEnoughCardsException
     * @see Card
     */
    public abstract Card dealCard() throws NotEnoughCardsException;

    /**
     *Deals the requested number of cards.
     * @param numberOfCards
     * @return
     * @throws Deck.NotEnoughCardsException
     */
    public abstract Collection<Card> dealCards(int numberOfCards)throws NotEnoughCardsException;

    /**
     *Creates a new Deck of cards. All cards in the previous deck are removed.
     */
    public abstract void shuffle();

    /**
     *Prints the deck to the screen. Used for testing.
     */
    public abstract void printDeck();

    /**
     *Removes a given card from the deck.
     * @param card The card to be removed.
     * @return true if the card has been removed and false other wises. 
     */
    public abstract boolean removeCard(Card card);

    /**
     *Deals number of cards asked for but instead of returning the card it simply returns the names of the cards.
     * @param numberOfCards
     * @return A string array of card names.
     */
    public abstract String[] dealCardNames(int numberOfCards);
    
    /**
     * Should only be used for testing. REMOVE
     * @return 
     */
    public List<Card> getDeck(){
        return deck;
    }

}
