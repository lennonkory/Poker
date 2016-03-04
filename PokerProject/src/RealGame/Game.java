package RealGame;

import Player.Bet;
import Player.InvalidBetException;
import Player.Player;
import Deck.Deck;
import Deck.RegularDeck;
import static Player.Choice.*;
import java.util.*;

/**
 * Abstract class that controls how the Game is played.
 * @author Kory Bryson
 */
public abstract class Game {
    
    private int button;//Whos dealing
    private int smallBlind;
    private int antee;
    private int pot;
    public final Deck deck;
    
    /**
     *A list of Players.
     */
    protected List<Player> playerList;
    
    /**
     *Creates a Game. Games should only be created using the GameFactory.
     */
    public Game(){
        playerList = new ArrayList<>();
        deck = new RegularDeck();
    }
    
    /**
     *Creates a Game. Games should only be created using the GameFactory.
     * @param playerList a list of Players to join the game.
     */
    public Game(List<Player> playerList){
        this.playerList = playerList;
        deck = new RegularDeck();
    }
    
    /**
     * Starts the game.
     */
    public abstract void start();
    
    /**
     *This method sets up the next hand. A new Bet object should be created.
     * All players previous bets should be set to 0.
     */
    public abstract void nextHand();
 
    public Bet turn(Bet bet,Round round) throws InvalidBetException{
    
        //This loop keeps running intil the turn is over. Either everyone 
        //has called or only one player is left (everyone else folded);
        List<Player> toRemove = new ArrayList<>();
        
        do{
            round.removePlayers(toRemove);
            for(Player p : round.getPlayers()){
                if(round.turnOver()){
                    System.out.println("Breaking");
                    break;
                }
                p.printHand();
                bet = p.turn(bet);
                
                if(bet.getChoice() == BET || bet.getChoice() == RAISE){
                    round.resetCallNum();
                    round.addToCallNum();
                }
                else if(bet.getChoice() == CALL  || bet.getChoice() == CHECK){
                    round.addToCallNum();
                }
                else if(bet.getChoice() == FOLD){
                    toRemove.add(p);
                    round.addToCallNum();
                }
                System.out.println();
            }
        }while(!round.turnOver());
        
        return bet;
        
    }
    
    /**
     *This method decides who the winners are and allocates chips to the winner(s).
     */
    public void declareWinners(){}
    
    /**
     * Adds a player to the Game.
     * @param player the Player to be added.
     * @see Player
     */
    public void addPlayer(Player player){
        playerList.add(player);
    }
    
    /**
     * Removes a player from the Game. Down when the player decides to leave the table.
     * @param player
     */
    public void removePlayer(Player player){
        playerList.remove(player);
    }
    
    /**
     *Resets bets and Players for the next hand.
     */
    public void resetPlayers(){
        for(Player p : playerList){
            if(p.getChips() > 0){
                p.setInHand(true);
                p.setCalled(0);
            }
        }
    }

    /**
     * Returns who is dealing.
     * @return the button
     */
    public int getButton() {
        return button;
    }

    /**
     * @param button the button to set
     */
    public void setButton(int button) {
        this.button = button;
    }

    /**
     * @return the smallBlind
     */
    public int getSmallBlind() {
        return smallBlind;
    }

    /**
     * @param smallBlind the smallBlind to set
     */
    public void setSmallBlind(int smallBlind) {
        this.smallBlind = smallBlind;
    }

    /**
     * @return the antee
     */
    public int getAntee() {
        return antee;
    }

    /**
     * @param antee the antee to set
     */
    public void setAntee(int antee) {
        this.antee = antee;
    }

    /**
     * @return the pot
     */
    public int getPot() {
        return pot;
    }

    /**
     * @param pot the pot to set
     */
    public void setPot(int pot) {
        this.pot = pot;
    }
    
    /**
     *Removes the first player on the top of the list to the back
     * of the list.
     */
    public void getNextList(){
        
        Player p = this.playerList.remove(0);
        this.playerList.add(p);
 
    }
  
    public void printPlaysStatus(){
        for(Player p : playerList){
            p.printStatus();
        }
    }
    
    public static void main(String [] args){
        
        List<Player> players = new ArrayList<>();
        
        players.add(new Player("Kory"));
        players.add(new Player("Bob"));
        players.add(new Player("Andy"));
        players.add(new Player("Mike"));
        //players.add(new Player("Jess"));
        
        Game game = new TexasCash(players);
        //game.printPlaysStatus();
        game.start();
        game.printPlaysStatus();
        
    }
    
}
