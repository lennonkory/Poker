package Player;

import java.io.*;
import java.net.*;

/**
 *
 * @author Kory Bryson
 */
public class Server {

    Communicator com;

    private ServerSocket serverSocket;
    private final int port;

    /**
     *
     * @param port
     */
    public Server(int port) {
        this.port = port;
    }

    /**
     *
     * @throws IOException
     * @throws Player.InvalidBetException
     */
    public void start() throws IOException, InvalidBetException {

        System.out.println("Starting the socket server at port:" + port);
        serverSocket = new ServerSocket(port);

        //Listen for clients. Block till one connects
        System.out.println("Waiting for clients...");
        while (true) {
            Socket client = serverSocket.accept();
            com = new SocketCommunicator(client);
            Player p = new Player("Kory", 100, null,com);
            Bet b = new Bet(10, 10, -1);
            p.turn(b);
            //A client has connected to this server. Send welcome message
            //sendWelcomeMessage();
           
        }
    }

    private void sendWelcomeMessage() throws IOException {
        com.sendMessage("Hello. You are connected to a Simple Socket Server. What is your name?");
    }

    /**
     * Creates a SocketServer object and starts the server.
     *
     * @param args
     * @throws Player.InvalidBetException
     */
    public static void main(String[] args) throws InvalidBetException {
        // Setting a default port number.
        int portNumber = 9990;

        try {
            // initializing the Socket Server
            Server socketServer = new Server(portNumber);
            socketServer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
