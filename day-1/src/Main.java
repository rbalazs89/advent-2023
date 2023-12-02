import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isDigit;

public class Main {
    public static void main(String[] args) {
        List<String> input = readFile("src/input1.txt");
        List<Integer> lineSolutions = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();

        int solution = 0;

        for(int i = 0; i < input.size(); i ++){
            for(int j = 0; j < input.get(i).length(); j ++){
                if(isDigit(input.get(i).charAt(j))){
                    lineSolutions.add(Integer.valueOf(input.get(i).substring(j,j+1)));
                } else {
                    if(j + 3 <= input.get(i).length()){
                        if(input.get(i).substring(j,j+3).equals("one")){
                            lineSolutions.add(1);
                        }
                        if(input.get(i).substring(j,j+3).equals("two")){
                            lineSolutions.add(2);
                        }
                        if(input.get(i).substring(j,j+3).equals("six")){
                            lineSolutions.add(6);
                        }
                    }
                    if(j + 4 <= input.get(i).length()){
                        if(input.get(i).substring(j,j+4).equals("four")){
                            lineSolutions.add(4);
                        }
                        if(input.get(i).substring(j,j+4).equals("five")){
                            lineSolutions.add(5);
                        }
                        if(input.get(i).substring(j,j+4).equals("nine")){
                            lineSolutions.add(9);
                        }
                    }
                    if(j + 5 <= input.get(i).length()){
                        if(input.get(i).substring(j,j+5).equals("three")){
                            lineSolutions.add(3);
                        }
                        if(input.get(i).substring(j,j+5).equals("seven")){
                            lineSolutions.add(7);
                        }
                        if(input.get(i).substring(j,j+5).equals("eight")){
                            lineSolutions.add(8);
                        }
                    }
                }
            }

            numbers.add(lineSolutions.get(0));
            numbers.add(lineSolutions.get(lineSolutions.size()-1));
            lineSolutions.clear();
        }

        for(int i = 0 ; i < numbers.size(); i = i + 2){
            solution = solution + Integer.valueOf(String.valueOf(numbers.get(i)) + String.valueOf(numbers.get(i+1)));
        }
        System.out.println(solution);

    }

    public static List<String> readFile(String file){
        Path filePath = Paths.get(file);
        try{
            List<String> fileLines = Files.readAllLines(filePath);
            return fileLines;
        }
        catch (IOException e){
            System.err.println("beep beep error");
            return new ArrayList<>();
        }
    }
}