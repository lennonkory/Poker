package GUI;

import Controller.InputListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;
import javax.swing.event.ChangeEvent;

/**
 * @author kory
 */
public class ButtonPanel extends JPanel implements ActionListener {

    private final JSlider slider;
    private JButton fold;
    private JButton call;
    private JButton bet;

    private InputListener il;

    public ButtonPanel() {
        
        super();
        slider = new JSlider(0, 100, 0);
        setSlider();

    }

    public ButtonPanel(InputListener il) {
        super();

        slider = new JSlider(0, 100, 0);
        setButtons();
        setSlider();
        this.il = il;
   
    }

    private void setSlider() {

        slider.setMajorTickSpacing(25);
        slider.setMinorTickSpacing(5);

        slider.setPaintTicks(true);
        slider.setPaintTrack(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);

        //this.setOpaque(true);
        //this.setBackground(new Color(65, 105, 225, 150));
        slider.addChangeListener((ChangeEvent e) -> {
            if(call.getText().equals("Check")){
                bet.setText("Bet " + slider.getValue());
            }
            else{
                bet.setText("Raise " + slider.getValue());
            }
        });

        JLabel sliderValue = new JLabel();

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addComponent(sliderValue)
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(slider)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(LEADING)
                                        .addComponent(fold))
                                .addGroup(layout.createParallelGroup(LEADING)
                                        .addComponent(call))
                                .addGroup(layout.createParallelGroup(LEADING)
                                        .addComponent(bet)))));

        // layout.linkSize(SwingConstants.HORIZONTAL, call, fold, bet);
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(sliderValue)
                        .addComponent(slider))
                .addGroup(layout.createParallelGroup(LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(BASELINE)
                                        .addComponent(fold)
                                        .addComponent(call)
                                        .addComponent(bet))))
        );

        this.add(slider);

    }

    private void setButtons() {

        fold = new JButton("Fold");
        fold.setName("fold");
        call = new JButton("Call");
        call.setName("call");
        bet = new JButton("Bet");
        bet.setName("bet");

        fold.addActionListener(this);
        call.addActionListener(this);
        bet.addActionListener(this);

    }

    public void setSliderValue(int value) {
        slider.setValue(value);
    }

    public void updateButton(String buttonName, String text){
     
        if(buttonName.equals("call")){
            call.setText(text);
        }
        else{
            bet.setText(text);
        }
        
    }
    
    public void setListener(InputListener il){
        this.il = il;
    }
    
     @Override
    public void actionPerformed(ActionEvent e) {
        if (il == null) {
            System.err.println("IL IS NULL");
        } else {
            JButton b = (JButton) e.getSource();
            //if(b.get){}
            il.input(b.getText().toLowerCase());
        }
    }
    
}
