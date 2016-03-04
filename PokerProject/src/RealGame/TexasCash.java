package RealGame;

import Player.InvalidBetException;
import Player.Player;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kory Bryson
 */
public class TexasCash extends Texas{

    TexasCash(List<Player> players) { 
        super(players);
        this.setAntee(0);
        this.setSmallBlind(10);
    }

    @Override
    public void start() {
        
        this.setButton(0);
      
        this.nextHand();
        
        //define blinds and antees?
        
    }

    @Override
    public void nextHand() {
        
        this.resetPlayers();
        this.deck.shuffle();
        Round round = new Round(this.playerList);

        try {
            this.preflop(round);
        } catch (InvalidBetException ex) {
            Logger.getLogger(TexasCash.class.getName()).log(Level.SEVERE, null, ex);
        }
         try {
        round.resetCallNum();
        this.flop(round);
        } catch (InvalidBetException ex) {
        Logger.getLogger(TexasCash.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }


}
