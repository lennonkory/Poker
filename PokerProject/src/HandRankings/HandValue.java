package HandRankings;

import java.util.List;

/**
 * @author kory
 * @param <T>
 */
public abstract class HandValue<T> implements Comparable{
  
    private final List<T> highCards;
    public final Rank rank;
    
    public HandValue(Rank rank, List<T> highCards){
        this.highCards = highCards;
        this.rank = rank;
    }
    
    public HandValue(Rank rank){
        this.rank = rank;
        this.highCards = null;
    }
    
    public Rank getRank(){
        return rank;
    }
    
    public List<T> getCards(){
        return highCards;
    }
  
}
