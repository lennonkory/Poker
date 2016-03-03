package HandRankings;

import Deck.*;
import Deck.StandardCard;
import Deck.StandardCard.Suit;
import Deck.StandardCard.Value;
import java.util.*;
import java.util.Random;
/**
 * @author kory
 */
public class GenerateHands {
    
    private static final Deck deck = new RegularDeck();
    
    private static final Random ran = new Random();
    
    private GenerateHands(){}
    
    private static boolean hasValue(List<Value> valueList, Value value){
    
        for(Value v : valueList){
            if(v == value){
                return true;
            }
        }
        
        return false;
        
    }
    
    private static Card removeCardWithValue(Value value){
        for(Card c : deck.getDeck()){
            if(c.getValue() == value){
                deck.deck.remove(c);
                return c;
            }
        }
        return null;
    }
    
    private static Card removeCardWithDifferentValue(List<Value> valueList){
        Card card = null;
        do{
            card = deck.deck.get(ran.nextInt(deck.deck.size()));
        }while(hasValue(valueList,card.getValue()));
        deck.removeCard(card);
        return card;
    }
    
    public static Hand createStraight(){
        
        Hand hand = new TexasHand();
        
        Value start = Value.values()[ran.nextInt(13-5)];
        
        for(int i = 0 ; i < 5; i++){
            hand.addCard(new StandardCard(Suit.values()[ran.nextInt(4)], Value.values()[start.ordinal()+i]));
        }
        
        hand.addCard(new StandardCard(Suit.values()[ran.nextInt(4)], Value.values()[ran.nextInt(13)]));
        hand.addCard(new StandardCard(Suit.values()[ran.nextInt(4)], Value.values()[ran.nextInt(13)]));
        
        hand.sortHandHigh();
        
        return hand;
    
    }
    
    public static Hand createFlush(){
        
        Hand hand = new TexasHand();
        
        Suit suit = Suit.values()[ran.nextInt(4)];
        
        for(int i = 0 ; i < 5; i++){
            hand.addCard(new StandardCard(suit, Value.values()[ran.nextInt(13)]));
        }
        
        hand.addCard(new StandardCard(Suit.values()[ran.nextInt(4)], Value.values()[ran.nextInt(13)]));
        hand.addCard(new StandardCard(Suit.values()[ran.nextInt(4)], Value.values()[ran.nextInt(13)]));
        
        hand.sortHandHigh();
        
        return hand;
    
    }
    
    public static Hand createStraightFlush(){
        
        Hand hand = new TexasHand();
        
        Value start = Value.values()[ran.nextInt(13-5)];
        Suit suit = Suit.values()[ran.nextInt(4)];
        
        for(int i = 0 ; i < 5; i++){
            hand.addCard(new StandardCard(suit, Value.values()[start.ordinal()+i]));
        }
        
        hand.addCard(new StandardCard(Suit.values()[ran.nextInt(4)], Value.values()[ran.nextInt(13)]));
        hand.addCard(new StandardCard(Suit.values()[ran.nextInt(4)], Value.values()[ran.nextInt(13)]));
        
        hand.sortHandHigh();
        
        return hand;
    
    }
    
    public static Hand createFullHouse(){
        
        Hand hand = new TexasHand();
        
        Value start = Value.values()[ran.nextInt(13)];
        Suit suit = Suit.values()[ran.nextInt(2)];
        
        for(int i = 0 ; i < 3; i++){
            hand.addCard(new StandardCard(Suit.values()[suit.ordinal()+i], Value.values()[start.ordinal()]));
        }
        
        Value two;
        
        do{
            two = Value.values()[ran.nextInt(13)];
        }while(two == start);
        
        hand.addCard(new StandardCard(Suit.values()[ran.nextInt(4)], two));
        hand.addCard(new StandardCard(Suit.values()[ran.nextInt(4)], two));
        
        hand.sortHandHigh();
        
        return hand;
    
    }
    
    public static Hand createPair(){
        
        Hand hand = new TexasHand();
        
        deck.shuffle();
        
        Card p = deck.dealCard();
        hand.addCard(p);
        hand.addCard(removeCardWithValue(p.getValue()));
        
        List<Value> valueList = new ArrayList<>();
        valueList.add(p.getValue());
        
        for(int i = 0 ; i < 5; i++){
            Card c = removeCardWithDifferentValue(valueList);
            hand.addCard(c);
            valueList.add(c.getValue());
        }
        return hand;
    }
    
    public static Hand createTwoPair(){
        
        Hand hand = new TexasHand();
        
        deck.shuffle();
        
        Card p = deck.dealCard();
        hand.addCard(p);
        hand.addCard(removeCardWithValue(p.getValue()));
        
        List<Value> valueList = new ArrayList<>();
        valueList.add(p.getValue());

        p = removeCardWithDifferentValue(valueList);
        hand.addCard(p);
        hand.addCard(removeCardWithValue(p.getValue()));

        valueList.add(p.getValue());
        
        for(int i = 0 ; i < 3; i++){
            Card c = removeCardWithDifferentValue(valueList);
            hand.addCard(c);
            valueList.add(c.getValue());
        }
        return hand;
    }
    
    public static void main(String [] args){
    
        Deck d = new RegularDeck();
        d.shuffle();
        
        createTwoPair().printHand();
    }

}
