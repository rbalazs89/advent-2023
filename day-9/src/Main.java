import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        List<String> input = readFile("src/input2.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static int part1(List<String> input) {
        //PROCESS FILE:
        List<List<Integer>> numbers = new ArrayList<>();

        for (String line : input) {
            List<Integer> lineNumbers = new ArrayList<>();
            Pattern pattern = Pattern.compile("-?\\d+");
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                int number = Integer.parseInt(matcher.group());
                lineNumbers.add(number);
            }
            numbers.add(lineNumbers);
        }

        //LOGIC:
        int solution = 0;

        for (List<Integer> wip : numbers) {
            while (!wip.stream().allMatch(x -> x == 0)) {
                solution += wip.get(wip.size() - 1);
                List<Integer> nextLine = new ArrayList<>();
                for (int i = 0; i < wip.size() - 1; i++) {
                    int diff = wip.get(i + 1) - wip.get(i);
                    nextLine.add(diff);
                }
                wip = nextLine;
            }
        }
        return solution;
    }

    public static int part2(List<String> input) {
        //PROCESS FILE:
        List<List<Integer>> numbers = new ArrayList<>();

        for (String line : input) {
            List<Integer> lineNumbers = new ArrayList<>();
            Pattern pattern = Pattern.compile("-?\\d+");
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                int number = Integer.parseInt(matcher.group());
                lineNumbers.add(number);
            }
            numbers.add(lineNumbers);
        }

        //LOGIC:
        List<Integer> solutions = new ArrayList<>();
        for (List<Integer> wip : numbers) {
            List<Integer> firstColumn = new ArrayList<>();
            while (!wip.stream().allMatch(x -> x == 0)) {
                firstColumn.add(wip.get(0));
                List<Integer> nextLine = new ArrayList<>();
                for (int i = 0; i < wip.size() - 1; i++) {
                    int diff = wip.get(i + 1) - wip.get(i);
                    nextLine.add(diff);
                }
                wip = nextLine;
            }
            System.out.println(firstColumn);
            int tempNumber = 0;
            for (int i = 0; i < firstColumn.size(); i++) {
                tempNumber = firstColumn.get(firstColumn.size() - 1 - i) - tempNumber;
                System.out.println(tempNumber);
                if(i == firstColumn.size() - 1){
                    solutions.add(tempNumber);
                }
            }
        }

        int sum = 0;
        for (int i = 0; i < solutions.size(); i++) {
            sum += solutions.get(i);
        }
        return sum;
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