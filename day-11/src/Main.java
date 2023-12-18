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
    }

    public static int part1(List<String> input){

        //PROCESS INPUT FILE:
        ArrayList<Galaxy> galaxies = new ArrayList<>();
        int sizeX = input.get(0).length();
        int sizeY = input.size();
        for(int i = 0; i < sizeY; i ++){
            for (int j = 0; j < sizeX; j++) {
                if(input.get(i).charAt(j) == '#'){
                 galaxies.add(new Galaxy(j,i));
                }
            }
        }

        //COSMIC EXPANSION:
        //Vertical expansion:
        int yExpansion = 0;
        for(int i = 0; i < sizeY; i ++){

            int tempCounter = 0;
            for (int j = 0; j < sizeX; j++) {
                if(input.get(i).charAt(j) != '#'){
                    tempCounter++;
                }
            }

            if (tempCounter == sizeX){

                yExpansion++;
                for (int k = 0; k < galaxies.size(); k++) {
                    if(galaxies.get(k).y >= i + yExpansion){
                        galaxies.get(k).y ++;
                    }
                }
            }
        }

        //Horizontal expansion:
        int xExpansion = 0;
        for(int i = 0; i < sizeX; i ++){

            int tempCounter = 0;
            for (int j = 0; j < sizeY; j++) {
                if(input.get(j).charAt(i) != '#'){
                    tempCounter++;
                }
            }

            if (tempCounter == sizeY){

                xExpansion++;
                for (int k = 0; k < galaxies.size(); k++) {
                    if(galaxies.get(k).x >= i + xExpansion){
                        galaxies.get(k).x ++;
                    }
                }
            }
        }

        int result = 0;
        //CALCULATING DISTANCES
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = 0; j < galaxies.size(); j++) {
                result += Math.abs(galaxies.get(i).x - galaxies.get(j).x) + Math.abs(galaxies.get(i).y - galaxies.get(j).y);
            }
        }

        return result/2;
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