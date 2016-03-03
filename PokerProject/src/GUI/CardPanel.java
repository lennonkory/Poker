package GUI;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

/**
 * @author kory
 */
public final class CardPanel extends JPanel implements MouseListener {

    public final String path = System.getProperty("user.dir") + "/src/res/";

    private final Icon back = new ImageIcon(path + "redBackSmall.png");
    private final Icon cardImages[];

    private final String cardNames[];
    
    private final int xLocation;
    private final int yLocation;

    private int cardnum = 0;
    private final int totalNumberofCards;
    private boolean upCards;

    /**
     * @param num number of cards in pane
     * @param x location on table
     * @param y location on table
     * @param cardNames names of cards
     * @param upCards
     */
    public CardPanel(int num, int x, int y, String[] cardNames, boolean upCards) {

        cardImages = new ImageIcon[num];

        this.cardNames = cardNames;
        totalNumberofCards = num;
        
        this.upCards = upCards;

        xLocation = x;
        yLocation = y;
        
        this.setBounds(x, y, 60 * num, 87);
        
        for (int i = 0; i < num; i++) {
            addCard(cardNames[i]);
        }
        
        this.setOpaque(false);

    }

    public CardPanel(int num, int x, int y,boolean upCards) {

        totalNumberofCards = num;
        cardImages = new ImageIcon[num];
        this.cardNames = new String[num];
        this.upCards = upCards;

        this.setBounds(x, y, 60 * num, 87);
        xLocation = x;
        yLocation = y;

        this.setOpaque(false);
        
    }

    public void addCard() {
        
        if (cardnum == totalNumberofCards ) {
            return;
        }
        //System.out.println("HERE");
        //cardNames[cardnum] = "";
        cardImages[cardnum] = back;

        cardnum++;
        
        if (cardnum == totalNumberofCards) {
            this.repaint();   
        }

    }
    
    public void addCard(String name) {
        
        if (cardnum == totalNumberofCards ) {
            return;
        }
        //System.out.println("HERE");
        cardNames[cardnum] = name;
        cardImages[cardnum] = back;

        cardnum++;
        
        if (cardnum == totalNumberofCards) {
           // System.out.println("Added");
            this.repaint();   
        }

    }
    
    public void setCardListener(){
        this.addMouseListener(this);
    }

    public void setFaces(String cards[]){
        for(int i = 0 ; i < cards.length; i++){
            cardNames[i] = cards[i];
        }
        //System.arraycopy(cards, 0, cardNames, 0, cards.length);
    }
    
    public void viewFaces() {
        for (int i = 0; i < cardNames.length; i++) {
            cardImages[i] = new ImageIcon(path + cardNames[i] + ".png");
        }
        repaint();
    }

    public final void viewBack() {
        for (int i = 0; i < cardNames.length; i++) {
           cardImages[i] = back;
        }
        repaint();
    }

    @Override
    public String toString(){
        return "X: " + this.xLocation + " Y: " + this.yLocation;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i = 0 ; i < cardnum; i++){
            cardImages[i].paintIcon(this, g, i * 60, 0);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.viewFaces();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.viewBack();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.viewBack();
    }

    /**
     * @return the xLocation
     */
    public int getxLocation() {
        return xLocation;
    }

    /**
     * @return the yLocation
     */
    public int getyLocation() {
        return yLocation;
    }

}
