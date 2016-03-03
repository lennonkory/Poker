package PlayerNOTUSED;

import Deck.Card;
import HandRankings.Hand;
import static PlayerNOTUSED.Choice.FOLD;
import java.util.Collection;

/**
 * This class controls the Player. Note that Player is a Server side
 * Object. GUI, Socket and Text are all done through Communicator. Any AI will be done through betType.
 * @author Kory Bryson
 */
public class Player {

    private boolean inHand; //IS the player involved in the current hand.
    private int chips; //number of chips the player has at the table.
    private int called; //Number of chips the Player has already called in the current Round.
    private int chipsInRound;//how many chips the player has in the current ROUND
    private final String name;
    
    private final BetType betType;
    private final Communicator com;
    private Hand hand;

    /**
     * Player Constructor. A player must be created this way,. A Player is a server side Object.
     * Only the server interacts with the Player.
     * @param name Name of the Player
     * @param chips How many chips they have.
     * @param betType The Players bet type. Can be no limit or limit.
     * @param communicator How the Server communicates with a User.
     * @see BetType
     * @see Communicator
     */
    public Player(String name, int chips, BetType betType, Communicator communicator) {
        
        this.name = name;
        this.chips = chips;
        this.betType = betType;
        this.com = communicator;
        this.chipsInRound = 0;
        this.betType.setCommunicator(this.com);
        
        inHand = true;
        called = 0;
    }
    
    /**
     *
     * @param name
     */
    public Player(String name){
        this.name = name;
        this.chips = 100;
        this.betType = new NoLimitBetType();
        this.com = new TextCommunicator();
        this.chipsInRound = 0;
        this.betType.setCommunicator(this.com);
    }

    /**
     *Sets the players Hand. This needs to be set each round.
     * @param h The hand type to be used. Needs to be set each round.
     */
    public void setHand(Hand h){
        hand = h;
    }
    
    public void addCard(Card c){
        this.hand.addCard(c);
    }
    
    public void addCards(Collection<Card> card){
        card.stream().forEach((c) -> {
            this.hand.addCard(c);
        });
    }
    
    public void printHand(){
        this.hand.printHand();
    }
    
    /**Sets whole cards*/
    public void setCards(){
        hand.setCards();
    }
    
    /**
     * Allows the player to take a turn. Most of the work is done in BetType.
     * BetType will control how much the user can bet and how they can bet.
     * Turn will then make the correct modifications to the Players chips.
     * @param bet The current Bet object.
     * @return A modified Bet object.
     * @throws Player.InvalidBetException
     * @see Communicator
     */
    public Bet turn(Bet bet) throws InvalidBetException {
        
        com.sendMessageln(this.name + " it is Your turn. What do you wish to do: ");
        
        bet.setMaxPlayerBet(this.chips);
        
        bet = betType.takeTurn(bet,called);
        
        if(bet.getChoice() != FOLD){

            int amount = this.removeChips(bet.getCurrentBet() - called);
            this.addToRound(amount);
            this.called = amount + called;

            bet.setAcutalPlayerBet(called);

            if(this.called < bet.getCurrentBet() && this.called > bet.getHighestCalled()){
                bet.setHighestCalled(this.called);
            }
        }
        
        com.sendMessageln("Turn Over");//muse be sent inorder to tell the player theri turn is overr
        
        return bet;
    }

    /**
     * Prints the Players current status. Prints out Chips, current bet, actual bet and so on.
     */
    public void printStatus(){
        System.out.println("***************");
        System.out.println("Name: " + name);
        System.out.println("Chips: " + chips);
        System.out.println("Called: " + called);
        System.out.println("Current Round Chips: " + this.chipsInRound);
        System.out.println("***************");
    }
    
    /**
     * Sets the amount the player has called in this Round.
     * @param call the amount to set call to.
     */
    public void setCalled(int call) {
        this.called = call;
    }

    /**
     * Returns how much a player has called.
     * @return how much a player has called.
     */
    public int getCalled() {
        return called;
    }

    /**
     * Checks if the player is in the hand
     *
     * @return true of the Player is in the hand. False otherwise.
     */
    public boolean isInHand() {
        return inHand;
    }

    /**
     * Sets if the player is still in the hand.
     * @param inHand sets inHand.
     */
    public void setInHand(boolean inHand) {
        this.inHand = inHand;
    }

    /**
     * Returns the number of chips a player has.
     *
     * @return the number of chips in a Players hand.
     */
    public int getChips() {
        return chips;
    }

    /**
     * Set the number chips a Player has. Should only be used if the Player is buying back in.
     * @param chips The amount to set the Players chips to.
     */
    public void setChips(int chips) {
        this.chips = chips;
    }
    
    /**
     * Adds chips to the player. There is no limit to the number of chips a player can have.
     * @param toAdd The amount added to the Players chips.
     */
    public void addToChips(int toAdd){
        this.chips += toAdd;
    }

    /**
     * Removes chips from a player. A player can not be left with negative chips.
     * If the the number of chips to remove is greater than the total chips the player has
     * The players chips are set to 0 and the number of remain chips are returned.
     * @param toRemove Th amount to remove from the Players chips.
     * @return The amount of chips removed. Will be equal to toRemove unless
     * removing that many chips would result in a negative number.
     */
    public int removeChips(int toRemove){
        
        int result = chips - toRemove;
        
        if(result < 0){
            result = chips;
            chips = 0;
        }
        else{
            chips -= toRemove;
            result = toRemove;
        }
        return result;
    }
    
    /**
     *
     * @param currentBet
     */
    public void addToRound(int currentBet) {     
        this.chipsInRound += currentBet;
    }
    
    /**
     * Returns money to a player if their bet was not covered. This method will be moved into the 
     * game engine.
     * @param players list of Players in the hand
     * @param b the current bet object
     * @param pot the size of the pot.
     * @return The amount of money in the pot.
     */
    public static int returnMoney(Player players[], Bet b,int pot){

        for(Player p : players){
            if(p.called > b.getHighestCalled()){
                int toAdd = p.called - b.getHighestCalled();
                System.out.println("toAdd: " + toAdd);
                p.addToChips(toAdd);
                pot -= toAdd;
            }
        }
        
        return pot;
        
    }
    
    /**
     * Main
     * @param args Nothing
     * @throws Player.InvalidBetException
     */
    public static void main(String []args) throws InvalidBetException{
        
        Player p = new Player("Kory",40,new NoLimitBetType(), new TextCommunicator());
        Bet b = new Bet(0,10,-1);
        
        int pot = 0;
        
        b = p.turn(b);
        
        pot += b.getAcutalPlayerBet();
      
        Player p2 = new Player("Bob",15,new NoLimitBetType(), new TextCommunicator());
   
        b = p2.turn(b);
        pot += b.getAcutalPlayerBet();

        Player p3 = new Player("John",20,new NoLimitBetType(), new TextCommunicator());
      
        b = p3.turn(b);
        pot += b.getAcutalPlayerBet();
    
        
        Player players[] = new Player[3];
        
        players[0] = p;
        players[1] = p2;
        players[2] = p3;
        
        pot = returnMoney(players,b,pot);
        
        System.out.println("Pot: " + pot + "\n");
        
        for(Player i : players){
            i.printStatus();
        }
        
    }

}
