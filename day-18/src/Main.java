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
        System.out.println(part2());
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

    public static Long part2(){

        //calculating path and saving coordinates
        int x = 0;
        int y = 0;

        //everything same as part1 but need to fix instructions
        for (int i = 0; i < instructions.size(); i++) {
            instructions.get(i).number = Integer.parseInt(instructions.get(i).hashcode.substring(0,instructions.get(i).hashcode.length()-1),16);
            if(instructions.get(i).hashcode.substring(instructions.get(i).hashcode.length() - 1).equals("0")){
                instructions.get(i).direction = "R";
            }
            if(instructions.get(i).hashcode.substring(instructions.get(i).hashcode.length() - 1).equals("1")){
                instructions.get(i).direction = "D";
            }
            if(instructions.get(i).hashcode.substring(instructions.get(i).hashcode.length() - 1).equals("2")){
                instructions.get(i).direction = "L";
            }
            if(instructions.get(i).hashcode.substring(instructions.get(i).hashcode.length() - 1).equals("3")){
                instructions.get(i).direction = "U";
            }
        }

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
        long area = 0L;
        for (int i = 0; i < coordinates.size() - 1; i++) {
            area = area +  ((long)coordinates.get(i).x - (long)coordinates.get(i + 1).x) * ((long)coordinates.get(i).y + (long)coordinates.get(i + 1).y);
        }
        area = area / 2;

        //calculating fence area:
        long fenceArea = 0;
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
            Command command = new Command(tempString[0], Integer.parseInt(tempString[1]), tempString[2].substring(2,tempString[2].length()-1));
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