package GUI;

import Deck.*;
import Game.Player;
import Game.PlayerInfo;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * @author kory
 */
public class GUIView extends View {

    private JFrame frame;

    private JTextArea area;
    private String raise;
    private ButtonPanel buttonPanel;

    private JLayeredPane lp;
    private CardPanel cardPanel[];

    private final int numberPlayers;
    private final int numberCards;

    public final static int TABLE_WIDTH = 1050;
    public final static int TABLE_HEIGHT = 690;

    private JLabel potLabel;

    private JScrollPane sp;

    private CardDealer cardDealer;

    private Deck deck;

    private final static Logger LOGGER = Logger.getLogger(GUIView.class.getName());

    public GUIView(int np, int nc) {

        super("GUI");
        this.numberCards = nc;
        this.numberPlayers = np;
        createGUI();

    }

    private void createGUI() {

        frame = new JFrame("Table");
        frame.setSize(TABLE_WIDTH, TABLE_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        deck = new RegularDeck();
        deck.shuffle();

        ImageIcon i = new ImageIcon(System.getProperty("user.dir") + "/src/res/table.png");
        Image im = i.getImage();
        i.setImage(im.getScaledInstance(TABLE_WIDTH, TABLE_HEIGHT - 150, Image.SCALE_DEFAULT));

        TableBackGroundPanel p = new TableBackGroundPanel(i.getImage());
        p.setBounds(0, 0, TABLE_WIDTH, TABLE_HEIGHT - 150);

        lp = new JLayeredPane();

        lp.add(p);
        lp.setBounds(0, 0, TABLE_WIDTH, TABLE_HEIGHT - 150);

        /**
         * **
         */
        raise = "Raise ";

        cardDealer = new CardDealer();

        cardDealer.setViewCompleteListener(() -> {
            updatePlayer();
        });

        lp.add(cardDealer, new Integer(30));

        JButton deal = new JButton("Deal Cards");

        deal.addActionListener((ActionEvent e) -> {

            deck.shuffle();

            List<Boolean> peeps = new ArrayList<>();

            for (int j = 0; j < 9; j++) {

                peeps.add(false);
            }

            for (PlayerInfo player : players) {
                if (player.getId() != -1) {
                    peeps.set(player.getId(), Boolean.TRUE);
                }
            }

            this.dealCards(0, peeps);
            
            for (PlayerInfo player : players) {
                if (player.getId() != -1) {
                    viewCards(player.getId(), deck.dealCardNames(2));
                }
            }

        });

        JButton clear = new JButton("Clear Cards");

        clear.addActionListener((ActionEvent e) -> {

            clearCards();

        });

        frame.add(lp, BorderLayout.CENTER);

        potLabel = new JLabel("0");

        potLabel.setFont(new Font(Font.SERIF, Font.BOLD, 30));

        potLabel.setBounds(500, 120, 400, 40);

        lp.add(potLabel, new Integer(90));

        JPanel panel = new JPanel();
        panel.setSize(200, 150);
        panel.setLayout(new BorderLayout());

        area = new JTextArea(5, 20);
        sp = new JScrollPane(area);

        buttonPanel = new ButtonPanel(il);
        buttonPanel.setVisible(true);

        panel.add(buttonPanel, BorderLayout.EAST);
        panel.add(sp, BorderLayout.WEST);

        frame.add(panel, BorderLayout.SOUTH);
        frame.add(deal, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    @Override
    public void updateView(String message) {
        area.append(message + "\n");
        area.setCaretPosition(area.getDocument().getLength());
    }

    @Override
    public void updatePlayer(PlayerInfo info) {
        cardDealer.activePlayer(info);
    }

    @Override
    public void updateButtons(int call, int bet) {

        if (call == 0) {
            buttonPanel.updateButton("call", "Check");
            buttonPanel.setSliderValue(bet);
            buttonPanel.updateButton("bet", "Bet " + bet);
        } else {
            buttonPanel.updateButton("call", "Call " + call);
            buttonPanel.setSliderValue(bet);
            buttonPanel.updateButton("bet", "Raise " + bet);
        }

    }

    @Override
    public void dealCards(int start, List<Boolean> players) {
        cardDealer.dealCards(start, players);
    }

    @Override
    public void viewCards(int id, String cards[]) {
        cardDealer.setCardsVisible(id, cards);
    }

    @Override
    public void dealFlop(String[] cards) {
        cardDealer.dealFlop(cards);
    }

    @Override
    public void dealTurn(String card) {
        cardDealer.dealTurn(card);
    }

    @Override
    public void dealRiver(String card) {
        cardDealer.dealRiver(card);
    }

    @Override
    public void clearCards() {

        buttonPanel.setVisible(false);

        cardDealer.clearBoard();

        for (PlayerInfo player : players) {
            if (player.getId() != -1) {
                removeCards(player.getId());
            }
        }

    }

    @Override
    public void setListener() {
        if (il == null) {
            System.err.println("Why you null");
        }

        buttonPanel.setListener(il);
    }

    /**
     * Clears all elements off the GUI.
     */
    private void clear() {
        try {
            removeCards();
        } catch (NullPointerException e) {
            System.err.println("No cards to remove");
        }
        lp.repaint();
    }

    private void addCards() {

        try {
            removeCards();
        } catch (NullPointerException e) {
            System.err.println("No cards to remove");
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
                names[j] = t;
            }
            cards[i] = new CardPanel(numCards, n * (i + 1), n * (i + 1), names, false);
            cards[i].addMouseListener(new CardListener(cards[i]));
        }

        return cards;

    }

    /**
     * Removes all the cards from the table.
     */
    private void removeCards() {

        for (CardPanel cardPanel1 : cardPanel) {
            lp.remove(cardPanel1);
        }
        lp.repaint();
    }

    @Override
    public void updatePlayer() {
        buttonPanel.setVisible(true);
        cardDealer.setAllNotActive();
        try {
            this.il.notifyPlayer();
        } catch (NullPointerException e) {
            
            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }

    @Override
    public void removeCards(int id) {
        cardDealer.removeCards(id);
    }

    @Override
    public void setTable() {

        cardDealer.addLogos(players);

    }

    public static void main(String[] args) {

        View v = new GUIView(2, 2);

        v.addPlayer(new Player("Kory", 0).getInfo());
        v.addPlayer(new Player("Bill", 1).getInfo());
        v.addPlayer(new Player("Mike", 2).getInfo());
        v.addPlayer(new Player("James", 6).getInfo());

        v.setTable();
        v.clearCards();

    }

    @Override
    public void updatePot(int pot) {
        potLabel.setText(pot + "");
    }

    @Override
    public void updatePlayerList(PlayerInfo[] info) {
        for(PlayerInfo p : info){
            players[p.getId()] = p;
        }
    }

    @Override
    public void updatePlayerInfo(PlayerInfo info) {
        cardDealer.addLogo(info);
    }

}

class TableBackGroundPanel extends JPanel {

    private Image img;

    public void setImage(Image img) {
        this.img = img;
        this.repaint();
    }

    public TableBackGroundPanel(Image img) {

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
