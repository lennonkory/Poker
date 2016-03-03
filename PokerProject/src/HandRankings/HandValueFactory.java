package HandRankings;

import Deck.Card;
import Deck.StandardCard;
import Deck.StandardCard.*;
import static HandRankings.Rank.*;
import java.util.HashMap;
import java.util.List;

/**
 * @author kory
 */
public class HandValueFactory {

    private HandValueFactory(){}
    
    public static HandValue create(Rank rank, List<Value> pairs, List<Value> cards) {
        if(rank == TWOPAIR){
            return new TwoPairRank(pairs,cards);
        }
        if(rank == PAIR){
            return new PairRank(pairs,cards);
        }
        
        return null;
    }

    public static HandValue create(Rank rank, int main, int high, int secondary) {
        return new KindRank(rank,main, high, secondary);
    }
    
    public static HandValue create(Rank rank, int main, int secondary) {
        return new FullHouseRank(main, secondary);
    }

    public static HandValue create(Rank rank, List<Card> highCards){

        return new StandardRank(rank,highCards);
        
    }
    
    public static void main(String [] args){
        
        Hand one = new TexasHand();
        
        one.addCard(new StandardCard(Suit.CLUBS,Value.TWO));
        one.addCard(new StandardCard(Suit.HEARTS,Value.ACE));
        one.addCard(new StandardCard(Suit.CLUBS,Value.TEN));
        one.addCard(new StandardCard(Suit.DIAMONDS,Value.KING));
        one.addCard(new StandardCard(Suit.HEARTS,Value.FOUR));
        one.addCard(new StandardCard(Suit.CLUBS,Value.KING));
        one.addCard(new StandardCard(Suit.CLUBS,Value.KING));
        
        Hand two = new TexasHand();
        
        two.addCard(new StandardCard(Suit.CLUBS,Value.QUEEN));
        two.addCard(new StandardCard(Suit.DIAMONDS,Value.ACE));
        two.addCard(new StandardCard(Suit.CLUBS,Value.KING));
        two.addCard(new StandardCard(Suit.DIAMONDS,Value.KING));
        two.addCard(new StandardCard(Suit.CLUBS,Value.KING));
        
        one.sortHandHigh();
        two.sortHandHigh();
        
        HashMap<Value,Integer> oneMap = RankHand.hashHand(one)[0];
        HashMap<Value,Integer> twoMap = RankHand.hashHand(two)[0];
        
        HandValue o = RankHand.getKind(oneMap);
        HandValue t = RankHand.getKind(twoMap);
        
        System.out.println(o.compareTo(t));
        
    }

}
