package ComTest;

//Example 25
import Controller.InputListener;
import GUI.View;
import GUI.GUIView;
import Game.PlayerInfo;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MultiThreadChatClient implements Runnable {

    // The client socket
    private static Socket clientSocket = null;
    // The output stream
    private static PrintStream os = null;
    static ObjectInputStream oos;
    // The input stream
    //private static DataInputStream is = null;
    //private static BufferedReader is = null;

    private static BufferedReader inputLine = null;
    private static boolean closed = false;

    static View view;
    
    private int count = 0;
   
    public static void main(String[] args) throws ClassNotFoundException {

        // The default port.
        int portNumber = 2222;
        // The default host.
        String host = "localhost";

        if (args.length < 2) {
            System.out
                .println("Usage: java MultiThreadChatClient <host> <portNumber>\n"
                    + "Now using host=" + host + ", portNumber=" + portNumber);
        } else {
            host = args[0];
            portNumber = Integer.valueOf(args[1]).intValue();
        }

        /*
         * Open a socket on a given host and port. Open input and output streams.
         */
        try {
            clientSocket = new Socket(host, portNumber);
            inputLine = new BufferedReader(new InputStreamReader(System.in));
            os = new PrintStream(clientSocket.getOutputStream());

            //ios = new ObjectInputStream(clientSocket.getInputStream());
            //is = new DataInputStream(clientSocket.getInputStream());
            InputStream is = clientSocket.getInputStream();
            oos = new ObjectInputStream(is);

            // is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + host);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to the host "
                + host);
        }

        /*
         * If everything has been initialized then we want to write some data to the
         * socket we have opened a connection to on the port portNumber.
         */
        if (clientSocket != null && os != null && oos != null) {
            try {
                view = new GUIView(9, 2);

                view.addInputListener(new InputListener() {

                    @Override
                    public void input(String message) {
                        System.out.println("Mes: " + message);
                        os.println("PUTTING");
                    }

                    @Override
                    public void input(String message, int bet) {
                        System.out.println("cat food");
                        System.out.println("Bet: " + message + " " + bet);
                    }

                    @Override
                    public void notifyPlayer() {
                    }
                });

                view.addPlayer(new Game.Player("Kory", 0).getInfo());
                view.addPlayer(new Game.Player("Bill", 1).getInfo());
                view.addPlayer(new Game.Player("Mike", 2).getInfo());
                view.addPlayer(new Game.Player("James", 6).getInfo());

                view.setListener();

                view.setTable();
                /* Create a thread to read from the server. */
                new Thread(new MultiThreadChatClient()).start();

                while (!closed) {
                    os.println(inputLine.readLine().trim());
                }
                /*
                 * Close the output stream, close the input stream, close the socket.
                 */
                os.close();
                oos.close();
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }
    }

    /*
     * Create a thread to read from the server. (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
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
                System.out.println("HERE: " + e.getMessage());
                break;
            }
        }
        closed = true;
    }

    /*t[0] is the name of the View. t[1] is the command. t[2] is the value of the command*/
    public void command(String com) throws ClassNotFoundException, IOException {

        String t[] = com.split(" ");
        System.err.println("IN CLI: " + com);

        if (t[1].equals("pot")) {
            view.updatePot(27);
        }
        else if (t[1].equals("info")) {
            PlayerInfo info = (PlayerInfo) oos.readObject();
            System.out.println(info.toString());
            view.updatePlayer(info);
        }
        else if (t[1].equals("message")) {
            String info = (String) oos.readObject();
            view.updateView(info);
        }
        else if (t[1].equals("buttons")) {
            String info = (String) oos.readObject();
            String results[] = info.split(" ");
            try {
                int call = Integer.parseInt(results[0]);
                int bet = Integer.parseInt(results[1]);
                view.updateButtons(call, bet);
            } catch (NumberFormatException e) {
                view.updateButtons(0, 0);
            }
        }
        else if (t[1].equals("cards")) {
            String cards[] = (String[]) oos.readObject();
            view.viewCards(count, cards);
            count++;
        }
        else if (t[1].equals("add")) {
            PlayerInfo info = (PlayerInfo) oos.readObject();
            System.out.println(info.toString());
             view.updatePlayerInfo(info);
        }
        
        else if(t[1].equals("pre")){
          
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
        }
        
        else if(t[1].equals("flop")){
            String cards[] = (String[]) oos.readObject();
            view.dealFlop(cards);
        }
        else if(t[1].equals("turn")){
            String card = (String) oos.readObject();
            System.out.println("TURN: " + card);
            view.dealTurn(card);
        }
        else if(t[1].equals("river")){
            String card = (String) oos.readObject();
            view.dealRiver(card);
        }
        else if(t[1].equals("clear")){
           view.clearCards();
        }

    }

}
