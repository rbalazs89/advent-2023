import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    //test:
    static ArrayList<Integer> timeValuesTest = new ArrayList<>(Arrays.asList(7, 15, 30));
    static ArrayList<Integer> distanceValuesTest = new ArrayList<>(Arrays.asList(9, 40, 200));


    //my input:
    static ArrayList<Integer> timeValues = new ArrayList<>(Arrays.asList(48, 93, 84, 66));
    static ArrayList<Integer> distanceValues = new ArrayList<>(Arrays.asList(261, 1192, 1019, 1063));

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }

    public static int part1(){
        ArrayList<Integer> solutionCounter = new ArrayList<>();
        for(int i = 0; i < timeValues.size(); i ++){
            int tempCounter = 0;
            for(int j = 0; j < timeValues.get(i); j ++){
                if((timeValues.get(i) - j) * j > distanceValues.get(i)){
                    tempCounter++;
                }
            }
            solutionCounter.add(tempCounter);
        }
        int solution = 1;

        for(int i = 0; i < solutionCounter.size(); i ++){
            solution = solution * solutionCounter.get(i);
        }

        return solution;
    }

    public static int part2(){
        int solution = 0;
        ArrayList<Long> timeValues = new ArrayList<>(List.of(48938466L));
        ArrayList<Long> distanceValues = new ArrayList<>(List.of(261119210191063L));
        for(int i = 0; i < timeValues.size(); i ++){
            for(int j = 0; j < timeValues.get(i); j ++){
                if((timeValues.get(i) - j) * j > distanceValues.get(i)){
                    solution++;
                }
            }
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