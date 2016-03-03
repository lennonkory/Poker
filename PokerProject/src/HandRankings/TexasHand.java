package HandRankings;

import Deck.Card;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kory
 */
public class TexasHand extends Hand{
    
    List<Card> wholeCards;
    
    /**
     *
     */
    public TexasHand(){
        super();
        wholeCards = new ArrayList<>();
    }

    /**
     *
     * @param hand
     */
    public TexasHand(List<Card> hand){
        super(hand);
        wholeCards = new ArrayList<>();
    }
    
    /**
     *Sets the players wholecards. Needs to be called each round if texas is being played.
     */
    @Override
    public void setCards(){
        wholeCards.add(this.getListHand().get(0));
        wholeCards.add(this.getListHand().get(1));
    }
    
    /**
     *
     */
    @Override
    public void resetCards(){
        this.wholeCards.clear();
    }
    
    /**
     *
     */
    public void viewWholeCards(){
        wholeCards.stream().forEach((c) -> {
            System.out.println(c);
        });
    }
    @Override
    public void printHand(){
        System.out.println("****WHOLECARDS******");
        this.viewWholeCards();
        System.out.println("****WHOLECARDS******");
        super.printHand();
        
    }
}
