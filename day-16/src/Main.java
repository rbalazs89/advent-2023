import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static ArrayList<Tile> table = new ArrayList<>();
    static int length;
    static int width;

    public static void main(String[] args) {
        List<String> input = readFile("src/input2.txt");
        processFile(input);

        System.out.println(part1());
    }

    public static int part1(){
        travellingLight(0,0,"right");

        int solution = 0;
        for (int i = 0; i < table.size(); i++) {
            if(table.get(i).energized){
                solution ++;
            }
        }

        return solution;
    }

    public static void travellingLight(int startingX, int startingY, String direction){
        int currentX = startingX;
        int currentY = startingY;
        String currentDirection = direction;

        while(continueToTravel(currentX, currentY, currentDirection)){

            getTile(currentX, currentY).energized = true;

            //set current tile travelled to true:
            if(currentDirection.equals("left")){
                getTile(currentX, currentY).left = true;
            }
            if(currentDirection.equals("right")){
                getTile(currentX, currentY).right = true;
            }
            if(currentDirection.equals("up")){
                getTile(currentX, currentY).up = true;
            }
            if(currentDirection.equals("down")){
                getTile(currentX, currentY).down = true;
            }

            //travel further logic
            String currentTileType = getTile(currentX, currentY).type;

            if(currentDirection.equals("left")){
                if(currentTileType.equals(".") || currentTileType.equals("-")){
                    currentDirection = "left";
                    currentX--;
                }
                else if(currentTileType.equals("\\")) {
                    currentDirection = "up";
                    currentY--;
                } else if(currentTileType.equals("/")) {
                    currentDirection = "down";
                    currentY++;
                } else if(currentTileType.equals("|")) {
                    travellingLight(currentX, currentY - 1, "up");
                    travellingLight(currentX, currentY + 1, "down");
                }
            }

            else if(currentDirection.equals("right")){
                if(currentTileType.equals(".") || currentTileType.equals("-")){
                    currentDirection = "right";
                    currentX++;
                }
                else if(getTile(currentX, currentY).type.equals("\\")) {
                    currentDirection = "down";
                    currentY++;
                } else if(currentTileType.equals("/")) {
                    currentDirection = "up";
                    currentY--;
                } else if(currentTileType.equals("|")) {
                    travellingLight(currentX, currentY - 1, "up");
                    travellingLight(currentX, currentY + 1, "down");
                }
            }

            else if(currentDirection.equals("up")){
                if(currentTileType.equals(".") || currentTileType.equals("|")) {
                    currentDirection = "up";
                    currentY--;
                }
                else if(currentTileType.equals("\\")) {
                    currentDirection = "left";
                    currentX--;
                } else if(currentTileType.equals("/")) {
                    currentDirection = "right";
                    currentX++;
                } else if(currentTileType.equals("-")){
                travellingLight(currentX - 1, currentY, "left");
                travellingLight(currentX + 1, currentY, "right");
                 }
            }

            else if(currentDirection.equals("down")){
                if(currentTileType.equals(".") || currentTileType.equals("|")) {
                    currentDirection = "down";
                    currentY++;
                }
                else if(currentTileType.equals("\\")) {
                    currentDirection = "right";
                    currentX++;
                } else if(currentTileType.equals("/")) {
                    currentDirection = "left";
                    currentX--;
                } else if(currentTileType.equals("-")){
                    travellingLight(currentX - 1, currentY, "left");
                    travellingLight(currentX + 1, currentY, "right");
                }
            }
        }
    }

    public static boolean continueToTravel(int x, int y, String direction){
        if (x < 0 || x > width || y < 0 || y > length) {
            return false;
        }

        Tile currentTile = getTile(x, y);
        if (currentTile == null) { // this should never happen
            return false;
        }

        return switch (direction) {
            case "left" -> !currentTile.left;
            case "right" -> !currentTile.right;
            case "up" -> !currentTile.up;
            case "down" -> !currentTile.down;
            default -> true;
        };
    }

    public static Tile getTile(int x, int y){
        for (int i = 0; i < table.size(); i++) {
            if(table.get(i).x == x && table.get(i).y == y){
                return table.get(i);
            }
        }
        return null;
    }

    public static void processFile(List<String> input){
        width = input.get(0).length();
        length = input.size();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                Tile tile = new Tile(i, j, input.get(j).substring(i, i+1));
                table.add(tile);
            }
        }
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