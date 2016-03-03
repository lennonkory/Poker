package PlayerNOTUSED;

import ComTest.*;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kory Bryson
 */
public class SocketCommunicator extends Communicator {

    PrintWriter writer;
    BufferedReader stdIn;

    SocketCommunicator(Socket s) throws IOException {
        writer = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);
        stdIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    /**
     *This method retrieves a String from a socket. If the socket has a String ready,
     * it returns it right away. If the Socket does not have a string ready it waits.
     * @return
     */
    @Override
    public String getMessage() {
        String returnStr = null;
        try {
            if(stdIn.ready()){
                return stdIn.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(SocketCommunicator.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while (!stdIn.ready() && returnStr==null) {
                returnStr = stdIn.readLine();
            }
            /*while ((userInput = stdIn.readLine()) != null) {
             returnStr = userInput;
             System.out.println(userInput);
             }*/
        } catch (IOException ex) {
            Logger.getLogger(SocketCommunicator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnStr;
    }

    /**
     *
     * @param str
     */
    @Override
    public void sendMessage(String str) {
        writer.print(str);
    }

    /**
     *
     * @param str
     */
    @Override
    public void sendMessageln(String str) {
        writer.println(str);
    }
}
