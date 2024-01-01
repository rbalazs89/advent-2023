import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    static ArrayList<Modules> modules = new ArrayList<>();
    static ArrayList<Modules> broadcasterConnects = new ArrayList<>();
    static ArrayList<Command> commands = new ArrayList<>();
    static int lowPulse = 0;
    static int highPulse = 0;

    // PART 2 global variables:
    static int buttonPresses = 0;
    static int[] solutions = new int[4];
    static boolean module0CounterFound = false;
    static boolean module1CounterFound = false;
    static boolean module2CounterFound = false;
    static boolean module3CounterFound = false;

    public static void main(String[] args) {
        List<String> input = readFile("src/input2.txt");
        processFile(input);

        // PART 1:
        for (int i = 0; i < 1000; i++) {
            lowPulse++;
            part1();
        }
        System.out.println(lowPulse * highPulse);

        clearAll();
        processFile(input);

        // PART 2:
        for (int i = 0; i < 100000; i++) {
            buttonPresses ++;
            part2();
        }

        BigInteger num1 = new BigInteger(String.valueOf(solutions[0]));
        BigInteger num2 = new BigInteger(String.valueOf(solutions[1]));
        BigInteger num3 = new BigInteger(String.valueOf(solutions[2]));
        BigInteger num4 = new BigInteger(String.valueOf(solutions[3]));

        BigInteger lcm = num1.multiply(num2).multiply(num3).multiply(num4)
                .divide(num1.gcd(num2).gcd(num3).gcd(num4));

        System.out.println(lcm);
    }

    public static void processFile(List<String> input){

        //Create special type module
        Modules rx = new Modules();
        rx.name = "rx";
        modules.add(rx);

        //Create modules
        for (int i = 0; i < input.size(); i++) {
            if(input.get(i).charAt(0) == '%'){
                FlipFlop flipFlop = new FlipFlop();
                flipFlop.name = input.get(i).substring(1, input.get(i).indexOf(' '));
                modules.add(flipFlop);
            }
            if (input.get(i).charAt(0) == '&'){
                Conjunction conjunction = new Conjunction();
                conjunction.name = input.get(i).substring(1, input.get(i).indexOf(' '));
                modules.add(conjunction);
            }
        }

        //Broadcaster:
        for (int i = 0; i < input.size(); i++) {
            if(input.get(i).charAt(0) == 'b'){
                String[] string1 = input.get(i).substring(input.get(i).indexOf('>') + 1).replaceAll("\\s", "").split(",");

                outerLoop:
                for (int k = 0; k < string1.length; k++) {
                    for (int j = 0; j < modules.size(); j++) {
                        if(string1[k].equals(modules.get(j).name)){
                            broadcasterConnects.add(modules.get(j));
                            continue outerLoop;
                        }
                    }
                }
                break;
            }
        }

        //Add sendTo list to modules
        outerLoop2:
        for (int i = 0; i < modules.size(); i++) {
            for (int j = 0; j < input.size(); j++) {
                if(modules.get(i).name.equals(input.get(j).substring(1, input.get(j).indexOf(' ')))){
                    String[] string1 = input.get(j).substring(input.get(j).indexOf('>') + 1).replaceAll("\\s", "").split(",");
                    outerLoop3:
                    for (int k = 0; k < string1.length; k++) {
                        for (int l = 0; l < modules.size(); l++) {
                            if(string1[k].equals(modules.get(l).name)){
                                modules.get(i).sendingTo.add(modules.get(l));
                                continue outerLoop3;
                            }
                        }
                    }
                    continue outerLoop2;
                }
            }
        }

        //Add memory to conjunctions
        for (int i = 0; i < modules.size(); i++) {
            if(modules.get(i) instanceof Conjunction){
                for (int j = 0; j < modules.size(); j++) {
                    if(modules.get(j).sendingTo.contains(modules.get(i))){
                        ((Conjunction) modules.get(i)).memory.put(modules.get(j), false);
                    }
                }
            }
        }
    }

    public static void part1(){
        // broadcaster:
        for (int i = 0; i < broadcasterConnects.size(); i++) {
            Command command = new Command();
            command.sendingTo = broadcasterConnects.get(i);
            command.isHighPulse = false;
            commands.add(command);
        }

        //all other commands:
        while(commands.size() > 0){
            executeCommand(commands.get(0));
            if(commands.get(0).isHighPulse){
                highPulse++;
            } else {
                lowPulse ++;
            }
            commands.remove(commands.get(0));
        }
    }
    public static void part2(){
        // broadcaster commands:
        for (int i = 0; i < broadcasterConnects.size(); i++) {
            Command command = new Command();
            command.sendingTo = broadcasterConnects.get(i);
            command.isHighPulse = false;
            commands.add(command);
        }

        // find conjunction module connected
        Modules RXConnectedConjunction = null;

        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).sendingTo.size() > 0) {
                if (modules.get(i).sendingTo.get(0).name.equals("rx")) {
                    RXConnectedConjunction = modules.get(i);
                    break;
                }
            }
        }

        ArrayList<Modules> RXConnectedModules = new ArrayList<>();

        //Find memory of conjunction:
        if (RXConnectedConjunction != null) {
            for (Map.Entry<Modules, Boolean> entry : ((Conjunction) RXConnectedConjunction).memory.entrySet()) {
                RXConnectedModules.add(entry.getKey());
            }
        }

        //all other commands:
        while(commands.size() > 0){
            if(commands.get(0).sendingFrom != null) {
                if (!module0CounterFound && commands.get(0).sendingFrom.name.equals(RXConnectedModules.get(0).name) && commands.get(0).isHighPulse) {
                    solutions[0] = buttonPresses;
                    System.out.println(buttonPresses);
                    module0CounterFound = true;
                }
                if (!module1CounterFound && commands.get(0).sendingFrom.name.equals(RXConnectedModules.get(1).name) && commands.get(0).isHighPulse) {
                    solutions[1] = buttonPresses;
                    System.out.println(buttonPresses);
                    module1CounterFound = true;
                }
                if (!module2CounterFound && commands.get(0).sendingFrom.name.equals(RXConnectedModules.get(2).name) && commands.get(0).isHighPulse) {
                    solutions[2] = buttonPresses;
                    System.out.println(buttonPresses);
                    module2CounterFound = true;
                }
                if (!module3CounterFound && commands.get(0).sendingFrom.name.equals(RXConnectedModules.get(3).name) && commands.get(0).isHighPulse) {
                    solutions[3] = buttonPresses;
                    System.out.println(buttonPresses);
                    module3CounterFound = true;
                }
            }

            executeCommand(commands.get(0));
            if(commands.get(0).isHighPulse){
                highPulse++;
            } else {
                lowPulse ++;
            }
            commands.remove(commands.get(0));
        }
    }

    public static void executeCommand(Command command){
        //FlipFlop:
        if(command.sendingTo instanceof FlipFlop){
            if(!command.isHighPulse){
                if(((FlipFlop) command.sendingTo).isOn){
                    for (int i = 0; i < command.sendingTo.sendingTo.size(); i++) {
                        Command command2 = new Command();
                        command2.sendingTo = command.sendingTo.sendingTo.get(i);
                        command2.isHighPulse = false;
                        command2.sendingFrom = command.sendingTo;
                        commands.add(command2);
                    }
                } else {
                    for (int i = 0; i < command.sendingTo.sendingTo.size(); i++) {
                        Command command2 = new Command();
                        command2.sendingTo = command.sendingTo.sendingTo.get(i);
                        command2.isHighPulse = true;
                        command2.sendingFrom = command.sendingTo;
                        commands.add(command2);
                    }
                }
                ((FlipFlop) command.sendingTo).isOn = !((FlipFlop) command.sendingTo).isOn;
            }
        }
        //Conjunction:
        if(command.sendingTo instanceof Conjunction){
            ((Conjunction) command.sendingTo).memory.put(command.sendingFrom,command.isHighPulse);
            int all = 0;
            int isHigh = 0;
            for (Map.Entry<Modules, Boolean> entry : ((Conjunction) command.sendingTo).memory.entrySet()) {
                if(entry.getValue()){
                    isHigh ++;
                }
                all++;
            }

            for (int i = 0; i < command.sendingTo.sendingTo.size(); i++) {
                Command command3 = new Command();
                command3.isHighPulse = all != isHigh;
                command3.sendingTo = command.sendingTo.sendingTo.get(i);
                command3.sendingFrom = command.sendingTo;
                commands.add(command3);
            }
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

    public static void clearAll(){
        modules.clear();
        broadcasterConnects.clear();
        commands.clear();
        lowPulse = 0;
        highPulse = 0;
    }
}