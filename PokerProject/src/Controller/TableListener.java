package Controller;

import java.util.List;

/**
 *
 * @author kory
 */
public interface TableListener {
    
    public void update(String message);

    public void dealFlop(String cards[]);

    public void dealPreflop(int start, List<Boolean> inhand);

    public void dealTurn(String card);

    public void dealRiver(String card);

    public void updatePot(int pot);
    
    public void clearCards();
    
    public void removeCards(int id);
    
    public String getType();
    
    public void setPlayerInfo();


}
