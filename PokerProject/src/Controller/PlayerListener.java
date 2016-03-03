package Controller;

import Game.PlayerInfo;

public interface PlayerListener {

    public void update(String message);

    public void updateButtons(int call, int raise);
 
    public void viewCards(int id, String cards[]);
    
    public void activatePlayer(PlayerInfo info);
    
    public void updatePlayer(PlayerInfo info);

}
