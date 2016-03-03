package HandRankings;

import Deck.Card;
import static HandRankings.Rank.*;
import java.util.List;

/**
 *
 * @author Kory Bryson
 */
public class StandardRank extends HandValue{

    public StandardRank(Rank rank, List<Card> cards) {
        super(rank, cards);
    }

    @Override
    public int compareTo(Object o) {
  
        HandValue other = (HandValue)o;
        
        /*If hands are the same rank compare card by card*/
        if(getRank() == other.getRank()){
            
            List<Card> cards = getCards();
            List<Card> cardsOther = other.getCards();
            
            for(int i = 0 ; i < cards.size(); i++){
                int cValue = cards.get(i).getIntValue();
                int otherValue = cardsOther.get(i).getIntValue();
                if( cValue != otherValue){
                    return cValue - otherValue;//return the first insatance of one card being bigger
                }
            }  
        }
        
        return getRank().ordinal() - other.getRank().ordinal();
        
    }

    @Override
    public Rank getRank() {
        return rank;
    }
        

}
