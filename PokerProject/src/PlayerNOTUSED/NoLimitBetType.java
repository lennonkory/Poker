package PlayerNOTUSED;

/**
 * This Class controls how the no limit betting system works. 
 * @author Kory Bryson
 */
public class NoLimitBetType extends BetType {

    /**
     * Creates a NoLimitBetType
     * @param communicator the type of communicator the User is using.
     */
    public NoLimitBetType(Communicator communicator) {
        super(communicator);
    }

    /**
     *Creates a NoLimitBetType
     */
    public NoLimitBetType() {
        super();
    }

    //Maybe set min bet here?

    /**
     *Controls how the Player bets. As this is no limit the Player can bet has much as
     * they wish as long as it is above the min Bet. A Bet is only different from a raise as it 
     * is the first action involving chips in a round. This method maybe merged with bet.
     * @param bet The current bet object.
     * @return the modified Bet object.
     */
        @Override
    public int raise(Bet bet) {
        
        int intBet = 0;

        this.sendMessage("How much do you wish to raise?: ");

        String betAmount = this.getMessage();

        try {
            intBet = Integer.parseInt(betAmount);
            if (intBet > bet.getMaxPlayerBet()) {
                intBet = bet.getMaxPlayerBet();
            }
        } catch (NumberFormatException e) {
            intBet = 0;
        }

        return intBet;
    }

    /**
     * Controls how the Player bets. As this is no limit the Player can bet has much as
     * they wish as long as it is above the min Bet. A Bet is only different from a raise as it 
     * is the first action involving chips in a round. This method maybe merged with raise.
     * @param bet The current bet object.
     * @return the modified Bet object.
     */
    @Override
    public int bet(Bet bet) {

        int intBet = 0;

        this.sendMessage("How much do you wish to bet?: ");

        String betAmount = this.getMessage();

        try {
            intBet = Integer.parseInt(betAmount);
            if (intBet > bet.getMaxPlayerBet()) {
                intBet = bet.getMaxPlayerBet();
            }
        } catch (NumberFormatException e) {
            intBet = 0;
        }

        return intBet;

    }

}
