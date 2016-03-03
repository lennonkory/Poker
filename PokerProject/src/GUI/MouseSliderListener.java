package GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 * @author kory
 */
public class MouseSliderListener implements MouseListener{

    JPanel p;
    
    public MouseSliderListener(JPanel p){
        this.p = p;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
       // p.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //p.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        p.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        p.repaint();
    }

}
