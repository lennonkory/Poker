package PlayerNOTUSED;

import static PlayerNOTUSED.Choice.*;


/**
 *The Bet object contains useful information about the current state of a ROUND. If contains information
 * about how much as been bet, how much as been called an the currents Players action.
 * @author Kory
 */
public class Bet {

    private int currentBet;
    private int highestCalled;//Only differs from current bet if Player couldn't cover total amount.
    private int acutalPlayerBet;
    private final int min;
    private final int max;
    private int maxPlayerBet; //The max the player can bet
    private Choice choice;

    /**
     * Creates a Bet object. A new Bet object should created EVERY HAND. and
     * used throughout a single hand.
     *
     * @param cb Current bet in the round. Starts at zero
     * @param min min bet in a HAND
     * @param max max bet in a HAND
     */
    public Bet(int cb, int min, int max) {
        this.currentBet = cb;
        this.highestCalled = cb;
        this.min = min;
        this.max = max;
        this.choice = FOLD;
    }

    /**
     * Returns the current bet.
     * @return the current bet size.
     */
    public int getCurrentBet() {
        return this.currentBet;
    }

    /**
     * Returns the min bet allowed. 
     * Note: min should only bet modified at the start of a new hand.
     * @return The min bet allowed.
     */
    public int getMinBet() {
        return this.min;
    }

    /**
     * Returns the max bet allowed. This will be -1 in no limit holdem.
     * Note: max should only bet modified at the start of a new hand.
     * @return The max amount a Player can bet.
     */
    public int getMaxBet() {
        return this.max;
    }

    /**
     * Returns the choice the player made.
     *
     * @return The action the player took.
     * @see Choice
     */
    public Choice getChoice() {
        return this.choice;
    }

    /**
     * Sets the players Choice.
     *
     * @param choice the choice the player makes.
     * @see Choice
     */
    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    /**
     * Sets the currentBet.
     * @param bet The amount a Player has bet.
     * @throws Player.InvalidBetException
     */
    public void setCurrentBet(int bet) throws InvalidBetException {
        if (bet < min) {
            throw new InvalidBetException("Bet must be at least the size of the min bet");
        }
        this.currentBet = bet;
    }

    /**
     * The max a Player can bet. This will be the number of chips the Player has if they are playing no limit.
     * @return the the max a Player can bet.
     */
    public int getMaxPlayerBet() {
        return maxPlayerBet;
    }

    /**
     * Sets the max a Player can bet.
     * @param maxPlayerBet the maxPlayerBet to set
     */
    public void setMaxPlayerBet(int maxPlayerBet) {
        this.maxPlayerBet = maxPlayerBet;
    }

    /**
     * Returns the highest bet that a Player called if they couldn't cover the full bet.
     * @return the highestCalled
     */
    public int getHighestCalled() {
        return highestCalled;
    }

    /**
     * Sets the highest bet a Player could call.
     * @param highestCalled the highestCalled to set
     */
    public void setHighestCalled(int highestCalled) {
        this.highestCalled = highestCalled;
    }

    /**
     * Prints the bet.
     */
    public void printBet() {

        System.out.println("Bet: ");
        System.out.println("Current Bet: " + this.currentBet);
        System.out.println("Highest Bet: " + this.highestCalled);
        System.out.println("Actual Player Bet: " + this.acutalPlayerBet);
        System.out.println("**************");
    }

    /**
     * The actual amount the Player bet that round.
     * @return the amount the player bet.
     */
    public int getAcutalPlayerBet() {
        return acutalPlayerBet;
    }

    /**
     * Sets the actual amount the player bet.
     * @param acutalPlayerBet the amount the Player bet.
     */
    public void setAcutalPlayerBet(int acutalPlayerBet) {
        this.acutalPlayerBet = acutalPlayerBet;
    }

}
