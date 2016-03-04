package Player;

/**
 * Is thrown whenever an invalid bet is made.
 * @author Kory Bryson
 */
public class InvalidBetException extends Exception {

    /**
     * Calls Super.
     * @param message
     */
    public InvalidBetException(String message) {
        super(message);
    }

}
