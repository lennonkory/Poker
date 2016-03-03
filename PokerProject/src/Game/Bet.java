package Game;

public class Bet {

    String choice;
    int betAmount;

    public Bet(String c, int b) {
        this.choice = c;
        this.betAmount = b;
    }
    
    @Override
    public String toString(){
        return "Bet: " + this.choice +  " " + this.betAmount;
    }

}
