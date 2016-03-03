package GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * @author kory
 */
public class CardListener implements MouseListener{
    
    private final CardPanel card;
    
    public CardListener(CardPanel c){
        card = c;
    }
  
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        card.viewFaces();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        card.viewBack();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       
    }

    @Override
    public void mouseExited(MouseEvent e) {
        card.viewBack();
    }


}
