import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> myInput = readFile("src/input2.txt");
        System.out.println(part1(myInput));
    }
    public static int part1(List<String> input){
        ArrayList<Integer> winningNumbers = new ArrayList<>();
        ArrayList<Integer> myNumbers = new ArrayList<>();
        int solution = 0;
        for (String s : input) {
            int hits = 0;
            for (int j = 10; j < 40; j = j + 3) {
                winningNumbers.add(Integer.valueOf(s.substring(j, j + 2).replaceAll("\\s", "")));
            }
            for (int k = 42; k < s.length(); k = k + 3) {
                myNumbers.add(Integer.valueOf(s.substring(k, k + 2).replaceAll("\\s", "")));
            }
            for (Integer winningNumber : winningNumbers) {
                if (myNumbers.contains(winningNumber)) {
                    hits++;
                }
            }
            if (hits != 0) {
                solution = solution + (int) Math.pow(2, hits - 1);
            }
            winningNumbers.clear();
            myNumbers.clear();
        }

        return solution;
    }

    public static List<String> readFile(String file) {
        Path filePath = Paths.get(file);
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            System.err.println("beep beep error");
            return new ArrayList<>();
        }
    }
}