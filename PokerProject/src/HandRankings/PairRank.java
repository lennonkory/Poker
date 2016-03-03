package HandRankings;

import Deck.StandardCard.*;
import static HandRankings.Rank.PAIR;
import java.util.List;

/**
 *
 * @author Kory Bryson
 */
public class PairRank extends HandValue {

    private final List<Value> pairs;
    private final List<Value> cards;

    public PairRank(List<Value> pairs, List<Value> cards) {
        super(PAIR);
        this.cards = cards; //remaining cards not including pairs
        this.pairs = pairs;
    }

    @Override
    public int compareTo(Object o) {

        HandValue other = (HandValue) o;

        if (this.getRank() != other.getRank()) {
            return getRank().ordinal() - other.getRank().ordinal();
        }

        PairRank otherTwo = (PairRank) other; //Has to be pair at this point

        //Compare  pair.
        int onePair = this.getPairValue();
        int twoPair = otherTwo.getPairValue();

        if (onePair != twoPair) {
            return onePair - twoPair;
        }

        for (int i = 0; i < cards.size(); i++) {
            int cValue = cards.get(i).ordinal();
            int otherValue = otherTwo.cards.get(i).ordinal();
            if (cValue != otherValue) {
                return cValue - otherValue;//return the first insatance of one card being bigger
            }
        }

        return 0;
    }

    /**
     * Returns the Value of a paint as an int. pair number is 1 or 2;
     *
     * @return
     */
    public int getPairValue() {

        return pairs.get(0).ordinal();
    }

}
