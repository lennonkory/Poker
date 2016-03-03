package Controller;

import GUI.View;
import Game.Bet;
import Game.Factory;
import Game.Game;
import Game.Player;


/**
 *
 * @author kory
 */
public final class LocalController {

    private final Game game;
    private final View view;
    //private final InputController ic;

    public LocalController(Game game, View view) {

        this.game = game;
        this.view = view;

    }

    private LocalController(View b) {
        game = null;
        view = b;

        b.addInputListener(new InputListener() {

            @Override
            public void input(String message) {
                command(message);
            }

            @Override
            public void input(String message, int bet) {
            }

            @Override
            public void notifyPlayer() {
            }
        });

    }

    public void addInputistener(InputListener il) {
        //  ic.addInputListener(il);
    }

    public void start() {

        for(Player p : game.players){
            view.addPlayer(p.getInfo());
        }
        
        view.setTable();
        
        game.setPlayersInHand();
        game.nextHand();

    }

    public void command(String command) {

        if (command.equalsIgnoreCase("Added")) {
            return;
        }

        String coms[] = command.split(" ");

        int b = 0;

        try {
            b = Integer.parseInt(coms[1]);
        } catch (IndexOutOfBoundsException e) {
            b = 0;
        }

        Bet bet = new Bet(coms[0], b);

       // System.out.println(bet.toString());

        game.command(bet);
    }

    public void updatePlayer(){
        game.notifyPlayer();
    }
    
    public static void main(String[] args) {
        LocalController c = Factory.createNewGUITexas();
        c.start();
    }

}
