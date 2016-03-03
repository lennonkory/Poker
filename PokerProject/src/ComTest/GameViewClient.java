package ComTest;

import Controller.InputListener;
import GUI.GUIView;
import GUI.View;
import Game.PlayerInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kory Bryson
 */
public class GameViewClient implements Runnable {

    private final View view;

    private PrintStream os = null;
    private ObjectInputStream oos;

    private boolean closed = false;

    private int count = 0;

    public GameViewClient(Socket s) throws IOException {

        //os = new PrintStream(s.getOutputStream());

        InputStream is = s.getInputStream();
        oos = new ObjectInputStream(is);
        os = new PrintStream(s.getOutputStream());
        view = new GUIView(9, 2);

        view.addInputListener(new InputListener() {

            @Override
            public void input(String message) {
                System.out.println("Mes: " + message);
                os.println(message);
            }

            @Override
            public void input(String message, int bet) {
                System.out.println("cat food");
                System.out.println("Bet: " + message + " " + bet);
            }

            @Override
            public void notifyPlayer() {
                os.println("notifyPlayer");
            }
        });

        view.addPlayer(new Game.Player("Kory", 0).getInfo());
        view.addPlayer(new Game.Player("Bill", 1).getInfo());
        view.addPlayer(new Game.Player("Mike", 2).getInfo());
        view.addPlayer(new Game.Player("James", 3).getInfo());

        view.setListener();

        view.setTable();
    }

    @Override
    public void run() {
        /*
         * Keep on reading from the socket till we receive "Bye" from the
         * server. Once we received that then we want to break.
         */
        String responseLine;
        while (true) {
            try {
                responseLine = (String) oos.readObject();
                System.out.println(responseLine);
                command(responseLine);
                if (responseLine.contains("*** Bye")) {
                    break;
                }
            } catch (Exception e) {
                System.out.println("HEREcaat: " + e);
                System.exit(0);
            }
        }
        closed = true;
    }

    /*t[0] is the name of the View. t[1] is the command. t[2] is the value of the command*/
    public void command(String com) throws ClassNotFoundException, IOException {

        String t[] = com.split(" ");
        System.err.println("IN CLI: " + com);

        switch (t[1]) {
            case "pot":
                view.updatePot(27);
                break;
            case "info": {
                PlayerInfo info = (PlayerInfo) oos.readObject();
                System.out.println(info.toString());
                view.updatePlayer(info);
                break;
            }
            
            case "updatePlayer": {
                PlayerInfo info = (PlayerInfo) oos.readObject();
                System.out.println(info.toString());
                view.updatePlayerInfo(info);
                break;
            }
            
            case "message": {
                String info = (String) oos.readObject();
                view.updateView(info);
                break;
            }
            case "buttons": {
                String info = (String) oos.readObject();
                String results[] = info.split(" ");
                try {
                    int call = Integer.parseInt(results[0]);
                    int bet = Integer.parseInt(results[1]);
                    view.updateButtons(call, bet);
                } catch (NumberFormatException e) {
                    view.updateButtons(0, 0);
                }
                break;
            }
            case "cards": {
                String cards[] = (String[]) oos.readObject();
                view.viewCards(count, cards);
                count++;
                break;
            }
            case "add": {
                PlayerInfo info = (PlayerInfo) oos.readObject();
                System.out.println(info.toString());
                view.updatePlayerInfo(info);
                break;
            }
            case "pre":
                List<Boolean> peeps = new ArrayList<>();
                for (int j = 0; j < 9; j++) {

                    peeps.add(false);
                }
                for (PlayerInfo player : view.players) {
                    if (player.getId() != -1) {
                        peeps.set(player.getId(), Boolean.TRUE);
                    }
                }
                view.dealCards(0, peeps);
                break;
            case "flop": {
                String cards[] = (String[]) oos.readObject();
                view.dealFlop(cards);
                break;
            }
            case "turn": {
                String card = (String) oos.readObject();
                System.out.println("TURN: " + card);
                view.dealTurn(card);
                break;
            }
            case "river": {
                String card = (String) oos.readObject();
                view.dealRiver(card);
                break;
            }
            case "clear":
                view.clearCards();
                break;
        }

    }

}
