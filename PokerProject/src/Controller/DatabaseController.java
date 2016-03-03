package Controller;

import DateBase.*;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controllers access to the Database. Note that all methods call the DataBase which is part of the Model.
 * @author kory
 */
public class DatabaseController {

    DataBase database;
    
    /**
     *Creates and connects to a  new DataBase Object.
     */
    public DatabaseController (){
        String url = "jdbc:mysql://192.168.0.103:3306/Players";
        String username = "kbryson";
        String password = "password";
        
        database = new DataBase(url, username, password);

    }   
    
     /**
     *Creates and connects to a  new DataBase Object.
     * @param url location of DataBase
     * @param username username of host
     * @param password password of host
     */
    public DatabaseController (String url, String username, String password){
        database = new DataBase(url, username, password);
    }  
    
    /*Maybe return an error message*/
    /**
     *Adds a Player to the Players table. Chips are defaulted to 1000.
     * If the Username is already in the database the Player is not added.
     * @param name name of Player.
     * @param password password of Player.
     * @return true if added, false otherwise.
     */
    
    public boolean addToDataBase(String name, String password){
        
        if(database.inDataBase(name)){
            return false;
        }
        
        PlayerProfile  pf = new PlayerProfile(name, password, 1000);
        
        database.addPlayerProfile(pf);
        
        return true;
    }
    
    /**
     *Adds a Player to the Players table using a PlayerProfile Object. Returns
     * false of the username is already in the dataBase.
     * @param pf the Players profile
     * @return true if added, false otherwise.
     * @see PlayerProfile
     */
    public boolean addToDataBase(PlayerProfile pf){
        
        if(database.inDataBase(pf.getUsername())){
            return false;
        }
        
        database.addPlayerProfile(pf);
        return true;
        
    }
    
    /**
     * Validates login.
     * @param name The username of the player.
     * @param password The players password.
     * @return true if username and password match, false otherwise.
     */
    public boolean validateLogin(String name, String password){
        return database.checkPassword(name, password);
    }
    
    /**
     * Returns a list of games.
     * @return A list of Games
     * @see GameData.
     */
    public List<GameData> getGames(){
        try {
            return database.getGames();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     *Returns a PlayerProfile given a name.
     * @param name The Players name
     * @return The profile. NULL if nothing found.
     */
    public PlayerProfile getPlayerProfile(String name){
        return database.getProfile(name);
    }
    
}
