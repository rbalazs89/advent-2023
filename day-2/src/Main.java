import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isDigit;

public class Main {
    public static void main(String[] args) {
        List<String> myInput = readFile("src/input1.txt");
        System.out.println(part1(myInput));
        List<String> myInput2 = readFile("src/input1.txt");
        System.out.println(part2(myInput2));
    }

    public static int part1(List<String> input){
        int solution = 0;
        int blue = 14;
        int red = 12;
        int green = 13;

        for(int i = 0; i < input.size(); i ++){
            int thisRed = 0;
            int thisBlue = 0;
            int thisGreen = 0;


            while(input.get(i).contains("green")){
                String tempString = input.get(i);
                int indexOfGreen = tempString.indexOf("green");
                if(isDigit(tempString.charAt(indexOfGreen-3))){
                    if(isDigit(tempString.charAt(indexOfGreen-4))){
                        thisGreen = 100;
                        System.out.println("problem");
                    }
                    else {
                        if(thisGreen < Integer.valueOf(tempString.substring(indexOfGreen-3,indexOfGreen-1))){
                            thisGreen = Integer.valueOf(tempString.substring(indexOfGreen-3,indexOfGreen-1));
                        }
                    }
                }
                else {
                    if(thisGreen < Integer.valueOf(tempString.substring(indexOfGreen-2,indexOfGreen-1))){
                        thisGreen = Integer.valueOf(tempString.substring(indexOfGreen-2,indexOfGreen-1));
                    }
                }
                input.set(i, tempString.substring(0,indexOfGreen) + tempString.substring(indexOfGreen+5));
            }

            while(input.get(i).contains("red")){
                String tempString = input.get(i);
                int indexOfRed = tempString.indexOf("red");
                if(isDigit(tempString.charAt(indexOfRed-3))){
                    if(isDigit(tempString.charAt(indexOfRed-4))){
                        thisRed = 100;
                        System.out.println("problem");
                    }
                    else {
                        if(thisRed < Integer.valueOf(tempString.substring(indexOfRed-3,indexOfRed-1))){
                            thisRed = Integer.valueOf(tempString.substring(indexOfRed-3,indexOfRed-1));
                        }
                    }
                }
                else {
                    if(thisRed < Integer.valueOf(tempString.substring(indexOfRed-2,indexOfRed-1))){
                        thisRed = Integer.valueOf(tempString.substring(indexOfRed-2,indexOfRed-1));
                    }
                }
                input.set(i, tempString.substring(0,indexOfRed) + tempString.substring(indexOfRed+3));
            }

            while(input.get(i).contains("blue")){
                String tempString = input.get(i);
                int indexOfBlue = tempString.indexOf("blue");
                if(isDigit(tempString.charAt(indexOfBlue-3))){
                    if(isDigit(tempString.charAt(indexOfBlue-4))){
                        thisBlue = 100;
                        System.out.println("problem");
                    }
                    else {
                        if(thisBlue < Integer.valueOf(tempString.substring(indexOfBlue-3,indexOfBlue-1))){
                            thisBlue = Integer.valueOf(tempString.substring(indexOfBlue-3,indexOfBlue-1));
                        }
                    }
                }
                else {
                    if(thisBlue < Integer.valueOf(tempString.substring(indexOfBlue-2,indexOfBlue-1))){
                        thisBlue = Integer.valueOf(tempString.substring(indexOfBlue-2,indexOfBlue-1));
                    }
                }
                input.set(i, tempString.substring(0,indexOfBlue) + tempString.substring(indexOfBlue+4));
            }
            if(thisRed <= red && thisBlue <= blue && thisGreen <= green){
                solution = solution + i+1;
            }
        }
        return solution;
    }

    public static int part2(List<String> input){
        int solution = 0;

        for(int i = 0; i < input.size(); i ++){

            int thisRed = 0;
            int thisBlue = 0;
            int thisGreen = 0;

            while(input.get(i).contains("green")){
                String tempString = input.get(i);
                int indexOfGreen = tempString.indexOf("green");
                if(isDigit(tempString.charAt(indexOfGreen-3))){
                    if(isDigit(tempString.charAt(indexOfGreen-4))){
                        thisGreen = 100;
                    }
                    else {
                        if(thisGreen < Integer.valueOf(tempString.substring(indexOfGreen-3,indexOfGreen-1))){
                            thisGreen = Integer.valueOf(tempString.substring(indexOfGreen-3,indexOfGreen-1));
                        }
                    }
                }
                else {
                    if(thisGreen < Integer.valueOf(tempString.substring(indexOfGreen-2,indexOfGreen-1))){
                        thisGreen = Integer.valueOf(tempString.substring(indexOfGreen-2,indexOfGreen-1));
                    }
                }
                input.set(i, tempString.substring(0,indexOfGreen) + tempString.substring(indexOfGreen+5));
            }

            while(input.get(i).contains("red")){
                String tempString = input.get(i);
                int indexOfRed = tempString.indexOf("red");
                if(isDigit(tempString.charAt(indexOfRed-3))){
                    if(isDigit(tempString.charAt(indexOfRed-4))){
                        thisRed = 100;
                    }
                    else {
                        if(thisRed < Integer.valueOf(tempString.substring(indexOfRed-3,indexOfRed-1))){
                            thisRed = Integer.valueOf(tempString.substring(indexOfRed-3,indexOfRed-1));
                        }
                    }
                }
                else {
                    if(thisRed < Integer.valueOf(tempString.substring(indexOfRed-2,indexOfRed-1))){
                        thisRed = Integer.valueOf(tempString.substring(indexOfRed-2,indexOfRed-1));
                    }
                }
                input.set(i, tempString.substring(0,indexOfRed) + tempString.substring(indexOfRed+3));
            }

            while(input.get(i).contains("blue")){
                String tempString = input.get(i);
                int indexOfBlue = tempString.indexOf("blue");
                if(isDigit(tempString.charAt(indexOfBlue-3))){
                    if(isDigit(tempString.charAt(indexOfBlue-4))){
                        thisBlue = 100;
                    }
                    else {
                        if(thisBlue < Integer.valueOf(tempString.substring(indexOfBlue-3,indexOfBlue-1))){
                            thisBlue = Integer.valueOf(tempString.substring(indexOfBlue-3,indexOfBlue-1));
                        }
                    }
                }
                else {
                    if(thisBlue < Integer.valueOf(tempString.substring(indexOfBlue-2,indexOfBlue-1))){
                        thisBlue = Integer.valueOf(tempString.substring(indexOfBlue-2,indexOfBlue-1));
                    }
                }
                input.set(i, tempString.substring(0,indexOfBlue) + tempString.substring(indexOfBlue+4));
            }

            solution = solution + thisGreen * thisRed * thisBlue;
        }
        return solution;
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