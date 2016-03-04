package ComTest;

import Controller.PlayerListener;
import Controller.TableListener;
import Deck.Deck;
import Deck.NotEnoughCardsException;
import Deck.RegularDeck;
import Game.*;
import Game.PlayerInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kory Bryson
 */
public class GameServerThread extends Thread {

    private String clientName = null;
    private BufferedReader is;
    private ObjectOutputStream oos;
    private Socket clientSocket = null;
    private final GameServerThread[] threads;
    private final int maxClientsCount;
    public static final Deck deck = new RegularDeck();
    public static final Scanner in = new Scanner(System.in);
    private final Texas game;

    public GameServerThread(Socket clientSocket, GameServerThread[] threads, Texas game, Player p) {

        this.clientSocket = clientSocket;
        this.threads = threads;
        maxClientsCount = threads.length;
        deck.shuffle();
        this.game = game;

        game.setTableListener(new TableListener() {

            @Override
            public void update(String message) {
            }

            @Override
            public void dealFlop(String[] cards) {
                try {
                    all("flop", cards);
                } catch (IOException ex) {
                    Logger.getLogger(GameServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void dealPreflop(int start, List<Boolean> inhand) {
                try {
                    try {
                        all("game", "pre");
                    } catch (NotEnoughCardsException ex) {
                        Logger.getLogger(GameServerThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(GameServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void dealTurn(String card) {
            }

            @Override
            public void dealRiver(String card) {
            }

            @Override
            public void updatePot(int pot) {
            }

            @Override
            public void clearCards() {
                try {
                    try {
                        all("game", "clear");
                    } catch (NotEnoughCardsException ex) {
                        Logger.getLogger(GameServerThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(GameServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void removeCards(int id) {
            }

            @Override
            public String getType() {
                return "GUI";
            }

            @Override
            public void setPlayerInfo() {
            }
        });

        p.setViewListener(new PlayerListener() {

            @Override
            public void update(String message) {
                try {
                    privateMessage(clientName, "message", message, null);
                } catch (IOException ex) {
                    Logger.getLogger(GameServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void updateButtons(int call, int raise) {
                try {
                    privateMessage(clientName, "buttons", call + " " + raise, null);
                } catch (IOException ex) {
                    Logger.getLogger(GameServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void viewCards(int id, String[] cards) {
                try {
                    privateMessage(clientName, "cards", null, cards);
                } catch (IOException ex) {
                    Logger.getLogger(GameServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void activatePlayer(PlayerInfo info) {
                try {
                    privateMessage(clientName, "info", info, null);
                } catch (IOException ex) {
                    Logger.getLogger(GameServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void updatePlayer(PlayerInfo info) {
                try {
                    privateMessage(clientName, "updatePlayer", info, null);
                } catch (IOException ex) {
                    Logger.getLogger(GameServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        game.addPlayer(p);

    }

    @Override
    public void run() {

        int maxClientsCount = this.maxClientsCount;
        GameServerThread[] threads = this.threads;

        try {

            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            oos = new ObjectOutputStream(clientSocket.getOutputStream());

            String name;
            while (true) {

                oos.writeObject("Enter your name. ");
                name = is.readLine().trim();
                System.out.println("THE NAME: " + name);

                if (name.indexOf('@') == -1) {
                    break;
                } else {
                    oos.writeObject("The name should not contain '@' character.");
                }
            }

            /* Welcome the new the client. */
            oos.writeObject("Welcome " + name
                + " to our chat room.\nTo leave enter /quit in a new line.");
            synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null && threads[i] == this) {
                        clientName = "@" + name;
                        break;
                    }
                }
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null && threads[i] != this) {
                        threads[i].oos.writeObject("*** A new user " + name
                            + " entered the chat room !!! ***");
                    }
                }
            }

            game.setPlayersInHand();
            game.nextHand();

            /* Start the conversation. */
            while (true) {
                String line;

                //synchronized (this) {
                line = is.readLine().trim();
                //}

                if (line.startsWith("/quit")) {
                    break;
                }
                System.out.println("LINE: " + line);
                
                if(line.equals("updatePlayer")){
                    notifyPlayer();
                }
                else{
                    try {
                        command(line);
                    } catch (NotEnoughCardsException ex) {
                        Logger.getLogger(GameServerThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                /* If the message is private sent it to the given client. */
                if (line.startsWith("@")) {
                    String[] words = line.split("\\s", 2);
                    if (words.length > 1 && words[1] != null) {
                        words[1] = words[1].trim();
                        if (!words[1].isEmpty()) {
                            privateMessage(name, words);
                        }
                    }
                } else if (line.equals("flop")) {
                    try {
                        game.dealFlop();
                    } catch (NotEnoughCardsException ex) {
                        Logger.getLogger(GameServerThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        all(name, line);
                    } catch (NotEnoughCardsException ex) {
                        Logger.getLogger(GameServerThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null && threads[i] != this
                        && threads[i].clientName != null) {
                        threads[i].oos.writeObject("*** The user " + name
                            + " is leaving the chat room !!! ***");
                    }
                }
            }
            oos.writeObject("*** Bye " + name + " ***");

            /*
             * Clean up. Set the current thread variable to null so that a new client
             * could be accepted by the server.
             */
            synchronized (this) {
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == this) {
                        threads[i] = null;
                    }
                }
            }
            /*
             * Close the output stream, close the input stream, close the socket.
             */
            is.close();
            oos.close();
            clientSocket.close();
        } catch (IOException e) {
        }
    }

    private void command(String line) throws NotEnoughCardsException {
        String coms[] = line.split(" ");

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

    private void notifyPlayer() {
        game.notifyPlayer();
    }

    private void all(String line, String card[]) throws IOException {
        for (int i = 0; i < maxClientsCount; i++) {
            if (threads[i] != null && threads[i].clientName != null) {
                threads[i].oos.writeObject("<" + "Game" + "> " + line);
                if (line.equals("flop")) {
                    threads[i].oos.writeObject(card);
                } else if (line.equals("turn")) {
                    threads[i].oos.writeObject(card);
                } else if (line.equals("river")) {
                    threads[i].oos.writeObject(card);
                }
            }
        }
    }

    private void all(String name, String line) throws IOException, NotEnoughCardsException {

        System.out.println("ALL: " + line);

        synchronized (this) {
            String cards[] = null;
            String card = null;

            if (line.equals("flop")) {
                cards = deck.dealCardNames(3);
            } else if (line.equals("turn")) {
                card = deck.dealCard().getName();
            } else if (line.equals("river")) {
                card = deck.dealCard().getName();
            }

            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] != null && threads[i].clientName != null) {
                    threads[i].oos.writeObject("<" + name + "> " + line);
                    if (line.equals("flop")) {
                        threads[i].oos.writeObject(cards);
                    } else if (line.equals("turn")) {
                        threads[i].oos.writeObject(card);
                    } else if (line.equals("river")) {
                        threads[i].oos.writeObject(card);
                    }
                }
            }
        }
    }

    private void privateMessage(String name, String words[]) throws IOException {
        synchronized (this) {
            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] != null
                    && threads[i].clientName != null
                    && threads[i].clientName.equals(words[0])) {
                    threads[i].oos.writeObject("<" + name + "> " + words[1]);

                    String line = words[1];

                    switch (line) {
                        case "info":
                            threads[i].oos.writeObject(new PlayerInfo("Kory", 2, 200));
                            break;
                        case "message":
                            threads[i].oos.writeObject(line);
                            break;
                        case "buttons":
                            threads[i].oos.writeObject("10 20");
                            break;
                        case "cards":
                            System.err.println("HERE");
                            threads[i].oos.writeObject(deck.dealCardNames(2));
                            break;
                        case "add":
                            threads[i].oos.writeObject(new PlayerInfo("Kory", 2, 200));
                            break;
                    }
                    break;
                }
            }
        }
    }

    private void privateMessage(String name, String command, Object word, String cards[]) throws IOException {
        synchronized (this) {
            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] != null
                    && threads[i].clientName != null
                    && threads[i].clientName.equals(name)) {
                    threads[i].oos.writeObject("<" + name + "> " + command);

                    switch (command) {
                        case "info":
                            threads[i].oos.writeObject(word);
                            break;
                        case "updatePlayer":
                            threads[i].oos.writeObject(word);
                            break;
                        case "message":
                            threads[i].oos.writeObject(word);
                            break;
                        case "buttons":
                            threads[i].oos.writeObject("10 20");
                            break;
                        case "cards":
                            threads[i].oos.writeObject(cards);
                            break;
                        case "add":
                            threads[i].oos.writeObject(new PlayerInfo("Kory", 2, 200));
                            break;
                    }
                    break;
                }
            }
        }
    }

}
