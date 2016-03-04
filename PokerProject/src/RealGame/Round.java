package RealGame;

import Player.Player;
import java.util.*;

/**
 * Controls Players for each round. Only players that are in
 * the hand are in the Round. A round will always start with all active players.
 * @author Kory Bryson
 */
public class Round {

    private List<Player> players;
    private int callNum;
    
    /**
     *Creates a new Round.
     * @param players
     */
    public Round(List<Player> players){
        
        this.players = new ArrayList<>();
        
        for(Player p : players){
            this.players.add(p);
        }
        callNum = 0;
    }
    
    /**
     *Removes a Player from the list. This only happens if they fold.
     * @param player the Player to be removed
     */
    public void removePlayer(Player player){
        getPlayers().remove(player);
    }
    
    /**
     * Removes a Player from the list at a certain index. This only happens if
     * they fold.
     * @param index
     */
    public void removePlayerAt(int index){
        getPlayers().remove(index);
    }

    public void removePlayers(List<Player> toRemove){
        toRemove.stream().forEach((p) -> {
            getPlayers().remove(p);
        });
    }
    
    /**
     * Returns the list of Players still in the Hand.
     * @return the list of Players still in the round.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @param players the players to set
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     * Returns callNum. Call number is the number of players that
     * have called the hand.
     * @return the callNum
     */
    public int getCallNum() {
        return callNum;
    }
    
    /**
     *Adds 1 to callNum if callNum is less than the 
     * size of the player list.
     */
    public void addToCallNum(){
        if(this.callNum < players.size()){
            this.callNum++;
        }
    }
    
    /**
     * Resets callNum to 0. Should only be used at the beginning 
     * of each turn.
     */
    public void resetCallNum(){
        callNum = 0;
    }
    
    /**
     * Determines if the turn is over.
     * @return true if the turn is over, false otherwise
     */
    public boolean turnOver(){
        return callNum == players.size();
    }
    
    public void setPreFlopOrder(){
        
        for(int i = 0; i < 3; i++){
            Player p = this.players.remove(0);
            this.players.add(p);
        }
        
    }
    
}
