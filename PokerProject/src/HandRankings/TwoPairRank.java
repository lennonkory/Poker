package HandRankings;

import Deck.StandardCard.*;
import static HandRankings.Rank.TWOPAIR;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kory Bryson
 */
public class TwoPairRank extends HandValue {

    private final List<Value> pairs;
    private final List<Value> cards;

    public TwoPairRank(List<Value> pairs, List<Value> cards) {
        super(TWOPAIR);
        this.cards = cards; //remaining cards not including pairs
        this.pairs = pairs;
    }

    @Override
    public int compareTo(Object o) {

        HandValue other = (HandValue) o;

        if (this.getRank() != other.getRank()) {
            return getRank().ordinal() - other.getRank().ordinal();
        }

        TwoPairRank otherTwo = (TwoPairRank) other; //Has to be twopair at this point

        try {
            //Compare first pair.
            int onePair = this.getPairValue(0);
            int twoPair = otherTwo.getPairValue(0);

            if (onePair != twoPair) {
                return onePair - twoPair;
            }
            //Compare second pair
            onePair = this.getPairValue(1);
            twoPair = otherTwo.getPairValue(1);
            if (onePair != twoPair) {
                return onePair - twoPair;
            }
        } catch (InvalidPairNumberException ex) {
            Logger.getLogger(TwoPairRank.class.getName()).log(Level.SEVERE, null, ex);
        }

        int cValue = cards.get(0).ordinal();
        int otherValue = otherTwo.cards.get(0).ordinal();
        return cValue - otherValue;//return the first insatance of one card being bigger

    }

    /**
     * Returns the Value of a paint as an int. pair number is 1 or 2;
     *
     * @param pairNumber
     * @return
     * @throws HandRankings.InvalidPairNumberException
     */
    private int getPairValue(int pairNumber) throws InvalidPairNumberException {
        if (pairNumber < 0 && pairNumber > 1) {
            throw new InvalidPairNumberException();
        }
        return pairs.get(pairNumber).ordinal();
    }

}
