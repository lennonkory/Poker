package GUI;

import Deck.Deck;
import Deck.RegularDeck;
import static GUI.CardDealer.HEIGHT_SIZE;
import static GUI.CardDealer.ICON_SIZE;
import static GUI.CardDealer.WIDTH_SIZE;
import Game.PlayerInfo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 * @author kory
 */
public final class CardDealer extends JPanel {

    public static final int ICON_SIZE = 60;
    public static final int WIDTH_SIZE = 1050;
    public static final int HEIGHT_SIZE = 690;

    private int BOARD_WIDTH_SIZE;
    private int BOARD_HEIGHT_SIZE;

    private final List<Dimension> logoLocations;

    private final LogoIcon logos[];

    private ImagePanel card;

    private final JLayeredPane lp;
    private final JButton deal;

    private Timer dealTimer;

    private CardPanel flop;
    private CardPanel turn;
    private CardPanel river;

    CardPanel cps[] = new CardPanel[9];

    private int state = 0;

    private int count = 0;
    private final Deck deck;//REMOVE

    private ViewCompleteListener vcl;

    public CardDealer() {

        super();

        this.setSize(WIDTH_SIZE, HEIGHT_SIZE - 150);
        this.setLayout(new BorderLayout());

        this.setOpaque(true);

        lp = new JLayeredPane();
        lp.setPreferredSize(new Dimension(WIDTH_SIZE, HEIGHT_SIZE));

        deal = new JButton("Deal");

        deal.setPreferredSize(new Dimension(20, 20));

        logoLocations = new ArrayList<>();

        logos = new LogoIcon[9];

        //addLogos(names);
        deck = new RegularDeck();
        deck.shuffle();

        deal.addActionListener((ActionEvent e) -> {
            String cards[] = new String[2];
            cards[0] = deck.dealCard().getName();
            cards[1] = deck.dealCard().getName();
            //
            //dealCards(3, 3, cards);

        });


        JButton b = new JButton("Move");

        b.addActionListener((ActionEvent ev) -> {
            if (state == 0) {
                String cards[] = new String[3];
                cards[0] = deck.dealCard().getName();
                cards[1] = deck.dealCard().getName();
                cards[2] = deck.dealCard().getName();
                dealFlop(cards);
            } else if (state == 1) {
                dealTurn(deck.dealCard().getName());
            } else if (state == 2) {
                dealRiver(deck.dealCard().getName());
            } else {
                clearBoard();
            }
        });

        //this.add(deal, BorderLayout.NORTH);
        this.add(lp, BorderLayout.CENTER);

        this.setOpaque(false);

    }

    public void addLogos(PlayerInfo names[]) {

        int x = (this.getWidth() - ICON_SIZE) / 2;
        int y = (this.getHeight() - (ICON_SIZE));

        int top47Offset = y / 2 - ICON_SIZE * 2;//???

        int centerOffsetccc = this.getHeight() / 8;
        int sideOffset = 30;

        int newC = this.getWidth() / 4;

        JPanel dealer = new JPanel();
        dealer.setBounds((this.getWidth() - 120) / 2, 0, 120, 60);

        //resources/logo.jpg
        dealer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        lp.add(dealer, new Integer(50));

        logos[0] = new LogoIcon(names[0], x, y);
        logos[1] = new LogoIcon(names[1], newC, y - centerOffsetccc);
        logos[2] = new LogoIcon(names[2], sideOffset, y - top47Offset);
        logos[3] = new LogoIcon(names[3], sideOffset, top47Offset);
        logos[4] = new LogoIcon(names[4], newC, centerOffsetccc);
        logos[5] = new LogoIcon(names[5], x + newC - sideOffset, centerOffsetccc);
        logos[6] = new LogoIcon(names[6], this.getWidth() - ICON_SIZE - sideOffset, top47Offset);
        logos[7] = new LogoIcon(names[7], this.getWidth() - ICON_SIZE - sideOffset, y - top47Offset);
        logos[8] = new LogoIcon(names[8], x + newC - sideOffset, y - centerOffsetccc);

        lp.add(logos[0], new Integer(50));//Bottom middle 0
        logoLocations.add(new Dimension(x, y));

        lp.add(logos[1], new Integer(50)); //bottom left 1
        logoLocations.add(new Dimension(newC, y - centerOffsetccc));

        lp.add(logos[2], new Integer(50)); //left bottom 2
        logoLocations.add(new Dimension(sideOffset, y - top47Offset));

        lp.add(logos[3], new Integer(50));// left top 3
        logoLocations.add(new Dimension(sideOffset, top47Offset));

        lp.add(logos[4], new Integer(50));//top left 4
        logoLocations.add(new Dimension(newC, centerOffsetccc));

        lp.add(logos[5], new Integer(50));//top right 5
        logoLocations.add(new Dimension(x + newC - sideOffset, centerOffsetccc));

        lp.add(logos[6], new Integer(50));//right top 6
        logoLocations.add(new Dimension(this.getWidth() - ICON_SIZE - sideOffset, top47Offset));

        lp.add(logos[7], new Integer(50));
        logoLocations.add(new Dimension(this.getWidth() - ICON_SIZE - sideOffset, y - top47Offset));//right bottom 7

        lp.add(logos[8], new Integer(50));
        logoLocations.add(new Dimension(x + newC - sideOffset, y - centerOffsetccc)); // bottom right 8

    }

