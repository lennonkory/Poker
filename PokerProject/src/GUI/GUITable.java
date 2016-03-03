package GUI;

import Deck.Deck;
import Deck.RegularDeck;
import java.awt.*;
import static javax.swing.GroupLayout.Alignment.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author kory
 */
public class GUITable {

    private final JFrame frame;
    private final JLayeredPane lp;
    private CardPanel cardPanel[];

    private JPanel controlPanel;

    private final JButton foldButton;

    private JLabel sliderValue;
    private JSlider slider;

    private int numberPlayers;
    private int numberCards;

    private Deck deck;

    JButton fold = new JButton("Fold");
    JButton call = new JButton("Call");
    JButton bet = new JButton("Bet");

    public GUITable(int np, int nc) {

        this.numberCards = nc;
        this.numberPlayers = np;

        deck = new RegularDeck();
        deck.shuffle();

        ImageIcon i = new ImageIcon(System.getProperty("user.dir") + "/src/res/table.png");

        TableBackGroundPanelTemp p = new TableBackGroundPanelTemp(i.getImage());
        p.setBounds(0, 0, 700, 400);

        frame = new JFrame();
        frame.setPreferredSize(new Dimension(700, 400));

        lp = new JLayeredPane();

        frame.setLayout(new BorderLayout());

        foldButton = new JButton("Fold");
        foldButton.setBounds(50, 50, 60, 20);

        lp.add(p);

        lp.add(foldButton, new Integer(1));
        lp.setBounds(0, 0, 600, 400);

        //this.getButtonPanel(null);
        //lp.add(this.controlPanel, new Integer(1));
        foldButton.addActionListener((ActionEvent e) -> {
            clear();
        });

        JButton b = new JButton("Remove Cards");

        b.addActionListener((ActionEvent e) -> {
            getButtonPanel(null);

            try {
                removeCards(cardPanel[0]);
            } catch (NullPointerException npe) {
                System.out.println("No cards to remove");
            }
        });

        JButton d = new JButton("Redeal Cards");
        d.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addCards();
            }
        });

        frame.add(lp, BorderLayout.CENTER);
        frame.add(b, BorderLayout.SOUTH);
        //frame.add(d, BorderLayout.NORTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void getButtonPanel(String[] options) {

        try {
            controlPanel.remove(slider);
            lp.remove(controlPanel);
        } catch (Exception e) {
            System.err.println(e.toString());
        }

        controlPanel = new JPanel();

        if (options == null) {
            options = "fold call bet".split(" ");
        }

        controlPanel.setOpaque(false);
        //p.setBackground(new Color(255, 0, 0, 100));

        controlPanel.setBounds(300, 200, 300, 250);

        for (String op : options) {
            System.out.println(op);
            if (op.equalsIgnoreCase("call")) {
                call.setText("Call");
            } else if (op.equalsIgnoreCase("check")) {
                call.setText("Check");
            } else if (op.equalsIgnoreCase("bet")) {
                bet.setText("Bet");
            } else if (op.equalsIgnoreCase("raise")) {
                bet.setText("Raise");
            }
        }

        slider = getSlider();

        sliderValue.setOpaque(true);
        sliderValue.setBackground(new Color(65, 105, 225, 150));

        int width = 30;
        int height = 30;

        Dimension d = new Dimension(width, height);

        sliderValue.setMinimumSize(d);
        sliderValue.setPreferredSize(d);
        sliderValue.setMaximumSize(d);

        Font f = new Font(Font.SERIF, Font.BOLD, 15);

        sliderValue.setFont(f);
        sliderValue.setBorder(BorderFactory.createEmptyBorder());

        GroupLayout layout = new GroupLayout(controlPanel);
        controlPanel.setLayout(layout);

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

        slider.addMouseListener(new MouseSliderListener(controlPanel));
        lp.add(this.controlPanel, new Integer(1));
        lp.repaint();

    }

    private JSlider getSlider() {

        sliderValue = new JLabel("0", SwingConstants.CENTER);

        slider = new JSlider(0, 100, 0);

        slider.setOpaque(true);

        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintTrack(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);

        Color c = new Color(65, 105, 225, 100);

        slider.setBackground(c);

        slider.setToolTipText("Slide the cursor to place a bet");
        slider.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));

        Font font = new Font("Serif", Font.BOLD, 15);
        slider.setFont(font);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = slider.getValue();
                value = (int) Math.round((double) value / 20) * 20;
                sliderValue.setText("" + value);
                slider.setValue(value);
                //controlPanel.repaint();
            }

        });

        return slider;

    }

    public void start() {
        frame.setVisible(true);
    }

    /**
     * @param numCards number of cards in each panel
     * @param numberPanels number of panels
     * @return
     */
    private CardPanel[] createCardPanels(int numCards, int numberPanels) {

        CardPanel cards[] = new CardPanel[numberPanels];

        int n = 100;

        for (int i = 0; i < numberPanels; i++) {
            String names[] = new String[numCards];
            for (int j = 0; j < numCards; j++) {
                String t = deck.dealCard().getName();
                System.out.println(t);
                names[j] = t;
            }
            cards[i] = new CardPanel(numCards, n * (i + 1), n * (i + 1), names,false);
            cards[i].addMouseListener(new CardListener(cards[i]));
        }

        return cards;

    }

    /**
     * Clears all elements off the GUI.
     */
    public void clear() {
        try {
            removeCards();
        } catch (NullPointerException e) {
            System.out.println("No cards to remove");
        }
        lp.repaint();
    }

    private void addCards() {

        try {
            removeCards();
        } catch (NullPointerException e) {
            System.out.println("No cards to remove");
        }

        /*Inorder to place the cards numberPlayer will need to be viewed
         if there are 3 players the cards will be played out deferenetly than if there are two etc
         */
        cardPanel = createCardPanels(this.numberCards, this.numberPlayers);

        for (CardPanel c : cardPanel) {
            lp.add(c, 0);
        }
        lp.repaint();
    }

    /**
     * Removes all the cards from the table.
     */
    public void removeCards() {

        for (CardPanel cardPanel1 : cardPanel) {
            lp.remove(cardPanel1);
        }
        lp.repaint();
    }

    /**
     * Removes one set of cards (after someone has folded).
     *
     * @param cp The cards to be removed.
     */
    public void removeCards(CardPanel cp) {
        try {
            lp.remove(cp);
        } catch (NullPointerException e) {
            System.out.println("Could not remove cards");
        }
        lp.repaint();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                GUITable g = new GUITable(2, 2);
                g.start();

            }
        });

    }

    public void setButtonListeners(ActionListener action) {
        fold.addActionListener(action);
        call.addActionListener(action);
        bet.addActionListener(action);
    }

    void resetPanel(String[] split) {
        try {
            lp.remove(controlPanel);
        } catch (Exception e) {
        }
        this.getButtonPanel(split);
    }

}

class TableBackGroundPanelTemp extends JPanel {

    Image img;

    public void setImage(Image img) {
        this.img = img;
        this.repaint();
    }

    public TableBackGroundPanelTemp(Image img) {

        this.img = img;

        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        this.setPreferredSize(size);
        this.setMinimumSize(size);
        this.setMaximumSize(size);
        this.setSize(size);
        this.setLayout(null);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);

    }

}
