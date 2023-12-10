import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static ArrayList<Tile> tiles = new ArrayList<>();
    static int startingX;
    static int startingY;
    static int STileType = 4;
    static int lineLength;
    static int startingDirection = 2;

    public static void main(String[] args) {
        List<String> input = readFile("src/input.txt");
        processFile(input);
        System.out.println(part1());

    }

    public static int part1(){

        int steps = 0;
        int currentX = startingX;
        int currentY = startingY;
        int directionTo = startingDirection;

        while(steps >= 0) {

            if (directionTo == 1) {
                currentY--;
            } else if (directionTo == 2) {
                currentX++;
            } else if (directionTo == 3) {
                currentY++;
            } else if (directionTo == 4) {
                currentY--;
            }
            Tile currentTile = tiles.get(currentY * lineLength + currentY);

            int tempInt = 0;
            if(directionTo == 1){
                if(currentTile.type == 1){
                    tempInt = 1;
                }
                if(currentTile.type == 4){
                    tempInt = 2;
                }
                if(currentTile.type == 6){
                    tempInt = 4;
                }
            }
            if(directionTo == 2){
                if(currentTile.type == 2){
                    tempInt = 2;
                }
                if(currentTile.type == 3){
                    tempInt = 1;
                }
                if(currentTile.type == 6){
                    tempInt = 3;
                }
            }
            if(directionTo == 3){
                if(currentTile.type == 1){
                    tempInt = 3;
                }
                if(currentTile.type == 3){
                    tempInt = 4;
                }
                if(currentTile.type == 5){
                    tempInt = 2;
                }
            }
            if(directionTo == 4){
                if(currentTile.type == 2){
                    tempInt = 4;
                }
                if(currentTile.type == 4){
                    tempInt = 3;
                }
                if(currentTile.type == 5){
                    tempInt = 1;
                }
            }
            directionTo = tempInt;

            steps ++ ;
            if(currentX == startingX && currentY == startingY){
                break;
            }
        }
        return steps;
    }

    // 1: top and bottom |
    // 2: right and left -
    // 3: J
    // 4: F
    // 5: L
    // 6: 7
    public static void processFile(List<String> input){
        lineLength = input.get(0).length();
        for(int i = 0; i < input.size(); i ++){
            for(int j = 0; j < input.get(i).length(); j ++) {
                Tile tile = new Tile(0, j, i);
                char currentChar = input.get(i).charAt(j);
                switch (currentChar) {
                    case '|' -> tile.type = 1;
                    case '-' -> tile.type = 2;
                    case 'J' -> tile.type = 3;
                    case 'F' -> tile.type = 4;
                    case 'L' -> tile.type = 5;
                    case '7' -> tile.type = 6;
                    case 'S' -> tile.type = 0;
                }
                tiles.add(tile);
            }
        }
        for(int i = 0; i < tiles.size(); i ++){
            if(tiles.get(i).type == 0){
                startingX = tiles.get(i).x;
                startingY = tiles.get(i).y;
                tiles.get(i).type = STileType;
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