public class Brick {
    int startingX = 0;
    int endingX = 0;
    int startingY = 0;
    int endingY = 0;
    int startingZ = 0;
    int endingZ = 0;

    public Brick(int startingX, int endingX, int startingY, int endingY, int startingZ, int endingZ) {
        this.startingX = startingX;
        this.endingX = endingX;
        this.startingY = startingY;
        this.endingY = endingY;
        this.startingZ = startingZ;
        this.endingZ = endingZ;
    }

    public Brick(Brick original) {
        this.startingX = original.startingX;
        this.endingX = original.endingX;
        this.startingY = original.startingY;
        this.endingY = original.endingY;
        this.startingZ = original.startingZ;
        this.endingZ = original.endingZ;
    }
}
