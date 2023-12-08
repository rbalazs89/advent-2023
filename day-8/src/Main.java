import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> input = readFile("src/input.txt");
        System.out.println(part1(input));
       
    }
    
    public static int part1(List<String> input){
        
        //PROCESS FILE:
        StringBuilder instructions = new StringBuilder();
        ArrayList<Node> nodes = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            if(input.get(i).length() > 0){
                if(!input.get(i).contains("=")){
                    instructions.append(input.get(i));
                }
                if(input.get(i).contains("=")){
                    String tempString = input.get(i);
                    nodes.add(new Node(tempString.substring(0,3), tempString.substring(7,10), tempString.substring(12,15)));
                }
            }
        }

        //TRAVEL THE GRAPH
        String currentNodeName = "AAA";
        Node currentNode = nodes.get(0); // must be overwritten anyway
        int solution = 0;

        for(int test = 0; test < Integer.MAX_VALUE; test ++){
            solution++;
            char currentInstruction = instructions.charAt((solution - 1) % instructions.length()) ;
            for (int j = 0; j < nodes.size(); j++) {
                if(nodes.get(j).name.equals(currentNodeName)){
                    currentNode = nodes.get(j);
                    break;
                }
            }
            if(currentInstruction == 'R'){
                currentNodeName = currentNode.right;
                if(currentNodeName.equals("ZZZ")){
                    break;
                }
            } else {
                currentNodeName = currentNode.left;
                if(currentNodeName.equals("ZZZ")){
                    break;
                }
            }
        }
        return solution;
    }

    public static List<String> readFile(String file) {
        Path filePath = Paths.get(file);
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            System.err.println("beep beep error");
            return new ArrayList<>();
        }
    }
}