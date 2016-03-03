package ComTest;

import Game.Player;
import Game.Texas;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Kory Bryson
 */
public class GameServer {

    // The server socket.
    private ServerSocket serverSocket = null;
    // The client socket.
    private Socket clientSocket = null;

    // This chat server can accept up to maxClientsCount clients' connections.
    private static final int maxClientsCount = 9;
    // private static final SeverThread[] threads = new SeverThread[maxClientsCount];
    private final GameServerThread[] threads = new GameServerThread[maxClientsCount];

    private final Texas game;

    public GameServer(int port, Texas game) {
        // The default port number.
        int portNumber = port;

        try {
            serverSocket = new ServerSocket(portNumber);
            System.out.println("Usage: java GameServer <portNumber>\n"
                + "Now using port number = " + portNumber);
        } catch (IOException e) {
            System.out.println(e);
        }
        
        this.game = game;
    }

    public void start() {
        
        Player players[] = new Player[4];
        players[0] = new Player("Kory", 0);
        players[1] = new Player("Bill", 1);
        players[2] = new Player("Mike", 2);
        players[3] = new Player("James", 3);
        
        while (true) {
            try {
                clientSocket = serverSocket.accept();
                int i = 0;
                for (i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == null) {
                        game.addPlayer(players[i]);
                        (threads[i] = new GameServerThread(clientSocket, threads, game, players[i])).start();
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

    public static void main(String args[]) {

        new GameServer(2222, new Texas()).start();
    }
}
