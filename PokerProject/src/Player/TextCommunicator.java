package Player;

import java.util.Scanner;

/**
 * Based test communicator. Uses Scanner and System.out.print to communicate with the User.
 * @author Kory Bryson
 */
public class TextCommunicator extends Communicator{

    private final Scanner input;
    
    /**
     * Creates a TextCommunicator. This is mainly for testing.
     */
    public TextCommunicator(){
        input = new Scanner(System.in);
    }
    
    /**
     * Returns input from the User. Uses the Scanner.
     * @return a string from the user.
     */
    @Override
    public String getMessage() {
        return input.nextLine();
    }

    /**
     * Displays a String on the screen. Uses out.println.
     * @param str the string to send to the user.
     */
    @Override
    public void sendMessage(String str) {
        System.out.print(str);
    }

    /**
     * Sends a message with a newline cated on.
     * @param str the message to ve send.
     */
    @Override
    public void sendMessageln(String str) {
        System.out.println(str);
    }

}
