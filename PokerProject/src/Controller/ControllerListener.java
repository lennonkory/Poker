package Controller;

import DateBase.GameData;
import DateBase.PlayerProfile;
import java.util.List;

/**
 *This interface controls data between the View and the Controller. If the View needs to update the controller
 * an event is fired. The ControllerListener takes the information given and the Controller updates the Model.
 * @author kory
 */
public interface ControllerListener {

    /**
     *Fires a message to the Controller to adds a Player to the Database.
     * @param name The user name of the Player.
     * @param password The password of the Player.
     * @return true if the player has been added, false otherwise.
     */
    public boolean playerToAdd(String name, String password);

    /**
     * Checks if the log in information is correct.
     * @param name The username of the player.
     * @param password The users password.
     * @return true if the correct username and password have been entered. False otherwise.
     */
    public boolean validateLogin(String name, String password);

    /**
     *Returns the player profile. Note this will only ever be called after the player has logged in.
     * That's why only the name is needed.
     * @param name User name of the player.
     * @return The PlayerProfile associated with the name.
     * @see PlayerProfile
     */
    public PlayerProfile getPlayerProfile(String name);

    /**
     * Returns a list of all the games in the database.
     * @return A list of all the games in the database.
     * @see GameData.
     */
    public List<GameData> getGameData();
    
}
