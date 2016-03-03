package HandRankings;

import static HandRankings.Rank.*;
import java.util.*;
import Deck.Card;
import Deck.*;
import Deck.StandardCard.*;
import static Deck.StandardCard.Suit.*;
import static Deck.StandardCard.Value.*;

/**
 * @author kory
 */
public final class RankHand {

    /**
     * Creates two hashmaps based on the cards in the hand Value and Suit
     *
     * @param hand
     * @return
     */
    public static HashMap[] hashHand(Hand hand) {

        HashMap<Value, Integer> valueHash = new HashMap<>();
        HashMap<Suit, Integer> suitHash = new HashMap<>();

        for (Card c : hand.getHand()) {

            /*Adding value of card to hash*/
            Value v = c.getValue();
            if (valueHash.containsKey(v)) {
                int num = valueHash.get(v);
                valueHash.put(v, ++num);
            } else {
                valueHash.put(v, 1);
            }

            /*Adding suit*/
            Suit s = c.getSuit();

            if (suitHash.containsKey(s)) {
                int num = suitHash.get(s);
                suitHash.put(s, ++num);
            } else {
                suitHash.put(s, 1);
            }

        }

        return new HashMap[]{valueHash, suitHash};

    }

    /**
     *
     * @param hand
     * @param s
     * @return
     */
    public static List<Card> getOnlyOneSuit(Hand hand, Suit s) {

        List<Card> newHand = new ArrayList<>();

        /*This loop removes any cards that aren't the same suit as the flush*/
        for (Card c : hand.getHand()) {
            if (c.getSuit() == s) {
                newHand.add(c);
            }
        }
        return newHand;
    }

    /**
     * Checks if hand is a straight flush. NOTE: This method assumes That the
     * hand is a flush already.
     *
     * @param s The suit of the flush
     * @param hand
     * @return
     */
    public static HandValue getStraightFlush(Suit s, Hand hand) {

        List<Card> newHand = (List<Card>) getOnlyOneSuit(hand, s);

        HandValue hv = getStraight(new Hand(newHand));
        
        if(hv != null){
            return new StandardRank(STRAIGHTFLUSH,newHand);
        }
        
        return hv;

    }
    
    /**
     * Checks if the hand is 4 or 3 of a kind
     *
     * @param valueHash
     * @return fourofakind, threeofakind or highcard if hand isn't a kind
     */
    public static HandValue getKind(HashMap<Value, Integer> valueHash) {

        Rank rank = HIGHCARD;
        int main = 0;
        int highCard = 0;
        int secondCard = 1;
        
        List<Card> high = new ArrayList<>();

        for (Map.Entry<Value, Integer> entry : valueHash.entrySet()) {
            
            Value v = entry.getKey();
            Integer i = entry.getValue();
            
            if (i == 4) {
                rank = FOUROFAKIND;
                main = v.ordinal();
            }
            else if (i == 3) {
                rank = THREEOFAKIND;
                main = v.ordinal();
            }
            else if(i == 2){
                high.add(new StandardCard(CLUBS,v));
                high.add(new StandardCard(HEARTS,v));
            }
            else{
                high.add(new StandardCard(CLUBS,v));
            }
        }

        high.sort((Card o1, Card o2) -> {
            return o2.getIntValue() - o1.getIntValue();
        });
        
        if(rank == THREEOFAKIND){
            secondCard = high.get(1).getIntValue();
        }
        
        highCard = high.get(0).getIntValue();
        
        if (rank != HIGHCARD) {
            //System.out.println(highCard + " " + secondCard);
            return HandValueFactory.create(rank, main, highCard, secondCard);
        }

        return null;

    }

    /**
     * Determines if a hand has the rank of FLUSH. Returns HandValue (StandardRank) if
     * the hand is a FLUSH, null other wise.
     * @param suitHash HashMap contains how many of which suit are in a Hand
     * @param hand The players hand
     * @return StandardRank if a flush is found. Null otherwise.
     */
    public static HandValue getFlush(HashMap<Suit, Integer> suitHash, Hand hand) {
        
        Suit s = getFlushSuit(suitHash);
        
        if( s!=null ){
            List<Card> flush = getOnlyOneSuit(hand,s);
            return new StandardRank(FLUSH,flush);
        }
        
        return null;
    }

    /**
     *
     * @param valueHash
     * @return
     */
    public static HandValue getFullHouse(HashMap<Value, Integer> valueHash) {

        int kind = -1;
        int pair = -1;
        
        /*Assumes that the hand was sorted*/
        for (Map.Entry<Value, Integer> entry : valueHash.entrySet()) {

            Value v = entry.getKey();
            Integer i = entry.getValue();
            
            if(kind == -1 && i == 3){
                kind = v.ordinal() + 2;
            }
            else if (i >= 2) {
                if(v.ordinal() + 2 > pair){
                    pair = v.ordinal() + 2;
                }
            }
        }
        
        if (kind != -1 && pair != -1) {
            return HandValueFactory.create(FULLHOUSE, kind, pair);
        }

        return null;

    }

