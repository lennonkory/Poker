package HandRankings;

import static HandRankings.Rank.FULLHOUSE;


/**
 *
 * @author Kory Bryson
 */
public class FullHouseRank extends HandValue {

    private final int mainNum;
    private final int secondary;

    public FullHouseRank(int m, int s) {
        super(FULLHOUSE);
        mainNum = m;
        secondary = s;
    
    }

    @Override
    public int compareTo(Object o) {

        HandValue other = (HandValue) o;

        if (this.getRank() != other.getRank()) {
            return getRank().ordinal() - other.getRank().ordinal();
        }
        
        FullHouseRank otherKind = (FullHouseRank)other;
        
        if(mainNum != otherKind.mainNum){
            return mainNum - otherKind.mainNum;
        }

        return this.secondary - otherKind.secondary;
        
    }
}
