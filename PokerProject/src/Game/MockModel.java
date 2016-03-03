package Game;

import GUI.GUIView;
import GUI.View;
import Deck.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author kory
 */
public class MockModel {

    Deck deck;
    View view;

    public MockModel(View v) {
        view = v; 
        deck = new RegularDeck();
    }

    private List<Boolean> getPlayerList(){
        
        List<Boolean> players = new ArrayList<>();
        
        for(int i = 0 ; i < 9; i++){
            players.add(true);
        }
  
        return players;
        
    }
    
    public void start() {

        Scanner input = new Scanner(System.in);
        deck.shuffle();

        while (true) {

            System.out.print("Command: ");

            String com = input.nextLine();

            if (com.equalsIgnoreCase("shuffle")) {
                deck.shuffle();
            } else if (com.equalsIgnoreCase("deal")) {
                
                    
                    String cards[] = new String[2];
                    cards[0] = deck.dealCard().getName();
                    cards[1] = deck.dealCard().getName();
                    view.dealCards(2, getPlayerList());
                    
                

            } else if (com.equalsIgnoreCase("flop")) {
                String cards[] = new String[3];
                cards[0] = deck.dealCard().getName();
                cards[1] = deck.dealCard().getName();
                cards[2] = deck.dealCard().getName();
                view.dealFlop(cards);
                
            } else if (com.equalsIgnoreCase("turn")) {
                String card = deck.dealCard().getName();
                view.dealTurn(card);
            } else if (com.equalsIgnoreCase("river")) {
                String card = deck.dealCard().getName();
                view.dealTurn(card);
                
            } else if (com.equalsIgnoreCase("clear")) {
                view.clearCards();
            } else {
                break;
            }

        }

    }

    public static void main(String[] args) {

        MockModel m = new MockModel(new GUIView(9,2));
        m.start();

    }

}
