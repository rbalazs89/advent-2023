import java.util.ArrayList;

public class Block {

    int horizontalSolution;
    int verticalSolution;
    ArrayList<String> content = new ArrayList<>();
    public Block(){

    }

    public Block Reverser() {
        Block reversedBlock = new Block();
        ArrayList<String> reversedArray = new ArrayList<>();

        for (int i = 0; i < this.content.get(0).length(); i++) {
            reversedArray.add("");
        }

        for (int i = 0; i < this.content.size(); i++) {
            for (int j = 0; j < this.content.get(i).length(); j++) {
                reversedArray.set(j, reversedArray.get(j) + this.content.get(i).charAt(j));
            }
        }
        reversedBlock.content = reversedArray;
        return reversedBlock;
    }


    public int CheckReflectionHorizontal(int solution) {
        Block block = new Block();
        block.content = new ArrayList<>(content);

        for (int i = 1; i < block.content.size(); i++) {
            int loops = Math.min(i, block.content.size() - i);
            int tempInt = 0;
            for (int j = 0; j < loops; j++) {
                if (block.content.get(i - j - 1).equals(block.content.get(i + j))) {
                    tempInt++;
                }
            }
            if (tempInt == loops && i != solution) {
                return i;
            }
        }
        return 0;
    }

    public int Horizontal() {
        Block block = new Block();
        block.content = new ArrayList<>(content);

        for (int i = 1; i < block.content.size(); i++) {
            int loops = Math.min(i, block.content.size() - i);
            int tempInt = 0;
            for (int j = 0; j < loops; j++) {
                if (block.content.get(i - j - 1).equals(block.content.get(i + j))) {
                    tempInt++;
                }
            }
            if (tempInt == loops) {
                horizontalSolution = i;
                return i;
            }
        }
        return 0;
    }

    public int CheckReflectionVertical(int solution) {
        Block block = new Block();
        block.content = new ArrayList<>(Reverser().content);

        for (int i = 1; i < block.content.size(); i++) {
            int loops = Math.min(i, block.content.size() - i);
            int tempInt = 0;
            for (int j = 0; j < loops; j++) {
                if (block.content.get(i - j - 1).equals(block.content.get(i + j))) {
                    tempInt++;
                }
            }
            if (tempInt == loops && i != solution) {
                return i;
            }
        }
        return 0;
    }

    public int Vertical() {
        Block block = new Block();
        block.content = new ArrayList<>(Reverser().content);

        for (int i = 1; i < block.content.size(); i++) {
            int loops = Math.min(i, block.content.size() - i);
            int tempInt = 0;
            for (int j = 0; j < loops; j++) {
                if (block.content.get(i - j - 1).equals(block.content.get(i + j))) {
                    tempInt++;
                }
            }
            if (tempInt == loops) {
                verticalSolution = i;
                return i;
            }
        }
        return 0;
    }

    public int CheckSmudgeHorizontal(){
        int solution = this.horizontalSolution;
        for (int i = 0; i < content.size(); i++) {
            for (int j = 0; j < content.get(i).length(); j++) {
                Block block = new Block();
                block.content = new ArrayList<>(content);

                String modifiedLine = block.content.get(i).substring(0, j) +
                        switchString(block.content.get(i).substring(j, j + 1)) +
                        block.content.get(i).substring(j + 1);
                block.content.set(i, modifiedLine);

                if (block.CheckReflectionHorizontal(solution) != 0){
                    return block.CheckReflectionHorizontal(solution);
                }
            }
        }
        return 0;
    }


    public int CheckSmudgeVertical(){
        int solution = this.verticalSolution;
        Block tempBlock = new Block();
        tempBlock.content = new ArrayList<>(content);

        for (int i = 0; i < tempBlock.content.size(); i++) {
            for (int j = 0; j < tempBlock.content.get(i).length(); j++) {
                Block block = new Block();
                block.content = new ArrayList<>(tempBlock.content);

                String modifiedLine = block.content.get(i).substring(0,j) +
                        switchString(block.content.get(i).substring(j, j + 1)) +
                        block.content.get(i).substring(j + 1);
                block.content.set(i, modifiedLine);

                if (block.CheckReflectionVertical(solution) != 0){
                    return block.CheckReflectionVertical(solution);
                }
            }
        }
        return 0;
    }

    public String switchString(String s){
        if(s.equals("#")){
            return ".";
        } else return "#";
    }

}
