import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    static int width;
    static int length;
    static int[][] table;
    static ArrayList<CubeRock> cubeRocks = new ArrayList<>();
    static ArrayList<RoundRock> roundRocks = new ArrayList<>();


    public static void main(String[] args) {
        List<String> input = readFile("src/input2.txt");
        System.out.println(part2(input));
        //part2(input);
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
        processFile2(input);

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

        int state1 = calculateLoad();
        boolean addthisonce = false;
        for (int i = 100; i < 1000000000; i++) {

            if(!addthisonce){
                i = i + 34 * 29411700;
                addthisonce = true;
            }
            rollUp2();
            rollLeft2();
            rollDown2();
            rollRight2();
            /*if (Arrays.deepEquals(table, table2)) {
                System.out.println("printme");
                System.out.println(i);
            }*/
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
    public static void processFile2(List<String> input){
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

    //////////////////////////////////////////////////////////////////////////////////////
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