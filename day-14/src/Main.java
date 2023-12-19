import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    static int width;
    static int length;
    static ArrayList<CubeRock> cubeRocks = new ArrayList<>();
    static ArrayList<RoundRock> roundRocks = new ArrayList<>();


    public static void main(String[] args) {
        List<String> input = readFile("src/input.txt");
        System.out.println(part2(input));


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
        int load = 0;
        for (int i = 0; i < roundRocks.size(); i++) {
            load = load + length - roundRocks.get(i).y;
        }
        return load;
    }

    public static int part2(List<String> input){
        processFile(input);
        for (int i = 0; i < 1; i++) {
            rollUp();
            //rollLeft();
            //rollDown();
            //rollRight();
        }


        int load = 0;
        for (int i = 0; i < roundRocks.size(); i++) {
            load = load + length - roundRocks.get(i).y;
        }
        return load;

    }
    public static void processFile(List<String> input){
        cubeRocks.clear();
        roundRocks.clear();
        width = input.get(0).length();
        length = input.size();
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if(input.get(i).charAt(j) == 'O'){
                    roundRocks.add(new RoundRock(j,i));
                } else if(input.get(i).charAt(j) == '#'){
                    cubeRocks.add(new CubeRock(j,i));
                }
            }
        }
    }

    public static void rollDown(){
        Collections.sort(roundRocks, Comparator.comparingInt(RoundRock::getY).reversed());
        for (int i = 0; i < roundRocks.size(); i++) {
            int x = roundRocks.get(i).x;
            int y = roundRocks.get(i).y;

            ArrayList<Integer> tempList = new ArrayList<>();
            for (int j = 0; j < roundRocks.size(); j++) {
                if (roundRocks.get(j).x == x && roundRocks.get(j).y > y){
                    tempList.add(roundRocks.get(j).y);
                }
            }
            for (int j = 0; j < cubeRocks.size(); j++) {
                if (cubeRocks.get(j).x == x && cubeRocks.get(j).y > y){
                    tempList.add(cubeRocks.get(j).y);
                }
            }
            Collections.sort(tempList);
            if (tempList.size() == 0){
                roundRocks.get(i).y = length - 1;
            }
            else {
                roundRocks.get(i).y = tempList.get(0) - 1;
            }
        }
    }

    public static void rollUp(){
        Collections.sort(roundRocks, Comparator.comparingInt(RoundRock::getY));
        for (int i = 0; i < roundRocks.size(); i++) {
            int x = roundRocks.get(i).x;
            int y = roundRocks.get(i).y;

            int tempInt = 0;
            boolean changed = false;
            for (int j = 0; j < roundRocks.size(); j++) {
                if (roundRocks.get(j).x == x && roundRocks.get(j).y < y){
                    tempInt = Math.max(tempInt, roundRocks.get(j).y);
                    changed = true;
                }
            }
            for (int j = 0; j < cubeRocks.size(); j++) {
                if (cubeRocks.get(j).x == x && cubeRocks.get(j).y < y){
                    tempInt = Math.max(tempInt, cubeRocks.get(j).y);
                    changed = true;
                }
            }
            System.out.println(changed);

            if(changed){
                roundRocks.get(i).y = tempInt + 1;
            } else roundRocks.get(i).y = 0;
        }
    }

    public static void rollRight(){
        Collections.sort(roundRocks, Comparator.comparingInt(RoundRock::getX).reversed());
        for (int i = 0; i < roundRocks.size(); i++) {
            int x = roundRocks.get(i).x;
            int y = roundRocks.get(i).y;

            ArrayList<Integer> tempList = new ArrayList<>();
            for (int j = 0; j < roundRocks.size(); j++) {
                if (roundRocks.get(j).y == y && roundRocks.get(j).x > x){
                    tempList.add(roundRocks.get(j).x);
                }
            }
            for (int j = 0; j < cubeRocks.size(); j++) {
                if (cubeRocks.get(j).y == y && cubeRocks.get(j).x > x){
                    tempList.add(cubeRocks.get(j).x);
                }
            }
            Collections.sort(tempList);
            if (tempList.size() == 0){
                roundRocks.get(i).x = length - 1;
            }
            else {
                roundRocks.get(i).x = tempList.get(0) - 1;
            }
        }
    }

    public static void rollLeft(){
        Collections.sort(roundRocks, Comparator.comparingInt(RoundRock::getX));
        for (int i = 0; i < roundRocks.size(); i++) {
            int x = roundRocks.get(i).x;
            int y = roundRocks.get(i).y;

            ArrayList<Integer> tempList = new ArrayList<>();
            for (int j = 0; j < roundRocks.size(); j++) {
                if (roundRocks.get(j).y == y && roundRocks.get(j).x < x){
                    tempList.add(roundRocks.get(j).x);
                }
            }
            for (int j = 0; j < cubeRocks.size(); j++) {
                if (cubeRocks.get(j).y == y && cubeRocks.get(j).x < x){
                    tempList.add(cubeRocks.get(j).x);
                }
            }
            Collections.sort(tempList, Collections.reverseOrder());
            if (tempList.size() == 0){
                roundRocks.get(i).x = 0;
            }
            else {
                roundRocks.get(i).x = tempList.get(0) + 1;
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