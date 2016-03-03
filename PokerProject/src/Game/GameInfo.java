package Game;

class GameInfo {

    int pot;
    int small;
    int big;

    public GameInfo() {
        pot = 0;
        small = 5;
        big = small * 2;
    }

    public void reset() {
        pot = 0;
    }

}
