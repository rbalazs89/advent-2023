public class Command {
    String label;
    int number;
    int boxNumber;
    //0 for minus sign
    //1 for equal sign
    int type;
    public Command(String label, int boxNumber, int type){
        this.label = label;
        this.boxNumber = boxNumber;
        this.type = type;
    }
}
