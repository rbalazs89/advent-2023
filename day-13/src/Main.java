import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> input = readFile("src/input2.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static int part1(List<String> input) {

        //PROCESS FILE:
        ArrayList<String> reflection = new ArrayList<>();
        ArrayList<ArrayList<String>> reflections = new ArrayList<>();

        for (String line : input) {
            if (line.isEmpty()) {
                reflections.add(new ArrayList<>(reflection));
                reflection.clear();
            } else {
                reflection.add(line);
            }
        }

        if (!reflection.isEmpty()) {
            reflections.add(new ArrayList<>(reflection));
        }


        //CALCULATION:
        ArrayList<Integer> horizontals = new ArrayList<>();
        ArrayList<Integer> verticals = new ArrayList<>();

        //check horizontal reflection:
        for (int k = 0; k < reflections.size(); k++) {
            for (int i = 1; i < reflections.get(k).size(); i++) {
                int loops = Math.min(i,reflections.get(k).size() - i);
                int tempInt = 0;
                for (int j = 0; j < loops; j++) { //assume that the i-th line is the reflection and check if true
                    if(reflections.get(k).get(i-j-1).equals(reflections.get(k).get(i+j))){
                        tempInt++;
                    }
                }
                if (tempInt == loops && loops != 0){
                    horizontals.add(i);
                    break;
                }
            }
        }

        //check vertical reflection:
        //first rearrange blocks:
        ArrayList<ArrayList<String>> reversedReflections = new ArrayList<>();

        for (ArrayList<String> block : reflections) {
            int blockSize = block.size();
            int lineLength = block.get(0).length();

            ArrayList<String> transposedBlock = new ArrayList<>();
            for (int i = 0; i < lineLength; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < blockSize; j++) {
                    sb.append(block.get(j).charAt(i));
                }
                transposedBlock.add(sb.toString());
            }

            reversedReflections.add(transposedBlock);
        }

        //calculation with transposed blocks:
        
        for (int k = 0; k < reversedReflections.size(); k++) {
            for (int i = 1; i < reversedReflections.get(k).size(); i++) {
                int loops = Math.min(i,reversedReflections.get(k).size() - i);
                int tempInt = 0;
                for (int j = 0; j < loops; j++) { //assume that the i-th line is the reflection and check if true
                    if(reversedReflections.get(k).get(i-j-1).equals(reversedReflections.get(k).get(i+j))){
                        tempInt++;
                    }
                }
                if (tempInt == loops && loops != 0){
                    verticals.add(i);
                    break;
                }
            }
        }

        int solution = 0;
        for (int i = 0; i < horizontals.size(); i++) {
            solution += horizontals.get(i) * 100;
        }

        for (int i = 0; i < verticals.size(); i++) {
            solution += verticals.get(i);
        }

        return solution;
    }

    public static int part2 (List<String> input){

        //PROCESS FILE:
        ArrayList<Block> blocks = new ArrayList<>();

        ArrayList<String> reflection = new ArrayList<>();
        ArrayList<ArrayList<String>> reflections = new ArrayList<>();

        for (String line : input) {
            if (line.isEmpty()) {
                reflections.add(new ArrayList<>(reflection));
                reflection.clear();
            } else {
                reflection.add(line);
            }
        }

        if (!reflection.isEmpty()) {
            reflections.add(new ArrayList<>(reflection));
        }

        for (int i = 0; i < reflections.size(); i++) {
            Block block = new Block();
            block.content = reflections.get(i);
            blocks.add(block);
        }

        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).Vertical();
        }

        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).Horizontal();
        }



        ArrayList<Integer> horizontals = new ArrayList<>();
        ArrayList<Integer> verticals = new ArrayList<>();



        for (int i = 0; i < blocks.size(); i++) {
            horizontals.add(blocks.get(i).CheckSmudgeHorizontal());
        }

        for (int i = 0; i < blocks.size(); i++) {
            verticals.add(blocks.get(i).CheckSmudgeVertical());
        }


        int solution = 0;
        for (int i = 0; i < horizontals.size(); i++) {
            solution += horizontals.get(i) * 100;
        }

        for (int i = 0; i < verticals.size(); i++) {
            solution += verticals.get(i);
        }

        return solution;
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