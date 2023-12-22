import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static int[][] table;
    static int width;
    static int length;

    public static void main(String[] args) {
        List<String> input = readFile("src/input2.txt");
        processFile(input);
        System.out.println(part1());
    }

    public static int part1(){
        int steps = 64;
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
        //printTable();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if(table[i][j] == steps+1){
                    solution++;
                }
            }
        }
        return solution;
    }
    public static void printTable(){
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(table[j][i] + " ");
            }
            System.out.println();
        }
    }

    public static void processFile(List<String> input){
        length = input.size();
        width = input.get(0).length();

        table = new int[width][length];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if(input.get(j).charAt(i) == '.'){
                    table[i][j] = 0;
                }
                if(input.get(j).charAt(i) == '#'){
                    table[i][j] = -1;
                }
                if(input.get(j).charAt(i) == 'S'){
                    table[i][j] = 1;
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