    public void setCardsVisible(int id, String cards[]) {
        cps[id].setFaces(cards);
        cps[id].setCardListener();
    }

    public void dealCards(int start, List<Boolean> playerLocations) {

        int size = 9;

        count = 0;
  
        if(card != null){
            lp.remove(card);
        }
        
        card = new ImagePanel(600, 0, 0, 0, this.getBounds());
        lp.add(card, new Integer(60));

        card.setEndLocation(540, 600);        

        for (int i = 0; i < size; i++) {

            boolean value = false;

            try {
                value = playerLocations.get(i);
            } catch (NullPointerException e) {
                value = true;
            }

            if (value) {
                Dimension d = logoLocations.get(i);//was loc
                cps[i] = new CardPanel(2, d.width - 30, d.height - 90, true);              
                lp.add(cps[i], new Integer(50));
            } else {
                cps[i] = null;
            }

        }

        try {
            card.setEndLocation(cps[start].getxLocation(), cps[start].getyLocation());
        } catch (NullPointerException e) {
            System.out.println("Start is NULL");

        }
        int delay = new Double(600.0 / card.numMovesWithInc()).intValue();

        //card.printInfo();
        dealTimer = new Timer(delay, new ActionListener() {

            boolean next = false;
            boolean done = false;
            int plusX = 0;
            int value = (start + count) % 9;

            @Override
            public void actionPerformed(ActionEvent t) {

                if (!card.move()) {

                    cps[value].addCard();
                    count++;
                    value = (count + start) % 9;

                    /*Find next none null card*/
                    while (cps[value] == null) {
                        count++;
                        value = (count + start) % 9;
                    }

                    if (count >= size && next) {
                        done = true;
                        dealTimer.stop();
                        lp.remove(card);
                        lp.repaint();
                        vcl.done();
                    }
                    if (count >= size && !next) {
                        count = 0;
                        next = true;
                        plusX = 60;
                    }
                    if (!done) {

                        value = (count + start) % 9;

                        try {
                            card.setEndLocation(cps[value].getxLocation() + plusX, cps[value].getyLocation());
                        } catch (NullPointerException e) {
                        }

                        dealTimer.restart();
                        dealTimer.setDelay(new Double(600.0 / card.numMovesWithInc()).intValue());
                    }
                }
            }
        });

        dealTimer.start();
        count = 0;

    }

    public void dealFlop(String cardNames[]) {

        BOARD_WIDTH_SIZE = (WIDTH_SIZE / 2) - 90;
        BOARD_HEIGHT_SIZE = (HEIGHT_SIZE / 2) - 100;

        flop = new CardPanel(3, BOARD_WIDTH_SIZE, BOARD_HEIGHT_SIZE, cardNames, true);
        lp.add(flop, new Integer(60));
        flop.viewFaces();

        state = 1;
    }

    public void dealTurn(String cardName) {

        turn = new CardPanel(3, BOARD_WIDTH_SIZE + (ICON_SIZE * 3), BOARD_HEIGHT_SIZE, true);

        turn.addCard(cardName);

        lp.add(turn, new Integer(70));

        turn.viewFaces();

        state = 2;

    }

