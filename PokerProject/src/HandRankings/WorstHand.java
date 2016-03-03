package HandRankings;

import java.util.List;

/**
 *
 * @author Kory Bryson
 */
public class WorstHand extends HandValue{

    public WorstHand(Rank rank, List highCards) {
        super(rank, highCards);
    }

    @Override
    public int compareTo(Object o) {
        return -10000;
    }

}
