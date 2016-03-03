package GUI;

import Controller.InputListener;
import Controller.PlayerListener;
import Game.PlayerInfo;
import java.util.List;

/**
 * @author kory
 */
public abstract class View {

    PlayerListener vl;
    InputListener il;

    public PlayerInfo players[];

    String type;

    public View(String type) {
        this.type = type;
        players = new PlayerInfo[9];
        
        for(int i = 0 ; i < 9; i++){
            players[i] = new PlayerInfo();
        }
        
    }

    public abstract void updateView(String message);

    public abstract void updateButtons(int call, int bet);

    public abstract void updatePlayer(PlayerInfo info);
    
    public abstract void updatePlayerInfo(PlayerInfo info);

    public abstract void updatePlayer();

    public abstract void dealCards(int start, List<Boolean> players);

    public abstract void dealFlop(String[] cards);

    public abstract void dealTurn(String card);

    public abstract void dealRiver(String card);

    public abstract void clearCards();

    public abstract void viewCards(int id, String cards[]);

    public abstract void removeCards(int id);

    public abstract void setListener();

    public abstract void setTable();
    
    public abstract void updatePot(int pot);
    
    public abstract void updatePlayerList(PlayerInfo info[]);

    public void addPlayer(PlayerInfo p) {
        players[p.getId()] = p;
    }

    public void addViewListener(PlayerListener vl) {
        this.vl = vl;
    }

    public PlayerListener getViewListener() {
        return vl;
    }

    public void addInputListener(InputListener il) {

        if (il == null) {
            System.err.println("Adding null listener");
        }

        this.il = il;
      
    }

    public String getType(){
        return type;
    }
    
}
