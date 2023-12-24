import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static ArrayList<Brick> bricks = new ArrayList<>();
    static ArrayList<CubicTile> tiles = new ArrayList<>();
    static int smallestX = Integer.MAX_VALUE;
    static int largestX = Integer.MIN_VALUE;
    static int smallestY = Integer.MAX_VALUE;
    static int largestY = Integer.MIN_VALUE;
    static int smallestZ = Integer.MAX_VALUE;
    static int largestZ = Integer.MIN_VALUE;

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


        for (int i = 0; i < 10; i++) {
            //cubic tiles occupied or not
            cubicTilesRefresh();

            //bricks falling in place:
            fallingInPlace();
        }
        System.out.println();
        for (int i = 0; i < tiles.size(); i++) {
            if(!tiles.get(i).isOccupied){
                System.out.println(tiles.get(i).x + " " + tiles.get(i).y + " " + tiles.get(i).z);
            }
        }
        System.out.println(bricks.get(6).startingZ);
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