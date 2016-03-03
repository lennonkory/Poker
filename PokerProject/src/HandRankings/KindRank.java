package HandRankings;


/**
 *
 * @author Kory Bryson
 */
public class KindRank extends HandValue {

    public final int mainNum;
    public final int HIGH;
    public final int SECONDARY;

    public KindRank(Rank rank, int m, int h, int s) {
        super(rank);
        mainNum = m;
        HIGH = h;
        SECONDARY = s;
    }

    @Override
    public int compareTo(Object o) {

        HandValue other = (HandValue) o;

        if (this.getRank() != other.getRank()) {
            return getRank().ordinal() - other.getRank().ordinal();
        }
        
        KindRank otherKind = (KindRank)other;

        if(this.mainNum != otherKind.mainNum){
            return this.mainNum - otherKind.mainNum;
        }
        if(this.HIGH != otherKind.HIGH){
            return this.HIGH - otherKind.HIGH;
        }
        if(this.SECONDARY != otherKind.SECONDARY){
            return this.SECONDARY - otherKind.SECONDARY;
        }
        
        return 0;
        
    }
}
