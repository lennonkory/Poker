package Player;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Kory Bryson
 */
public class Client {

    Communicator com;

    private final String hostname;
    private final int port;
    Socket socketClient;

    /**
     *
     * @param hostname
     * @param port
     */
    public Client(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
    }

    /**
     *
     * @throws UnknownHostException
     * @throws IOException
     */
    public void connect() throws UnknownHostException, IOException{
        System.out.println("Attempting to connect to "+hostname+":"+port);
        socketClient = new Socket(hostname,port);
        com = new SocketCommunicator(socketClient);
        System.out.println("Connection Established");
    }
    
    /**
     *
     * @return
     * @throws IOException
     */
    public String readResponse() throws IOException{
        return com.getMessage();
    }

    /**
     *
     * @param arg
     */
    public static void main(String arg[]){
        //Creating a SocketClient object
        Client client = new Client ("localhost",9990);
        try {
            //trying to establish connection to the server
            client.connect();
            //if successful, read response from server
            String t = client.readResponse();
            System.out.println("What: " + t);
            t = client.readResponse();
            System.out.println("What: " + t);
            client.com.sendMessage("Bet");
           

        } catch (UnknownHostException e) {
            System.err.println("Host unknown. Cannot establish connection");
        } catch (IOException e) {
            System.err.println("Cannot establish connection. Server may not be up."+e.getMessage());
        }
    }
   
}
