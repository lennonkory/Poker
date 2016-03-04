package Game;

import Controller.TableListener;
import Deck.*;
import HandRankings.Hand;
import HandRankings.RankHand;
import static java.lang.System.out;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kory
 */
public abstract class Game {

    public List<Player> players;//all players in the game.
    List<Player> playersInHand;//all players in the hand
    int playerTurn;//whos turn it is
    int dealer; //whos the dealer

    Deck deck;

    Player active;

    int currentBet = 0;
    int callNumber = 0;

    boolean handOver = false;
    
    public TableListener tl;

    GameInfo info; //pot size, min bets etc.

    public Game() {
        players = new ArrayList<>();
        playersInHand = new ArrayList<>();
        dealer = 0;
        info = new GameInfo();
        deck = new RegularDeck();

    }

    public Game(List<Player> players) {

        this.players = players;
        playersInHand = new ArrayList<>();

        dealer = 0;
        info = new GameInfo();
        deck = new RegularDeck();

    }

    public void addPlayer(Player p) {

        players.add(p);
    }

    public void setTableListener(TableListener tl) {
        this.tl = tl;
    }

    public void startGame() {

        //set players, betinfo
        //only called once
    }

    public abstract void command(Bet bet)throws NotEnoughCardsException;

    public int getNextActivePlayer(int start) {

        int size = playersInHand.size();
        int place = (start + 1) % size;

        for (int i = 0; i < size; i++) {
            if (playersInHand.get(place).isInHand()) {
                return place;
            }
            place++;
            place %= size;
        }

        return place;

    }

    public void setPlayersInHand() {

        playersInHand.clear();

        for (Player p : players) {
            if (p.isPlaying()) {
                p.setInHand(true);
                playersInHand.add(p);
            }
        }

    }

    public List<Boolean> getPlayerInHand() {

        List<Boolean> inHand = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            inHand.add(false);
        }

        for (Player p : players) {
            if (p.isPlaying()) {
                inHand.set(p.id, Boolean.TRUE);
            }
        }

        return inHand;
    }

    public abstract void getBlindsAnttes();

    public abstract void nextHand();
    
    public int playersStillInHand(){
        int num = 0;
        
        for(Player p : playersInHand){
            if(p.isInHand()){
                num++;
            }
        }

        return num;
    }

    public void printPlayers() {

        for (Player p : playersInHand) {
            p.printInfo();
        }

    }

    public void removePlayerFromHand(int num) {
        playersInHand.get(num).setInHand(false);
    }

    public void removePlayerFromHand(Player p) {
        playersInHand.get(p.id).setInHand(false);
    }

    public void updateState(Bet bet) {
        info.pot += bet.betAmount;
        changeState();
    }

    public abstract /*state*/ void changeState();

    public abstract void notifyPlayer();

    public void updatePlayer(String message) {
        active.updateView(message);
    }

    public void updateAllPlayers(String message) {
        playersInHand.stream().forEach((p) -> {
            p.updateView(message);
        });
    }

    public int declareWinners() {

        int size = playersInHand.size();
        Hand hands[] = new Hand[size];

        for (int i = 0; i < size; i++) {
            hands[i] = playersInHand.get(i).getHand();
        }

        List<Integer> winners = RankHand.compareHands(hands);

        if (winners.size() == 1) {
            Player p = playersInHand.get(winners.get(0));
            System.err.println(p.name + " WINS");
            p.addToChips(info.pot);
        } else {
            int win = winners.size();
            int share = (int) ((double) info.pot / (double) win);
            int mod = ((int) ((double) info.pot % (double) win));

            for (Integer i : winners) {
                Player p = playersInHand.get(winners.get(0));
                System.err.println(p.name + " WINS");
                p.addToChips(share);
            }
            
            //Give the odd chip to the first player in the list.
            playersInHand.get(winners.get(0)).addToChips(mod);
        }

        System.out.println("Pot: " + info.pot);
        
        this.printPlayers();
        
        return 0;
    }

    public void resetCalled() {
        for (Player p : playersInHand) {
            p.called = 0;
        }
    }

    public static void main(String[] args) {

        Game game = new Texas();
        game.setPlayersInHand();

        Scanner input = new Scanner(System.in);

        Bet bet = new Bet("fold", 10);

        game.printPlayers();

        game.nextHand();

        String command = null;

        do {
            System.out.print("Enter command: ");
            command = input.nextLine();
            if (command.equals("quit")) {
                break;
            }

            if (command.equals("bet")) {
                int b = Integer.parseInt(input.nextLine());
                bet.betAmount = b;
            }

            bet.choice = command;
            try {
                game.command(bet);
            } catch (NotEnoughCardsException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            game.printPlayers();
            System.out.println();

        } while (!command.equals("quit"));

    }

}
