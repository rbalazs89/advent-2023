import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        List<String> input = readFile("src/input2.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static int part2(List<String> input){

        ArrayList<Command> commands = new ArrayList<>();
        ArrayList<ArrayList<Lens>> boxes = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            ArrayList<Lens> box = new ArrayList<>();
            boxes.add(box);
        }

        //PROCESS FILE:
        String[] items = input.get(0).split(",\\s*");
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(items));

        Pattern pattern = Pattern.compile("^([A-Za-z]+)([-=]?)(\\d*)$");

        for (int i = 0; i < arrayList.size(); i++) {
            Matcher matcher = pattern.matcher(arrayList.get(i));
            String[] result = new String[3];
            if (matcher.matches()) {
                result[0] = matcher.group(1);
                result[1] = matcher.group(2);
                result[2] = matcher.group(3);
            }
            int type = 0;
            if (result[1].equals("=")){
                type = 1;
            }
            Command command = new Command(result[0], hashing(result[0]), type);
            if(type == 1){
                command.number = Integer.valueOf(result[2]);
            }
            commands.add(command);
        }

        //EXECUTE THE COMMANDS:
        for (int i = 0; i < commands.size(); i++) {
            // type 1:
            if (commands.get(i).type == 1){
                Lens lens = new Lens();
                lens.boxNumber = commands.get(i).boxNumber;
                lens.label = commands.get(i).label;
                lens.number = commands.get(i).number;

                boolean contains = false;
                int position = -1;
                for (int j = 0; j < boxes.get(lens.boxNumber).size(); j++) {
                    if(boxes.get(lens.boxNumber).get(j).label.equals(lens.label)){
                        contains = true;
                        position = j;
                    }
                }
                if(contains){
                    boxes.get(lens.boxNumber).get(position).number = lens.number;
                }
                if(!contains){
                    boxes.get(lens.boxNumber).add(lens);
                }
            }

            //type 0:
            if(commands.get(i).type == 0){
                boolean contains = false;
                int position = -1;
                for (int j = 0; j < boxes.get(commands.get(i).boxNumber).size(); j++) {
                    if(boxes.get(commands.get(i).boxNumber).get(j).label.equals(commands.get(i).label)){
                        contains = true;
                        position = j;
                    }
                }
                if(contains){
                    boxes.get(commands.get(i).boxNumber).remove(position);
                }
            }
        }

        //CALCULATE FOCUSING POWER
        int solution = 0;
        for (int i = 0; i < boxes.size(); i++) {
            for (int j = 0; j < boxes.get(i).size(); j++) {
                solution = solution + boxes.get(i).get(j).number * (i+1) * (j+1);
            }
        }

        return solution;
    }

    public static int part1(List<String> input){

        String[] items = input.get(0).split(",\\s*");
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(items));

        ArrayList<Integer> solutions = new ArrayList<>();
        for (int j = 0; j < arrayList.size(); j++) {
            int value = 0;
            for (int i = 0; i < arrayList.get(j).length(); i++) {
                value = function(value, arrayList.get(j).substring(i,i+1));
            }
            solutions.add(value);
        }
        int result = 0;
        for (int i = 0; i < solutions.size(); i++) {
            result += solutions.get(i);
        }
        return result;
    }

    public static int hashing(String s){
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            result = function(result, s.substring(i,i+1));
        }
        return result;
    }

    public static int function(int number, String s){
        number = number + (int)(s.charAt(0));
        number = number * 17;
        number = number % 256;
        return number;
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