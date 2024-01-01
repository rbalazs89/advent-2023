import java.util.HashMap;

public class Conjunction extends Modules {
    HashMap<Modules, Boolean> memory = new HashMap<>();
    public Conjunction(){
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("&");
        string.append(super.name);
        string.append(" -> ");
        for (int i = 0; i < sendingTo.size(); i++) {
            string.append(sendingTo.get(i).name).append(", ");
        }
        //string = string.substring(0, string.length() - 2);
        //string += " (";
        //for (Map.Entry<Modules, Boolean> entry : memory.entrySet()) {
        //    string += entry.getKey().name + ", ";
        //}
        string = new StringBuilder(string.substring(0, string.length() - 2));
        //string += ")";
        return string.toString();
    }
}
