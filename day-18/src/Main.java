import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static ArrayList<Command> instructions = new ArrayList<>();
    static ArrayList<Coordinate> coordinates = new ArrayList<>();

    public static void main(String[] args) {
        List<String> input = readFile("src/input2.txt");
        processFile(input);
        System.out.println(part1());
    }

    public static int part1(){

        //calculating path and saving coordinates
        int x = 0;
        int y = 0;

        Coordinate coordinate = new Coordinate(0,0);
        coordinates.add(coordinate);
        for (int i = 0; i < instructions.size(); i++) {
            if(instructions.get(i).direction.equals("U")){
                y -= instructions.get(i).number;
            } else if(instructions.get(i).direction.equals("R")){
                x += instructions.get(i).number;
            } else if(instructions.get(i).direction.equals("D")){
                y += instructions.get(i).number;
            } else if(instructions.get(i).direction.equals("L")){
                x -= instructions.get(i).number;
            }
            Coordinate tempCoordinate = new Coordinate(x,y);
            coordinates.add(tempCoordinate);
        }

        //calculating area based on coordinates:
        int area = 0;
        for (int i = 0; i < coordinates.size() - 1; i++) {
            area = area + (coordinates.get(i).x - coordinates.get(i + 1).x) * (coordinates.get(i).y + coordinates.get(i + 1).y);
        }
        area = area / 2;

        //calculating fence area:
        int fenceArea = 0;
        for (int i = 0; i < instructions.size(); i++) {
            fenceArea += instructions.get(i).number;
        }

        //adjustment:
        area = area - ((fenceArea - 4)/2) + fenceArea - 1;

        return area;
    }

    public static void processFile(List<String> input){
        for (int i = 0; i < input.size(); i++) {
            String[] tempString = input.get(i).split(" ");
            Command command = new Command(tempString[0], Integer.parseInt(tempString[1]), tempString[2].substring(1,tempString[2].length()-1));
            instructions.add(command);
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