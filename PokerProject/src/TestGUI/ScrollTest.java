package TestGUI;

import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 * @author kory
 */
public class ScrollTest extends JFrame {
    
    int count = 0;
    
    public ScrollTest(){
        super();
        this.setSize(600, 400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JTextArea area = new JTextArea(5,15);
        JScrollPane pane = new JScrollPane(area);
        
        JButton b = new JButton("Press");
        
        b.addActionListener((ActionEvent e)->{
            area.append("Count " + count + "\n");
            count++;
        });
        
        JPanel panel = new JPanel();
        
        panel.add(pane);
        panel.add(b);
        
        this.add(panel);
        
        this.pack();
        
        this.setVisible(true);
    }
    
    public static void main(String [] args){
    
        new ScrollTest();
        
    }

}