    /**
     *
     * @param hand
     * @return
     */
    public static HandValue getStraight(Hand hand) {

        hand.sortHandHigh();

        int count = 1;
        int lastValue = 0;
        List<Card> cardValues = new ArrayList<>();

        for (Card c : hand.getHand()) {

            int value = c.getIntValue();

            if (lastValue == 0) {
                lastValue = value;
                cardValues.add(c);
            } 
            else {
                if (value + 1 == lastValue) {
                    count++;
                    cardValues.add(c);
                }
                else if(lastValue == value){
                    //do nothing
                }
                else {
                    count = 1;
                    cardValues.clear();
                    cardValues.add(c);
                }

            }

            if (count >= 5) {
                return HandValueFactory.create(STRAIGHT, cardValues);
            }
            lastValue = value;
        }
        
        if(count == 4 && lastValue == 2 && hand.getListHand().get(0).getValue() == ACE){
            cardValues.add(hand.getListHand().get(0));
            return HandValueFactory.create(STRAIGHT, cardValues);
            
        }
        //If the hand is not a straight
        return null;
    }

    /**
     *
     * @param valueHash
     * @return
     */
    public static HandValue getPairs(HashMap<Value, Integer> valueHash) {

        Rank rank = HIGHCARD;

        List<Value> cards = new ArrayList<>();
        List<Value> pairs = new ArrayList<>();
        
        for (Map.Entry<Value, Integer> entry : valueHash.entrySet()) {

            Value v = entry.getKey();
            Integer i = entry.getValue();
            
            if (i == 2) {
                if (rank == HIGHCARD) {
                    rank = PAIR;
                    pairs.add(v);
                } else if (rank == PAIR) {
                    rank = TWOPAIR;
                    pairs.add(1, v);
                }
            } else {
                cards.add(v);
            }

        }

        if (rank != HIGHCARD) {
            return HandValueFactory.create(rank, pairs, cards);
        }

        return null;

    }

    /**
     *
     * @param suitHash
     * @return
     */
    public static Suit getFlushSuit(HashMap<Suit, Integer> suitHash) {

        for (Map.Entry<Suit, Integer> entry : suitHash.entrySet()) {
            
            Suit key = entry.getKey();
            Integer value = entry.getValue();

            if (value >= 5) {
                return key;
            }

        }
        return null;
    }

    /**
     *
     * @param hand
     * @return
     */
    public static List<Card> getHighCards(Hand hand) {

        hand.sortHandHigh();

        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            cards.add(hand.getListHand().get(i));
        }
        return cards;
    }

    /**
     * THIS MTHOD NEEDS TO BE REDONE
     * @param hand
     * @return 
     */
    public static HandValue getRank(Hand hand) {

        HandValue hv = null;
        hand.sortHandHigh();
        
        //[0] == Value, [1] == Suit
        HashMap maps[] = hashHand(hand);
        
        hv = getKind(maps[0]);
        
        if(hv != null){
            if(hv.rank == FOUROFAKIND){
                return hv;
            }
        }
        
        hv = getFullHouse(maps[0]);
        
        if(hv != null){
            return hv;
        }
        
        hv = getFlush(maps[1], hand);
        
        if(hv != null){
            HandValue t= getStraightFlush(getFlushSuit(maps[1]),hand);
            if(t != null){
                return t;
            }
            return hv;
        }

        hv = getStraight(hand);
        
        if(hv != null){
            return hv;
        }
        
        hv = getKind(maps[0]);
        
        if(hv != null){
            return hv;
        }
        
        hv = getPairs(maps[0]);
        
        if(hv != null){
            return hv;
        }
        
        return new StandardRank(HIGHCARD,getHighCards(hand));
        
    }

    /**
     * Compares hands and returns the winner. The winner is the index number of
     * the array of the winning hand
     * @param hands the hands to compare
     * @return array of winners of hands. 
     */
    public static List<Integer> compareHands(Hand[] hands) {

        HandValue rank = getRank(hands[0]);
       
        List<Integer> winners = new ArrayList<>();

        int counter = 0;
        
        for (Hand hand : hands) {
            
            HandValue hv = getRank(hand);
           // System.out.println("HV: " + hv.rank + " rank: " + rank.rank);
           // System.out.println(hv.compareTo(rank));
            if(hv.compareTo(rank) > 0){
                rank = hv;
                winners.clear();
                winners.add(counter);
            }
            else if(hv.compareTo(rank) == 0){
                rank = hv;
                winners.add(counter);
            }
            counter++;
        }
        
        return winners;
    }


    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        Hand hands [] = new Hand[5];
        
        Hand test = new Hand();
        test.addCard(new StandardCard(CLUBS,ACE));
        test.addCard(new StandardCard(SPADES,ACE));
        test.addCard(new StandardCard(HEARTS,KING));
        test.addCard(new StandardCard(SPADES,KING));
        test.addCard(new StandardCard(SPADES,QUEEN));
        test.addCard(new StandardCard(CLUBS,TEN));
        test.addCard(new StandardCard(SPADES,FOUR));
        
        hands[0] = GenerateHands.createTwoPair();
        hands[1] = test;
        hands[2] = GenerateHands.createTwoPair();
        hands[3] = test;
        hands[4] = GenerateHands.createTwoPair();
        //hands[4] = GenerateHands.createStraightFlush();
        
        for(Hand h : hands){
            h.printHand();
            System.out.println();
        }
        for(Integer i : compareHands(hands)){
            System.out.println(i);
        }

    }

}
