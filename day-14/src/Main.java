import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    static ArrayList<CubeRock> cubeRocks = new ArrayList<>();
    static ArrayList<RoundRock> roundRocks = new ArrayList<>();

    public static void main(String[] args) {
        List<String> input = readFile("src/input2.txt");
        System.out.println(part1(input));
    }

    public static int part1(List<String> input){
        //PROCESS FILE:
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if(input.get(i).charAt(j) == 'O'){
                    roundRocks.add(new RoundRock(j,i));
                } else if(input.get(i).charAt(j) == '#'){
                    cubeRocks.add(new CubeRock(j,i));
                }
            }
        }

        //ROLL EVERYTHING TO NORTH:
        for (int i = 0; i < roundRocks.size(); i++) {
            int x = roundRocks.get(i).x;
            int y = roundRocks.get(i).y;

            ArrayList<Integer> tempList = new ArrayList<>();
            for (int j = 0; j < roundRocks.size(); j++) {
                if (roundRocks.get(j).x == x && roundRocks.get(j).y < y){
                    tempList.add(roundRocks.get(j).y);
                }
            }
            for (int j = 0; j < cubeRocks.size(); j++) {
                if (cubeRocks.get(j).x == x && cubeRocks.get(j).y < y){
                    tempList.add(cubeRocks.get(j).y);
                }
            }
            Collections.sort(tempList, Collections.reverseOrder());
            if (tempList.size() == 0){
                roundRocks.get(i).y = 0;
            }
            if (tempList.size() != 0){
                roundRocks.get(i).y = tempList.get(0) + 1;
            }
        }

        //CALCULATE LOAD:
        int length = input.size();
        System.out.println(length);

        int load = 0;
        for (int i = 0; i < roundRocks.size(); i++) {
            load = load + length - roundRocks.get(i).y;
        }

        return load;
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