import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    static int width;
    static int length;
    static int[][] table;

    public static void main(String[] args) {
        List<String> input = readFile("src/input2.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static int part1(List<String> input){
        processFile(input);
        rollUp2();
        return calculateLoad();
    }

    public static int part2(List<String> input){
        // Start rotation to get the repeating cycle and to see how many times in needs to be repeated
        processFile(input);
        for (int i = 0; i < 100; i++) {
            rollUp2();
            rollLeft2();
            rollDown2();
            rollRight2();
        }

        int[][] table2 = new int[table.length][];
        for (int i = 0; i < table.length; i++) {
            table2[i] = table[i].clone();
        }

        int period = 0;
        int repeatThisManyTimes;

        for (int i = 100; i < 1000; i++) {
            rollUp2();
            rollLeft2();
            rollDown2();
            rollRight2();
            if (Arrays.deepEquals(table, table2)) {
                period = i - 99;
                break;
            }
        }
        repeatThisManyTimes = 999999900 / period;
        repeatThisManyTimes = repeatThisManyTimes - 1;


        //calculate the final state:
        processFile(input);
        for (int i = 0; i < 100; i++) {
            rollUp2();
            rollLeft2();
            rollDown2();
            rollRight2();
        }
        boolean addThisOnce = false;
        for (int i = 100; i < 1000000000; i++) {
            if(!addThisOnce){
                i = i + period * repeatThisManyTimes;
                addThisOnce = true;
            }
            rollUp2();
            rollLeft2();
            rollDown2();
            rollRight2();
        }
        return calculateLoad();
    }

    public static int calculateLoad(){
        int load = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if(table[i][j] == 1){
                    load += length - j;
                }
            }
        }
        return load;
    }
    public static void processFile(List<String> input){
        width = input.get(0).length();
        length = input.size();
        table = new int[width][length];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if(input.get(j).charAt(i) == 'O'){
                    table[i][j] = 1;
                } else if(input.get(j).charAt(i) == '#'){
                    table[i][j] = 2;
                }
            }
        }
    }

    public static void rollUp2(){
        for (int i = 0; i < width; i++) {
            for (int j = 1; j < length; j++) {
                if(table[i][j] == 1){
                    boolean changed = false;
                    for (int k = j - 1; k >= 0; k--) {
                        if (table[i][k] > 0) {
                            table[i][j] = 0;
                            table[i][k+1] = 1;
                            changed = true;
                            break;
                        }
                    }
                    if (!changed){
                        table[i][j] = 0;
                        table[i][0] = 1;
                    }
                }
            }
        }
    }

    public static void rollDown2(){
        for (int i = 0; i < width; i++) {
            for (int j = length - 1; j >= 0; j--) {
                if(table[i][j] == 1){
                    boolean changed = false;
                    for (int k = j + 1; k < length; k++) {
                        if (table[i][k] > 0) {
                            table[i][j] = 0;
                            table[i][k - 1] = 1;
                            changed = true;
                            break;
                        }
                    }
                    if (!changed){
                        table[i][j] = 0;
                        table[i][length - 1] = 1;
                    }
                }
            }
        }
    }

    public static void rollLeft2(){
        for (int i = 1; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if(table[i][j] == 1){
                    boolean changed = false;
                    for (int k = i - 1; k >= 0; k--) {
                        if (table[k][j] > 0) {
                            table[i][j] = 0;
                            table[k + 1][j] = 1;
                            changed = true;
                            break;
                        }
                    }
                    if (!changed){
                        table[i][j] = 0;
                        table[0][j] = 1;
                    }
                }
            }
        }
    }

    public static void rollRight2(){
        for (int i = width - 2; i >= 0; i--) {
            for (int j = 0; j < length; j++) {
                if(table[i][j] == 1){
                    boolean changed = false;
                    for (int k = i + 1; k < width; k++) {
                        if (table[k][j] > 0) {
                            table[i][j] = 0;
                            table[k - 1][j] = 1;
                            changed = true;
                            break;
                        }
                    }
                    if (!changed){
                        table[i][j] = 0;
                        table[width - 1][j] = 1;
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

    public static void printTable(){
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(table[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}