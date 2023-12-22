import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    // 1249 too high
    // S starting direction and type must be typed manually
    static ArrayList<Tile> tiles = new ArrayList<>();
    static ArrayList<Tile> walkedTiles = new ArrayList<>();
    static int startingX;
    static int startingY;
    static int STileType = 3;
    static int lineLength;
    static int startingDirection = 1;
    static int width;
    static int length;

    public static void main(String[] args) {
        List<String> input = readFile("src/input2.txt");
        processFile(input);
        System.out.println(part1());
        System.out.println(part2());

    }

    public static long part2(){

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
                currentX--;
            }

            Tile currentTile = tiles.get(currentY * lineLength + currentX);
            walkedTiles.add(currentTile);

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
        Tile tile = new Tile(STileType, startingX, startingY);
        walkedTiles.add(tile);


        //calculating area based on coordinates:
        long area = 0L;
        for (int i = 0; i < walkedTiles.size() - 1; i++) {
            area = area + ((long)walkedTiles.get(i).x - (long)walkedTiles.get(i + 1).x) * ((long)walkedTiles.get(i).y + (long)walkedTiles.get(i + 1).y);
        }
        area = area / 2;

        //calculating fence area:
        long fenceArea = 14126;

        //adjustment:
        area = area - ((fenceArea - 4)/2) + fenceArea - 1;
        area = area - fenceArea;

        //another method to calculate inside area, this gaves the same result too :)
        boolean inside = false;
        boolean topLeftType = false;
        boolean bottomLeftType = false;
        int solution = 0;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                Tile myTile = getTile(j,i);

                if(inside){
                    if(myTile == null){
                        solution ++;
                        continue;
                    }
                }
                if (myTile != null) {

                    if(myTile.type == 1) {
                        inside = !inside;
                        continue;
                    }
                    if(myTile.type == 4) {
                        topLeftType = true;
                        continue;
                    }
                    if(myTile.type == 5) {
                        bottomLeftType = true;
                        continue;
                    }

                    //top left type
                    if(topLeftType && !inside){
                        if(myTile.type == 3) {
                            topLeftType = false;
                            inside = true;
                            continue;
                        }
                    }
                    if(topLeftType && inside){
                        if(myTile.type == 3) {
                            inside = false;
                            topLeftType = false;
                            continue;
                        }
                    }
                    if(topLeftType && !inside){
                        if(myTile.type == 6) {
                            topLeftType = false;
                            continue;
                        }
                    }
                    if(topLeftType && inside){
                        if(myTile.type == 6) {
                            topLeftType = false;
                            continue;
                        }
                    }
                    //bottom left type
                    if(bottomLeftType && !inside){
                        if(myTile.type == 3) {
                            bottomLeftType = false;
                            continue;
                        }
                    }
                    if(bottomLeftType && inside){
                        if(myTile.type == 3) {
                            bottomLeftType = false;
                            continue;
                        }
                    }
                    if(bottomLeftType && !inside){
                        if(myTile.type == 6) {
                            bottomLeftType = false;
                            inside = true;
                            continue;
                        }
                    }
                    if(bottomLeftType && inside){
                        if(myTile.type == 6) {
                            bottomLeftType = false;
                            inside = false;
                        }
                    }
                }
            }
            inside = false;
            topLeftType = false;
            bottomLeftType = false;
        }

        System.out.println(solution);
        return area;
    }
    public static Tile getTile(int x, int y){
        for (int i = 0; i < walkedTiles.size(); i++) {
            if(walkedTiles.get(i).x == x && walkedTiles.get(i).y == y){
                return walkedTiles.get(i);
            }
        }
        return null;
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
                currentX--;
            }

            Tile currentTile = tiles.get(currentY * lineLength + currentX);

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
        return steps/2;
    }

    // 1: top and bottom |
    // 2: right and left -
    // 3: J
    // 4: F
    // 5: L
    // 6: 7
    public static void processFile(List<String> input){
        length = input.get(0).length();
        width = input.size();

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
                        case '.' -> tile.type = 7;
                    }
                    tiles.add(tile);
            }
        }
        for(int i = 0; i < tiles.size(); i ++){
            if(tiles.get(i).type == 0){
                System.out.println("processfile printme");
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