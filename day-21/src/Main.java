import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static long fullFilledEven = 7688;
    static long fullFilledOdd = 7656;
    static int[][] table;
    static int width;
    static int length;
    static int startingX;
    static int startingY;
    static int multiplier = 9; // should be infinity
    static int fullTiles = 202300;
    static int fullstepts = 26501365;
    static long X = 202300;


    public static void main(String[] args) {
        List<String> input = readFile("src/input2.txt");
        //processFilePart1(input);
        //System.out.println(part1());
        processFile(input);
        part2();
    }

    public static int part1(){
        int steps = 64 ;

        for (int i = 1; i < steps + 1; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < length; k++) {
                    if(table[j][k] == i){
                        table[j][k] = 0;

                        if(j - 1  >= 0){
                            if(table[j-1][k] == 0){
                                table[j-1][k] = i+1;
                            }
                        }

                        if(j + 1  < width){
                            if(table[j+1][k] == 0){
                                table[j+1][k] = i+1;
                            }
                        }

                        if(k + 1  < length){
                            if(table[j][k+1] == 0){
                                table[j][k+1] = i+1;
                            }
                        }
                        if(k - 1  >= 0){
                            if(table[j][k-1] == 0){
                                table[j][k-1] = i+1;
                            }
                        }

                    }
                }
            }
        }

        int solution = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if(table[i][j] == steps+1){
                    solution++;
                }
            }
        }
        //printMe();
        return solution;
    }

    public static long part2(){
        //65 + 4 * 131
        int steps = 589 ;

        for (int i = 1; i < steps + 1; i++) {
            for (int j = 0; j < width * multiplier; j++) {
                for (int k = 0; k < length * multiplier; k++) {
                    if(table[j][k] == i){
                        table[j][k] = 0;

                        if(j - 1  >= 0){
                            if(table[j-1][k] == 0){
                                table[j-1][k] = i+1;
                            }
                        }

                        if(j + 1  < width * multiplier){
                            if(table[j+1][k] == 0){
                                table[j+1][k] = i+1;
                            }
                        }

                        if(k + 1  < length * multiplier){
                            if(table[j][k+1] == 0){
                                table[j][k+1] = i+1;
                            }
                        }
                        if(k - 1  >= 0){
                            if(table[j][k-1] == 0){
                                table[j][k-1] = i+1;
                            }
                        }

                    }
                }
            }
        }

        long realSolution = 0;
        long rightFromMiddle = 0;
        long leftFromMiddle = 0;
        long leftMostMiddle = 0;
        long rightMostMiddle = 0;
        long topLeftSmall = 0;
        long topLeftBig = 0;
        long bottomLeftSmall = 0;
        long bottomLeftBig = 0;
        long topRightSmall = 0;
        long topRightBig = 0;
        long bottomRightSmall = 0;
        long bottomRightBig = 0;
        long bottomMiddle = 0;
        long topMiddle = 0;

        // realSolution:
        for (int i = 0; i < width * multiplier; i++) {
            for (int j = 0; j < length * multiplier; j++) {
                if(table[i][j] == steps+1){
                    realSolution++;
                }
            }
        }

        // right block from start
        for (int i = width * (multiplier/2 + 1); i < width * (multiplier/2 + 2); i++) {
            for (int j = length * (multiplier/2); j < length * (multiplier/2 + 1); j++) {
                if(table[i][j] == steps+1){
                    rightFromMiddle++;
                }
            }
        }

        //left block from start
        for (int i = width * (multiplier/2 + 1); i < width * (multiplier/2 + 2); i++) {
            for (int j = length * (multiplier/2); j < length * (multiplier/2 + 1); j++) {
                if(table[i][j] == steps+1){
                    leftFromMiddle++;
                }
            }
        }

        //leftmost mid:
        for (int i = 0; i < width; i++) {
            for (int j = length * (multiplier/2); j < length * (multiplier/2 + 1); j++) {
                if(table[i][j] == steps+1){
                    leftMostMiddle++;
                }
            }
        }

        //rightmost mid:
        for (int i = width * (multiplier - 1); i < width * multiplier; i++) {
            for (int j = length * (multiplier/2); j < length * (multiplier/2 + 1); j++) {
                if(table[i][j] == steps+1){
                    rightMostMiddle++;
                }
            }
        }

        //////////////////////

        //top left small:
        for (int i = 0; i < width; i++) {
            for (int j = length * (multiplier/2 - 1); j < length * (multiplier/2); j++) {
                if(table[i][j] == steps+1){
                    topLeftSmall++;
                }
            }
        }

        //top left big:
        for (int i = width; i < width*2; i++) {
            for (int j = length * (multiplier/2 - 1); j < length * (multiplier/2); j++) {
                if(table[i][j] == steps+1){
                    topLeftBig++;
                }
            }
        }

        //

        //bottom left small:
        for (int i = 0; i < width; i++) {
            for (int j = length * (multiplier/2 + 1); j < length * (multiplier/2 + 2); j++) {
                if(table[i][j] == steps+1){
                    bottomLeftSmall++;
                }
            }
        }

        //bottom left big:
        for (int i = width; i < width*2; i++) {
            for (int j = length * (multiplier/2 + 1); j < length * (multiplier/2 + 2); j++) {
                if(table[i][j] == steps+1){
                    bottomLeftBig++;
                }
            }
        }

        //////////////////////

        //top right small:
        for (int i = width * (multiplier - 1); i < width * multiplier; i++) {
            for (int j = length * (multiplier/2 - 1); j < length * (multiplier/2); j++) {
                if(table[i][j] == steps+1){
                    topRightSmall++;
                }
            }
        }

        //top right big:
        for (int i = width * (multiplier - 2); i < width * (multiplier-1); i++) {
            for (int j = length * (multiplier/2 - 1); j < length * (multiplier/2); j++) {
                if(table[i][j] == steps+1){
                    topRightBig++;
                }
            }
        }

        //

        //bottom right small:
        for (int i = width * (multiplier - 1); i < width * multiplier; i++) {
            for (int j = length * (multiplier/2 + 1); j < length * (multiplier/2 + 2); j++) {
                if(table[i][j] == steps+1){
                    bottomRightSmall++;
                }
            }
        }

        //bottom right big:
        for (int i = width * (multiplier - 2); i < width * (multiplier-1); i++) {
            for (int j = length * (multiplier/2 + 1); j < length * (multiplier/2 + 2); j++) {
                if(table[i][j] == steps+1){
                    bottomRightBig++;
                }
            }
        }

        //////////////////////////////////////////////////////////////////////

        //bottom middle:
        for (int i = width * (multiplier / 2); i < width * (multiplier / 2 + 1); i++) {
            for (int j = length * (multiplier - 1); j < length * (multiplier ); j++) {
                if(table[i][j] == steps+1){
                    bottomMiddle++;
                }
            }
        }

        //top middle:
        for (int i = width * (multiplier / 2); i < width * (multiplier / 2 + 1); i++) {
            for (int j = 0; j < length; j++) {
                if(table[i][j] == steps+1){
                    topMiddle++;
                }
            }
        }

        //printMe();
        System.out.println("right from start: " + rightFromMiddle);
        System.out.println("left from start: " + leftFromMiddle);
        System.out.println("middle: " + 7656);
        System.out.println("rightmost mid: " + rightMostMiddle);
        System.out.println("leftmost mid: " + leftMostMiddle);
        System.out.println("top left small: " + topLeftSmall);
        System.out.println("top left big: " + topLeftBig);
        System.out.println("bottom left small: " + bottomLeftSmall);
        System.out.println("bottom left big: " + bottomLeftBig);
        System.out.println("top right small: " + topRightSmall);
        System.out.println("top right big: " + topRightBig);
        System.out.println("bottom right small: " + bottomRightSmall);
        System.out.println("bottom right big: " + bottomRightBig);

        System.out.println("solution bruteforce: " + realSolution);
        System.out.println("solution calculated from parts: " + (bottomMiddle + topMiddle + leftMostMiddle + rightMostMiddle + 7656 * 3 * 3 + 4 * 4 * rightFromMiddle
        + 3 * topLeftBig + 4 * topLeftSmall + 3 * topRightBig + 4* topRightSmall
        + 3 * bottomLeftBig + 4* bottomLeftSmall + 3 * bottomRightBig + 4 * bottomRightSmall));

        /*long solution = X * (topLeftSmall + topRightSmall + bottomLeftSmall + bottomRightSmall) + (X-1) * (bottomLeftBig + bottomRightBig + topLeftBig + topRightBig) +
                1 * (topMiddle + bottomMiddle + leftMostMiddle + rightMostMiddle) + X * X * (fullFilledEven) + (X-1) * (X-1) * (fullFilledOdd);
        System.out.println(solution);*/

        BigInteger part2Solution = BigInteger.valueOf(X * (topLeftSmall + topRightSmall + bottomLeftSmall + bottomRightSmall) + (X-1) * (bottomLeftBig + bottomRightBig + topLeftBig + topRightBig) +
                1 * (topMiddle + bottomMiddle + leftMostMiddle + rightMostMiddle) + X * X * (fullFilledEven) + (X-1) * (X-1) * (fullFilledOdd));

        System.out.println(part2Solution);

        return 1;
    }

    public static void printMe(){
        String printTable[][] = new String[width * multiplier][length * multiplier];
        for (int i = 0; i < width * multiplier; i++) {
            for (int j = 0; j < length * multiplier; j++) {
                if(table[j][i] == -1){
                    printTable[j][i] = "#";
                }
                else if(table[j][i] == 0){
                    printTable[j][i] = ".";
                } else {
                    printTable[j][i] = "O";
                }
            }
        }
        for (int i = 0; i < length * multiplier; i++) {
            for (int j = 0; j < width * multiplier; j++) {
                System.out.print(printTable[j][i] + " ");
            }
            System.out.println();
        }
    }

    public static void processFile(List<String> input){

        length = input.size();
        width = input.get(0).length();

        table = new int[width*multiplier][length*multiplier];
        boolean startFound = false;

        for (int i = 0; i < width * multiplier; i++) {
            for (int j = 0; j < length * multiplier; j++) {
                if(input.get(j%width).charAt(i%length) == '.'){
                    table[i][j] = 0;
                }
                if(input.get(j%width).charAt(i%length) == '#'){
                    table[i][j] = -1;
                }

                if(!startFound) {
                    if (input.get(j%width).charAt(i%length) == 'S') {
                        startingY = j;
                        startingX = i;
                        table[j][i] = 0;
                        startFound = true;
                    }
                }
            }
        }
        table[width * (multiplier/2) + startingX][length * (multiplier/2) + startingY] = 1;
    }

    public static void processFilePart1(List<String> input){

        length = input.size();
        width = input.get(0).length();

        table = new int[width][length];
        boolean startFound = false;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if(input.get(j).charAt(i) == '.'){
                    table[i][j] = 0;
                }
                if(input.get(j).charAt(i) == '#'){
                    table[i][j] = -1;
                }

                if(!startFound) {
                    if (input.get(j).charAt(i) == 'S') {
                        startingY = j;
                        startingX = i;
                        table[j][i] = 1;
                        startFound = true;
                    }
                }
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