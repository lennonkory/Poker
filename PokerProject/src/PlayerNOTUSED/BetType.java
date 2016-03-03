package PlayerNOTUSED;

import static PlayerNOTUSED.Choice.*;


/**
 * Interface for the two different types of betting: No limit and Limit.
 *
 * @author Kory Bryson
 */
public abstract class BetType {

    private Communicator communicator = null;

    /**
     * Creates a new BetType.
     * @param communicator the communicator used to interact with the user.
     */
    public BetType(Communicator communicator) {
        this.communicator = communicator;
    }

    /**
     *Creates a new BetType.
     */
    public BetType() {
        communicator = null;
    }

    /**
     * Sets the communicator used in raise and bet. If communicator is already
     * set returns null.
     *
     * @param communicator
     */
    public void setCommunicator(Communicator communicator) {
        if (this.communicator != null) {
            return;
        }
        this.communicator = communicator;
    }

    /*All methods return the amount that a player is betting*/
    /*A bet is the first no check action in a round. A raise is any bet that is not a call or bet*/

    /**
     *  Controls how the {@link Player} can raise.
     * @param bet the current Bet object.
     * @param called Amount the player has already called.
     * @return the amount called.
     * @see Bet
     * @see Player
     */
    
    public int call(Bet bet, int called) {
        
        communicator.sendMessageln("Player calls");
        
        int result = bet.getCurrentBet() - called;
        System.out.println("CB: " + bet.getCurrentBet() + " Called: " + called);
        return result;
    }

    /**
     * Controls how the {@link Player} can raise.
     * @param bet the current bet object.
     * @return the amount raised.
     * @see Bet
     * @see Player
     */
    public abstract int raise(Bet bet);

    /**
     * Controls how the {@link Player} can bet.
     * @param bet the current bet object.
     * @return the amount bet.
     */
    public abstract int bet(Bet bet);

    /*This method should update the GUI so it only shows valid options*/
    private String[] displayChoices(Bet bet, int called) {
        
        /*Based on whats in bet*/

        String options = "(F)old";

        String[] choices = new String[3];
        
        choices[0] = "f";
        choices[1] = "c";

        if (bet.getCurrentBet() == 0 || bet.getCurrentBet() == called) {
            options += ",(Ch)eck,(B)et: ";
            choices[1]+="h";
            choices[2] = "b";
        } else {
            options += ",(C)all,(R)aise: ";
            choices[2] = "r";
        }

        communicator.sendMessage(options);

        return choices;

    }

    private boolean validateChoice(String[] options, String choice) {

        for (String i : options) {
            if (i.equalsIgnoreCase(choice)) {
                return true;
            }
        }
        
        this.sendMessageln("Invalid Choice please pick again");

        return false;

    }

    /**
     * Controls the turn for the type for the user.
     * @param bet the current bet object.
     * @param called how much the player has already called.
     * @return a modified bet object.
     * @throws InvalidBetException
     */
    public Bet takeTurn(Bet bet, int called) throws InvalidBetException {

        String choices[] = displayChoices(bet, called);

        int betAmount = 0;
        
        String ch;
        
        do {

            ch = communicator.getMessage();

            System.out.println("Choice: " + ch);

            if (ch.equalsIgnoreCase("B")) {
                betAmount = bet(bet);
                bet.setCurrentBet(betAmount);
                bet.setChoice(BET);  
            } 
            else if (ch.equalsIgnoreCase("C")) {
                betAmount = call(bet, called);
                //bet.setCurrentBet(betAmount + called);
                bet.setChoice(CALL);
            }
            else if (ch.equalsIgnoreCase("CH")) {
                bet.setCurrentBet(called);
                bet.setChoice(CHECK);
            }
            else if (ch.equalsIgnoreCase("F")) {
                bet.setChoice(FOLD);  
            }
            else if (ch.equalsIgnoreCase("R")) {
                betAmount = bet(bet);
                bet.setCurrentBet(betAmount);
                bet.setChoice(RAISE);  
            } 
            else {
                System.err.println("Not supported yet");
            }

        }while(!validateChoice(choices, ch));
  
        return bet;

    }

    /**
     * Uses Communicator to send a message. The message will have a newline added at the end of it.
     * @param message The message to be sent.
     */
    public void sendMessageln(String message) {
        communicator.sendMessageln(message);
    }

    /**
     * Uses {@link Communicator} to send a message.
     * @param message The message to be sent.
     */
    public void sendMessage(String message) {
        communicator.sendMessage(message);
    }

    /**
     * Gets a message from the User.
     * @return the message from the User.
     * @see Communicator
     */
    public String getMessage() {
        return communicator.getMessage();
    }

}
