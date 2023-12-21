import java.util.ArrayList;

public class Workflow {

    String name;
    ArrayList<Rule> rules = new ArrayList<>();

    public Workflow(String name){
        this.name = name;
    }
}
