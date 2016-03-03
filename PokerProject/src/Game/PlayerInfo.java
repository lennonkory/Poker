package Game;

import java.awt.Image;
import java.io.Serializable;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * @author kory
 */
public class PlayerInfo implements Serializable{

    private final String name;
    private int chips;
    private final int id;

    private Icon icon;
    private boolean inHand = true;

    public PlayerInfo(String name, int id, int chips) {
        this.name = name;
        this.id = id;
        this.chips = chips;
        
        Image i = new ImageIcon("resources/defaultIcon.png").getImage();    
        icon = new ImageIcon(i.getScaledInstance(60, 60, Image.SCALE_DEFAULT));
        
    }
    
    public PlayerInfo() {
        this.name = "   ";
        this.id = -1;
        this.chips = 0;
        
        Image i = new ImageIcon("resources/defaultIcon.png").getImage();
        i = i.getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        icon = new ImageIcon(i);
        
    }

    @Override
    public String toString() {
        return "PlayerInfo{" + "name=" + name + ", chips=" + chips + ", id=" + id + ", icon=" + icon + ", inHand=" + inHand + '}';
    }
  
    public boolean isInHand() {
        return inHand;
    }

    public void setInHand(boolean inHand) {
        this.inHand = inHand;
    }
        
    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public int getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    public String getName() {
        return name;
    }

}
