public class Tile {

    int type;
    // 1: top and bottom |
    // 2: right and left -
    // 3: J
    // 4: F
    // 5: L
    // 6: 7

    int directionFrom = 0;
    int directionTo = 0;
    // 1 North
    // 2 East
    // 3 South
    // 4 West

    int x;
    int y;

    public Tile(int type, int x, int y){
        this.type = type;
        this.x = x;
        this.y = y;
    }
}
