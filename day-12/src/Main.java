import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static ArrayList<Record> records = new ArrayList<>();
    public static void main(String[] args) {
        List<String> input = readFile("src/input.txt");
        processFile2(input);
        System.out.println(part1());
    }

    public static int part1(){
        int solution = 0;

        for (int m = 0; m < records.size(); m++) {
            int sum = 0;
            for (int i = 0; i < records.get(m).instructions.size(); i++) {
                sum += records.get(m).instructions.get(i);
            }

            ArrayList<Integer[]> combinations = findCombinations(records.get(m).smudgedRecord.length() - sum,records.get(m).instructions.size() + 1);

            for (int i = 0; i < combinations.size(); i++) {
                String createdString = "";
                for (int j = 0; j < records.get(m).instructions.size(); j++) {
                    createdString = createdString + ".".repeat(combinations.get(i)[j]);
                    createdString = createdString + "#".repeat(records.get(m).instructions.get(j));
                    if( j == records.get(m).instructions.size() - 1){
                        createdString = createdString + ".".repeat(combinations.get(i)[j+1]);
                    }
                }
                for (int k = 0; k < createdString.length(); k++) {
                    if(createdString.substring(k, k+1).equals(records.get(m).smudgedRecord.substring(k,k+1))
                            || records.get(m).smudgedRecord.charAt(k) == '?'){

                        if(k == createdString.length() - 1) {
                            solution++;
                        }
                    }
                    else {
                        break;
                    }
                }
            }
        }
        return solution;
    }

    public static long part2(){

        return 1;
    }

    public static void processFile(List<String> input){
        for (int i = 0; i < input.size(); i++) {
            Record record = new Record();
            record.smudgedRecord = input.get(i).split(" ")[0];
            String tempString = input.get(i).split(" ")[1];
            String[] numArray = tempString.split(",");

            for (int j = 0; j < numArray.length; j++) {
                String num = numArray[j];
                int parsedNum = Integer.parseInt(num.trim());
                record.instructions.add(parsedNum);
            }
            records.add(record);
        }
    }
    public static void processFile2(List<String> input){
        records.clear();
        for (int i = 0; i < input.size(); i++) {
            Record record = new Record();
            record.smudgedRecord = input.get(i).split(" ")[0];
            record.smudgedRecord = record.smudgedRecord.repeat(5);
            String tempString = input.get(i).split(" ")[1];
            String[] numArray = tempString.split(",");


            for (int j = 0; j < numArray.length * 5; j++) {
                String num = numArray[j%numArray.length];
                int parsedNum = Integer.parseInt(num.trim());
                record.instructions.add(parsedNum);
            }
            records.add(record);
        }
    }



    public static ArrayList<Integer[]> findCombinations(int targetSum, int maxPositions) {
        ArrayList<Integer[]> solutions = new ArrayList<>();
        findCombinationsHelper(targetSum, new Integer[maxPositions], 0, solutions);
        solutions = removeBadCombinations(solutions);
        return solutions;
    }

    public static ArrayList<Integer[]> removeBadCombinations(ArrayList<Integer[]> allCombinations){
        ArrayList<Integer[]> goodCombinations = new ArrayList<>();
        for (int i = 0; i < allCombinations.size(); i++) {
            int goodCombination = 0;
            int tempLength = allCombinations.get(0).length;
            for (int j = 1; j < tempLength - 1; j++) {
                if(allCombinations.get(i)[j] > 0){
                    goodCombination++;
                }
            }
            if(goodCombination == tempLength-2){
                goodCombinations.add(allCombinations.get(i));
            }
        }
        return goodCombinations;
    }

    private static void findCombinationsHelper(int targetSum, Integer[] currentCombination, int index, ArrayList<Integer[]> solutions) {
        if (index == currentCombination.length) {
            int sum = 0;
            for (int num : currentCombination) {
                sum += num;
            }
            if (sum == targetSum) {
                solutions.add(currentCombination.clone());
            }
            return;
        }

        for (int i = 0; i <= targetSum; i++) {
            currentCombination[index] = i;
            findCombinationsHelper(targetSum, currentCombination, index + 1, solutions);
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