package Deck;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author kory
 */
public abstract class Deck {
    public final List<Card> deck;
    
    public Deck(List<Card> deck){
        this.deck = deck;
    }
    
    public abstract Card dealCard();
    public abstract Collection<Card> dealCards(int numberOfCards);
    public abstract void shuffle();
    public abstract void printDeck();
    public abstract boolean removeCard(Card card);
    public abstract String[] dealCardNames(int numberOfCards);
    
    /**
     * Should only be used for testing. REMOVE
     * @return 
     */
    public List<Card> getDeck(){
        return deck;
    }

}