    public void dealRiver(String cardName) {

        river = new CardPanel(3, BOARD_WIDTH_SIZE + (ICON_SIZE * 4), BOARD_HEIGHT_SIZE, true);
        river.addCard(cardName);

        lp.add(river, new Integer(80));
        river.viewFaces();

        state = 3;

    }

    public void removeCards(int number) {
        try {
            lp.remove(cps[number]);
            cps[number] = null;
        } catch (NullPointerException e) {
            //System.out.println("Could not remove " + number);
        }
        
        lp.repaint();
    }

    public void clearBoard() {
        try {
            lp.remove(flop);
            lp.remove(turn);
            lp.remove(river);
        } catch (NullPointerException e) {
        }
        
        for(LogoIcon lg : logos){
            lg.setNotActive();
        }
        
        lp.repaint();
        state = 0;
        
        
    }
    
    public void setAllNotActive(){
        for(LogoIcon lg : logos){
            lg.setNotActive();
        }
    }

    public void resetLogo(int num) {
        logos[num].resetLogo();
    }

    public void addLogo(PlayerInfo info) {
        logos[info.getId()].resetLogo(info);
    }

    public void setViewCompleteListener(ViewCompleteListener vcl) {
        this.vcl = vcl;
    }
    
    public void activePlayer(PlayerInfo info){
        logos[info.getId()].setActive();
        logos[info.getId()].resetLogo(info);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Card Dealer");

        CardDealer c = new CardDealer();

        frame.setSize(WIDTH_SIZE, HEIGHT_SIZE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(c);
        frame.pack();
        frame.setVisible(true);
    }

}

class LogoIcon extends JPanel {

    Icon icon;
    JLabel name;
    JLabel chips;
    JLabel pic = new JLabel();
    
    boolean active = false;

    public LogoIcon(PlayerInfo info, int x, int y) {

        this.name = new JLabel(info.getName());
        Font f = new Font(Font.SERIF, Font.BOLD, 20);
        this.name.setFont(f);
        this.name.setForeground(Color.red);

        icon = info.getIcon();
        pic =  new JLabel(icon);
  
        pic.setBounds(0, 0, 60, 60);
        
        chips = new JLabel(""+info.getChips());
        
        this.add(this.name);
        this.add(this.chips);
        this.add(pic);
        this.setOpaque(false);
        this.setBorder(BorderFactory.createLineBorder(Color.RED));
        this.setBounds(x, y, ICON_SIZE, ICON_SIZE + 50);
    }

    public void resetLogo() {
        this.name.setText("");
        icon = new ImageIcon("");
        repaint();
    }

    public void resetLogo(PlayerInfo info) {
        this.name.setText(info.getName());
        icon = info.getIcon();
        this.chips.setText(""+info.getChips());       
        repaint();
    }
    
    public void setNotActive(){
        active = false;
        this.setBorder(BorderFactory.createLineBorder(Color.RED));
        repaint();
    }
    
    public void setActive(){
        
        if(!active){
            this.setBorder(BorderFactory.createLineBorder(Color.BLUE,5));          
            active = true;
        }
        else{
            this.setBorder(BorderFactory.createLineBorder(Color.RED));
            active = false;
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        //icon.paintIcon(this, g, 0, 0);

    }

}

final class ImagePanel extends JPanel {

    @Override
    public String toString() {
        return "ImagePanel{" + "icon=" + icon + ", x=" + x + ", y=" + y + ", b=" + b + ", xCounter=" + xCounter + ", finalX=" + finalX + ", finalY=" + finalY + ", diffX=" + diffX + ", slope=" + slope + ", counter=" + counter + '}';
    }

    Icon icon;
    double x = 0;
    double y = 0;
    double b;
    double xCounter = 0;

    double finalX = 100;
    double finalY = 200;
    double startX = 100;
    double startY = 200;

    double diffX = 0;

    private double slope = 0;

    int counter = 0;

    Timer timer;

    int mult = 0;
    private double increment = 1;
    private double realincrement = 1;

    public ImagePanel(int x, int y, int fx, int fy, Rectangle rec) {

        icon = new ImageIcon("resources/redBackSmall.png");

        if (icon == null) {
            System.err.println("Could not load Image");
        }

        Dimension d = new Dimension(WIDTH_SIZE, HEIGHT_SIZE);
        this.setPreferredSize(d);
        this.setOpaque(false);

        this.setBounds(rec);

        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;

        this.finalX = fx;
        this.finalY = fy;
        this.diffX = finalX - (double) x;
        this.b = y;

        slope = getSlope();

        increment = this.numMoves() / 600.0;

        if (increment < 1) {
            realincrement = 0.5;
        } else if (increment > 1) {
            realincrement = 2;
        } else if (increment > 5) {
            realincrement = 5;
        } else if (increment > 10) {
            realincrement = 10;
        }

        repaint();
    }

