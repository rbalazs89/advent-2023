import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static int[][] table;
    static int width;
    static int length;
    static int startingX;
    static int startingY;
    static int multiplier = 15; // should be infinity

    public static void main(String[] args) {
        List<String> input = readFile("src/input2.txt");
        processFile(input);
        System.out.println(part1());
    }

    public static int part1(){
        int steps = 720 ;

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

        int solution = 0;

        for (int i = 0; i < width * multiplier; i++) {
            for (int j = 0; j < length * multiplier; j++) {
                if(table[i][j] == steps+1){
                    solution++;
                }
            }
        }
        //printMe();
        return solution;
    }

    public static void part2(){
        /**
            feels impossible
         */
    }

    public static void printTable() {
        int maxDigits = String.valueOf(length * multiplier * width * multiplier).length();

        for (int i = 0; i < length * multiplier; i++) {
            for (int j = 0; j < width * multiplier; j++) {
                String formatted = String.format("%" + (maxDigits + 1) + "s", table[j][i]);
                System.out.print(formatted);
            }
            System.out.println();
        }
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
        //System.out.println(width * multiplier / 50);

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