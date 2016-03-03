package GUI;

import Game.PlayerInfo;
import java.util.List;

/**
 * @author kory
 */
public class TextView extends View{
    
    public TextView(){
        super("TEXT");
    }
    
    @Override
    public void updateView(String message) {
        
    }

    @Override
    public void updatePlayer(PlayerInfo info) {
    }

    @Override
    public void updateButtons(int call, int bet) {
        System.out.println(call + " " + bet);
    }

    @Override
    public void setListener() {
    }

    @Override
    public void dealFlop(String[] cards) {
        for(String c : cards){
            System.out.print(c + " ");
        }
        System.out.println();
    }

    @Override
    public void dealTurn(String card) {
        System.out.println(card);
    }

    @Override
    public void dealRiver(String card) {
        System.out.println(card);
    }

    @Override
    public void clearCards() {
    }

    @Override
    public void dealCards(int start, List<Boolean> players) {
    }

    @Override
    public void viewCards(int id, String[] cards) {
        for(String c : cards){
            System.out.print(c + " ");
        }
        System.out.println();
    }

    @Override
    public void updatePlayer() {
    }

    @Override
    public void removeCards(int id) {
    }

    @Override
    public void setTable() {
    }

    @Override
    public void updatePot(int pot) {
    }

    @Override
    public void updatePlayerList(PlayerInfo[] info) {
    }

    @Override
    public void updatePlayerInfo(PlayerInfo info) {
    }

}
