package DateBase;

/**
 * @author kory
 */
public class PlayerProfile {
    
    private String username;
    private String password;
    private int chips;
    private String picLocation;
    
    public PlayerProfile(String u, String p, int c){
        username = u;
        password = p;
        chips = c;
    }
    
    public PlayerProfile(String u, int c, String pic){
        username = u;
        password = null;
        chips = c;
        picLocation = pic;
    }
    
    
    public String getPicLocation() {
        return picLocation;
    }

    public void setPicLocation(String picLocation) {
        this.picLocation = picLocation;
    }
    
    public String getUsername(){
        return username;
    }
    
    public int getChips(){
        return chips;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "PlayerProfile{" + "username=" + username + ", password=" + password + ", chips=" + chips + '}';
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param chips the chips to set
     */
    public void setChips(int chips) {
        this.chips = chips;
    }
    

}