    public void setEndLocation(int fx, int fy) {

        finalX = fx;
        finalY = fy;

        this.x = this.startX;
        this.y = this.startY;

        this.diffX = finalX - x;
        this.b = y;

        slope = getSlope();
        xCounter = 0;
        counter = 0;
        increment = this.numMoves() / 600.0;

        if (increment < 0.12) {
            realincrement = 1.5;
        }
        if (increment >= 0.12 && increment < 0.13) {
            realincrement = 4;
        } else if (increment >= 0.13 && increment < 0.5) {
            realincrement = 10;
        } else if (increment >= 0.5 && increment < 1) {
            realincrement = 8;
        } else {
            realincrement = 10;
        }

    }

    public boolean moveUntilDone() {

        timer = new Timer(2, (ActionEvent e) -> {
            if (!move()) {
                timer.stop();
            }
        });
        timer.start();

        return true;

    }

    public double getSlope() {

        if (x - finalX == 0) {
            return 0;
        }

        double m = (double) (y - finalY) / (x - finalX);

        return m;

    }

    private double getYValue() {

        if (slope == 0.0) {
            y += 10;
            return y;
        }

        double tY = (slope * xCounter) + b;
        return tY;

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        icon.paintIcon(this, g, new Double(x + xCounter).intValue(), new Double(y).intValue());

    }

    public double getHypot() {

        double a = Math.abs((startX - finalX));
        double b = Math.abs((startY - finalY));

        return Math.sqrt((a * a) + (b * b));

    }

    public double getInc() {
        return this.increment;
    }

    public boolean finished() {
        // System.out.println("X: " + (xCounter) + " Y: " + y + " Slope: " + slope + " Dif: "  + diffX);

        if (slope < 0) {
            return x + xCounter <= finalX && y >= finalY;
        } else if (slope == 0) {
            return x + xCounter == finalX && y >= finalY;
        } else {
            return x + xCounter >= finalX && y >= finalY - 1;
        }
    }

    public int getCounter() {
        return counter;
    }

    public int numMovesWithInc() {

        double xc = xCounter;
        double fy = y;

        while (!finished()) {
            if (slope > 0.0) {
                xCounter += realincrement;
            } else if (y - finalY == 0) {

                if (x > finalX) {
                    xCounter -= realincrement;
                } else {
                    xCounter += realincrement;
                }
            } else if (slope == 0.0) {
                //do nothing for x
            } else {
                xCounter -= realincrement;
            }

            if (y < finalY) {
                y = getYValue();
            }

            this.repaint();
            counter++;
        }

        xCounter = xc;
        y = fy;

        int count = counter;
        counter = 0;

        mult = counter % 6;

        return count;
    }

    public int numMoves() {

        double xc = xCounter;
        double fy = y;

        while (!finished()) {
            if (slope > 0.0) {
                xCounter += Math.abs(slope);
            } else if (y - finalY == 0) {

                if (x > finalX) {
                    xCounter -= 1;
                } else {
                    xCounter += 1;
                }
            } else if (slope == 0.0) {
                //do nothing for x
            } else {
                xCounter -= Math.abs(slope);
            }

            if (y < finalY) {
                y = getYValue();
            }

            this.repaint();
            counter++;
        }

        xCounter = xc;
        y = fy;

        int count = counter;
        counter = 0;

        mult = counter % 6;

        return count;
    }

    public boolean move() {
        if (slope > 0.0) {
            xCounter += realincrement;
        } else if (y - finalY == 0) {

            if (x > finalX) {
                xCounter -= 1;
            } else {
                xCounter += 1;
            }
        } else if (slope == 0.0) {
            //do nothing for x
        } else {
            xCounter -= realincrement;
        }

        if (y < finalY) {
            y = getYValue();
        }

        this.repaint();
        counter++;

        return !finished();

    }

    public void printInfo() {
        System.out.println("Moves: " + numMoves() + " INC Moves: " + numMovesWithInc() + " INC: " + this.increment);
    }

}
