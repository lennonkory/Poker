package Game;

import Controller.PlayerListener;
import Deck.Card;
import HandRankings.Hand;
import java.util.Collection;

/**
 *
 * @author kory
 */
public class Player {

    int chips;
    public String name;
    int called = 0;
    public final int id;
    
    private PlayerListener view;
    private Hand hand;
    
    private boolean inHand;

    public Player(String name, int id) {
        chips = 100;
        this.name = name;
        this.id = id;
        this.hand = new Hand();
        inHand = true;

    }
    
    /**
     *Sets the players Hand. This needs to be set each round.
     * @param h The hand type to be used. Needs to be set each round.
     */
    public void setHand(Hand h){
        hand = h;
    }

    public boolean isInHand() {
        return inHand;
    }

    public void setInHand(boolean inHand) {
        this.inHand = inHand;
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
    
    public PlayerListener getViewListener(){
        return this.view;
    }

    public boolean isPlaying() {
        return true;
    }

    public int removeChips(int toRemove) {

        int removed = toRemove;

        if (chips - toRemove < 0) {
            removed = chips;
            chips = 0;
        } else {
            chips -= toRemove;

        }

        return removed;

    }
    
    public void addToChips(int toAdd){
        this.chips += toAdd;
    }

    public void printInfo() {
        System.out.println("Name: " + name + " Chips: " + chips + " called: " + called);
    }

    public void updateView(String message) {
        view.update(message);
    }
    
    public void updateView(int call, int bet) {
        view.updateButtons(call, bet);
    }
    
    public void activate(){
         view.activatePlayer(getInfo());
     }
    
    public void updatePlayerInfo(){
        
    }
      
    public void viewCards(int id, String cards[]){
        view.viewCards(id, cards);
    }

    public void setViewListener(PlayerListener vl) {
        this.view = vl;
    }

    public int called(int amount) {
        int removed = removeChips(amount - called);
        called += removed;
        return removed;
    }
    
    public Hand getHand(){
        return hand;
    }

    public PlayerInfo getInfo(){
        return new PlayerInfo(this.name,this.id, this.chips);
    }
    
    public static void main(String[] args) {

        Player p = new Player("Kory",0);
        Bet bet = new Bet("call", 110);
        p.printInfo();

        System.out.println("Bet: " + p.called(110));

        p.printInfo();

    }

    

}
