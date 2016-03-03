package Deck;

import HandRankings.Hand;
import java.util.*;


/**
 * @author kory
 */
public class RegularDeck extends Deck{

    private final Random random;
    private boolean shuffled = false; //is the deck shuffled
    
    public RegularDeck(){
        super(new ArrayList<>());
        random = new Random();
    }
    
    @Override
    public void shuffle(){
        
        shuffled = true;
        deck.clear();
        
        for(StandardCard.Suit s : StandardCard.Suit.values()){
           for(StandardCard.Value v : StandardCard.Value.values()){
               deck.add(new StandardCard(s,v));
            } 
        }
        
    }
    
    @Override
    public void printDeck(){
    
        deck.stream().forEach((_item) -> {
            System.out.println(_item.toString());
        });
        
    }
    
    @Override
    public Card dealCard() {
       
        shuffled = false;
        
        Card c;
        c = deck.get(random.nextInt(deck.size()));    
        deck.remove(c);
        
        return c;
    }

    @Override
    public Collection<Card> dealCards(int numberOfCards) {
        Collection<Card> c = new ArrayList<>();
        
        for(int i = 0 ; i < numberOfCards; i++){
            c.add(this.dealCard());
        } 
        return c;
    }

    /**
     * Gets a random hand. ONLY USED FOR TESTING
     * @return 
     */
    public Hand getRandomHand(){
        
        Hand h = new Hand();
        
        if(deck.isEmpty()){
            shuffle();
        }
        
        for(int i = 0 ; i < 7; i++){
            h.addCard(this.dealCard());
        }
        
        return h;
        
    } 
    
    public static void main(String [] args){
    
        Deck d = new RegularDeck();
        System.out.println("Creating Deck: ");
        d.shuffle();
        System.out.println("Getting Cards: ");
        
        for(Card c : d.dealCards(3)){
            System.out.println(c.toString());
        }
        System.out.println("********");
        d.printDeck();
        System.out.println("********");
        d.shuffle();
        d.printDeck();
        
    
    }

    @Override
    public boolean removeCard(Card card) {
        return deck.remove(card);
    }

    @Override
    public String[] dealCardNames(int numberOfCards) {
        String cards[] = new String[numberOfCards];
        
        for(int i = 0 ; i < numberOfCards; i++){
            cards[i] = this.dealCard().getName();
        }
        
        return cards;
    }

}
