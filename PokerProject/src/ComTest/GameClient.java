package ComTest;

//Example 25
import GUI.GUIView;
import GUI.View;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Kory Bryson
 */
public class GameClient{

    // The client socket
    private Socket clientSocket = null;
    // The output stream
    private PrintStream os = null;
    private ObjectInputStream oos;
   
    private BufferedReader inputLine = null;
    private boolean closed = false;

    private int portNumber;
    private int count = 0;

    public GameClient(int port) {
        
        this.portNumber = port;
        String host = "localhost";
        try {
            clientSocket = new Socket(host, portNumber);
            inputLine = new BufferedReader(new InputStreamReader(System.in));
            os = new PrintStream(clientSocket.getOutputStream());

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + host);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to the host "
                + host);
        }

        System.out.println("Name: ");
        
        if (clientSocket != null && os != null) {
            try {
               
                new Thread(new GameViewClient(clientSocket)).start();

                while (!closed) {
                    os.println(inputLine.readLine().trim());
                }
                os.close();
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }

    }

    public static void main(String[] args){
        new GameClient(2222);

    }

   
}
