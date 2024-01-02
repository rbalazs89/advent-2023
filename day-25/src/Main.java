import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static ArrayList<Wire> allWires = new ArrayList<>();
    static ArrayList<Component> allComponents = new ArrayList<>();
    static ArrayList<Wire> pathsToTake = new ArrayList<>();
    static ArrayList<Component> visitedComponents = new ArrayList<>();
    static ArrayList<Wire> visitedWires = new ArrayList<>();

    public static void main(String[] args) {
        List<String> input = readFile("src/input2.txt");
        System.out.println(part1(input));
    }

    public static int part1(List<String> input){

        processFile(input);
        int graphSize = allComponents.size();
        int variations = allWires.size();

        for (int i = 0; i < variations - 2; i++) {
            for (int j = i + 1; j < variations - 1; j++) {
                for (int k =  j + 1; k < variations; k++) {
                    allWires.remove(k);
                    allWires.remove(j);
                    allWires.remove(i);
                    int currentSize = countConnectedComponents();
                    if ( currentSize != graphSize) {
                        System.out.println(i + " " +  j + " " + k);
                        System.out.println(graphSize);
                        return (graphSize - currentSize) * currentSize;
                    }
                    clearAll();
                    processFile(input);

                }
            }
        }
        return 0;
    }

    public static int countConnectedComponents(){
        firstPath();
        while(pathsToTake.size() > 0){
            toTakeAPath(pathsToTake.get(0));
            pathsToTake.remove(0);
        }
        return visitedComponents.size();
    }
    public static void toTakeAPath(Wire wire){
        ArrayList<Wire> tempWires = new ArrayList<>();
        Component component = null;
        Component connectedComponent1 = wire.connectedComponents[0];
        Component connectedComponent2 = wire.connectedComponents[1];

        if (!visitedComponents.contains(connectedComponent1)) {
            component = connectedComponent1;
        } else if (!visitedComponents.contains(connectedComponent2)) {
            component = connectedComponent2;
        }

        if (component == null) {
            return;
        }

        visitedComponents.add(component);

        for (int i = 0; i < allWires.size(); i++) {
            if(component.name.equals(allWires.get(i).connectedComponents[0].name) ||
                    component.name.equals(allWires.get(i).connectedComponents[1].name)){
                tempWires.add(allWires.get(i));
            }
        }

        for (int i = 0; i < tempWires.size(); i++) {
            if(!visitedWires.contains(tempWires.get(i))){
                visitedWires.add(tempWires.get(i));
                pathsToTake.add(tempWires.get(i));
            }
        }
    }

    public static void firstPath(){
        visitedComponents.add(allComponents.get(0));
        for (int i = 0; i < allWires.size(); i++) {
            if(allComponents.get(0).name.equals(allWires.get(i).connectedComponents[0].name) ||
                    allComponents.get(0).name.equals(allWires.get(i).connectedComponents[1].name)){
                pathsToTake.add(allWires.get(i));
                visitedWires.add(allWires.get(i));
            }
        }
    }

    public static void processFile(List<String> input){
        for (int j = 0; j < input.size(); j++) {
            String[] componentsString = input.get(j).replaceAll(":","").split(" ");
            for (int i = 0; i < componentsString.length; i++) {
                Component component = new Component();
                component.name = componentsString[i];
                boolean alreadyExists = false;
                for (int k = 0; k < allComponents.size(); k++) {
                    if(allComponents.get(k).name.equals(component.name)){
                        alreadyExists = true;
                        break;
                    }
                }
                if(!alreadyExists){
                    allComponents.add(component);
                }
            }
        }

        for (int i = 0; i < input.size(); i++) {
            int firstComponent = 0;
            String[] componentsString = input.get(i).replaceAll(":","").split(" ");

            for (int k = 0; k < allComponents.size(); k++) {
                if(allComponents.get(k).name.equals(componentsString[0])){
                    firstComponent = k;
                    break;
                }
            }

            for (int j = 1; j < componentsString.length; j++) {
                Wire wire = new Wire();
                int secondComponent = 0;
                wire.connectedComponents[0] = allComponents.get(firstComponent);
                for (int k = 0; k < allComponents.size(); k++) {
                    if(allComponents.get(k).name.equals(componentsString[j])){
                        secondComponent = k;
                        break;
                    }
                }
                wire.connectedComponents[1] = allComponents.get(secondComponent);
                allWires.add(wire);
            }
        }
    }

    public static void clearAll(){
        allWires.clear();
        allComponents.clear();
        visitedComponents.clear();
        visitedWires.clear();
        pathsToTake.clear();
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