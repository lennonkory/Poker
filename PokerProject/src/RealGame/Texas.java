package RealGame;

import Deck.Card;
import HandRankings.TexasHand;
import PlayerNOTUSED.Bet;
import PlayerNOTUSED.InvalidBetException;
import PlayerNOTUSED.Player;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kory Bryson
 */
public abstract class Texas extends Game{

    private List<Card> flop;
    private Card turn;
    private Card river;
    
    Texas(List<Player> players) {
        super(players);
    }
  
    private int getBlindsAndAntees(){
        
        int smallBlind = (this.getButton()+ 1) % this.playerList.size();
        int bigBlind = (this.getButton()+ 2) % this.playerList.size();

        int small = this.getSmallBlind();
        int big = small * 2;
        
        //Gets the blinds from the players and sets what they have already called.
        int pot = playerList.get(smallBlind).removeChips(small);
        playerList.get(smallBlind).setCalled(small);
        playerList.get(smallBlind).addToRound(small);
        
        pot = playerList.get(bigBlind).removeChips(big);
        playerList.get(bigBlind).setCalled(big);
        playerList.get(bigBlind).addToRound(big);
        
        if(this.getAntee() > 0){
            for(Player p : this.playerList){
                pot += p.removeChips(this.getAntee());
            }
        }
        return pot;
    }
    
    public void preflop(Round round) throws InvalidBetException{
    
        getBlindsAndAntees();

        round.setPreFlopOrder();
        dealPreFlop();

        Bet b = new Bet(this.getSmallBlind()*2,this.getSmallBlind()*2,100);
        b.printBet();
        this.turn(b, round);
        
    }
    
    public void flop(Round round) throws InvalidBetException{
        System.out.println("???");
        dealFlop();
        Bet b = new Bet(this.getSmallBlind()*2,0,100);
        this.turn(b, round);
    }
    
    public void turnRiver(){}

    private void dealPreFlop(){
        
        this.playerList.stream().filter((p) -> (p.isInHand())).forEach((p) -> {
            p.setHand(new TexasHand());
            p.addCard(this.deck.dealCard());
        });
        
        this.playerList.stream().filter((p) -> (p.isInHand())).forEach((p) -> {
            p.addCard(this.deck.dealCard());
            p.setCards();
        });
        
    }
    
    private void dealFlop(){
        flop = new ArrayList<>(deck.dealCards(3));
        
        this.playerList.stream().filter((p)->(p.isInHand())).forEach((p)->{
            p.addCards(flop);
        });
        
    }
    
}
