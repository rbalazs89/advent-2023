import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isDigit;

public class Main {

    public static ArrayList<Gear> gearList = new ArrayList<>();

    public static void main(String[] args) {
        List<String> myInput = readFile("src/input2.txt");
        System.out.println(part1(myInput));
        System.out.println(part2(myInput));
    }

    public static int part1(List<String> input) {
        int solution = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                int startDigit = 0;
                int endDigit = 0;
                boolean isValid = false;
                if (isDigit(input.get(i).charAt(j))) {
                    startDigit = j;
                    endDigit = j + 1;
                    if (isDigit(input.get(i).charAt(j + 1))) {
                        endDigit++;
                        if (j + 2 < input.get(i).length()) {
                            if (isDigit(input.get(i).charAt(j + 2))) {
                                endDigit++;
                            }
                        }
                    }
                    for (int k = startDigit; k < endDigit; k++) {
                        // check top
                        if (i > 0) {
                            if (!(isDigit(input.get(i - 1).charAt(k)) || input.get(i - 1).charAt(k) == '.')) {
                                isValid = true;
                            }
                        }

                        //check bottom
                        if (i < input.size() - 1) {
                            if (!(isDigit(input.get(i + 1).charAt(k)) || input.get(i + 1).charAt(k) == '.')) {
                                isValid = true;
                            }
                        }
                    }

                    //check left
                    if (startDigit != 0) {
                        if (!(isDigit(input.get(i).charAt(startDigit - 1)) || input.get(i).charAt(startDigit - 1) == '.')) {
                            isValid = true;
                        }
                    }
                    //check right
                    if (endDigit != input.get(i).length()) {
                        if (!(isDigit(input.get(i).charAt(endDigit)) || input.get(i).charAt(endDigit) == '.')) {
                            isValid = true;
                        }
                    }

                    //check top left
                    if (startDigit != 0 && i > 0) {
                        if (!(isDigit(input.get(i - 1).charAt(startDigit - 1)) || input.get(i - 1).charAt(startDigit - 1) == '.')) {
                            isValid = true;
                        }
                    }

                    //check top right
                    if (endDigit != input.get(i).length() && i > 0) {
                        if (!(isDigit(input.get(i - 1).charAt(endDigit)) || input.get(i - 1).charAt(endDigit) == '.')) {
                            isValid = true;
                        }
                    }

                    //check bottom left
                    if (startDigit != 0 && i < input.get(i).length() - 1) {
                        if (!(isDigit(input.get(i + 1).charAt(startDigit - 1)) || input.get(i + 1).charAt(startDigit - 1) == '.')) {
                            isValid = true;
                        }
                    }

                    //check bottom right
                    if (endDigit != input.get(i).length() && i < input.get(i).length() - 1) {
                        if (!(isDigit(input.get(i + 1).charAt(startDigit + 1)) || input.get(i + 1).charAt(endDigit) == '.')) {
                            isValid = true;
                        }
                    }

                    j = j + endDigit - startDigit;
                }
                if (isValid) {
                    solution = solution + Integer.parseInt(input.get(i).substring(startDigit, endDigit));
                }
            }
        }
        return solution;
    }

    public static int part2(List<String> input) {

        int solution = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                int startDigit;
                int endDigit;

                if (isDigit(input.get(i).charAt(j))) {
                    startDigit = j;
                    endDigit = j + 1;
                    if (isDigit(input.get(i).charAt(j + 1))) {
                        endDigit++;
                        if (j + 2 < input.get(i).length()) {
                            if (isDigit(input.get(i).charAt(j + 2))) {
                                endDigit++;
                            }
                        }
                    }
                    //check top + bottom
                    for (int k = startDigit; k < endDigit; k++) {
                        // check top
                        if (i > 0) {
                            if (input.get(i - 1).charAt(k) == '*') {
                                createNewGear(k, i - 1, Integer.parseInt(input.get(i).substring(startDigit, endDigit)));
                            }
                        }

                        //check bottom
                        if (i < input.size() - 1) {
                            if (input.get(i + 1).charAt(k) == '*') {
                                createNewGear(k, i + 1, Integer.parseInt(input.get(i).substring(startDigit, endDigit)));
                            }
                        }
                    }

                    //check left
                    if (startDigit != 0) {
                        if (!(isDigit(input.get(i).charAt(startDigit - 1)) || input.get(i).charAt(startDigit - 1) == '.')) {
                            createNewGear(startDigit - 1, i, Integer.parseInt(input.get(i).substring(startDigit, endDigit)));
                        }
                    }
                    //check right
                    if (endDigit != input.get(i).length()) {
                        if (input.get(i).charAt(endDigit) == '*') {
                            createNewGear(endDigit, i, Integer.parseInt(input.get(i).substring(startDigit, endDigit)));
                        }
                    }

                    //check top left
                    if (startDigit != 0 && i > 0) {
                        if (input.get(i - 1).charAt(startDigit - 1) == '*') {
                            createNewGear(startDigit - 1, i - 1, Integer.parseInt(input.get(i).substring(startDigit, endDigit)));
                        }
                    }

                    //check top right
                    if (endDigit != input.get(i).length() && i > 0) {
                        if (input.get(i - 1).charAt(endDigit) == '*') {
                            createNewGear(endDigit, i - 1, Integer.parseInt(input.get(i).substring(startDigit, endDigit)));
                        }
                    }

                    //check bottom left
                    if (startDigit != 0 && i < input.get(i).length() - 1) {
                        if (input.get(i + 1).charAt(startDigit - 1) == '*') {
                            createNewGear(startDigit - 1, i + 1, Integer.parseInt(input.get(i).substring(startDigit, endDigit)));
                        }
                    }

                    //check bottom right
                    if (endDigit != input.get(i).length() && i < input.get(i).length() - 1) {
                        if (input.get(i + 1).charAt(endDigit) == '*') {
                            createNewGear(endDigit, i + 1, Integer.parseInt(input.get(i).substring(startDigit, endDigit)));
                        }
                    }

                    j = j + endDigit - startDigit;
                }
            }
        }
        for (Gear gear : gearList) {
            if (gear.engineParts.size() == 2) {
                solution = solution + gear.engineParts.get(0) * gear.engineParts.get(1);
            }
        }
        return solution;
    }

    public static void createNewGear(int x, int y, int enginePartValue) {
        Gear newGear = new Gear(x, y);
        boolean alreadyExist = false;
        for (Gear gear : gearList) {
            if (gear.x == x && gear.y == y) {
                alreadyExist = true;
                newGear = gear;
                break;
            }
        }
        if (!alreadyExist) {
            gearList.add(newGear);
        }
        newGear.engineParts.add(enginePartValue);
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