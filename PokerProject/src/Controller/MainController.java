package Controller;

import DateBase.DataBase;
import DateBase.GameData;
import DateBase.PlayerProfile;
import GUI.GUILobby;
import GUI.GUITable;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 * Main MainController for the game. Will contain other controllers.
 *
 * @author kory
 */
public class MainController {

    private final DatabaseController db;
    private GUILobby lobby;

    /**
     * Constructor that takes in a lobby.
     *
     * @param lobby
     */
    public MainController() {

        db = new DatabaseController();
        
        SwingUtilities.invokeLater(() -> {
            this.lobby = new GUILobby();
            addDataListener();
        });
        

    }

    /*call when using guilobby*/
    private void addDataListener() {
        
        this.lobby.addDataBaseListener(new ControllerListener() {

            @Override
            public boolean playerToAdd(String name, String password) {
                return addPlayerToDataBase(new PlayerProfile(name, password, 1000));
            }

            @Override
            public boolean validateLogin(String name, String password) {
                return db.validateLogin(name, password);
            }

            @Override
            public PlayerProfile getPlayerProfile(String name) {
                return db.getPlayerProfile(name);
            }

            @Override
            public List<GameData> getGameData() {
                return getGames();
            }
        });
    }

    private boolean addPlayerToDataBase(PlayerProfile pf) {
        db.addToDataBase(pf);
        return true;
    }

    /**
     * Returns a list of games from the database
     *
     * @return A list of games.
     * @see DataBase
     * @see GameData
     */
    public List<GameData> getGames() {
        return db.getGames();
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        MainController c = new MainController();
        
    }
}
