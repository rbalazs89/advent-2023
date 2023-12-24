import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static ArrayList<Brick> bricks = new ArrayList<>();
    static ArrayList<CubicTile> tiles = new ArrayList<>();
    //tatic int tiles[][][];
    static int smallestX = Integer.MAX_VALUE;
    static int largestX = Integer.MIN_VALUE;
    static int smallestY = Integer.MAX_VALUE;
    static int largestY = Integer.MIN_VALUE;
    static int smallestZ = Integer.MAX_VALUE;
    static int largestZ = Integer.MIN_VALUE;

    static ArrayList<Brick> tempBricks = new ArrayList<>();

    public static void main(String[] args) {
        List<String> input = readFile("src/input3.txt");
        processFile(input);
    }

    public static void processFile(List<String> input) {

        //filling bricks list area:
        for (int i = 0; i < input.size(); i++) {
            String[] coordinates1 = input.get(i).split("~")[0].split(",");
            String[] coordinates2 = input.get(i).split("~")[1].split(",");
            Brick brick = new Brick(Math.min(Integer.parseInt(coordinates1[0]),Integer.parseInt(coordinates2[0])), Math.max(Integer.parseInt(coordinates1[0]),Integer.parseInt(coordinates2[0])),
                    Math.min(Integer.parseInt(coordinates1[1]),Integer.parseInt(coordinates2[1])), Math.max(Integer.parseInt(coordinates1[1]),Integer.parseInt(coordinates2[1])),
                    Math.min(Integer.parseInt(coordinates1[2]),Integer.parseInt(coordinates2[2])), Math.max(Integer.parseInt(coordinates1[2]),Integer.parseInt(coordinates2[2])));
            bricks.add(brick);
        }

        //find out the full space where bricks are
        for (Brick brick : bricks) {
            smallestX = Math.min(smallestX, Math.min(brick.startingX, brick.endingX));
            largestX = Math.max(largestX, Math.max(brick.startingX, brick.endingX));
            smallestY = Math.min(smallestY, Math.min(brick.startingY, brick.endingY));
            largestY = Math.max(largestY, Math.max(brick.startingY, brick.endingY));
            smallestZ = 0;
            //smallestZ = Math.min(smallestZ, Math.min(brick.startingZ, brick.endingZ));
            largestZ = Math.max(largestZ, Math.max(brick.startingZ, brick.endingZ + 1));
        }

        //filling all cubic tiles this could be with Array instead of arraylist?
        for (int i = smallestZ; i < largestZ + 1; i++) {
            for (int j = smallestY; j < largestY + 1; j++) {
                for (int k = smallestX; k < largestX + 1; k++) {
                    CubicTile cubicTile = new CubicTile(k, j, i, false);
                    if(i == 0){
                        cubicTile.isOccupied = true;
                    }
                    tiles.add(cubicTile);
                }
            }
        }

        //cubic tiles occupied or not
        cubicTilesRefresh();
        for (int i = 0; i < 10; i++) {

            //bricks falling in place:
            fallingInPlace();
        }


        //now after bricks fell down, check which are safe to remove:
        int solution = 0;
        for (Brick brick : bricks) {
            tempBricks.add(new Brick(brick));
        }

        for (int i = 0; i < tempBricks.size(); i++) {
            int tempX1 = tempBricks.get(i).startingX;
            int tempX2 = tempBricks.get(i).endingX;
            int tempY1 = tempBricks.get(i).startingY;
            int tempY2 = tempBricks.get(i).endingY;
            int tempZ1 = tempBricks.get(i).startingZ;
            int tempZ2 = tempBricks.get(i).endingZ;
            if(tempX1 == tempX2 && tempY1 == tempY2){
                for (int j = tempZ1; j < tempZ2 + 1; j++) {
                    findTile(tempX1, tempY1, j).isOccupied = false;
                }
            }
            else if(tempX1 == tempX2 && tempZ1 == tempZ2){
                for (int j = tempY1; j < tempY2 + 1; j++) {
                    findTile(tempX1, j, tempZ1).isOccupied = false;
                }
            }
            else if(tempY1 == tempY2 && tempZ1 == tempZ2){
                for (int j = tempX1; j < tempX2 + 1; j++) {
                    findTile(j, tempY1, tempZ1).isOccupied = false;
                }
            }
            if(isItSafeToRemove()){
                System.out.println(i);
                solution++;
            }
            if(tempX1 == tempX2 && tempY1 == tempY2){
                for (int j = tempZ1; j < tempZ2 + 1; j++) {
                    findTile(tempX1, tempY1, j).isOccupied = true;
                }
            }
            else if(tempX1 == tempX2 && tempZ1 == tempZ2){
                for (int j = tempY1; j < tempY2 + 1; j++) {
                    findTile(tempX1, j, tempZ1).isOccupied = true;
                }
            }
            else if(tempY1 == tempY2 && tempZ1 == tempZ2){
                for (int j = tempX1; j < tempX2 + 1; j++) {
                    findTile(j, tempY1, tempZ1).isOccupied = true;
                }
            }
        }
        System.out.println(solution);
        tempBricks.clear();
    }

    public static void cubicTilesRefresh(){
        for (int i = 0; i < bricks.size(); i++) {
            Brick brick = bricks.get(i);
            if(brick.startingX == brick.endingX && brick.startingY == brick.endingY){
                for (int j = brick.startingZ; j < brick.endingZ + 1; j++) {
                    CubicTile tile = findTile(brick.endingX, brick.startingY, j);
                    tile.isOccupied = true;
                }
            }

            else if(brick.startingZ == brick.endingZ && brick.startingY == brick.endingY){
                for (int j = brick.startingX; j < brick.endingX + 1; j++) {
                    //System.out.println(j + " " + brick.startingY + " " + brick.startingZ);
                    CubicTile tile = findTile(j,  brick.startingY, brick.startingZ);
                    tile.isOccupied = true;
                }
            }

            else if(brick.startingZ == brick.endingZ && brick.startingX == brick.endingX){
                for (int j = brick.startingY; j < brick.endingY + 1; j++) {
                    CubicTile tile = findTile(brick.startingX, j, brick.startingZ);
                    tile.isOccupied = true;
                }
            }
        }
    }

    public static void fallingInPlace(){
            for (int i = 0; i < bricks.size(); i++) {
                Brick brick = bricks.get(i);
                boolean restartInnerLoop = false;
                insideLoop:
                for (int j = brick.startingX; j < brick.endingX + 1; j++) {
                    for (int k = brick.startingY; k < brick.endingY + 1; k++) {
                        CubicTile tile = findTile(j, k, brick.startingZ - 1);
                        if (tile != null) {
                            if (tile.isOccupied) {
                                break insideLoop;
                            }
                            if (j == brick.endingX && k == brick.endingY) {
                                refreshTile(brick);
                                restartInnerLoop = true;
                            }
                        }
                    }
                }

                if (restartInnerLoop) {
                    i--; // Restart the iteration for the current brick, to check if it can fall further
                }
            }
        }

    public static boolean isItSafeToRemove(){
        for (int i = 0; i < tempBricks.size(); i++) {
            Brick brick = tempBricks.get(i);
            insideLoop:
            for (int j = brick.startingX; j < brick.endingX + 1; j++) {
                for (int k = brick.startingY; k < brick.endingY + 1; k++) {
                    CubicTile tile = findTile(j, k, brick.startingZ - 1);
                    if (tile != null) {
                        if (tile.isOccupied) {
                            break insideLoop;
                        }
                        if (j == brick.endingX && k == brick.endingY) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    public static void refreshTile(Brick brick){
        //this method assumes the called cube will fall down, and updates
        for (int i = brick.startingX; i < brick.endingX + 1; i++) {
            for (int j = brick.startingY; j < brick.endingY + 1; j++) {
                findTile(i, j, brick.startingZ - 1).isOccupied = true;
                findTile(i, j, brick.endingZ).isOccupied = false;
            }
        }
        brick.startingZ--;
        brick.endingZ--;
    }


    public static CubicTile findTile(int x, int y, int z){
        for (int i = 0; i < tiles.size(); i++) {
            if(tiles.get(i).x == x && tiles.get(i).y == y && tiles.get(i).z == z){
                return tiles.get(i);
            }
        }
        return null;
    }

    public static List<String> readFile(String file) {
        Path filePath = Paths.get(file);
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e){
            System.err.println("beep beep error");
            return new ArrayList<>();
        }
    }
}