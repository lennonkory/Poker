package DateBase;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Connects to a Database. Allows for insertion of players into the database.
 * Allows for checking of passwords.
 * @author kory
 */
public class DataBase {

    private Connection connect;
    private Statement statement;
    private PreparedStatement pstate;
    private ResultSet res;

    private final PasswordConstructor pc;

    /**
     * Creates a database connection.
     * @param url where the database is
     * @param username owner of the database
     * @param password owners password
     */
    public DataBase(String url, String username, String password) {

        pc = PasswordConstructor.getPasswordConstructor();

        System.out.println("Connecting database...");
        System.out.println(url + " " + username + " " + password);

        try {
            connect = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected!");
        } catch (SQLException e) {
            Logger l = Logger.getLogger(DataBase.class.getName());
            l.log(Level.SEVERE, e.getMessage());
            System.exit(0);
        }

    }

    /**
     * Checks if a password matches what the user typed in
     *
     * @param username.
     * @param password
     * @return
     */
    public boolean checkPassword(String username, String password) {

        boolean match = false;

        try {

            String saltStr = null;
            String pas = null;

            statement = connect.createStatement();

            res = statement.executeQuery("select * from Players.players where username='" + username + "';");

            while (res.next()) {
                pas = res.getString("password");
                saltStr = res.getString("salt");
            }
            
            if (pas == null || saltStr == null) {
                return false;
            }

            match = pc.compare(saltStr, password, pas);

        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return match;
    }

    /**
     * Adds a player profile to the database. Passwords are not stored in the
     * player profile
     *
     * @param pf player profile
     * @return true if the player is added, false other wise
     */
    public boolean addPlayerProfile(PlayerProfile pf) {

        byte salt[] = pc.getSalt(32);

        String strPass = null;

        try {
            statement = connect.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            res = statement.executeQuery("select * from Players.players where username='" + pf.getUsername() + "';");
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (checkEntry(res, "username", pf.getUsername())) {
            return false;
        }

        try {
            byte hashedPassword[] = pc.hash(salt, pf.getPassword());
            strPass = pc.byteToString(hashedPassword);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeySpecException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        try {
            statement = connect.createStatement();
            statement.executeUpdate("INSERT INTO players values(default,'" + pf.getUsername() + "', '" + strPass + "'," + pf.getChips() + ", '" + pc.byteToString(salt) + "');");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }

        //System.out.println("INSERT INTO players values(default,'" + pf.getUsername() + "', '"+password +"'," +pf.getChips()+ ", '"+salt +"');");
        return false;

    }

    /**
     * Reads from database, more of a test method
     * @throws java.sql.SQLException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.spec.InvalidKeySpecException
     * @throws java.io.UnsupportedEncodingException
     */
    public void readDataBase() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException {

        statement = connect.createStatement();
        res = statement.executeQuery("select * from Players.players");
        //writeResultSet(res);
        byte salt[] = new byte[2];
        salt[0] = 100;
        salt[1] = -99;
        statement = connect.createStatement();

        statement.executeUpdate("UPDATE players SET password=\'" + pc.byteToString(pc.hash(salt, "newpass")) + " \' where username='Amy';");
        res = statement.executeQuery("select * from Players.players where username='Amy'");

    }

    /**
     * Checks to see if there is an entry in the database
     */
    private boolean checkEntry(ResultSet res, String field, String expected) {

        try {
            while (res.next()) {
                if (res.getString(field).equals(expected)) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return false;

    }

    /**
     * Checks if the Player is in the database
     *
     * @param username The name to check.
     * @return true if username is in the database, false otherwise.
     */
    public boolean inDataBase(String username) {

        try {
            statement = connect.createStatement();
            res = statement.executeQuery("select * from Players.players where username='" + username + "';");

            while (res.next()) {
                String name = res.getString("username");
                return name != null;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *Returns a player profile.
     * @param name The players name.
     * @return The profile associated with the name.
     */
    public PlayerProfile getProfile(String name) {

        PlayerProfile pf = null;

        try {
            statement = connect.createStatement();
            res = statement.executeQuery("select * from Players.players where username='" + name + "';");
            res.next();
            pf = new PlayerProfile(res.getString("username"),res.getInt("chips"),res.getString("picture"));
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pf;
    }

    private void writeResultSet(ResultSet res) throws SQLException {

        while (res.next()) {
            String user = res.getString("username");
            String pas = res.getString("password");
            String salt = res.getString("salt");
            int chips = res.getInt("chips");

            System.out.println("User: " + user + " Password: " + pas + " Salt: " + salt + " Chips: " + chips);
        }

    }

    private void upDateChips(String username, int chips) {
        try {
            statement = connect.createStatement();
            statement.executeLargeUpdate("UPDATE players SET chips=" + chips + " WHERE username='" + username + "';");
        } catch (SQLException ex) {
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    /**
     *Returns a list of Games.
     * @return A list of Games.
     * @throws SQLException
     */
    public List<GameData> getGames() throws SQLException {

        List<GameData> games = new ArrayList<>();

        statement = connect.createStatement();

        res = statement.executeQuery("select * from Players.Game;");

        while (res.next()) {
            games.add(new GameData(res.getInt("id"), res.getString("name"), res.getInt("numPlayers"), res.getInt("small"), res.getInt("big"), res.getFloat("id")));
        }

        return games;

    }

    /**
     *
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeySpecException
     */
    public static void testDB() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException {

        String url = "jdbc:mysql://192.168.0.103:3306/Players";
        String username = "kbryson";
        String password = "password";

        DataBase d = new DataBase(url, username, password);

        for (GameData gd : d.getGames()) {
            System.out.println(gd.toString());
        }
    }

    /**
     *
     * @param args
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeySpecException
     */
    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException {

        testDB();

    }

}
