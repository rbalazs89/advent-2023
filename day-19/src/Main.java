import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static ArrayList<Part> parts = new ArrayList<>();
    static ArrayList<Part> acceptedParts = new ArrayList<>();
    static ArrayList<Workflow> workflows = new ArrayList<>();

    public static void main(String[] args) {
        List<String> input = readFile("src/input2.txt");
        processFile(input);
        System.out.println(part1());
    }

    public static long part1(){

        for (int k = 0; k < parts.size(); k++) {
            Part part = parts.get(k);

            workflowForOnePart:
            while(!part.isAccepted || !part.isRejected) {;

                Workflow workflow = getWorkflowByName(part.currentWorkFlowName);
                if (workflow == null){
                    break workflowForOnePart; // make sure to break if there is
                }

                oneWorkFlow:
                for (int i = 0; i < workflow.rules.size(); i++) {
                    Rule currentRule = workflow.rules.get(i);
                    if(currentRule.ruleType == 2){
                        if(currentRule.sendTo.equals("A")){
                            part.isAccepted = true;
                            part.currentWorkFlowName = "";
                            acceptedParts.add(part);
                            break workflowForOnePart; // needs to break completely outside of the part
                        } if(currentRule.sendTo.equals("R")){
                            part.isRejected = true;
                            part.currentWorkFlowName = "";
                            break workflowForOnePart; // needs to break completely outside of the part
                        } else {
                            part.currentWorkFlowName = currentRule.sendTo;
                            break oneWorkFlow; // needs to break out of current workflow only
                        }
                    }
                    if(currentRule.ruleType == 1){
                        if(currentRule.subtype == "x"){
                            if(part.x < currentRule.value){
                                part.currentWorkFlowName = currentRule.sendTo;
                                break oneWorkFlow; // needs to break out of current workflow only
                            }
                        }
                        if(currentRule.subtype == "m"){
                            if(part.m < currentRule.value){
                                part.currentWorkFlowName = currentRule.sendTo;
                                break oneWorkFlow; // needs to break out of current workflow only
                            }
                        }
                        if(currentRule.subtype == "a"){
                            if(part.a < currentRule.value){
                                part.currentWorkFlowName = currentRule.sendTo;
                                break oneWorkFlow; // needs to break out of current workflow only
                            }
                        }
                        if(currentRule.subtype == "s"){
                            if(part.s < currentRule.value){
                                part.currentWorkFlowName = currentRule.sendTo;
                                break oneWorkFlow; // needs to break out of current workflow only
                            }
                        }
                    }

                    if(currentRule.ruleType == 0){
                        if(currentRule.subtype == "x"){
                            if(part.x > currentRule.value){
                                part.currentWorkFlowName = currentRule.sendTo;
                                break oneWorkFlow; // needs to break out of current workflow only
                            }
                        }
                        if(currentRule.subtype == "m"){
                            if(part.m > currentRule.value){
                                part.currentWorkFlowName = currentRule.sendTo;
                                break oneWorkFlow; // needs to break out of current workflow only
                            }
                        }
                        if(currentRule.subtype == "a"){
                            if(part.a > currentRule.value){
                                part.currentWorkFlowName = currentRule.sendTo;
                                break oneWorkFlow; // needs to break out of current workflow only
                            }
                        }
                        if(currentRule.subtype == "s"){
                            if(part.s > currentRule.value){
                                part.currentWorkFlowName = currentRule.sendTo;
                                break oneWorkFlow; // needs to break out of current workflow only
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < parts.size(); i++) {
            if(parts.get(i).currentWorkFlowName.equals("A")){
                parts.get(i).isAccepted = true;
                acceptedParts.add(parts.get(i));
            }
        }

        long result = 0;
        for (int i = 0; i < acceptedParts.size(); i++) {
            result = result + acceptedParts.get(i).x + acceptedParts.get(i).m + acceptedParts.get(i).a + acceptedParts.get(i).s;
        }

        return result;

    }



    public static void processFile(List<String> input) {

        List<String> partInput = new ArrayList<>();
        List<String> instructions = new ArrayList<>();

        // separate parts and instructions
        int inputRowCutoff = 0;
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).length() == 0) {
                inputRowCutoff = i;
                break;
            }
            instructions.add(input.get(i));
        }
        for (int i = inputRowCutoff + 1; i < input.size(); i++) {
            partInput.add(input.get(i));
        }

        // create parts:
        Pattern pattern = Pattern.compile("\\{x=(\\d+),m=(\\d+),a=(\\d+),s=(\\d+)\\}");
        for (String str : partInput) {
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                int x = Integer.parseInt(matcher.group(1));
                int m = Integer.parseInt(matcher.group(2));
                int a = Integer.parseInt(matcher.group(3));
                int s = Integer.parseInt(matcher.group(4));
                Part part = new Part(x, m, a, s);
                part.currentWorkFlowName = "in";
                parts.add(part);
            }
        }

        //create arrays for rule and workflow input:
        Pattern pattern2 = Pattern.compile("\\{([^}]+)\\}");

        for (int m = 0; m < instructions.size() ; m++) {
            String input2 = instructions.get(m);
            Matcher matcher2 = pattern2.matcher(input2);

            String outsideBrackets = "";
            List<String[]> insideArrays = new ArrayList<>();

            if (matcher2.find()) {
                outsideBrackets = input2.substring(0, matcher2.start());
                String insideBrackets = matcher2.group(1);

                String[] elements = insideBrackets.split(",");

                for (String element : elements) {
                    String[] myElement;

                    if (element.contains("<") || element.contains(">")) {
                        myElement = new String[4];
                        int colonIndex = element.indexOf(":");
                        int firstSignIndex = element.indexOf("<");
                        if (firstSignIndex == -1) {
                            firstSignIndex = element.indexOf(">");
                        }

                        myElement[0] = element.substring(0, firstSignIndex).trim();
                        myElement[1] = element.substring(firstSignIndex, firstSignIndex + 1).trim();
                        myElement[2] = element.substring(firstSignIndex + 1, colonIndex).trim();
                        myElement[3] = element.substring(colonIndex + 1).trim();
                    } else {
                        myElement = new String[1];
                        myElement[0] = element.trim();
                    }

                    insideArrays.add(myElement);
                }
            }

            //create workflow input:
            Workflow workflow = new Workflow(outsideBrackets);

            for (int i = 0; i < insideArrays.size(); i++) {
                Rule rule = new Rule();

                if(insideArrays.get(i).length == 1){
                    rule.ruleType = 2;
                    rule.sendTo = insideArrays.get(i)[0];
                }
                else{
                    rule.sendTo = insideArrays.get(i)[3];
                    rule.value = Integer.parseInt(insideArrays.get(i)[2]);
                    switch (insideArrays.get(i)[0]) {
                        case "x" -> rule.subtype = "x";
                        case "m" -> rule.subtype = "m";
                        case "a" -> rule.subtype = "a";
                        case "s" -> rule.subtype = "s";
                    }
                    if(insideArrays.get(i)[1].equals("<")){
                        rule.ruleType = 1;
                    } else if (insideArrays.get(i)[1].equals(">")){
                        rule.ruleType = 0;
                    }
                }
                workflow.rules.add(rule);
            }
            workflows.add(workflow);
        }
    }
    public static Workflow getWorkflowByName(String s){
        Workflow workflow;
        for (int i = 0; i < workflows.size(); i++) {
            if(workflows.get(i).name.equals(s)){
                return workflows.get(i);
            }
        }
        return null;
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