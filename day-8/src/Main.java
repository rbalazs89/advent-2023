import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> input = readFile("src/input2.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static int part1(List<String> input) {

        //PROCESS FILE:
        StringBuilder instructions = new StringBuilder();
        ArrayList<Node> nodes = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).length() > 0) {
                if (!input.get(i).contains("=")) {
                    instructions.append(input.get(i));
                }
                if (input.get(i).contains("=")) {
                    String tempString = input.get(i);
                    nodes.add(new Node(tempString.substring(0, 3), tempString.substring(7, 10), tempString.substring(12, 15)));
                }
            }
        }

        //TRAVEL THE GRAPH
        String currentNodeName = "AAA";
        Node currentNode = nodes.get(0); // must be overwritten anyway
        int solution = 0;

        for (int test = 0; test < Integer.MAX_VALUE; test++) {
            solution++;
            char currentInstruction = instructions.charAt((solution - 1) % instructions.length());
            for (int j = 0; j < nodes.size(); j++) {
                if (nodes.get(j).name.equals(currentNodeName)) {
                    currentNode = nodes.get(j);
                    break;
                }
            }
            if (currentInstruction == 'R') {
                currentNodeName = currentNode.right;
                if (currentNodeName.equals("ZZZ")) {
                    break;
                }
            } else {
                currentNodeName = currentNode.left;
                if (currentNodeName.equals("ZZZ")) {
                    break;
                }
            }
        }
        return solution;
    }

    public static BigInteger part2(List<String> input) {

        //PROCESS FILE:
        StringBuilder instructions = new StringBuilder();
        ArrayList<Node> nodes = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).length() > 0) {
                if (!input.get(i).contains("=")) {
                    instructions.append(input.get(i));
                }
                if (input.get(i).contains("=")) {
                    String tempString = input.get(i);
                    nodes.add(new Node(tempString.substring(0, 3), tempString.substring(7, 10), tempString.substring(12, 15)));
                }
            }
        }

        ArrayList<String> currentNodeNames = new ArrayList<>();
        ArrayList<Node> currentNodes = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).name.endsWith("A")) {
                currentNodes.add(nodes.get(i));
                currentNodeNames.add(nodes.get(i).name);
            }
        }

        //TRAVEL THE GRAPH
        int solution = 0;
        int arrived = currentNodes.size();
        List<Integer> solutions = new ArrayList<>();

        for (int test = 0; test < Integer.MAX_VALUE; test++) {
            solution++;
            char currentInstruction = instructions.charAt((solution - 1) % instructions.length());

            for (int i = 0; i < currentNodes.size(); i++) {
                for (int j = 0; j < nodes.size(); j++) {
                    if (nodes.get(j).name.equals(currentNodeNames.get(i))) {
                        currentNodes.set(i, nodes.get(j));
                        break;
                    }
                }
            }
            for (int i = 0; i < currentNodes.size(); i++) {
                if (currentInstruction == 'R') {
                    currentNodeNames.set(i, currentNodes.get(i).right);
                } else {
                    currentNodeNames.set(i, currentNodes.get(i).left);
                }
            }
            for (int i = 0; i < currentNodeNames.size(); i++) {
                if (currentNodeNames.get(i).endsWith("Z")) {
                    solutions.add(solution);
                }
            }
            if (solutions.size() == arrived) {
                break;
            }
        }
        //lcm solution works because each starting point only ever arrives to one ending point
        //all paths seem to be separate

        // code line above wouldn't work, if solutions were too far apart from eachother
        // ie: one starting point meets ending point twice, before some other circle finishes

        BigInteger lcm = BigInteger.valueOf(solutions.get(0));
        for (Integer integer : solutions) {
            lcm = lcm.multiply(BigInteger.valueOf(integer).divide(lcm.gcd(BigInteger.valueOf(integer))));
        }
        return lcm;
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