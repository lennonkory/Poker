package ComTest;

//Example 26 (updated)
import Deck.Deck;
import Deck.RegularDeck;
import Game.PlayerInfo;
import java.io.*;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Scanner;

/*
 * A chat server that delivers public and private messages.
 */
public class MultiThreadChatServerSync {

    // The server socket.
    private static ServerSocket serverSocket = null;
    // The client socket.
    private static Socket clientSocket = null;

    // This chat server can accept up to maxClientsCount clients' connections.
    private static final int maxClientsCount = 10;
   // private static final SeverThread[] threads = new SeverThread[maxClientsCount];
    private static final SeverThread[] threads = new SeverThread[maxClientsCount];
    

    public static Scanner in = new Scanner(System.in);
    
    public static void main(String args[]) {

        // The default port number.
        int portNumber = 2222;
        if (args.length < 1) {
            System.out.println("Usage: java MultiThreadChatServerSync <portNumber>\n"
                + "Now using port number=" + portNumber);
        } else {
            portNumber = Integer.valueOf(args[0]);
        }

        /*
         * Open a server socket on the portNumber (default 2222). Note that we can
         * not choose a port less than 1023 if we are not privileged users (root).
         */
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e);
        }

        /*
         * Create a client socket for each connection and pass it to a new client
         * thread.
         */
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                int i = 0;
                for (i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == null) {
                        (threads[i] = new SeverThread(clientSocket, threads)).start();
                        break;
                    }
                }
                if (i == maxClientsCount) {
                    PrintStream os = new PrintStream(clientSocket.getOutputStream());
                    os.println("Server too busy. Try later.");
                    os.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}

/*
 * The chat client thread. This client thread opens the input and the output
 * streams for a particular client, ask the client's name, informs all the
 * clients connected to the server about the fact that a new client has joined
 * the chat room, and as long as it receive data, echos that data back to all
 * other clients. The thread broadcast the incoming messages to all clients and
 * routes the private message to the particular client. When a client leaves the
 * chat room this thread informs also all the clients about that and terminates.
 */
class SeverThread extends Thread {

    private String clientName = null;
    private BufferedReader is;
    private ObjectOutputStream oos;
    private Socket clientSocket = null;
    private final SeverThread[] threads;
    private final int maxClientsCount;
    public static final Deck deck = new RegularDeck();
    public static final Scanner in = new Scanner(System.in);
   
    public SeverThread(Socket clientSocket, SeverThread[] threads) {
        this.clientSocket = clientSocket;
        this.threads = threads;
        maxClientsCount = threads.length;
        deck.shuffle();
    }

    @Override
    public void run() {
        
        int maxClientsCount = this.maxClientsCount;
        SeverThread[] threads = this.threads;

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

            /* Start the conversation. */
            while (true) {
                String line;
                
                synchronized (this) {
                    line = in.nextLine().trim();
                }
                
                if (line.startsWith("/quit")) {
                    break;
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
                } else {
                    all(name, line);
                    /* The message is public, broadcast it to all other clients. */
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

    private void all(String name, String line) throws IOException {
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

}
