import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> input = readFile("src/input2.txt");
        System.out.println(part1(input));
    }

    public static int part1(List<String> input){

        String[] items = input.get(0).split(",\\s*");
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(items));
        ArrayList<Integer> solutions = new ArrayList<>();
        for (int j = 0; j < arrayList.size(); j++) {
            int value = 0;
            for (int i = 0; i < arrayList.get(j).length(); i++) {
                value = function(value, arrayList.get(j).substring(i,i+1));
            }
            solutions.add(value);
        }
        System.out.println(solutions);
        int result = 0;
        for (int i = 0; i < solutions.size(); i++) {
            result += solutions.get(i);
        }
        return result;
    }

    public static int function(int number, String s){
        number = number + (int)(s.charAt(0));
        number = number * 17;
        number = number % 256;
        return number;
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