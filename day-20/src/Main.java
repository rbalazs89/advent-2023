import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static ArrayList<Conjunction> conjunctions = new ArrayList<>();


    public static void main(String[] args) {
        Conjunction conjunction = new Conjunction();
        conjunction.name = "testing";
        conjunctions.add(conjunction);

        for (int i = 0; i < conjunctions.size(); i++) {
            if(conjunctions.get(i).name == "testing"){
                conjunctions.get(i).age++;
            }
        }



        List<String> input = readFile("src/input.txt");
    }

    public static void processFile(){

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