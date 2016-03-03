package PlayerNOTUSED;

/**
 * Controllers communication between Sever and Client. Updates View.
 * Receives Input from View
 * @author Kory Bryson
 */
public abstract class Communicator {

    /**
     * Gets a message from the User.
     * @return the message from the user.
     */
    public abstract String getMessage();

    /**
     *Sends a message to the User.
     * @param str the message to be sent.
     */
    public abstract void sendMessage(String str);

    /**
     *Sends a message to the User with a newline attached.
     * @param str the message to be sent.
     */
    public abstract void sendMessageln(String str);

}
