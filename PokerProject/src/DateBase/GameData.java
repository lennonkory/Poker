package DateBase;

/**
 * @author kory
 */
public class GameData {
    
    private int id;
    private String name;
    private int numPlayers;
    private int small;
    private int big;
    private double avg;

    public GameData(int id, String name, int np,int small, int big, double avg) {
        this.id = id;
        this.name = name;
        this.numPlayers = np;
        this.small = small;
        this.big = big;
        this.avg = avg;
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "GameData{" + "id=" + id + ", name=" + name +", numPlayers=" + numPlayers + ", small=" + small + ", big=" + big + ", avg=" + avg + '}';
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the small
     */
    public int getSmall() {
        return small;
    }

    /**
     * @param small the small to set
     */
    public void setSmall(int small) {
        this.small = small;
    }

    /**
     * @return the big
     */
    public int getBig() {
        return big;
    }

    /**
     * @param big the big to set
     */
    public void setBig(int big) {
        this.big = big;
    }

    /**
     * @return the avg
     */
    public double getAvg() {
        return avg;
    }

    /**
     * @param avg the avg to set
     */
    public void setAvg(double avg) {
        this.avg = avg;
    }

}
