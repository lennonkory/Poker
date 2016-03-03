package HandRankings;

import java.util.*;
import Deck.Card;

/**
 * @author kory
 */
public  class Hand {
    
    private final List <Card> hand;
    
    public Hand(){
        hand = new ArrayList<>();
    }
    
    public Hand(List<Card> h){
        hand = h;
    }
    
    /**
     * Adds a card to the hand List.
     * @param c The card to add.
     * @return returns true if the card has been added.
     */
    public boolean addCard(Card c){
    
        hand.add(c);
        
        return true;
        
    }

    public void resetHand(){
        hand.clear();
    }
    
    public Iterable<Card> getHand(){
        return hand;
    }
    
    public void sortHand(){
        Collections.sort(hand, (Card o1, Card o2) -> o1.getIntValue() - o2.getIntValue());
    }
    
    public List<Card> getListHand(){
        return hand;
    }

    public void sortHandHigh() {
        Collections.sort(hand, (Card o1, Card o2) -> o2.getIntValue() - o1.getIntValue());
    }
    
    public void printHand(){
        hand.stream().forEach((c) -> {
            System.out.println(c.toString());
        });
    }

    public void setCards() {
    }
    
    /**
     *
     */
    public void resetCards() {
    }
    
}

enum Rank{ HIGHCARD, PAIR, TWOPAIR, THREEOFAKIND, STRAIGHT, FLUSH, FULLHOUSE, FOUROFAKIND, STRAIGHTFLUSH};
